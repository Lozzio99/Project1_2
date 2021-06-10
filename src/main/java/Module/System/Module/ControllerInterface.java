package Module.System.Module;

import Module.Math.ADT.Vector3dInterface;
import Module.System.State.StateInterface;

public interface ControllerInterface {

    /**
     * Main controller function; adjusts acceleration and torque
     *
     * @param currentState vector of position, rotation, velocity, angular rotation
     * @param currentTime  in s
     * @return new vector state
     */
    Vector3dInterface controlFunction(StateInterface<Vector3dInterface> currentState, double currentTime);

}
