package group17.phase1.Titan.Physics.Bodies;


import group17.phase1.Titan.Interfaces.ProbeSimulatorInterface;
import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.Physics.Math.Vector3D;

import java.awt.*;
import java.util.Random;

public class ProbeSimulator extends CelestialBody implements ProbeSimulatorInterface {
    public ProbeSimulator() {

    }

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) {
        return new Vector3dInterface[0];
    }

    @Override
    public String toString() {
        return "PROBE";
    }

    @Override
    public void initProperties() {
        this.setMASS(15000);
        this.setRADIUS(1e6);
        this.setColour(Color.GREEN);
         /*
        this.setVectorLocation(Main.simulation.getBody("EARTH").getVectorLocation().clone());
        this.setVectorLocation(this.getVectorLocation().add(new Vector3D(-7485730.186,6281273.438,-8172135.798)));
        this.setVectorVelocity(Main.simulation.getBody("EARTH").getVectorVelocity().clone());
        this.setVectorVelocity(this.getVectorVelocity().add(new Vector3D(36931.5681,-50000.58958, -1244.79425)));
         */
        this.setVectorLocation(new Vector3D(new Random().nextInt(50000), new Random(50000).nextInt(), new Random(50000).nextInt()));
        this.setVectorVelocity(new Vector3D(new Random().nextInt(50000), new Random().nextInt(50000), new Random().nextInt(50000)));
    }

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts) {
        return new Vector3dInterface[0];
    }
}
