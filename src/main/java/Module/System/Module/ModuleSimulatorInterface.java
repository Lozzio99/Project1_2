package Module.System.Module;

import Module.Math.ADT.Vector3dInterface;

public interface ModuleSimulatorInterface extends ProbeSimulatorInterface {
    double evaluateLoss(Vector3dInterface desiredVelocity, Vector3dInterface actualVelocity);
    void updateMass(double propellantConsumed);
    double getFuelMass();
}
