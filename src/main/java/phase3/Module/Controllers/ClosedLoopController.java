package phase3.Module.Controllers;

import phase3.Math.ADT.Vector3D;
import phase3.Math.ADT.Vector3dInterface;
import phase3.System.State.StateInterface;

public class ClosedLoopController implements ControllerInterface {

    private final Vector3dInterface V;
    private StateInterface<Vector3dInterface> currentState;

    public ClosedLoopController() {
        V = new Vector3D(0, 0, 0);
    }

    @Override
    public double[] controlFunction(double t, StateInterface<Vector3dInterface> y) {
        return new double[]{0.0, 0.0};
    }

}
