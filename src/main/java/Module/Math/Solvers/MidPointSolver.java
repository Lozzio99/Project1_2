package Module.Math.Solvers;

import Module.Math.ADT.Vector3dInterface;
import Module.Math.Functions.ODEFunctionInterface;
import Module.System.State.StateInterface;
import Module.System.State.SystemState;

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
        return y.addMul(h, f.call(t + (h / 2), y.addMul(h / 2, f.call(t, y))));
    }

    @Override
    public ODEFunctionInterface<E> getFunction() {
        return this.f;
    }

    @Override
    public String toString() {
        return "MidPointSolver";
    }
}
