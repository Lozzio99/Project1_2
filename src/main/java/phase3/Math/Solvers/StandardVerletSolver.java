package phase3.Math.Solvers;

import phase3.Math.Functions.ODEFunctionInterface;
import phase3.System.State.RateOfChange;
import phase3.System.State.StateInterface;

/**
 * The type Standard verlet solver.
 * Second Order Solver
 */
public class StandardVerletSolver<E> implements ODESolverInterface<E> {

    private boolean first;
    private final ODEFunctionInterface<E> f;
    private StateInterface<E> y_1;


    /**
     * Instantiates a new Standard verlet solver.
     *
     * @param f the f
     */
    public StandardVerletSolver(final ODEFunctionInterface<E> f) {
        first = true;
        this.f = f;
    }

    /**
     * Step of a Standard Verlet Algorithm:
     * y_n+1 = 2*(y_n) - (y_n-1) + f(y_n,t_n)*h^2
     * y - position, h - step size
     * Source: http://www.physics.udel.edu/~bnikolic/teaching/phys660/numerical_ode/node5.html
     *
     * @param f ordinary differential equations - function of acceleration
     * @param t current time
     * @param y current state; getPosition(0) - current position, getPosition(1) - prev position
     * @param h step size
     * @return new state
     */
    @Override
    @SuppressWarnings("{unchecked,unsafe}")
    public StateInterface<E> step(ODEFunctionInterface<E> f, double t, StateInterface<E> y, double h) {
        // next position
        StateInterface<E> next;
        if (first) {
            return new RungeKuttaSolver<>(f).step(f, t, y, h);
        } else {
            StateInterface<E> sub = y_1.addMul(h * h, f.call(t, y));
            sub = y.addMul(1,
                    new RateOfChange<>(y.get().clone()))  // y  + y ...
                    .addMul(-1,                              // ... + (-1 x sub)
                            new RateOfChange<>(sub.get().clone()));
            y_1 = y;
            return sub;
        }
    }


    @Override
    public ODEFunctionInterface<E> getFunction() {
        return this.f;
    }


    /**
     * Sets first.
     */
    public void setFirst() {
        this.first = true;
    }

    @Override
    public String toString() {
        return "StandardVerletSolver";
    }
}