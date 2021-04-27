package group17.phase1.Titan.Physics.Bodies;

import group17.phase1.Titan.Interfaces.RocketSimulatorInterface;
import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.Physics.Math.Vector3D;

import java.awt.*;

public class RocketSimulator extends CelestialBody implements RocketSimulatorInterface {

    // Variables
    double fuelMass;
    double burnRate;
    double specificImpulse;
    boolean burning = false;
    double burnPercentage;

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
        this.setMASS(2000);
        this.setRADIUS(20);
        this.setColour(Color.white);
        this.setVectorLocation(new Vector3D(0, 0, 0));
        this.setVectorVelocity(new Vector3D(700, 100, 0));
        /*
        this.setVectorLocation(Main.simulation.getBody("EARTH").getVectorLocation().clone());
        this.setVectorLocation(this.getVectorLocation().add(new Vector3D(-7485730.186, 6281273.438, -8172135.798)));
        this.setVectorVelocity(Main.simulation.getBody("EARTH").getVectorVelocity().clone());
        this.setVectorVelocity(this.getVectorVelocity().add(new Vector3D(36931.5681, -50000.58958, -1244.79425)));
         */
    }

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts) {
        return new Vector3dInterface[0];
    }

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) {
        return new Vector3dInterface[0];
    }
}
