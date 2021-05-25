package group17.Math.Solvers;

import group17.Interfaces.ODEFunctionInterface;
import group17.Interfaces.ODESolverInterface;
import group17.Interfaces.StateInterface;
import group17.Math.Lib.GravityFunction;
import org.jetbrains.annotations.Contract;

/**
 * The type Mid point solver.
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


    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        GravityFunction.setCurrentTime(t);
        GravityFunction.setChecked(false);

        return y.addMul(h, f.call(t + (h / 2), y.addMul(h / 2, f.call(t, y))));
    }

    @Override
    public ODEFunctionInterface getFunction() {
        return this.f;
    }



}
