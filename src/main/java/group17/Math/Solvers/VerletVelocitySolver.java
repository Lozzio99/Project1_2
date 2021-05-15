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
                y.getRateOfChange().getVelocities()
                        .set(i, totalAcc.clone());
            }
            checked = true;
            return y.getRateOfChange();
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
        RateInterface velocity = new RateOfChange();
        velocity.setVel(y.getRateOfChange().getVelocities());
        // next position
        StateInterface part2 = StateInterface.clone(y).rateMul(0.5 * h * h, f.call(1, StateInterface.clone(y))).add(StateInterface.clone(y));
        StateInterface part3 = StateInterface.clone(y).rateMul(h, velocity);
        StateInterface part4 = part2.add(part3);

        RateInterface part5 = f.call(1, StateInterface.clone(part4)).add(f.call(1, StateInterface.clone(y)));
        RateInterface part6 = part5.multiply(0.5 * h);
        RateInterface part7 = RateInterface.clone(velocity).add(part6);

        part4.getRateOfChange().setVel(part7.getVelocities());
        y = part4;
        return y;
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