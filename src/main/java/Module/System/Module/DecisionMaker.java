package Module.System.Module;

import Module.Math.ADT.Vector3dInterface;
import Module.System.State.StateInterface;

import static Module.Config.CLOSED;
import static Module.Config.OPEN;

public class DecisionMaker {

    private ControllerInterface controller;

    public DecisionMaker(int loopType) {
        switch (loopType) {
            case OPEN -> {
                controller = new OpenLoopController();
            }
            case CLOSED -> {
                controller = new ClosedLoopController();
            }
        }
    }

    public Vector3dInterface makeDecision(StateInterface<Vector3dInterface> currentState, double currentTime) {
        Vector3dInterface state;
        state = controller.controlFunction(currentState, currentTime);
        return state;
    }

}
