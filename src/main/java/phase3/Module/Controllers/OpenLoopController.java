package phase3.Module.Controllers;

import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Functions.ODEFunctionInterface;

public class OpenLoopController implements ControllerInterface {
    @Override
    public ODEFunctionInterface<Vector3dInterface> controlFunction() {
        return (t, y) -> y.getRateOfChange();
    }
}
