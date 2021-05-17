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

/**
 * Verlet Velosity Solver (both positions and velocity, less round-off errors)
 */
public class VerletVelocitySolver implements ODESolverInterface {

    public static double currTime = 0;
    public static double endTime = NaN;
    private ODEFunctionInterface singleCoreF;
    private boolean checked;

    @Contract(pure = true)
    public VerletVelocitySolver() {
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
                }
                rate.getVelocities().add(totalAcc);
            }
            checked = true;
            return rate;
        };
    }


    /**
     * Step of a Verlet Velocity Algorithm:
     * Consists of two equations:
     * x_n+1 = x_n + (v_n)*delta_t+1/2*(a_n)*(delta_t^2)
     * v_n+1 = v_n + 1/2*((a_n+1)+(a_n))*(delta_t)
     * x - position, v - velocity, delta_t - step size
     * Source: http://www.physics.udel.edu/~bnikolic/teaching/phys660/numerical_ode/node5.html
     *
     * @param f ordinary differential equations - function of acceleration
     * @param t current time
     * @param y current state;
     * @param h step size
     * @return new state
     */
    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        checked = false;
        // next position

        StateInterface part2 = y.rateMul(0.5 * h * h, f.call(1, y)).add(y);
        StateInterface part4 = part2.addMul(h, y.getRateOfChange());
        RateInterface part6 = f.call(1, y).multiply(h);

        part4.getRateOfChange().setVel(y.getRateOfChange().add(part6).getVelocities());


        return part4;
    }


    @Override
    public ODEFunctionInterface getFunction() {
        return singleCoreF;
    }

    @Override
    public void setF(ODEFunctionInterface f) {
        this.singleCoreF = f;
    }

}