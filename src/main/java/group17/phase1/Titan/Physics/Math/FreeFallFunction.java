package group17.phase1.Titan.Physics.Math;

import group17.phase1.Titan.Interfaces.ODEFunctionInterface;
import group17.phase1.Titan.Interfaces.RateInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.System.RateOfChange;

import java.util.ArrayList;

/**
 * Implements a free fall ODE
 * Solution to this function is a free fall equation: x = v*t - 0.5*g*t^2
 * Where x - position, v - velocity, t - time, g - gravitational constant of Earth
 * Used for testing Verlet Solvers
 */
public class FreeFallFunction implements ODEFunctionInterface {

    private RateOfChange next_rate;
    private ArrayList<Vector3dInterface> stateList;
    public static final double CONSTANT_G = 9.81998;

    @Override
    public RateInterface call(double t, StateInterface y) {
        Vector3D next_vector = new Vector3D();
        next_vector.setX(y.getPositions().get(0).getX());
        next_vector.setY(y.getPositions().get(0).getY());
        next_vector.setZ(-CONSTANT_G);

        next_rate = new RateOfChange();
        stateList = new ArrayList<>();
        stateList.add(next_vector);
        next_rate.setVel(stateList);

        return next_rate;
    }

    /**
     * For Higher cpu usages, shuts down all waiting or busy threads
     */
    @Override
    public void shutDown() {

    }
}
