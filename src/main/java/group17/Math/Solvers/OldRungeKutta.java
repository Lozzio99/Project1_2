package group17.Math.Solvers;

import group17.Interfaces.ODEFunctionInterface;
import group17.Interfaces.ODESolverInterface;
import group17.Interfaces.RateInterface;
import group17.Interfaces.StateInterface;
import org.jetbrains.annotations.Contract;

import static group17.Utils.Config.DEBUG;

/**
 *
 * The type Old runge kutta.
 * Fourth order solver for second order ODE
 */
public class OldRungeKutta implements ODESolverInterface {

    private final ODEFunctionInterface f;
    private boolean checked;
    private double currentTime;

    /**
     * Instantiates a new Old runge kutta.
     * @param f the f
     */
    @Contract(pure = true)
    public OldRungeKutta(final ODEFunctionInterface f) {
        this.f = f;
    }

    /**
     * Step of Rk4 for 2nd order ODE
     *
     * <a href=https://www.youtube.com/watch?v=TjZgQa2kec0&t=381s&ab_channel=LettherebemathLettherebemath>source link</a>
     * Formula:
     *  dx/dt=x ̇=v;   dx = v∗dt;
     *  dv/dt=v ̇=a=F(t,x,v);     dv = a*dt = F(t,x,v)∗dt;
     *
     * dx1=h∗k_x1=h∗v;		       dv1=h∗k_v1=h∗F(t,x,v);
     * dx2=h∗k_x2=h∗(v+(d∗v1)/2);   dv2=h∗k_v2=h∗F(t+h/2,x+dx1/2,v+dv1/2);
     * dx3=h∗k_x3=h∗(v+(d∗v2)/2);   dv3=h∗k_v3=h∗F(t+h/2,x+dx2/2,v+dv2/2);
     * dx4=h∗k_x4=h∗(v+dv_3);	   dv4=h∗k_v4=h∗F(t+h,x+dx3,v+dv1);
     *
     * dx=(dx1+2∗dx2+2∗dx3+dx4)/6    dv=(dv1+2∗dv2+2∗dv3+dv4)/6
     * x(t+h)=x(t)+dx;    		 v(t+h)=v(t)+vx;
     *
     *
     * @param f the function representing Newton's Gravitational law
     * @param y the instance of the Simulation
     * @param h stepSize
     * @return the next state of the simulation based on Runge-Kutta 4 Step
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
