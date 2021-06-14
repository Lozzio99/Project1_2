package phase3.Module.Controllers;

import phase3.Math.ADT.Vector3D;
import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Functions.ODEFunctionInterface;
import phase3.System.State.RateOfChange;

public class OpenLoopController implements ControllerInterface {
    @Override
    public ODEFunctionInterface<Vector3dInterface> controlFunction() {
        // return (t, y) -> y.getRateOfChange();
        return (t, y) -> {
            return new RateOfChange<>(new Vector3D(0, 0, 0));

        };
    }
}
