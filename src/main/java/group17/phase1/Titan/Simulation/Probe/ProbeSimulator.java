package group17.phase1.Titan.Simulation.Probe;

import group17.phase1.Titan.Interfaces.ProbeSimulatorInterface;
import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.Main;
import group17.phase1.Titan.SolarSystem.Bodies.CelestialBody;

public class ProbeSimulator extends CelestialBody implements ProbeSimulatorInterface
{


    ProbeSimulator(){
        this.setMASS(15000);
        this.setVectorLocation(Main.simulation.getBody("EARTH").getVectorLocation());
    }

    @Override
    public String toString() {
        return null;
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
