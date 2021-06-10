package Module.System.Module;

import Module.Math.ADT.Vector3dInterface;
import Module.System.State.StateInterface;

public class OpenLoopController implements ControllerInterface{
    @Override
    public Vector3dInterface controlFunction(StateInterface<Vector3dInterface> currentState, double currentTime) {
        return null;
    }
}
