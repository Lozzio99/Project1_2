package group17.System.Bodies;


import group17.Interfaces.ProbeSimulatorInterface;
import group17.Interfaces.Vector3dInterface;
import group17.Math.Vector3D;

import java.awt.*;

public class ProbeSimulator extends CelestialBody implements ProbeSimulatorInterface {

    public ProbeSimulator() {

    }

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) {
        return new Vector3dInterface[0];
    }

    @Override
    public String toString() {
        return "SOMETHING WRONG";
    }

    @Override
    public void initProperties() {
        this.setMASS(15000);
        this.setRADIUS(1e6);
        this.setColour(Color.GREEN);
         /*
        this.setVectorLocation(Main.simulationInstance.getBody("EARTH").getVectorLocation().clone());
        this.setVectorLocation(this.getVectorLocation().add(new Vector3D(-7485730.186,6281273.438,-8172135.798)));
        this.setVectorVelocity(Main.simulationInstance.getBody("EARTH").getVectorVelocity().clone());
        this.setVectorVelocity(this.getVectorVelocity().add(new Vector3D(36931.5681,-50000.58958, -1244.79425)));
        this.setVectorLocation(new Vector3D(new Random().nextInt(50000), new Random(50000).nextInt(), new Random(50000).nextInt()));
        this.setVectorVelocity(new Vector3D(new Random().nextInt(50000), new Random().nextInt(50000), new Random().nextInt(50000)));
          */
        this.setVectorLocation(new Vector3D(-1.471922101663588e+11 + 6.373e6, -2.860995816266412e+10, 8.278183193596080e+06)); //earth
        this.setVectorVelocity(new Vector3D());

    }

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts) {
        return new Vector3dInterface[0];
    }


}
