package Module.Math.Functions;

import Module.Math.Vector3dInterface;
import Module.System.State.RateOfChange;
import Module.System.State.StateInterface;

/**
 * Module landing function, will
 */
public final class ModuleFunction {

    private static final double G = 1.352;
    private static double currentTime;
    /**
     * Function that returns the rate of change of a state at a point in time
     * Given an initial state it can perform the f(t,y) on that and return a rate
     * which then will be added (e.g. eulerStepNextY = currentState + ( f.call * h)
     */

    private final ODEFunctionInterface<Vector3dInterface> evalRateOfChange = (h, y) -> new RateOfChange(velocityFromGravityAcceleration(y, h));

    /**
     * Instantiates a new Gravity function.
     */
    public ModuleFunction() {
    }

    /**
     * Sets current time.
     *
     * @param ct the ct
     */
    public static void setCurrentTime(double ct) {
        currentTime = ct;
    }

    private static Vector3dInterface velocityFromGravityAcceleration(final StateInterface<Vector3dInterface> y, double h) {
        //LOGIC HERE for single body
        // Vector have 3 components [ xPos, yPos, angle ];
        // must implement function for (xVel, yVel, angleVel)
        // in theory should affect the y component only , or partially the x component
        Vector3dInterface v = y.getRateOfChange().get().mul(h);
        v.setZ(y.getRateOfChange().get().getZ());  // don't affect the angle
        return v;
    }

    /**
     * Gets evaluate rate of change.
     *
     * @return the evaluate rate of change lambda function
     */
    public ODEFunctionInterface<Vector3dInterface> evaluateCurrentAccelerationFunction() {
        return this.evalRateOfChange;
    }

}
