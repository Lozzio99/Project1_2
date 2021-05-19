package group17.Math.Solvers;

import group17.Interfaces.*;
import group17.Math.Utils.Vector3D;
import group17.System.CollisionDetector;
import group17.System.RateOfChange;
import group17.System.SystemState;
import org.jetbrains.annotations.Contract;

import static group17.Config.DEBUG;
import static group17.Config.G;
import static group17.Main.simulation;
import static java.lang.StrictMath.pow;
import static java.lang.StrictMath.sqrt;

public class RungeKutta4thSolver implements ODESolverInterface {

    private ODEFunctionInterface singleCoreF;
    private boolean checked;
    private double currentTime;
    ODEFunctionInterface oldF = (t, y) ->
    {
        //TODO: check for t here, why do we need it
        RateInterface state = new RateOfChange();
        for (int i = 0; i < y.getPositions().size(); i++) {
            Vector3dInterface totalAcc = new Vector3D(0, 0, 0);
            for (int k = 0; k < y.getPositions().size(); k++) {
                if (i != k) {
                    Vector3dInterface acc = y.getPositions().get(i).clone();
                    double squareDist = pow(y.getPositions().get(i).dist(y.getPositions().get(k)), 2);
                    acc = y.getPositions().get(k).sub(acc); // Get the force vector
                    double den = sqrt(squareDist);
                    if (den != 0) {
                        acc = acc.mul(1 / den); // Normalise to length 1
                        acc = acc.mul((G * simulation.getSystem().getCelestialBodies().get(k).getMASS()) / squareDist); // Convert force to acceleration
                    }
                    totalAcc = totalAcc.addMul(t - currentTime, acc);
                    // p = h*acc(derivative of velocity)
                }
            }
            state.getVelocities().add(totalAcc);
        }
        return state;
    };

    @Contract(pure = true)
    public RungeKutta4thSolver() {
        this.singleCoreF = (t, y) -> {
            RateInterface state = new RateOfChange();
            for (int i = 0; i < y.getPositions().size(); i++) {
                Vector3dInterface totalAcc = new Vector3D(0, 0, 0);
                for (int k = 0; k < y.getPositions().size(); k++) {
                    if (i != k) {
                        Vector3dInterface acc = y.getPositions().get(i).clone();
                        double squareDist = pow(y.getPositions().get(i).dist(y.getPositions().get(k)), 2);
                        acc = y.getPositions().get(k).sub(acc); // Get the force vector
                        double den = sqrt(squareDist);
                        if (!checked) {
                            CollisionDetector.checkCollided(simulation.getSystem().getCelestialBodies().get(i),
                                    simulation.getSystem().getCelestialBodies().get(k), den);
                        }
                        /*
                            ! Important !
                            if two bodies collapses into the same point
                            that would crash to NaN and consequently
                            the same in all the system
                            UPDATE :::: Mark BODIES AS COLLIDED
                        */
                        if (den != 0) {
                            acc = acc.mul(1 / den); // Normalise to length 1
                            acc = acc.mul((G * simulation.getSystem().getCelestialBodies().get(k).getMASS()) / squareDist); // Convert force to acceleration
                        }
                        totalAcc = totalAcc.addMul(t - currentTime, acc);
                        // p = h*acc(derivative of velocity)
                    }
                }
                state.getVelocities().add(y.getRateOfChange().getVelocities().get(i).add(totalAcc));
            }
            checked = true;
            return state;
        };
    }

    @Override
    public StateInterface step(ODEFunctionInterface f, double t, final StateInterface y, double h) {
       /*
        state k1 = h * this.f.f_y(this.t,w);                      rate t0     , y0        -> current rate
        state k2 = h * this.f.f_y(this.t + (h/2), w + (k1/2));    rate t0+h/2 , y0+rate0/2-> some diff rate
        state k3 = h * this.f.f_y(this.t + (h/2), w + (k2/2));    rate t0+h/2 , y0+rate1/2-> some diff rate
        state k4 = h * this.f.f_y(this.t+h,w + k3);               rate t0+h   , y0+rate2  -> some diff rate
        state1 = state0 + ((1/6.) * (k1 + 2*k2 + 2*k3 + k4));     state t0+h  , y0+rate   -> a new state
       */
        currentTime = t;
        RateInterface k1, k2, k3, k4, k5;
        StateInterface clone = y.copy();
        k1 = f.call(t, y).multiply(h);
        k2 = f.call(t + (h / 2), y.addMul(0.5, k1)).multiply(h);
        k3 = f.call(t + (h / 2), y.addMul(0.5, k2)).multiply(h);
        k4 = f.call(t + h, y.addMul(1, k3)).multiply(h);
        k5 = k1.sumOf(k2.multiply(2), k3.multiply(2), k4);
        if (DEBUG) {
            System.out.println(y + "    t :" + t + "   h : " + h);
            System.out.println("k1");
            System.out.println(k1);
            System.out.println("k2");
            System.out.println(k2);
            System.out.println("k3");
            System.out.println(k3);
            System.out.println("k4");
            System.out.println(k4);
            System.out.println("k5");
            System.out.println(k5);
            System.out.println("____________________________");
        }
        StateInterface result = y.addMul(1, k5.div(6));
        return result;
    }

    public StateInterface old(ODEFunctionInterface f, double t, final StateInterface y, double h) {
        RateInterface v21, v22, v23, v24, kv;
        StateInterface s11, s12, s13, s14, kk;
        checked = false;

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

    private boolean checkDifferent(StateInterface a, StateInterface b) {
        for (int i = 0; i < a.getPositions().size(); i++) {
            if (!a.getPositions().get(i).equals(b.getPositions().get(i))) {
                return false;
            }
        }
        for (int i = 0; i < a.getRateOfChange().getVelocities().size(); i++) {
            if (!a.getRateOfChange().getVelocities().get(i).equals(b.getRateOfChange().getVelocities().get(i)))
                return false;
        }
        return true;
    }


    @Override
    public ODEFunctionInterface getFunction() {
        return this.singleCoreF;
    }

    @Override
    public String toString() {
        return "RungeKutta4thSolver{" +
                "function used =" + singleCoreF +
                '}';
    }

    @Override
    public void setF(ODEFunctionInterface f) {
        this.singleCoreF = f;
    }

}
