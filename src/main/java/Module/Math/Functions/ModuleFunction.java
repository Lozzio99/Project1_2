package Module.Math.Functions;

import Module.Math.Vector3D;
import Module.Math.Vector3dInterface;
import Module.System.State.RateOfChange;
import Module.System.State.StateInterface;

import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;

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


    private final ODEFunctionInterface<Vector3dInterface> evalRateOfChange = (h, y) -> {
        Vector3dInterface v = velocityFromGravityAcceleration(y, h);
        Vector3dInterface v2 = y.getRateOfChange().get().addMul(h, v);
        v2.setZ(v2.getZ() - (v.getZ() * h));
        return new RateOfChange<>(v2);
    };


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

    public static Vector3dInterface velocityFromGravityAcceleration(final StateInterface<Vector3dInterface> y, double h) {
        //LOGIC HERE for single body
        // Vector have 3 components [ xPos, yPos, angle ];
        // must implement function for (xVel, yVel, angleVel)
        // in theory should affect the y component only , or partially the x component
        final Vector3dInterface newRate = new Vector3D(0, 0, 0);
        newRate.setX(sin(y.get().getZ()) * 0);  //must multiply by u and v then
        newRate.setY((cos(y.get().getZ()) * 0) - G);
        newRate.setZ(y.get().getZ());

        // for Dan Midpoint Part
        //-----------
        //
        //
        // ----------
        /*
            newRate.setX(sin(y.get().getZ()) * 0);  //must multiply by u and v then
            newRate.setY((cos(y.get().getZ()) * 0) - G);
            newRate.setZ(y.get().getZ() );
        */
        return newRate;
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
