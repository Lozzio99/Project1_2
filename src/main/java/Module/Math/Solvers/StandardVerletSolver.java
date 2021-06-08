package Module.Math.Solvers;

import Module.Math.Gravity.GravityFunction;
import Module.Math.Gravity.ODEFunctionInterface;
import Module.System.State.StateInterface;

/**
 * The type Standard verlet solver.
 * Second Order Solver
 */
public class StandardVerletSolver implements ODESolverInterface {

    private boolean first;
    private final ODEFunctionInterface f;
    private StateInterface prevState;


    /**
     * Instantiates a new Standard verlet solver.
     *
     * @param f the f
     */
    public StandardVerletSolver(final ODEFunctionInterface f) {
        first = true;
        this.f = f;
    }

    /**
     * Step of a Standard Verlet Algorithm:
     * x_n+1 = 2*(x_n) - (x_n-1) + f(x_n,t_n)*h^2
     * x - position, h - step size
     * Source: http://www.physics.udel.edu/~bnikolic/teaching/phys660/numerical_ode/node5.html
     *
     * @param f ordinary differential equations - function of acceleration
     * @param t current time
     * @param y current state; getPosition(0) - current position, getPosition(1) - prev position
     * @param h step size
     * @return new state
     */
    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        // next position
        GravityFunction.setChecked(false);
        StateInterface diff;
        if (first) {
            ODESolverInterface solver = new RungeKuttaSolver(new GravityFunction().evaluateCurrentAccelerationFunction());
            diff = solver.step(solver.getFunction(), t, y, h);
            first = false;
        } else {
            StateInterface subPrev = prevState.copy().multiply(-1);
            StateInterface twiceY = y.copy().multiply(2.0);
            StateInterface rateMulPart = y.copy().rateMul(h * h, f.call(1, y));
            diff = subPrev.add(twiceY).add(rateMulPart);
        }
        prevState = y.copy();
        return diff;
    }


    @Override
    public ODEFunctionInterface getFunction() {
        return this.f;
    }


    /**
     * Sets first.
     */
    public void setFirst() {
        this.first = true;
    }
}