package group17.Math.Solvers;

import group17.Interfaces.*;
import group17.Math.Lib.Vector3D;
import group17.System.State.RateOfChange;
import group17.Utils.CollisionDetector;

import static group17.Main.simulation;
import static group17.Utils.Config.*;
import static java.lang.StrictMath.pow;
import static java.lang.StrictMath.sqrt;

public class MidPointSolver implements ODESolverInterface {
    private boolean checked;
    private ODEFunctionInterface singleCoreF = (t, y) ->
    {
        RateInterface rate = new RateOfChange();
        double dt = t - CURRENT_TIME;  //get dt
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
                    totalAcc = totalAcc.addMul(dt, acc);
                    // p = h*acc(derivative of velocity)
                }
            }
            rate.getVelocities().add(y.getRateOfChange().getVelocities().get(i).add(totalAcc));
        }
        checked = true;
        return rate;
    };


    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        CURRENT_TIME = t;
        checked = false;
        return y.addMul(h,
                f.call(t + (h / 2), y.addMul(h / 2,
                        f.call(t, y))));
    }

    @Override
    public ODEFunctionInterface getFunction() {
        return this.singleCoreF;
    }

    @Override
    public void setF(ODEFunctionInterface f) {
        this.singleCoreF = f;
    }


}
