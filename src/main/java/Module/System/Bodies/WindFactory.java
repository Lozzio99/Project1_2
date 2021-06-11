package Module.System.Bodies;

import Module.Math.ADT.Vector3D;
import Module.Math.ADT.Vector3dInterface;
import Module.Math.Functions.ODEFunctionInterface;
import Module.System.State.RateOfChange;

public class WindFactory implements WindInterface {
    @Override
    public ODEFunctionInterface<Vector3dInterface> getWindFunction() {
        return (t, y) -> {
            return new RateOfChange<>(new Vector3D(0, 0, 0));
        };
    }
}
