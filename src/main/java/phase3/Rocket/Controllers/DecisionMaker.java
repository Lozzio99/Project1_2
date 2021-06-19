package phase3.Rocket.Controllers;

import phase3.Math.ADT.Vector3dInterface;
import phase3.System.State.StateInterface;

import static phase3.Config.*;

public class DecisionMaker {

    private ControllerInterface controller;

    public DecisionMaker(int loopType) {
        switch (loopType) {
            case OPEN -> {
                controller = new OpenLoopManualNewController();
            }
            case CLOSED -> {
                controller = new ClosedLoopController();
            }
        }
    }

    public double[] getControls(double t, StateInterface<Vector3dInterface> y) {
        return controller.controlFunction(t, y);
    }
}
