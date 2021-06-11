package Module.System.Module;

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

    public ControllerInterface getController() {
        return controller;
    }
}
