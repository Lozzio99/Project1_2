package group17.Math.Solvers;

import group17.Interfaces.*;
import group17.Main;
import group17.Math.Lib.Vector3D;
import group17.System.State.RateOfChange;
import group17.Utils.CollisionDetector;
import org.jetbrains.annotations.Contract;

import static group17.Main.simulation;
import static group17.Utils.Config.G;
import static java.lang.Double.NaN;

/**
 * The type Standard verlet solver.
 */
public class StandardVerletSolver implements ODESolverInterface {

    /**
     * The constant currTime.
     */
    public static double currTime = 0;
    /**
     * The constant endTime.
     */
    public static double endTime = NaN;
    private boolean checked;
    private final boolean oldF = true;
    private boolean first = true;
    private StateInterface prevState, oldP;
    private ODEFunctionInterface singleCoreF;


    /**
     * Instantiates a new Standard verlet solver.
     */
    @Contract(pure = true)
    public StandardVerletSolver() {
        this.singleCoreF = (h, y) -> {
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
                        totalAcc = totalAcc.addMul(h, acc);
                        // p = h*acc(derivative of velocity)
                    }
                }  // y1 =y0 + h*acc
                // y1 = y0 + p
                //FIXME : why did we had to change this?
                rate.getVelocities().add(totalAcc);
            }
            checked = true;
            return rate;
        };
    }


    /**
     * Step of a Standard Verlet Algorithm:
     * x_n+1 = 2*(x_n) - (x_n-1) + f(x_n,t_n)*h^2
     * x - position, h - step size
     * Source: http://www.physics.udel.edu/~bnikolic/teaching/phys660/numerical_ode/node5.html
     *
     * @param f ordinary differential equations - function of acceleration
     * @param t current time
     * @param y current state; getPosition(0) - current position, getPosition(1) - prev position
     * @param h step size
     * @return new state
     */
    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        // next position
        checked = false;
        StateInterface diff;
        if (first) {
            RungeKutta4thSolver rk4 = new RungeKutta4thSolver();
            diff = rk4.old(rk4.getOldF(), t, y, h);
            first = false;
        } else {
            StateInterface subPrev = prevState.copy().multiply(-1);
            StateInterface twiceY = y.copy().multiply(2.0);
            StateInterface rateMulPart = y.copy().rateMul(h * h, f.call(1, y));
            diff = subPrev.add(twiceY).add(rateMulPart);
        }
        prevState = y.copy();
        return diff;
    }


    @Override
    public ODEFunctionInterface getFunction() {
        return singleCoreF;
    }

    @Override
    public void setF(ODEFunctionInterface f) {
        this.singleCoreF = f;
    }

    /**
     * Sets first.
     */
    public void setFirst() {
        this.first = true;
    }
}