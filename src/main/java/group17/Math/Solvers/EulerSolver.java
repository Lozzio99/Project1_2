package group17.Math.Solvers;


import group17.Interfaces.*;
import group17.Main;
import group17.Math.Lib.Vector3D;
import group17.System.State.RateOfChange;
import group17.Utils.CollisionDetector;

import static group17.Main.simulation;
import static group17.Utils.Config.G;
import static group17.Utils.Config.STEP_SIZE;
import static java.lang.Double.NaN;


public class EulerSolver implements ODESolverInterface {

    public static double currTime = 0;
    public static double endTime = NaN;
    private boolean checked;

    //p(x,y,z) //displacement
    //v(x,y,z) = p'(x,y,z)   //velocity
    //a(x,y,z) = dv(x,y,z) = dp(x,y,z)   //acceleration
    //           dt          dt
    public ODEFunctionInterface singleCoreF = (t, y) ->
    {
        RateInterface rate = new RateOfChange();
        for (int i = 0; i < y.getPositions().size(); i++) {
            Vector3dInterface totalAcc = new Vector3D(0, 0, 0);
            for (int k = 0; k < y.getPositions().size(); k++) {
                if (i != k) {
                    Vector3dInterface acc = y.getPositions().get(i).clone();
                    double squareDist = Math.pow(y.getPositions().get(i).dist(y.getPositions().get(k)), 2);
                    acc = y.getPositions().get(k).sub(acc); // Get the force vector
                    double den = Math.sqrt(squareDist);
                    if (!checked) {
                        CollisionDetector.checkCollided(simulation.getSystem().getCelestialBodies().get(i),
                                simulation.getSystem().getCelestialBodies().get(k), den);
                    }
                /*
                    ! Important !
                    if two bodies collapses into the same point
                    that would crash to NaN and consequently
                    the same in all the system
                */
                    acc = acc.mul(1 / (den == 0 ? 0.0000001 : den)); // Normalise to length 1
                    acc = acc.mul((G * Main.simulation.getSystem().getCelestialBodies().get(k).getMASS()) / (squareDist == 0 ? 0.0000001 : squareDist)); // Convert force to acceleration
                    totalAcc = totalAcc.addMul(STEP_SIZE, acc);
                    // p = h*acc(derivative of velocity)
                }
            }  // y1 =y0 + h*acc
            // y1 = y0 + p
            rate.getVelocities().add(y.getRateOfChange().getVelocities().get(i).add(totalAcc));
        }
        checked = true;
        return y.getRateOfChange();
    };


    //x1 = x0 + h*v0;
    //x2 = x1 + h*v1;
    //v1 = v0 + h* f(position)
    // f(pos){
    // return ((-G*M)/pos^3)* pos;}


    @Override
    public StateInterface step(ODEFunctionInterface f, double currentTime, StateInterface y, double stepSize) {
        // y1 = y0 + h*f(t,y0);
        checked = false;
        currTime = currentTime;
        return y.addMul(stepSize, f.call(currentTime, y));
        //       y0  +  (y * h      dy(rate of change))

        // add (y * h) to y
    }

    @Override
    public ODEFunctionInterface getFunction() {
        return singleCoreF;
    }


    @Override
    public String toString() {
        return "EulerSolver{" +
                "function used =" + singleCoreF +
                '}';
    }

    @Override
    public void setF(ODEFunctionInterface f) {
        this.singleCoreF = f;
    }

}
