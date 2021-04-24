package group17.phase1.Titan.Physics.Solvers;

import group17.phase1.Titan.Interfaces.*;
import group17.phase1.Titan.Main;
import group17.phase1.Titan.Physics.Math.Vector3D;
import group17.phase1.Titan.System.RateOfChange;

import static group17.phase1.Titan.Config.G;
import static java.lang.Double.NaN;

/**
 * Verlet Velosity Solver (both positions and velocity, less round-off errors)
 */
public class VerletVelocitySolver implements ODESolverInterface {

    public static double currTime = 0;
    public static double endTime = NaN;
    private final ODEFunctionInterface singleCoreF;

    public VerletVelocitySolver() {
        this.singleCoreF = (t, y) -> {
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
                        acc = acc.mul((G * Main.simulation.system().getCelestialBodies().get(k).getMASS()) / (squareDist == 0 ? 0.0000001 : squareDist)); // Convert force to acceleration
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
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {
        endTime = tf;
        StateInterface[] path = new StateInterface[(int)(Math.round(tf/h))+1];
        currTime = 0;
        for (int i = 0; i < path.length - 1; i++) {
            path[i] = this.step(f, currTime+=h, y0, h);
            y0 = path[i];
        }
        path[path.length - 1] = this.step(f, tf, y0, tf - currTime);
        return path;
    }

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {
        StateInterface[] states = new StateInterface[ts.length];
        endTime = ts[ts.length - 1];
        currTime = ts[0];

        for (int i = 0; i < ts.length - 1; i++) {
            double h = ts[i + 1] - ts[i];
            states[i] = this.step(f, currTime+=h, y0, h);
            y0 = states[i];
        }
        states[states.length - 1] = this.step(f, currTime, y0, ts[ts.length - 1] - currTime);
        return states;
    }

    /**
     * Step of a Verlet Velocity Algorithm:
     * Consists of two equations:
     * x_n+1 = x_n + (v_n)*delta_t+1/2*(a_n)*(delta_t^2)
     * v_n+1 = v_n + 1/2*((a_n+1)+(a_n))*(delta_t)
     * x - position, v - velocity, delta_t - step size
     * Source: http://www.physics.udel.edu/~bnikolic/teaching/phys660/numerical_ode/node5.html
     * @param f ordinary differential equations - function of acceleration
     * @param t current time
     * @param y current state;
     * @param h step size
     * @return new state
     */
    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        RateInterface velocity = new RateOfChange();
        velocity.setVel(y.getRateOfChange().getVelocities());
        // next position
        StateInterface part2 = StateInterface.clone(y).rateMul(0.5*h*h,f.call(t,StateInterface.clone(y))).add(StateInterface.clone(y));
        StateInterface part3 = StateInterface.clone(y).rateMul(h, velocity);
        StateInterface part4 = part2.add(part3);

        RateInterface part5 = f.call(t+h,StateInterface.clone(part4)).add(f.call(t, StateInterface.clone(y)));
        RateInterface part6 = part5.multiply(0.5*h);
        RateInterface part7 = RateInterface.clone(velocity).add(part6);
        part4.getRateOfChange().setVel(part7.getVelocities());

        return part4;
    }

    @Override
    public ODEFunctionInterface getFunction() {
        return singleCoreF;
    }
}