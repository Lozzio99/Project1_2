package Module.Math.Solvers;


import Module.Math.Functions.ODEFunctionInterface;
import Module.System.State.RateInterface;
import Module.System.State.StateInterface;

/**
 * The type Runge kutta 4 th solver.
 * Fourth order solver for First Order ODE
 */

//OK around 10^-2/10^-3 error
public class RungeKuttaSolver<E> implements ODESolverInterface<E> {


    private final ODEFunctionInterface<E> f;

    /**
     * Instantiates a new Runge kutta 4 th solver.
     *
     * @param f the function
     */
    public RungeKuttaSolver(final ODEFunctionInterface<E> f) {
        this.f = f;
    }



    /**
     *
     * k1=f(tn,yn)
     * k2=f(tn+h2,yn+h/2*k1)
     * k3=f(tn+h2,yn+h/2*k2)
     * k4=hf(tn+h,yn+h*k3)
     * kk = (k1 + 2*k2 + 2*k3 + k4)/6
     *
     *
     * @param f the function representing Newton's Gravitational law
     * @param y the instance of the Simulation
     * @param h stepSize
     * @return the next state of the simulation based on Runge-Kutta 4 Step
     */
    public StateInterface<E> step(ODEFunctionInterface<E> f, double t, final StateInterface<E> y, double h) {

        StateInterface<E> state;
        RateInterface<E> k1, k2, k3;
        state = y.addMul(h / 6, k1 = f.call(t, y));
        state = state.addMul(h / 3, k2 = f.call(t + (h / 2), y.addMul(0.5 * h, k1)));
        state = state.addMul(h / 3, k3 = f.call(t + (h / 2), y.addMul(0.5 * h, k2)));
        return state.addMul(h / 6, f.call(t + h, y.addMul(h, k3)));
    }

    @Override
    public ODEFunctionInterface<E> getFunction() {
        return this.f;
    }

    @Override
    public String toString() {
        return "RungeKutta4thSolver";
    }


}
