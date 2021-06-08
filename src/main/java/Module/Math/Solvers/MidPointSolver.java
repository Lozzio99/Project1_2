package Module.Math.Solvers;

import Module.Math.Functions.ODEFunctionInterface;
import Module.System.State.RateInterface;
import Module.System.State.StateInterface;

/**
 * The type Mid point solver.
 * Second order Solver
 */
public class MidPointSolver implements ODESolverInterface {
    private final ODEFunctionInterface f;

    /**
     * Instantiates a new Mid point solver.
     *
     * @param f the f
     */
    public MidPointSolver(final ODEFunctionInterface f) {
        this.f = f;
    }

    /**
     *
     * @param f the function representing Newton's Gravitational law
     * @param y the instance  of the Simulation
     * @param h stepSize
     * @return the next state of the simulation based on A Midpoint Step
     */
    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {

        RateInterface h0 = f.call(t, y);
        RateInterface h2 = f.call(t + (h / 2), y.addMul(h, h0));
        return y.addMul(h / 2, h2);
    }

    @Override
    public ODEFunctionInterface getFunction() {
        return this.f;
    }



}
