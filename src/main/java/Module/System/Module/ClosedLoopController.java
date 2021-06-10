package Module.System.Module;

import Module.Math.Vector3D;
import Module.Math.Vector3dInterface;
import Module.System.State.StateInterface;

public class ClosedLoopController implements ControllerInterface{

    private Vector3dInterface V;
    private StateInterface<Vector3dInterface> currentState;

    public ClosedLoopController() {
        V = new Vector3D(0,0,0);
    }

    @Override
    public Vector3dInterface controlFunction(StateInterface<Vector3dInterface> currentState, double currentTime) {
        this.currentState = currentState;
        if (getUpdateCondition()) {
            //TODO: optimize V regarding the currentState
        }
        return V;
    }

    private boolean getUpdateCondition() {
        //TODO: implement update condition
        return true;
    }

}
