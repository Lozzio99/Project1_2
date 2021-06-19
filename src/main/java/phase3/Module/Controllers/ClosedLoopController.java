package phase3.Module.Controllers;

import phase3.Math.ADT.Vector3D;
import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Functions.ODEFunctionInterface;
import phase3.System.State.RateOfChange;
import phase3.System.State.StateInterface;

/**
 * This class represents a feedback controller for a module to land on a surface safely.
 */
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

            // Modify for different behaviour:
            double turnSensitivity = 10;    // Higher value means faster turning
            double turnDampening = 90;      // Dampens the rotational acceleration to not overshoot the target angle
            double thrustFactor = 10;       // Factor for the amount of thrust applied
            double hoverThrust = 1.351;     // Required thrust to let the module hover
            double descentFactor = 10;      // Higher value means faster descent
            double posVelRatioFactor = 0.1; // Relation between vertical position and vertical velocity

            // --- Calculate rotational acceleration ---
            double rotationalAcceleration = getRotationalAcceleration(xVel, yPos, yVel, zPos, zVel, turnSensitivity, turnDampening);
            Vector3D rotVector = new Vector3D(0, 0, rotationalAcceleration);

            // --- Calculate thrust ---
            double burnAmount = getBurnAmount(yPos, yVel, thrustFactor, hoverThrust, descentFactor, posVelRatioFactor);
            Vector3D thrustVector = burn(burnAmount, y);

            // --- Apply changes to new control-vector ---
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

    /**
     * This function computes the rotational acceleration of the module
     * Angles are calculated in degrees.
     * @param xVel              current x-velocity
     * @param yVel              current y-velocity
     * @param zPos              current z-position (radians)
     * @param zVel              current z-velocity (radians)
     * @param turnSensitivity   higher value means faster turning
     * @param turnDampening     dampens the rotational acceleration to not overshoot the target angle
     * @return
     */
    private double getRotationalAcceleration(double xVel, double yPos, double yVel, double zPos, double zVel, double turnSensitivity, double turnDampening) {

        double targetAngle;
        if (yPos < 10)
            targetAngle = 90;
        else
            targetAngle = Math.atan2(yVel, xVel) * 180 / Math.PI; // module velocity

        double currentAngle = -90 - zPos ; // module orientation
        double rotationalVelocity = -zVel;

        double angleDifference = currentAngle - targetAngle;

        double rotationalAcceleration = (angleDifference * turnSensitivity) + (rotationalVelocity * turnDampening);

        // Rotational thruster restriction
        double rotThrustLimit = 1.16;

        if (rotationalAcceleration > rotThrustLimit)
            rotationalAcceleration = rotThrustLimit;

        if (rotationalAcceleration < -rotThrustLimit)
            rotationalAcceleration = -rotThrustLimit;

        return rotationalAcceleration;
    }

    /**
     * Function to calculate the amount of thrust that is being applied.
     * @param yPos                  current y-position
     * @param yVel                  current y-velocity
     * @param thrustFactor          factor for the amount of thrust applied
     * @param hoverThrust           required thrust to let the module hover
     * @return
     */
    private double getBurnAmount(double yPos, double yVel, double thrustFactor, double hoverThrust, double descentFactor, double posVelRatioFactor) {

        double targetDecentRate = Math.sqrt(descentFactor * yPos) * posVelRatioFactor;
        double burnAmount = ((-yVel - targetDecentRate) * thrustFactor) + (hoverThrust / (yPos + 1));

        // Main thruster restriction
        double thrusterThreshold = 7.5;

        if (burnAmount > thrusterThreshold)
            burnAmount = thrusterThreshold;

        // Makes sure the thrust is not negative
        if (burnAmount < 0)
            burnAmount = 0;

        return burnAmount;
    }

    private boolean getUpdateCondition() {
        //TODO: implement update condition
        return false;
    }

}
