package phase3.Math.Forces;

import phase3.Math.ADT.Vector3D;
import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Functions.ODEFunctionInterface;
import phase3.System.State.RateOfChange;
import phase3.System.State.StateInterface;

import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;

/**
 * Module landing function, will
 */
public final class ModuleFunction {

    public static final double G = 1.352;
    private static double currentTime;
    public static final double U_MAX = 7.5;
    public static final double V_MAX = 0.58;
    private final double[] CONTROLS = new double[]{0.0, 0.0};
    /**
     * Function that returns the rate of change of a state at a point in time
     * Given an initial state it can perform the f(t,y) on that and return a rate
     * which then will be added (e.g. eulerStepNextY = currentState + ( f.call * h)
     */

    private final ODEFunctionInterface<Vector3dInterface> evalRateOfChange = (h, y) -> {
        Vector3dInterface v = velocityFromGravityAcceleration(y, h);
        return new RateOfChange<>(y.get()[1], v);
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

    private Vector3dInterface velocityFromGravityAcceleration(final StateInterface<Vector3dInterface> y, double h) {
        //LOGIC HERE for single body
        // Vector have 3 components [ xPos, yPos, angle ];
        // must implement function for (xVel, yVel, angleVel)
        // in theory should affect the y component only , or partially the x component
        final Vector3dInterface newRate = new Vector3D(0, 0, 0);
        newRate.setX(sin(y.get()[0].getZ()) * CONTROLS[0]);  //must multiply by u and v then
        newRate.setY((cos(y.get()[0].getZ()) * CONTROLS[0]) - G);
        newRate.setZ(CONTROLS[1]);

        return newRate;
    }

    /**
     * Gets evaluate rate of change.
     *
     * @return the evaluate rate of change lambda function
     */
    public ODEFunctionInterface<Vector3dInterface> evaluateAcceleration() {
        return this.evalRateOfChange;
    }


    public void setControls(double[] CONTROLS) {
        this.CONTROLS[0] = CONTROLS[0];
        this.CONTROLS[1] = CONTROLS[1];
    }

    public void setControls(double u, double v) {
        this.CONTROLS[0] = u;
        this.CONTROLS[1] = v;
    }
}
