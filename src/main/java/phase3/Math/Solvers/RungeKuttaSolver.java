package phase3.Math.Solvers;


import phase3.Math.Functions.ODEFunctionInterface;
import phase3.System.State.RateInterface;
import phase3.System.State.StateInterface;

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
     * @return the next state of the simulationType based on Runge-Kutta 4 Step
     */
    public StateInterface<E> step(ODEFunctionInterface<E> f, double t, final StateInterface<E> y, double h) {
        RateInterface<E> k1, k2, k3, k4;
        k1 = f.call(t, y);
        k2 = f.call(t + (h / 2), y.addMul(h * 0.5, k1));
        k3 = f.call(t + (h / 2), y.addMul(h * 0.5, k2));
        k4 = f.call(t + h, y.addMul(h, k3));
        return y.addMul(h / 6, k1)
                .addMul(h / 3, k2)
                .addMul(h / 3, k3)
                .addMul(h / 6, k4);
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
