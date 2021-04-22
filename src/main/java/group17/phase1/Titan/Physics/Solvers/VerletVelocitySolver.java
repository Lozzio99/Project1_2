package group17.phase1.Titan.Physics.Solvers;

import group17.phase1.Titan.Interfaces.ODEFunctionInterface;
import group17.phase1.Titan.Interfaces.ODESolverInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Interfaces.Vector3dInterface;

import static java.lang.Double.NaN;

/**
 * Verlet Velosity Solver (both positions and velocity, less round-off errors)
 */
public class VerletVelocitySolver implements ODESolverInterface {

    public static double currTime = 0;
    public static double endTime = NaN;

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
        // next position
        Vector3dInterface part1 = y.getRateOfChange().getVelocities().get(0).mul(h); // + (v_n)*delta_t
        Vector3dInterface part2 = f.call(t, y).getVelocities().get(0).mul(0.5 * h * h).add(part1); // + 1/2*(a_n)*(delta_t^2)
        Vector3dInterface part3 = y.getPositions().get(0).add(part2); // + x_n
        StateInterface next_x = StateInterface.clone(y);
        next_x.getPositions().set(0, part3);
        // next velocity
        Vector3dInterface part4 = f.call(t + h, next_x).getVelocities().get(0); // a_n+1
        Vector3dInterface part5 = f.call(t, y).getVelocities().get(0); // a_n
        Vector3dInterface part6 = (part4.add(part5)).mul(0.5 * h); //
        Vector3dInterface part7 = y.getRateOfChange().getVelocities().get(0).add(part6); // v_n + 1/2*((a_n+1)+(a_n))*(delta_t)

        StateInterface next_y = StateInterface.clone(y);
        next_y.getPositions().set(0, part3);
        next_y.getRateOfChange().getVelocities().set(0, part7);

        return next_y;
    }

    /**
     * No idea why we need it here
     * @return null
     */
    @Override
    public ODEFunctionInterface getFunction() {
        return null;
    }
}