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
            double yPos = y.get().getY();
            double yVel = y.getRateOfChange().get().getY();

            this.currentState = y;
            double turnSensitivity = 10;
            double turnDampening = 90;
            double targetAngle = Math.atan2(y.getRateOfChange().get().getY(), y.getRateOfChange().get().getX()) * 180 / Math.PI; // module velocity
            double currentAngle = -90 - y.get().getZ() ; // module orientation
            double rotationalVelocity = -y.getRateOfChange().get().getZ();

            double angleDifferendce = currentAngle - targetAngle;

            // --- Calculate rotational acceleration ---
            double rotationalAcceleration = (angleDifferendce * turnSensitivity) + (rotationalVelocity * turnDampening);

            double rotThrustLimit = 1.16;
            if (rotationalAcceleration > rotThrustLimit) {
                rotationalAcceleration = rotThrustLimit;
            }

            if (rotationalAcceleration < -rotThrustLimit) {
                rotationalAcceleration = -rotThrustLimit;
            }
            Vector3D rotVector = new Vector3D(0, 0, rotationalAcceleration);

            // --- Calculate thrust ---

            double velocityFactor = 0.2;
            double locationFactor = 10;
            double thrustFactor = 10;
            double hoverThrust = 1.351;
            double descentFactor = 10; // Higher value means faster descent
            double posVelRatioFactor = 0.1;

            double targetDecentRate = Math.sqrt(descentFactor * yPos) * posVelRatioFactor ;
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

            Vector3D thrustVector = burn(burnAmount, y);

            System.out.println("--- TargetDescentRate: " + targetDecentRate);
            System.out.println("--- yVel: " + yVel);
            System.out.println("--- Y: " + y.get().getY());
            System.out.println("--- BurnAmount: " + burnAmount);

            // Apply changes
            return new RateOfChange<>(thrustVector.add(rotVector));
            //return new RateOfChange<>(new Vector3D(0, hoverThrust, 0));

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

    private boolean getUpdateCondition() {
        //TODO: implement update condition
        return false;
    }

}
