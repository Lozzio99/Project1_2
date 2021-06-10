package Module.Math.Solvers;

import Module.Math.Functions.ODEFunctionInterface;
import Module.System.State.RateInterface;
import Module.System.State.StateInterface;

/**
 * The type Mid point solver.
 * Second order Solver
 */
public class MidPointSolver<E> implements ODESolverInterface<E> {
    private final ODEFunctionInterface<E> f;

    /**
     * Instantiates a new Mid point solver.
     *
     * @param f the f
     */
    public MidPointSolver(final ODEFunctionInterface<E> f) {
        this.f = f;
    }

    /**
     * @param f the function representing Newton's Gravitational law
     * @param y the instance  of the Simulation
     * @param h stepSize
     * @return the next state of the simulation based on A Midpoint Step
     */
    @Override
    public StateInterface<E> step(ODEFunctionInterface<E> f, double t, StateInterface<E> y, double h) {
        RateInterface<E> k0 = f.call(t, y);
        RateInterface<E> k1 = f.call(t + (h / 2), y.addMul(h, k0));
        return y.addMul(h / 2, k1);
    }

    @Override
    public ODEFunctionInterface<E> getFunction() {
        return this.f;
    }


}
