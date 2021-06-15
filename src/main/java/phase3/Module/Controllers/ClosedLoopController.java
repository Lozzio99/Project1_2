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
            this.currentState = y;
            if (getUpdateCondition()) {
                //TODO: optimize V regarding the currentState
                //return the acceleration
                return new RateOfChange<>(V.add(y.get()[1]));
            } else {
                return new RateOfChange<>(new Vector3D(0, 0, 0));
            }
            // t : 0      1       2       3      4     5
            // p : 0      10      18     24
            // v : 10     8       6
            // a : 5      4       3      (?)
        };
    }

    private boolean getUpdateCondition() {
        //TODO: implement update condition
        return false;
    }

}
