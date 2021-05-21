package group17.Math.Solvers;

import group17.Interfaces.ODEFunctionInterface;
import group17.Interfaces.ODESolverInterface;
import group17.Interfaces.RateInterface;
import group17.Interfaces.StateInterface;
import group17.System.GravityFunction;
import org.jetbrains.annotations.Contract;

/**
 * The type Lazy runge kutta.
 */
public class LazyRungeKutta implements ODESolverInterface {
    private final ODEFunctionInterface f;

    /**
     * Instantiates a new Lazy runge kutta.
     *
     * @param f the f
     */
    @Contract(pure = true)
    public LazyRungeKutta(final ODEFunctionInterface f) {
        this.f = f;
    }

    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        GravityFunction.setCurrentTime(t);
        GravityFunction.setChecked(false);
        RateInterface k1 = f.call(t, y);
        RateInterface k2 = f.call(t + 0.5 * h, y.addMul(0.5, k1));
        RateInterface k3 = f.call(t + 0.5 * h, y.addMul(0.5, k2));
        RateInterface k4 = f.call(t + h, y.addMul(1, k3));
        RateInterface newRate = k1.div(6).add(k2.div(3)).add(k3.div(3)).add(k4.div(6));
        return y.addMul(h, newRate);
    }

    @Override
    public ODEFunctionInterface getFunction() {
        return this.f;
    }

}
