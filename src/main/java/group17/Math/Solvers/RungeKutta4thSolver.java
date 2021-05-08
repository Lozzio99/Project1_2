package group17.Math.Solvers;

import group17.Interfaces.*;
import group17.Math.Vector3D;
import group17.System.RateOfChange;

import static group17.Config.DEBUG;
import static group17.Config.G;
import static group17.Main.simulationInstance;

public class RungeKutta4thSolver implements ODESolverInterface {

    private int prev;
    private ODEFunctionInterface singleCoreF;

    public RungeKutta4thSolver() {
        this.singleCoreF = (t, y) -> {
            if (prev != 0 && prev < simulationInstance.getSystem().getCelestialBodies().size()) {
                return null;
            }
            prev = simulationInstance.getSystem().getCelestialBodies().size();
            for (int i = 0; i < y.getPositions().size(); i++) {
                Vector3dInterface totalAcc = new Vector3D(0, 0, 0);
                for (int k = 0; k < y.getPositions().size(); k++) {
                    if (i != k) {
                        Vector3dInterface acc = y.getPositions().get(i).clone();
                        double squareDist = Math.pow(y.getPositions().get(i).dist(y.getPositions().get(k)), 2);
                        acc = y.getPositions().get(k).sub(acc); // Get the force vector
                        double den = Math.sqrt(squareDist);
                    /*
                        ! Important !
                        if two bodies collapses into the same point
                        that would crash to NaN and consequently
                        the same in all the system
                    */
                        acc = acc.mul(1 / (den == 0 ? 0.0000001 : den)); // Normalise to length 1
                        acc = acc.mul((G * simulationInstance.getSystem().getCelestialBodies().get(k).getMASS()) / (squareDist == 0 ? 0.0000001 : squareDist)); // Convert force to acceleration
                        totalAcc = totalAcc.addMul(t, acc);
                        // p = h*acc(derivative of velocity)
                    }
                }  // y1 =y0 + h*acc
                // y1 = y0 + p
                //FIXME : why did we had to change this?
                y.getRateOfChange().getVelocities()
                        .set(i, totalAcc.clone());
            }
            return y.getRateOfChange();
        };
    }


    @Override
    public StateInterface step(ODEFunctionInterface f, double t, final StateInterface y, double h) {

        RateInterface v21, v22, v23, v24, kv;
        StateInterface k11, k12, k13, k14, kk;


        /*  f (has method f.calL) same as f2
        double RKStep (f, w, t, h )
        double k1 = h * f.f_y(t,w);
        double k2 = h * f.f_y(t + (h/2), w + (k1/2));
        double k3 = h * f.f_y(t + (h/2), w + (k2/2));
        double k4 = h * f.f_y(t+h,w + k3);
        return  w + ((1/6.) * (k1 + 2*k2 + 2*k3 + k4));
         */

        RateInterface velocity = new RateOfChange();
        velocity.setVel(y.getRateOfChange().getVelocities());
        k11 = y.rateMul(h, velocity);
        v21 = f.call(1, StateInterface.clone(y)).multiply(h);
        k12 = StateInterface.clone(y).rateMul(h, RateInterface.clone(velocity).add(RateInterface.clone(v21).multiply(0.5))); //!!
        v22 = f.call(1, StateInterface.clone(y).add(StateInterface.clone(k11).multiply(0.5))).multiply(h);
        k13 = StateInterface.clone(y).rateMul(h, RateInterface.clone(velocity).add(RateInterface.clone(v22).multiply(0.5)));
        v23 = f.call(1, StateInterface.clone(y).add(StateInterface.clone(k12).multiply(0.5))).multiply(h);
        k14 = StateInterface.clone(y).rateMul(h, RateInterface.clone(velocity).add(RateInterface.clone(v23)));
        v24 = f.call(1, StateInterface.clone(y).add(StateInterface.clone(k13))).multiply(h);

        //rate   -> y.add(rate).mul(h)   -> y.addMul(h,rate)

        if (DEBUG) {
            System.out.println("k11");
            System.out.println(k11);
            System.out.println("k12");
            System.out.println(k12);
            System.out.println("k13");
            System.out.println(k13);
            System.out.println("k14");
            System.out.println(k14);
        }

        k12 = k12.multiply(2);
        k13 = k13.multiply(2);

        v22 = v22.multiply(2);
        v23 = v23.multiply(2);

        kk = (k11.sumOf(k12, k13, k14)).div(6);

        kv = (v21.sumOf(v22, v23, v24)).div(6);

        //-6.80564659829616E8
        //-6.806783239281648E8


        y.getRateOfChange().setVel(y.getRateOfChange().add(kv).getVelocities());
        return y.add(kk);
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
