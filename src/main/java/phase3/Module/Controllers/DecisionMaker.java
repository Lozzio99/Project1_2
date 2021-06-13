package phase3.Module.Controllers;

import static phase3.Config.CLOSED;
import static phase3.Config.OPEN;

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
