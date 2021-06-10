package Module.System.Module;

import Module.Math.ADT.Vector3D;
import Module.Math.ADT.Vector3dInterface;
import Module.System.Bodies.CelestialBody;
import Module.System.State.StateInterface;

import java.awt.*;

import static Module.Config.STEP_SIZE;
import static Module.Main.simulation;

/**
 * The type Rocket simulator.
 */
public class ModuleSimulator extends CelestialBody implements ModuleSimulatorInterface {



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
    public ModuleSimulator() {
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
    @Override
    public double evaluateLoss(Vector3dInterface desiredVelocity, Vector3dInterface actualVelocity) {
        double deltaV = desiredVelocity.sub(actualVelocity).norm();

        double propellantConsumed = (fuelMass * deltaV) / (this.exhaustVelocity + deltaV);

        if (exhaustVelocity * (propellantConsumed / STEP_SIZE) > maxThrust) {
            System.out.println("Max Thrust exceeded!!!");
        }


        updateMass(propellantConsumed);
        return propellantConsumed;
    }


    /**
     * Update mass.
     *
     * @param propellantConsumed the propellant consumed
     */
    @Override
    public void updateMass(double propellantConsumed) {
        if (this.getFuelMass() - propellantConsumed >= 0) {
            this.setMASS(this.totalMass - propellantConsumed);
            this.fuelMass = (this.fuelMass - propellantConsumed);
        } else {
            System.out.println("Out of fuel!");
        }
    }

    @Override
    public double getFuelMass() {
        return this.fuelMass;
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
    public void initProperties() {
        this.setMASS(7.8e4);
        this.setRADIUS(1e2);
        this.setColour(Color.GREEN);
        this.setVectorLocation(new Vector3D(0, 500, Math.PI / 2));
        this.setVectorVelocity(new Vector3D(-500, 0.0, 0));
        this.localAcceleration = new Vector3D();
        this.fuelMass = this.startFuel;
        this.totalMass = this.startFuel + this.getMASS();
        this.setMASS(this.totalMass);
    }

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts) {
        assert (simulation.getSystem() != null);
        StateInterface<Vector3dInterface>[] seq = simulation.getRunner().getSolver().solve(
                simulation.getRunner().getSolver().getFunction(),
                simulation.getSystem().getState(),
                ts);

        return getSequence(seq);
    }

    private Vector3dInterface[] getSequence(StateInterface<Vector3dInterface>[] seq) {
        Vector3dInterface[] trajectory = new Vector3dInterface[seq.length];
        for (int i = 0; i < trajectory.length; i++) {
            trajectory[i] = (Vector3dInterface) seq[i];
        }
        return trajectory;
    }

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) {
        assert (simulation.getSystem() != null);
        StateInterface<Vector3dInterface>[] seq = simulation.getRunner().getSolver().solve(
                simulation.getRunner().getSolver().getFunction(),
                simulation.getSystem().getState(),
                tf, h);
        return getSequence(seq);
    }

}
