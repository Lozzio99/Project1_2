package Module.System.Module;

import Module.Math.ADT.Vector3dInterface;
import Module.Math.Functions.ODEFunctionInterface;

public class OpenLoopController implements ControllerInterface{
    @Override
    public ODEFunctionInterface<Vector3dInterface> controlFunction() {
        return (t, y) -> y.getRateOfChange();
    }
}
