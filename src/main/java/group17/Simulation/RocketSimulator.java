package group17.Simulation;

import group17.Interfaces.RocketInterface;
import group17.Interfaces.Vector3dInterface;
import group17.Math.Vector3D;
import group17.System.Bodies.ProbeSimulator;

import static group17.Config.STEP_SIZE;
import static group17.Main.simulationInstance;

public class RocketSimulator extends ProbeSimulator implements RocketInterface {

    // Variables
    double startFuel = 2e4;
    double fuelMass;
    double totalMass;
    double exhaustVelocity = 2e4;
    double maxThrust = 3e7;

    Vector3dInterface localAcceleration;


    public RocketSimulator() {
        this.localAcceleration = new Vector3D();
    }

    public void addAcceleration(Vector3dInterface toAdd) {
        this.localAcceleration = this.localAcceleration.add(toAdd);
    }

    public Vector3dInterface getLocalAcceleration() {
        return localAcceleration;
    }

    public void setLocalAcceleration(Vector3dInterface localAcceleration) {
        this.localAcceleration = localAcceleration;
    }

    /*
         desiredVelocity - obtain from Newton-Raphson method
         stepSize used to determine if m_dot * Ve > maxThrust
         y for getting currentVelocity of Rocket
    */
    public double evaluateLoss(Vector3dInterface desiredVelocity, Vector3dInterface actualVelocity) {
        double deltaV = desiredVelocity.sub(actualVelocity).norm();
        double propellantConsumed = (fuelMass * deltaV) / (this.exhaustVelocity + deltaV);
        if (exhaustVelocity * (propellantConsumed / STEP_SIZE) > maxThrust) {
            System.out.println("Max Thrust exceeded!!!");
        }
        simulationInstance.getSystem().systemState().getRateOfChange().getVelocities().set(11, desiredVelocity);
        updateMass(propellantConsumed);
        return propellantConsumed;
    }


    public void updateMass(double propellantConsumed) {
        if (this.getFuelMass() - propellantConsumed > 0) {
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

    public String info() {
        return "Dry Mass: " + this.getMASS() + "\n" +
                "Fuel Mass: " + this.fuelMass + "\n";
    }

    @Override
    public void update() {
        if (!this.isCollided()) {
            simulationInstance.getSystem().systemState().getRateOfChange().getVelocities().set(11,
                    simulationInstance.getSystem().systemState().getRateOfChange().getVelocities().get(11).addMul(STEP_SIZE, localAcceleration));
        }
    }


    @Override
    public void initProperties() {
        super.initProperties();
        this.localAcceleration = new Vector3D();
        this.fuelMass = this.startFuel;
        this.totalMass = this.startFuel + this.getMASS();
        this.setMASS(this.totalMass);
    }

}
