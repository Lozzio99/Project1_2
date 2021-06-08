package Module.Math.Solvers;


import Module.Math.Gravity.GravityFunction;
import Module.Math.Gravity.ODEFunctionInterface;
import Module.System.State.RateInterface;
import Module.System.State.StateInterface;

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
        RateInterface k1 = (RateInterface) f.call(t, y).multiply(h);
        RateInterface k2 = (RateInterface) f.call(t + 0.5 * h, y.addMul(0.5, k1)).multiply(h);
        RateInterface k3 = (RateInterface) f.call(t + 0.5 * h, y.addMul(0.5, k2)).multiply(h);
        RateInterface k4 = (RateInterface) f.call(t + h, y.addMul(1, k3)).multiply(h);
        RateInterface newRate = (RateInterface) k1.sumOf(k2.multiply(2), k3.multiply(2), k4).div(6);
        return y.addMul(h, (RateInterface) newRate.div(h));
    }

    @Override
    public ODEFunctionInterface getFunction() {
        return this.f;
    }

}
