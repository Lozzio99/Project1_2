package group17.System.Bodies;


import group17.Interfaces.ProbeSimulatorInterface;
import group17.Interfaces.StateInterface;
import group17.Interfaces.Vector3dInterface;
import group17.Math.Vector3D;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static group17.Main.simulationInstance;
import static java.lang.Double.NaN;

public class ProbeSimulator extends CelestialBody implements ProbeSimulatorInterface {

    public ProbeSimulator() {
    }

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) {
        assert (simulationInstance.getSystem() != null);
        StateInterface[] seq = simulationInstance.getUpdater().getSolver().solve(
                simulationInstance.getUpdater().getSolver().getFunction(),
                simulationInstance.getSystem().systemState(),
                tf, h);

        return getSequence(seq);
    }

    @Override
    public String toString() {
        return "SOMETHING WRONG";
    }

    @Override
    public void initProperties() {
        this.setMASS(7.8e4);
        this.setRADIUS(1e2);
        this.setColour(Color.GREEN);
         /*
        this.setVectorLocation(Main.simulationInstance.getBody("EARTH").getVectorLocation().clone());
        this.setVectorLocation(this.getVectorLocation().add(new Vector3D(-7485730.186,6281273.438,-8172135.798)));
        this.setVectorVelocity(Main.simulationInstance.getBody("EARTH").getVectorVelocity().clone());
        this.setVectorVelocity(this.getVectorVelocity().add(new Vector3D(36931.5681,-50000.58958, -1244.79425)));
        this.setVectorLocation(new Vector3D(new Random().nextInt(50000), new Random(50000).nextInt(), new Random(50000).nextInt()));
        this.setVectorVelocity(new Vector3D(new Random().nextInt(50000), new Random().nextInt(50000), new Random().nextInt(50000)));
          */
        this.setVectorLocation(new Vector3D(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06)); //earth
        this.getVectorLocation().add(new Vector3D(6.372e6, 0, 0));
        this.setVectorVelocity(new Vector3D(5.427193405797901e+03, -2.931056622265021e+04, 6.575428158157592e-01));

    }

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts) {
        assert (simulationInstance.getSystem() != null);
        StateInterface[] seq = simulationInstance.getUpdater().getSolver().solve(
                simulationInstance.getUpdater().getSolver().getFunction(),
                simulationInstance.getSystem().systemState(),
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


}
