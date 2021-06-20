package phase3.Math.Solvers;


import phase3.Math.Functions.ODEFunctionInterface;
import phase3.System.State.StateInterface;

/**
 * The type Euler solver.
 * First Order Solver
 */
public class EulerSolver<E> implements ODESolverInterface<E> {

    /**
     * The Single core f.
     */
    private final ODEFunctionInterface<E> f;

    /**
     * Instantiates a new Euler solver.
     *
     * @param f the f
     */
    public EulerSolver(final ODEFunctionInterface<E> f) {
        this.f = f;
    }

    /**
     * Formula
     * y1 = y0 + h*f(t,y0);
     *
     * @param f the function representing Newton's Gravitational law
     * @param y the instance  of the Simulation
     * @return the next state of the simulationType based on a Euler Step
     */
    @Override
    public StateInterface<E> step(ODEFunctionInterface<E> f, double t, StateInterface<E> y, double h) {
        return y.addMul(h, f.call(t, y));
    }

    @Override
    public ODEFunctionInterface<E> getFunction() {
        return f;
    }


    @Override
    public String toString() {
        return "EulerSolver";
    }


}
