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
            /*
            this.currentState = y;
            if (getUpdateCondition()) {
                //TODO: optimize V regarding the currentState
                //return the acceleration
                return new RateOfChange<>(V.add(y.getRateOfChange().get()));
            } else {
                return new RateOfChange<>(new Vector3D(0, 0, 0));
            }

             */

            // Turn the module to point retrograde to the current trajectory
            this.currentState = y;
            double turnSensitivity = 10;
            double turnDampening = 90;
            double targetAngle = Math.atan2(y.getRateOfChange().get().getY(), y.getRateOfChange().get().getX()) * 180 / Math.PI; // module velocity
            double currentAngle = -90 - y.get().getZ() ; // module orientation
            double rotationalVelocity = -y.getRateOfChange().get().getZ();

            double angleDifferendce = currentAngle - targetAngle;
            System.out.println("--- Y: " + y.get().getY());

            // Calculate the rotational acceleration
            double rotationalAcceleration = (angleDifferendce * turnSensitivity) + (rotationalVelocity * turnDampening);

            double rotThrustLimit = 1.16;
            if (rotationalAcceleration > rotThrustLimit) {
                rotationalAcceleration = rotThrustLimit;
            }

            if (rotationalAcceleration < -rotThrustLimit) {
                rotationalAcceleration = -rotThrustLimit;
            }
            Vector3D rotVector = new Vector3D(0, 0, rotationalAcceleration);

            // Calculate thrust

            double velocityFactor = 0.2;
            double locationFactor = 10;
            //double hoverThrust = 3486479.0461024586 / 7.8e6; // F = G * m / r = 6.67408e-11 * 1.345e23 / 2574700
            double hoverThrust = 1.351;

            // double burnAmount = (y.getRateOfChange().get().getY() * -1) * velFactor + (1 / y.get().getY() * locationFactor);
            double burnAmount = - (hoverThrust / (1 - y.get().getY())) - (y.getRateOfChange().get().getY() * velocityFactor) + (locationFactor / y.get().getY());

            Vector3D thrustVector = burn(burnAmount, y);

            // Apply changes
            return new RateOfChange<>(thrustVector.add(rotVector));
            //return new RateOfChange<>(new Vector3D(0, hoverThrust, 0));

            // t : 0      1       2       3      4     5
            // p : 0      10      18     24
            // v : 10     8       6
            // a : 5      4       3      (?)
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
