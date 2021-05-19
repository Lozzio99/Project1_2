package group17.Math.Utils;

import group17.Interfaces.ODEFunctionInterface;
import group17.Interfaces.RateInterface;
import group17.Interfaces.StateInterface;

/**
 * Implements a free fall ODE
 * Solution to this function is a free fall equation: x = v*t - 0.5*g*t^2
 * Where x - position, v - velocity, t - time, g - gravitational constant of Earth
 * Used for testing Verlet Solvers
 */
public class FreeFallFunction implements ODEFunctionInterface {

    public static final double CONSTANT_G = 9.81998;

    @Override
    public RateInterface call(double t, StateInterface y) {
        Vector3D next_vector = new Vector3D();
        next_vector.setX(y.getPositions().get(0).getX());
        next_vector.setY(y.getPositions().get(0).getY());
        next_vector.setZ(-CONSTANT_G);
        RateInterface next_rate = y.copy().getRateOfChange();
        next_rate.getVelocities().set(0, next_vector);
        return next_rate;
    }

    /**
     * For Higher cpu usages, shuts down all waiting or busy threads
     */
}
