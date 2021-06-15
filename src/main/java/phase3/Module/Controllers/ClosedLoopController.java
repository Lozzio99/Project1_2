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
            double turnDampening = 0.5;
            double angle0 = Math.atan2(y.getRateOfChange().get().getY(), y.getRateOfChange().get().getX()) * 180 / Math.PI; // module velocity
            double angle1 = -90 - y.get().getZ() ; // module orientation
            double angleVel = -y.getRateOfChange().get().getZ();
            // System.out.println("Velocity: X = " + y.getRateOfChange().get().getX() + " ,Y = " + y.getRateOfChange().get().getY());
            // System.out.println("Angle0: " + angle0);
            // System.out.println("Angle1: " + angle1);
            // System.out.println("AngelVel: " + angleVel);
            // System.out.println("---");
            double diff = angle1 - angle0;
            System.out.println("*** diff: " + diff);

            // Calculate the rotational acceleration
            double rotAcc = (diff + (angleVel * turnDampening)) * turnSensitivity;
            Vector3D rotVector = new Vector3D(0, 0, rotAcc);

            // Calculate thrust

            double velFactor = 0.2;
            double locationFactor = 80;

            double burnAmount = (y.getRateOfChange().get().getY() * -1) * velFactor + (1 / y.get().getY() * locationFactor);
            Vector3D thrustVector = burn(burnAmount, y);
            System.out.println("BurnAmount: " + burnAmount);
            System.out.println("ThrustVector: " + thrustVector);
            System.out.println("---");

            // Apply changes
            return new RateOfChange<>(thrustVector.add(rotVector));

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
