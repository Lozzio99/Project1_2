package phase3.Module.Controllers;

import phase3.Math.ADT.Vector3D;
import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Functions.ODEFunctionInterface;
import phase3.System.State.RateOfChange;
import phase3.System.State.StateInterface;

public class ClosedLoopController implements ControllerInterface {

    private final Vector3dInterface V;
    private StateInterface<Vector3dInterface> currentState;

    public ClosedLoopController() {
        V = new Vector3D(0, 0, 0);
    }

    @Override
    public ODEFunctionInterface<Vector3dInterface> controlFunction() {
        return (t, y) -> {

            // Variables
            this.currentState = y;

            double xVel = y.getRateOfChange().get().getX();
            double yPos = y.get().getY();
            double yVel = y.getRateOfChange().get().getY();
            double zPos = y.get().getZ();
            double zVel = y.getRateOfChange().get().getZ();
            double turnSensitivity = 10;
            double turnDampening = 90;

            // --- Calculate rotational acceleration ---

            double rotationalAcceleration = getRotationalAcceleration(xVel, yVel, zPos, zVel, turnSensitivity, turnDampening);


            Vector3D rotVector = new Vector3D(0, 0, rotationalAcceleration);

            // --- Calculate thrust ---

            double thrustFactor = 10;
            double hoverThrust = 1.351;
            double descentFactor = 10; // Higher value means faster descent
            double posVelRatioFactor = 0.1;

            double burnAmount = getBurnAmount(yPos, yVel, thrustFactor, hoverThrust, descentFactor, posVelRatioFactor);


            Vector3D thrustVector = burn(burnAmount, y);

            // Apply changes to new control-vector
            return new RateOfChange<>(thrustVector.add(rotVector));

        };
    }

    /**
     * Ensures that a rocket is only able to burn in the direction that the thruster is pointing at.
     * @param amount
     * @param state
     * @return
     */
    private Vector3D burn (double amount, StateInterface<Vector3dInterface> state) {
        return  new Vector3D(amount * Math.sin(state.get().getZ() * Math.PI / 180),
                             amount * Math.cos(state.get().getZ() * Math.PI / 180),
                             0);
    }

    private double getRotationalAcceleration(double xVel, double yVel, double zPos, double zVel, double turnSensitivity, double turnDampening) {

        double targetAngle = Math.atan2(yVel, xVel) * 180 / Math.PI; // module velocity
        double currentAngle = -90 - zPos ; // module orientation
        double rotationalVelocity = -zVel;

        double angleDifference = currentAngle - targetAngle;

        double rotationalAcceleration = (angleDifference * turnSensitivity) + (rotationalVelocity * turnDampening);

        double rotThrustLimit = 1.16;
        if (rotationalAcceleration > rotThrustLimit) {
            rotationalAcceleration = rotThrustLimit;
        }

        if (rotationalAcceleration < -rotThrustLimit) {
            rotationalAcceleration = -rotThrustLimit;
        }

        return rotationalAcceleration;
    }

    /**
     * Function to calculate the amount of thrust that is being applied.
     * @param yPos                  current y-position
     * @param yVel                  current y-velocity
     * @param thrustFactor
     * @param hoverThrust
     * @return
     */
    private double getBurnAmount(double yPos, double yVel, double thrustFactor, double hoverThrust, double descentFactor, double posVelRatioFactor) {

        double targetDecentRate = Math.sqrt(descentFactor * yPos) * posVelRatioFactor;
        double burnAmount = ((-yVel - targetDecentRate) * thrustFactor) + (hoverThrust / (yPos + 1));

        // Main thruster restriction
        double thrusterThreshold = 7.5;

        // Makes sure, it cannot burn with more than the maximum thrust
        if (burnAmount > thrusterThreshold) {
            burnAmount = thrusterThreshold;
        }

        // Makes sure the thrust is not negative
        if (burnAmount < 0) {
            burnAmount = 0;
        }
        return burnAmount;
    }

    private boolean getUpdateCondition() {
        //TODO: implement update condition
        return false;
    }

}
