package group17.Simulation;

import group17.Interfaces.RocketInterface;
import group17.Interfaces.Vector3dInterface;
import group17.Math.Vector3D;
import group17.System.Bodies.ProbeSimulator;

import static group17.Config.STEP_SIZE;
import static group17.Main.simulationInstance;

public class RocketSimulator extends ProbeSimulator implements RocketInterface {

    // Variables
    double fuelMass;
    double burnRate;
    double specificImpulse;
    boolean burning = false;
    double burnPercentage;
    Vector3dInterface localAcceleration;


    public RocketSimulator() {
        this.localAcceleration = new Vector3D();
    }


    @Override
    public void startBurn(Vector3dInterface direction, double percentage) {
        burning = true;
        burnPercentage = percentage;
    }

    @Override
    public void startBurn(Vector3dInterface direction, double percentage, int duration) {
        burning = true;
    }


    @Override
    public void stopBurn() {
        burning = false;
        burnPercentage = 0;
    }

    @Override
    public void updateDV() {
        this.getVectorVelocity().add(this.localAcceleration);
    }

    public void setLocalAcceleration(Vector3dInterface localAcceleration) {
        this.localAcceleration = this.localAcceleration.add(localAcceleration);
    }

    public void updateMass(double currBurnPercentage) {
        double burnAmount = this.burnRate * currBurnPercentage;

        if (this.getFuelMass() - burnAmount < 0) {
            this.setMASS(this.getMASS() - burnAmount);
            this.setFuelMass(this.getFuelMass() - burnAmount);
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
    }

}
