package Module.System.Module;

import Module.Math.Vector3dInterface;
import Module.System.State.StateInterface;

public interface ControllerInterface {
    StateInterface<Vector3dInterface> makeDecision(StateInterface<Vector3dInterface> currentState, double currentTime);

    enum Loop {OPEN, CLOSED}
}
