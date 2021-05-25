package group17.Math.Solvers;

import group17.Interfaces.*;
import group17.Math.Utils.Vector3D;
import group17.System.CollisionDetector;
import group17.System.RateOfChange;
import group17.System.SystemState;
import org.jetbrains.annotations.Contract;

import static group17.Config.*;
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
                    totalAcc = totalAcc.add(acc);
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
            RateInterface rate = new RateOfChange();
            double dt = t - this.currentTime;  //get dt
            for (int i = 0; i < y.getPositions().size(); i++) {
                Vector3dInterface totalAcc = new Vector3D(0, 0, 0);
                for (int k = 0; k < y.getPositions().size(); k++) {
                    if (i != k) {
                        Vector3dInterface acc = y.getPositions().get(i).clone();
                        double squareDist = pow(y.getPositions().get(i).dist(y.getPositions().get(k)), 2);
                        acc = y.getPositions().get(k).sub(acc); // Get the force vector
                        double den = sqrt(squareDist);
                        if (!checked && CHECK_COLLISIONS) {
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
                        totalAcc = totalAcc.add(acc);  // Changed just to add since multiply by h at return of step
                        // p = h*acc(derivative of velocity)
                    }
                }
              rate.getVelocities().add(y.getRateOfChange().getVelocities().get(i).add(totalAcc));
            }
            checked = true;
            return rate;
        };
    }




    public StateInterface old(ODEFunctionInterface f, double t, final StateInterface y, double h) {
        StateInterface k1, k2, k3, k4, kk;
        checked = false;

        RateInterface velocity = y.getRateOfChange();

        k1 = y.getState(velocity);
        k1.setRateOfChange(oldF.call(h, y));
        k2 = y.getState(velocity.add(k1.getRateOfChange().multiply(h/2))); //!!
        k2.setRateOfChange(oldF.call(h, y.add(k1.multiply(h/2))));
        k3 = y.getState(velocity.add(k2.getRateOfChange().multiply(h/2)));
        k3.setRateOfChange(oldF.call(h, y.add(k2.multiply(h/2))));
        k4 = y.getState(velocity.add(k3.getRateOfChange().multiply(h)));
        k4.setRateOfChange(oldF.call(h, y.add(k3.multiply(h))));
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
        kk = ((k1.sumOf(k2, k3, k4)));

        return new SystemState(y.add(kk.multiply(h/6)));
    }

    public ODEFunctionInterface getOldF() {
        return oldF;
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

    public StateInterface testingRK(ODEFunctionInterface f, double t, final StateInterface y, double h) {
        this.currentTime = t;
        RateInterface k1, k2, k3, k4, k5;
        k1 = f.call(t, y).multiply(h);
        k2 = f.call(t + (h / 2), y.addMul(0.5, k1)).multiply(h);
        k3 = f.call(t + (h / 2), y.addMul(0.5, k2)).multiply(h);
        k4 = f.call(t + h, y.addMul(1, k3)).multiply(h);
        k5 = k1.sumOf(k2.multiply(2), k3.multiply(2), k4).div(6);
        return y.addMul(1, k5);
    }


    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        /*
        this.currentTime = t;
        RateInterface k1 = f.call(t, y).div(6);
        RateInterface k2 = f.call(t + 0.5 * h, y.addMul(0.5 * h, k1)).div(3);
        RateInterface k3 = f.call(t + 0.5 * h, y.addMul(0.5 * h, k2)).div(3);
        RateInterface k4 = f.call(t + h, y.addMul(h, k3)).div(6);
        RateInterface newRate = k1.sumOf(k2, k3, k4);

         */
        return old(f,t,y.copy(),h);
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
