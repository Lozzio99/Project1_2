package group17.Math.Solvers;

import group17.Interfaces.ODEFunctionInterface;
import group17.Interfaces.ODESolverInterface;
import group17.Interfaces.RateInterface;
import group17.Interfaces.StateInterface;

import static group17.Utils.Config.DEBUG;

/**
 * The type Old runge kutta.
 */
public class OldRungeKutta implements ODESolverInterface {

    private final ODEFunctionInterface f;
    private boolean checked;
    private double currentTime;

    /**
     * Instantiates a new Old runge kutta.
     * @param f the f
     */
    public OldRungeKutta(final ODEFunctionInterface f) {
        this.f = f;
    }

    /**
     * Old state interface.
     *
     * @param f the f
     * @param t the t
     * @param y the y
     * @param h the h
     * @return the state interface
     */
    @Override
    public StateInterface step(ODEFunctionInterface f, double t, final StateInterface y, double h) {
        StateInterface k1, k2, k3, k4, kk;
        checked = false;
        RateInterface velocity = y.getRateOfChange();
        k1 = y.getState(velocity);
        k1.setRateOfChange(f.call(h, y));
        k2 = y.getState(velocity.add(k1.getRateOfChange().multiply(h / 2))); //!!
        k2.setRateOfChange(f.call(h, y.add(k1.multiply(h / 2))));
        k3 = y.getState(velocity.add(k2.getRateOfChange().multiply(h / 2)));
        k3.setRateOfChange(f.call(h, y.add(k2.multiply(h / 2))));
        k4 = y.getState(velocity.add(k3.getRateOfChange().multiply(h)));
        k4.setRateOfChange(f.call(h, y.add(k3.multiply(h))));
        if (DEBUG) {
            System.out.println("k11");
            System.out.println(k1);
            System.out.println("k12");
            System.out.println(k2);
            System.out.println("k13");
            System.out.println(k3);
            System.out.println("k14");
            System.out.println(k4);
        }
        k2 = k2.multiply(2);
        k3 = k3.multiply(2);
        kk = ((k1.add(k2).add(k3).add(k4)));
        return y.add(kk.multiply(h / 6));
    }

    /**
     * Gets old f.
     *
     * @return the old f
     */
    @Override
    public ODEFunctionInterface getFunction() {
        return this.f;
    }
}
