package group17.phase1.Titan.Physics.Solvers;

import group17.phase1.Titan.Interfaces.ODEFunctionInterface;
import group17.phase1.Titan.Interfaces.ODESolverInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.System.SystemState;

import static java.lang.Double.NaN;

/**
 * Standard Verlet Solver (only for position)
 */
public class VerletSolver implements ODESolverInterface {

    public static double currTime = 0;
    public static double endTime = NaN;

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {
        endTime = tf;
        StateInterface[] path = new StateInterface[(int) (Math.round(tf / h)) + 1];
        currTime = 0;
        // path[0] = TODO: First step should solved with a Runge-Kutta Method
        for (int i = 1; i < path.length - 1; i++) {
            path[i] = this.step(f, currTime, y0, h);
            path[i].getPositions().get(0).sub(path[i-1].getPositions().get(0)); // - x_n-1
            currTime+=h;
        }
        path[path.length - 1] = this.step(f, tf, y0, tf - currTime);
        return path;
    }

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {
        StateInterface[] states = new StateInterface[ts.length];
        endTime = ts[ts.length - 1];
        currTime = ts[0];
        // path[0] = TODO: First step should solved with a Runge-Kutta Method
        for (int i = 1; i < ts.length - 1; i++) {
            double h = ts[i + 1] - ts[i];
            states[i] = this.step(f, currTime, y0, h);
            states[i].getPositions().get(0).sub(states[i-1].getPositions().get(0)); // - x_n-1
            currTime += h;
        }
        states[states.length - 1] = this.step(f, currTime, y0, ts[ts.length - 1] - currTime);
        return states;
    }

    /**
     * Step of a Standard Verlet Algorithm:
     * x_n+1 = 2*(x_n) - (x_n-1) + f(x_n,t_n)*h^2
     * x - position, h - step size
     * Source: http://www.physics.udel.edu/~bnikolic/teaching/phys660/numerical_ode/node5.html
     * @param f ordinary differential equations - function of acceleration
     * @param t current time
     * @param y current state; get(0) - position vector
     * @param h step size
     * @return new state
     */
    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        // next position
        Vector3dInterface part1 = y.getPositions().get(0).mul(2); // 2*(x_n)
        Vector3dInterface part2 = f.call(t, y).getRateOfChange().get(0).mul(h*h).add(part1); // + f(x_n,t_n)*h^2
        StateInterface next_y = new SystemState();
        next_y.getPositions().add(part2);
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