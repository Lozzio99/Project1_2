package group17.Solvers;

import group17.Interfaces.ODEFunctionInterface;
import group17.Interfaces.ODESolverInterface;
import group17.Interfaces.RateInterface;
import group17.Interfaces.StateInterface;
import group17.Math.GravityFunction;
import org.jetbrains.annotations.Contract;

/**
 * The type Runge kutta 4 th solver.
 * Fourth order solver for First Order ODE
 */

//OK around 10^-2/10^-3 error
public class RungeKuttaSolver implements ODESolverInterface {


    private final ODEFunctionInterface f;

    /**
     * Instantiates a new Runge kutta 4 th solver.
     *
     * @param f the function
     */
    @Contract(pure = true)
    public RungeKuttaSolver(final ODEFunctionInterface f) {
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
    public StateInterface step(ODEFunctionInterface f, double t, final StateInterface y, double h) {
        GravityFunction.setCurrentTime(t);
        GravityFunction.setChecked(false);
        RateInterface k1, k2, k3, k4, k5;
        k1 = f.call(t, y);
        k2 = f.call(t + (h / 2), y.addMul(0.5 * h, k1));
        k3 = f.call(t + (h / 2), y.addMul(0.5 * h, k2));
        k4 = f.call(t + h, y.addMul(h, k3));
        k5 = k1.sumOf(k2, k2, k3, k3, k4);
        return y.addMul(h, k5.div(6));
        //return y.addMul(1,(k5.div(6).multiply(h)));  //to make it work with the numerical test
    }


    @Override
    public ODEFunctionInterface getFunction() {
        return this.f;
    }

    @Override
    public String toString() {
        return "RungeKutta4thSolver{" +
                "function used =" + f +
                '}';
    }


}
