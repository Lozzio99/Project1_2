package Module.System.Module;

import Module.Math.Vector3D;
import Module.Math.Vector3dInterface;
import Module.System.State.StateInterface;
import Module.System.State.SystemState;

public class DecisionMaker {

    private ControllerInterface controller;

    public DecisionMaker(ControllerInterface.Loop loopType) {
        switch (loopType) {
            case OPEN -> {
                controller = new OpenLoopController();
            }
            case CLOSED -> {
                controller = new ClosedLoopController();
            }
        }
    }

    public StateInterface<Vector3dInterface> makeDecision(StateInterface<Vector3dInterface> currentState, double currentTime) {
        StateInterface<Vector3dInterface> state;
        state = controller.controlFunction(currentState, currentTime);
        return state;
    }
}
