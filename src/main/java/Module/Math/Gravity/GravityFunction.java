package Module.Math.Gravity;

import Module.Math.Vector3D;
import Module.Math.Vector3dInterface;
import Module.System.State.RateInterface;
import Module.System.State.RateOfChange;
import Module.System.State.StateInterface;

import static Module.Main.simulation;
import static java.lang.StrictMath.pow;
import static java.lang.StrictMath.sqrt;

/**
 * Solar system gravitational motion function,
 * from here two different implementation of the
 */
public final class GravityFunction {
    private static final double G = 6;
    private static boolean checkedCollisions;
    private static double currentTime;
    /**
     * Function that returns the rate of change of a state at a point in time
     */
    private final ODEFunctionInterface evalRateOfChange = (h, y) -> {
        final RateInterface newRate = new RateOfChange();
        for (int i = 0; i < y.getPositions().size(); i++) {
            newRate.getPositions().add(
                    velocityFromAcceleration(i, y, 1)); // changed to 1 from h for OldRk
        }
        return newRate;
    };

    /**
     * Function that returns the rate of change of a state at a point in time, added
     * to the current state velocities
     */
    private final ODEFunctionInterface evalCurrVelocity = (t, y) -> {
        final RateInterface newRate = new RateOfChange();
        final double dt = t - currentTime;
        for (int i = 0; i < y.getPositions().size(); i++) {
            newRate.getPositions().add(
                    y.getRateOfChange().getPositions().get(i)
                            .add(velocityFromAcceleration(i, y, dt)));
        }
        return newRate;
    };

    /**
     * Instantiates a new Gravity function.
     */
    public GravityFunction() {
    }

    /**
     * Sets checked.
     *
     * @param value the value
     */
    public static void setChecked(boolean value) {
        checkedCollisions = value;
    }

    /**
     * Sets current time.
     *
     * @param ct the ct
     */
    public static void setCurrentTime(double ct) {
        currentTime = ct;
    }

    private static Vector3dInterface velocityFromAcceleration(int i, final StateInterface y, double h) {
        Vector3dInterface totalAcc = new Vector3D(0, 0, 0);
        for (int k = 0; k < y.getPositions().size(); k++) {
            if (i != k) {
                Vector3dInterface acc = y.getPositions().get(i).clone();
                double squareDist = pow(y.getPositions().get(i).dist(y.getPositions().get(k)), 2);
                acc = y.getPositions().get(k).sub(acc); // Get the force vector
                double den = sqrt(squareDist);

                /*if (!checkedCollisions && CHECK_COLLISIONS)
                    CollisionDetector.checkCollided(simulation.getSystem().getCelestialBodies().get(i), simulation.getSystem().getCelestialBodies().get(k), den);
                 */

                if (den != 0) {
                    acc = acc.mul(1 / den); // Normalise to length 1
                    acc = acc.mul((G * simulation.getSystem().getCelestialBodies().get(k).getMASS()) / squareDist); // Convert force to acceleration
                }

                totalAcc = totalAcc.addMul(h, acc);
                // p = h*acc(derivative of velocity)
            }
        }
        checkedCollisions = true;
        return totalAcc;
    }

    /**
     * Gets evaluate current velocity.
     *
     * @return the evaluate current velocity lambda function
     */
    public ODEFunctionInterface evaluateCurrentVelocityFunction() {
        return evalCurrVelocity;
    }

    /**
     * Gets evaluate rate of change.
     *
     * @return the evaluate rate of change lambda function
     */
    public ODEFunctionInterface evaluateCurrentAccelerationFunction() {
        return this.evalRateOfChange;
    }

}
