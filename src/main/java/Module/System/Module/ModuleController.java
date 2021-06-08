package Module.System.Module;

import Module.Math.Vector3D;
import Module.Math.Vector3dInterface;
import Module.System.State.StateInterface;
import Module.System.State.SystemState;

public class ModuleController implements ControllerInterface {
    ControllerInterface.Loop loopType;

    public ModuleController(ControllerInterface.Loop loopType) {
        this.loopType = loopType;
    }


    @Override
    public StateInterface<Vector3dInterface> makeDecision(StateInterface<Vector3dInterface> currentState, double currentTime) {
        StateInterface<Vector3dInterface> state;
        switch (this.loopType) {
            case OPEN -> {
                state = new SystemState(new Vector3D());
            }
            case CLOSED -> {
                state = new SystemState(null);
            }
            default -> throw new IllegalStateException("Unexpected value: " + this.loopType);
        }
        return state;
    }
}
