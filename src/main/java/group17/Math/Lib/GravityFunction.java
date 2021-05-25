package group17.Math.Lib;

import group17.Interfaces.ODEFunctionInterface;
import group17.Interfaces.RateInterface;
import group17.Interfaces.StateInterface;
import group17.Interfaces.Vector3dInterface;
import group17.System.State.RateOfChange;
import group17.Utils.CollisionDetector;
import org.jetbrains.annotations.Contract;

import static group17.Main.simulation;
import static group17.Utils.Config.CHECK_COLLISIONS;
import static group17.Utils.Config.G;
import static java.lang.StrictMath.pow;
import static java.lang.StrictMath.sqrt;

/**
 * The type Gravity function.
 */
public final class GravityFunction {
    private static volatile boolean checkedCollisions;
    private static volatile double currentTime;

    /**
     * Function that returns the rate of change of a state at a point in time
     */
    private final ODEFunctionInterface evalRateOfChange = (h, y) -> {
        final RateInterface newRate = new RateOfChange();
        for (int i = 0; i < y.getPositions().size(); i++) {
            newRate.getVelocities().add(
                    velocityFromAcceleration(i, y, 1));
        }
        return newRate;
    };

    /**
     * Function that returns the rate of change of a state at a point in time, added
     * to the current state velocities
     */
    private final ODEFunctionInterface evalCurrVelocity = (t, y) -> {
        final RateInterface newRate = new RateOfChange();
        double dt = t - currentTime;
        for (int i = 0; i < y.getPositions().size(); i++) {
            newRate.getVelocities().add(
                    y.getRateOfChange().getVelocities().get(i)
                            .add(velocityFromAcceleration(i, y, dt)));
        }
        return newRate;
    };

    /**
     * Instantiates a new Gravity function.
     */
    @Contract(pure = true)
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

                if (!checkedCollisions && CHECK_COLLISIONS)
                    CollisionDetector.checkCollided(simulation.getSystem().getCelestialBodies().get(i), simulation.getSystem().getCelestialBodies().get(k), den);

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
     * @return the evaluate current velocity
     */
    @Contract(pure = true)
    public ODEFunctionInterface getEvaluateCurrentVelocity() {
        return evalCurrVelocity;
    }

    /**
     * Gets evaluate rate of change.
     *
     * @return the evaluate rate of change
     */
    @Contract(pure = true)
    public ODEFunctionInterface getEvaluateRateOfChange() {
        return this.evalRateOfChange;
    }

}
