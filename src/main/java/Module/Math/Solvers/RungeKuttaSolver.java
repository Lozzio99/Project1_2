package Module.Math.Solvers;


import Module.Math.Functions.ODEFunctionInterface;
import Module.Math.Vector3dInterface;
import Module.System.State.RateInterface;
import Module.System.State.RateOfChange;
import Module.System.State.StateInterface;

/**
 * The type Runge kutta 4 th solver.
 * Fourth order solver for First Order ODE
 */

//OK around 10^-2/10^-3 error
public class RungeKuttaSolver implements ODESolverInterface<Vector3dInterface> {


    private final ODEFunctionInterface<Vector3dInterface> f;

    /**
     * Instantiates a new Runge kutta 4 th solver.
     *
     * @param f the function
     */
    public RungeKuttaSolver(final ODEFunctionInterface<Vector3dInterface> f) {
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
    public StateInterface<Vector3dInterface> step(ODEFunctionInterface<Vector3dInterface> f, double t, final StateInterface<Vector3dInterface> y, double h) {
        RateInterface<Vector3dInterface> k1, k2, k3, k4, k5;
        k1 = f.call(t, y);
        k2 = f.call(t + (h / 2), y.addMul(0.5 * h, k1));
        k3 = f.call(t + (h / 2), y.addMul(0.5 * h, k2));
        k4 = f.call(t + h, y.addMul(h, k3));
        k5 = new RateOfChange(k1.get().sumOf(k2.get(), k2.get(), k3.get(), k3.get(), k4.get()));
        return y.addMul(h, new RateOfChange(k5.get().div(6)));
        //return y.addMul(1,(k5.div(6).multiply(h)));  //to make it work with the numerical test
    }


    @Override
    public ODEFunctionInterface<Vector3dInterface> getFunction() {
        return this.f;
    }

    @Override
    public String toString() {
        return "RungeKutta4thSolver{" +
                "function used =" + f +
                '}';
    }


}
