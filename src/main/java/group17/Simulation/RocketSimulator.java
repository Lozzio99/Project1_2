package group17.Simulation;

import group17.Interfaces.RocketInterface;
import group17.Interfaces.Vector3dInterface;
import group17.System.Bodies.ProbeSimulator;

public class RocketSimulator extends ProbeSimulator implements RocketInterface {

    // Variables
    double fuelMass;
    double burnRate;
    double specificImpulse;
    boolean burning = false;
    double burnPercentage;
    Vector3dInterface localAcceleration;


    public RocketSimulator() {

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
        this.localAcceleration = localAcceleration;
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

    }

    @Override
    public void initProperties() {
        super.initProperties();
    }

}
