package group17.Solvers;

import group17.Interfaces.ODEFunctionInterface;
import group17.Interfaces.ODESolverInterface;
import group17.Interfaces.RateInterface;
import group17.Interfaces.StateInterface;
import group17.Math.GravityFunction;
import org.jetbrains.annotations.Contract;

/**
 * The type Lazy runge kutta.
 * ** WORKING WITH NUMERICAL TEST RUNGE KUTTA
 * *** NOT WORKING WITHIN THE SIMULATION (goes to NaN very quickly)
 */
//@Deprecated(forRemoval = true)
public class RungeKuttaSolverLazy implements ODESolverInterface {
    private final ODEFunctionInterface f;

    /**
     * Instantiates a new Lazy runge kutta.
     *
     * @param f the f
     */
    @Contract(pure = true)
    public RungeKuttaSolverLazy(final ODEFunctionInterface f) {
        this.f = f;
    }

    /**
     * Formula
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
    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        GravityFunction.setCurrentTime(t);
        GravityFunction.setChecked(false);
        RateInterface k1 = f.call(t, y).multiply(h);
        RateInterface k2 = f.call(t + 0.5 * h, y.addMul(0.5, k1)).multiply(h);
        RateInterface k3 = f.call(t + 0.5 * h, y.addMul(0.5, k2)).multiply(h);
        RateInterface k4 = f.call(t + h, y.addMul(1, k3)).multiply(h);
        RateInterface newRate = k1.sumOf(k2.multiply(2), k3.multiply(2), k4).div(6);
        return y.addMul(h, newRate.div(h));
    }

    @Override
    public ODEFunctionInterface getFunction() {
        return this.f;
    }

}
