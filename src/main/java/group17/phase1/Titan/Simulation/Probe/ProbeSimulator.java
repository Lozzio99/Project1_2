package group17.phase1.Titan.Simulation.Probe;

import group17.phase1.Titan.Interfaces.Vector3dInterface;

public class ProbeSimulator implements ProbeSimulatorInterface
{


    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts) {
        return new Vector3dInterface[0];
    }

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) {
        return new Vector3dInterface[0];
    }
}
