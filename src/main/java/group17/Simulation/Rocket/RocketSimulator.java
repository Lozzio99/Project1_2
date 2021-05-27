package group17.Simulation.Rocket;

import group17.Interfaces.RocketInterface;
import group17.Interfaces.StateInterface;
import group17.Interfaces.Vector3dInterface;
import group17.Math.Lib.Vector3D;
import group17.Simulation.System.Bodies.CelestialBody;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static group17.Main.simulation;
import static group17.Utils.Config.STEP_SIZE;
import static java.lang.Double.NaN;

/**
 * The type Rocket simulator.
 */
public class RocketSimulator extends CelestialBody implements RocketInterface {


// Variables
    double startFuel = 2e4;

    double fuelMass;

    double totalMass;

    double exhaustVelocity = 2e4;

    double maxThrust = 3e7;

    Vector3dInterface localAcceleration;


    /**
     * Instantiates a new Rocket simulator.
     */
    public RocketSimulator() {
        this.localAcceleration = new Vector3D();
    }

    /**
     * Add acceleration.
     *
     * @param toAdd the to add
     */
    public void addAcceleration(Vector3dInterface toAdd) {
        this.localAcceleration = this.localAcceleration.add(toAdd);
    }

    /**
     * Gets local acceleration.
     *
     * @return the local acceleration
     */
    public Vector3dInterface getLocalAcceleration() {
        return localAcceleration;
    }

    /**
     * Sets local acceleration.
     *
     * @param localAcceleration the local acceleration
     */
    public void setLocalAcceleration(Vector3dInterface localAcceleration) {
        this.localAcceleration = localAcceleration;
    }

    /**
     * Evaluate loss double.
     *
     * source : https://www.narom.no/undervisningsressurser/sarepta/rocket-theory/rocket-engines/the-rocket-equation/
     *
     *M(t)=M_(start )−m ̇  ;m ̇=  Δm/Δt
     *M(t)∗a(t)= m ̇∗V_e=F_max;
     *a(t)=  F_max/(Mstart−m ̇∗t);
     *∫a(t)dt=v(t); V_e∗ln(Mstart/M(t) ;
     *Δm=M_start∗(1 − e^(−Δv/V_e ));
     *Δm - Propellant Consumed for One Step
     * @param desiredVelocity the desired velocity
     * @param actualVelocity  the actual velocity
     * @return Δm, propellant consumed
     */
    public double evaluateLoss(Vector3dInterface desiredVelocity, Vector3dInterface actualVelocity) {
        double deltaV = desiredVelocity.sub(actualVelocity).norm();

        double propellantConsumed = (fuelMass * deltaV) / (this.exhaustVelocity + deltaV);

        if (exhaustVelocity * (propellantConsumed / STEP_SIZE) > maxThrust) {
            System.out.println("Max Thrust exceeded!!!");
        }


        simulation.getSystem().systemState().getRateOfChange().getVelocities().set(11, desiredVelocity);
        updateMass(propellantConsumed);
        return propellantConsumed;
    }


    /**
     * Update mass.
     *
     * @param propellantConsumed the propellant consumed
     */
    public void updateMass(double propellantConsumed) {
        if (this.getFuelMass() - propellantConsumed >= 0) {
            this.setMASS(this.totalMass - propellantConsumed);
            this.setFuelMass(this.fuelMass - propellantConsumed);
        } else {
            System.out.println("Out of fuel!");
        }
    }

    @Override
    public double getFuelMass() {
        return this.fuelMass;
    }

    @Override
    public void setFuelMass(double m) {
        this.fuelMass = m;
    }

    @Override
    public String toString() {
        return "ROCKET";
    }

    /**
     * Info string.
     *
     * @return the string
     */
    public String info() {
        return "Dry Mass: " + this.getMASS() + "\n" +
                "Fuel Mass: " + this.fuelMass + "\n";
    }

    @Override
    public void update() {
        if (!this.isCollided()) {
            simulation.getSystem().systemState().getRateOfChange().getVelocities().set(11, localAcceleration);
        }
    }

    @Override
    public void initProperties() {
        this.setMASS(7.8e4);
        this.setRADIUS(1e2);
        this.setColour(Color.GREEN);
        this.setVectorLocation(new Vector3D(-1.471922101663588e+12, -2.860995816266412e+10, 8.278183193596080e+06)); //earth
        this.getVectorLocation().add(new Vector3D(6.372e6, 0, 0));
        this.setVectorVelocity(new Vector3D(5.427193405797901e+03, -2.931056622265021e+04, 6.575428158157592e-01));
        this.localAcceleration = new Vector3D();
        this.fuelMass = this.startFuel;
        this.totalMass = this.startFuel + this.getMASS();
        this.setMASS(this.totalMass);
    }

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts) {
        assert (simulation.getSystem() != null);
        StateInterface[] seq = simulation.getUpdater().getSolver().solve(
                simulation.getUpdater().getSolver().getFunction(),
                simulation.getSystem().systemState(),
                ts);

        return getSequence(seq);
    }

    @NotNull
    private Vector3dInterface[] getSequence(StateInterface[] seq) {
        Vector3dInterface[] trajectory = new Vector3dInterface[seq.length];
        for (int i = 0; i < trajectory.length; i++) {
            if (seq[i].getPositions().size() < 11)        //may have collided
                trajectory[i] = new Vector3D(NaN, NaN, NaN);
            trajectory[i] = seq[i].getPositions().get(11);
        }
        return trajectory;
    }

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) {
        assert (simulation.getSystem() != null);
        StateInterface[] seq = simulation.getUpdater().getSolver().solve(
                simulation.getUpdater().getSolver().getFunction(),
                simulation.getSystem().systemState(),
                tf, h);

        return getSequence(seq);
    }
}
