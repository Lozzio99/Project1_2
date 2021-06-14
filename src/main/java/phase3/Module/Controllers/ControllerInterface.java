package phase3.Module.Controllers;

import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Functions.ODEFunctionInterface;
import phase3.System.State.StateInterface;

public interface ControllerInterface {

    /**
     * Main controller function; adjusts acceleration and torque
     *
     * @return new vector state
     */
    double[] controlFunction(double t, StateInterface<Vector3dInterface> y);

}
