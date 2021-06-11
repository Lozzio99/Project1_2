package Module.System.Module;

import Module.Math.ADT.Vector3dInterface;
import Module.Math.Functions.ODEFunctionInterface;

public interface ControllerInterface {

    /**
     * Main controller function; adjusts acceleration and torque
     *
     * @return new vector state
     */
    ODEFunctionInterface<Vector3dInterface> controlFunction();

}
