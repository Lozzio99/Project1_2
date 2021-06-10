package Module.Math.Functions;

import Module.Math.ADT.Vector3D;
import Module.Math.ADT.Vector3dInterface;
import Module.System.State.StateInterface;
import Module.System.State.SystemState;

public class WindModel {

    double[] strengthOfTheWindAtDifferentHeightsFromSurfaceToTopAtmosphere = new double[600]; //the total height of titan atmosphere
    //values may be negative to indicate different positions ( <-   or   -> )  but not too much random (no alternated left-right)

    public WindModel() {

    }

    //TODO : initialise the 'stochastic' model
    public void init() {
        // at higher height, wind is (some percentage) stronger,
        // at some intervals , it may change of at most 0.5,
        // max wind speed may be some fixed amount (research ~120m/s)
    }

    //TODO: modify the current state by checking the height(y component) and affecting the horizontal velocity
    // (x component) of the <E> (vector) in the state
    public StateInterface<Vector3dInterface> perturb(StateInterface<Vector3dInterface> currentState) {
        return new SystemState<>(new Vector3D());
    }

}
