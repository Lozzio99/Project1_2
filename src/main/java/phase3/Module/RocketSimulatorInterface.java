package phase3.Module;

import phase3.Math.ADT.Vector3dInterface;

public interface RocketSimulatorInterface extends ProbeSimulatorInterface {
    double evaluateLoss(Vector3dInterface desiredVelocity, Vector3dInterface actualVelocity);

    void updateMass(double propellantConsumed);

    double getFuelMass();
}
