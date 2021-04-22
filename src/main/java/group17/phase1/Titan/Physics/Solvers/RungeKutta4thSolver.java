package group17.phase1.Titan.Physics.Solvers;

import group17.phase1.Titan.Config;
import group17.phase1.Titan.Interfaces.ODEFunctionInterface;
import group17.phase1.Titan.Interfaces.ODESolverInterface;
import group17.phase1.Titan.Interfaces.RateInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.System.RateOfChange;
import group17.phase1.Titan.System.SystemState;

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
        RateInterface k21, k22, k23, k24, kv;
        StateInterface k11,k12,k13,k14,kk;
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
       // System.out.println(y.getRateOfChange().getVelocities() + " Init Velocities");

        System.out.println("StepSize: "+ h);

        System.out.println("old y");
        System.out.println(y);
        RateInterface velocity = new RateOfChange();

        velocity.setVel(y.getRateOfChange().getVelocities());


        k11 = StateInterface.clone(y).rateMul(h,RateInterface.clone(velocity)); // k11

        k21 = f.call(1,StateInterface.clone(y)).multiply(h);
        // to try - without multiply by h
        k12 = StateInterface.clone(y).rateMul(h,RateInterface.clone(velocity).add(RateInterface.clone(k21).multiply(0.5))); //!!

        k22 = f.call(1,StateInterface.clone(y).add(StateInterface.clone(k11).multiply(0.5))).multiply(h);

        k13 = StateInterface.clone(y).rateMul(h,RateInterface.clone(velocity).add(RateInterface.clone(k22).multiply(0.5)));

        k23 = f.call(1,StateInterface.clone(y).add(StateInterface.clone(k12).multiply(0.5))).multiply(h);

        k14 =StateInterface.clone(y).rateMul(h,RateInterface.clone(velocity).add(RateInterface.clone(k23)));

        k24 = f.call(1,StateInterface.clone(y).add(StateInterface.clone(k13))).multiply(h);


        System.out.println("k11");
        System.out.println(k11);
        System.out.println("k12");
        System.out.println(k12);
        System.out.println("k13");
        System.out.println(k13);
        System.out.println("k14");
        System.out.println(k14);


        k12 = k12.multiply(2);
        k13 = k13.multiply(2);

        k22 = k22.multiply(2);
        k23 = k23.multiply(2);






        kk = (k11.sumOf(k12, k13, k14)).div(6);

        kv = (k21.sumOf(k22,k23,k24)).div(6);

        //-6.80564659829616E8
        //-6.806783239281648E8

        System.out.println("kk");
        System.out.println(kk);
        y.getRateOfChange().setVel(y.getRateOfChange().add(kv).getVelocities());
        y = y.add(kk);


        System.out.println("new y");
        System.out.println(y);
        System.out.println("*******************************************************************************************************************");
        //System.exit(0);


        return y;
    }

    @Override
    public ODEFunctionInterface getFunction() {
        return this.singleCoreF;
    }
}
