package group17.phase1.Titan.Physics.Solvers;

import group17.phase1.Titan.Config;
import group17.phase1.Titan.Interfaces.ODEFunctionInterface;
import group17.phase1.Titan.Interfaces.ODESolverInterface;
import group17.phase1.Titan.Interfaces.StateInterface;

public class RungeKutta4thSolver implements ODESolverInterface {

    private static double t, tf;
    private final ODEFunctionInterface singleCoreF;

    public RungeKutta4thSolver() {
        this.singleCoreF = new EulerSolver().getFunction();
    }

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {
        StateInterface[] states = new StateInterface[ts.length];
        tf = ts[ts.length - 1];
        t = ts[0];

        for (int i = 0; i < ts.length - 1; i++) {
            double h = ts[i + 1] - ts[i];
            Config.STEP_SIZE = h;
            states[i] = this.step(f, h, y0, h);
            t += h;
        }
        states[states.length - 1] = y0.addMul(t, f.call(ts[ts.length - 1] - t, y0));
        return states;
    }

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {
        tf = tf;
        Config.STEP_SIZE = h;
        StateInterface[] path = new StateInterface[(int) (Math.round(tf / h)) + 1];
        t = 0;
        for (int i = 0; i < path.length - 1; i++) {
            path[i] = this.step(f, t, y0, h);
            t += h;
        }
        path[path.length - 1] = this.step(f, tf, y0, tf - t);
        return path;
    }

    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        StateInterface k1, k2, k3, k4, kk;

        /*  f (has method f.calL) same as f2

        def RK4step(f,w,dt):
        k1 = dt*f(w)
        k2 = dt*f(w+0.5*k1)
        k3 = dt*f(w+0.5*k2)
        k4 = dt*f(w+k3)
        return w0 + (k1+2*k2+2*k3+k4)/6;


        // 2*k2 ? 2*k3?e k1 = h * f.f_y(t,w);
        double k2 = h * f.f_y(t + (h/2), w + (k1/2));
        double k3 = h * f.f_y(t + (h/2), w + (k2/2));
        double k4 = h * f.f_y(t+h,w + k3);
        return  w + ((1/6.) * (k1 + 2*k2 + 2*k3 + k4));
         */
        System.out.println("old y");
        System.out.println(y);

        k1 = y.addMul(h, f.call(h, StateInterface.clone(y)));
        System.out.println("k1");
        System.out.println(k1);
        k2 = y.addMul(h / 2, f.call(h / 2, StateInterface.clone(k1).div(2))).multiply(2);
        System.out.println("k2");

        System.out.println(k2);

        k3 = y.addMul(h / 2, f.call(h / 2, StateInterface.clone(k2).div(2))).multiply(2);
        System.out.println("k3");

        System.out.println(k3);
        k4 = y.addMul(h, f.call(h, StateInterface.clone(k3)));
        System.out.println("k4");

        System.out.println(k4);
        kk = k1.sumOf(k2, k3, k4);


        kk = kk.div(6);

        System.out.println("kk");
        System.out.println(kk);
        y = StateInterface.clone(kk);


        System.out.println("new y");
        System.out.println(y);
        System.exit(0);


        return y;
    }

    @Override
    public ODEFunctionInterface getFunction() {
        return this.singleCoreF;
    }
}

