package group17.Math.Solvers;

import group17.Interfaces.ODEFunctionInterface;
import group17.Interfaces.ODESolverInterface;
import group17.Interfaces.StateInterface;
import group17.Math.Lib.GravityFunction;
import org.jetbrains.annotations.Contract;

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
    @Contract(pure = true)
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
        GravityFunction.setCurrentTime(t);
        GravityFunction.setChecked(false);
        StateInterface eulerStep = y.copy().addMul(h / 2, f.call(t, y));
        return y.addMul(h, f.call(t + (h / 2), eulerStep));
    }

    @Override
    public ODEFunctionInterface getFunction() {
        return this.f;
    }



}
