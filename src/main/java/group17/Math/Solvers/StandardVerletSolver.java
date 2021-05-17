package group17.Math.Solvers;

import group17.Interfaces.*;
import group17.Main;
import group17.Math.Utils.Vector3D;
import group17.System.CollisionDetector;
import group17.System.RateOfChange;
import org.jetbrains.annotations.Contract;

import static group17.Config.G;
import static group17.Main.simulation;
import static java.lang.Double.NaN;

public class StandardVerletSolver implements ODESolverInterface {

    public static double currTime = 0;
    public static double endTime = NaN;
    private boolean checked;
    private final boolean oldF = true;
    private boolean first = true;
    private StateInterface prevState, oldP;
    private ODEFunctionInterface singleCoreF;


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
        StateInterface nextState;
        if (first) {
            RungeKutta4thSolver rk4 = new RungeKutta4thSolver();
            nextState = rk4.step(f, t, y, h);
            first = false;
        } else {
            StateInterface subPrev = prevState.multiply(-1);
            StateInterface twiceY = y.multiply(2.0);
            nextState = twiceY.add(subPrev);
            nextState = nextState.addMul(h * h, y.getRateOfChange());
        }
        prevState = y.copy();
        return nextState;
    }


    @Override
    public ODEFunctionInterface getFunction() {
        return singleCoreF;
    }

    @Override
    public void setF(ODEFunctionInterface f) {
        this.singleCoreF = f;
    }

    public void setFirst() {
        this.first = true;
    }
}