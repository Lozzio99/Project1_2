package Module.Math.Solvers;

import Module.Math.Functions.ODEFunctionInterface;
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
        StateInterface<E> mid = y.addMul(h / 2, f.call(t, y));
        StateInterface<E> next = y.addMul(h, f.call(t + (h / 2), mid));
        next.getRateOfChange().set(mid.getRateOfChange().get());
        return next;
    }

    @Override
    public ODEFunctionInterface<E> getFunction() {
        return this.f;
    }

}
