package group17.Math.Solvers;

import group17.Interfaces.ODEFunctionInterface;
import group17.Interfaces.ODESolverInterface;
import group17.Interfaces.RateInterface;
import group17.Interfaces.StateInterface;
import group17.System.GravityFunction;
import group17.System.State.SystemState;

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
        RateInterface v21, v22, v23, v24, kv;
        StateInterface s11, s12, s13, s14, kk;
        GravityFunction.setChecked(false);
        RateInterface velocity = y.getRateOfChange();
        s11 = y.rateMul(h, velocity);
        v21 = f.call(h, y);
        s12 = y.rateMul(h, velocity.add(v21.div(2))); //!!
        v22 = f.call(h, y.add(s11.div(2)));
        s13 = y.rateMul(h, velocity.add(v22.div(2)));
        v23 = f.call(h, y.add(s12.div(2)));
        s14 = y.rateMul(h, velocity.add(v23));
        v24 = f.call(h, y.add(s13));

        if (DEBUG) {
            System.out.println("k11");
            System.out.println(s11);
            System.out.println("k12");
            System.out.println(s12);
            System.out.println("k13");
            System.out.println(s13);
            System.out.println("k14");
            System.out.println(s14);
        }

        s12 = s12.multiply(2);
        s13 = s13.multiply(2);
        v22 = v22.multiply(2);
        v23 = v23.multiply(2);
        kk = (s11.sumOf(s12, s13, s14)).div(6);
        kv = (v21.sumOf(v22, v23, v24)).div(6);

        //-6.80564659829616E8
        //-6.806783239281648E8


        return new SystemState(y.add(kk), (y.getRateOfChange().add(kv)));
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
