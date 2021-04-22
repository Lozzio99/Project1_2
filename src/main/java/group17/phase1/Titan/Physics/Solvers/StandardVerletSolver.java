package group17.phase1.Titan.Physics.Solvers;

import group17.phase1.Titan.Interfaces.ODEFunctionInterface;
import group17.phase1.Titan.Interfaces.ODESolverInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Interfaces.Vector3dInterface;

import static java.lang.Double.NaN;

public class StandardVerletSolver implements ODESolverInterface {

    public static double currTime = 0;
    public static double endTime = NaN;

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {
        // init
        endTime = tf;
        StateInterface[] path = new StateInterface[(int)(Math.round(tf/h))+1];
        currTime = 0;
        // first step with a Runge-Kutta
        RungeKutta4thSolver rk4 = new RungeKutta4thSolver();
        path[0] = rk4.step(f, currTime+=h,y0,h);
        // Adding previous storing prev position
        path[0].getPositions().add(y0.getPositions().get(0));
        // solve
        for (int i = 1; i < path.length - 1; i++) {
            path[i] = this.step(f, currTime, y0, h);
            y0 = path[i];
            currTime+=h;
        }
        path[path.length - 1] = this.step(f, tf, y0, tf - currTime);
        return path;
    }

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {
        // init
        StateInterface[] states = new StateInterface[ts.length];
        endTime = ts[ts.length - 1];
        currTime = ts[0];
        // first step with a Runge-Kutta
        RungeKutta4thSolver rk4 = new RungeKutta4thSolver();
        states[0] = rk4.step(f, currTime+=ts[1]-ts[0],y0,ts[1]-ts[0]);
        // Adding previous storing prev position
        states[0].getPositions().add(y0.getPositions().get(0));
        // solve
        for (int i = 1; i < ts.length - 1; i++) {
            double h = ts[i + 1] - ts[i];
            states[i] = this.step(f, currTime, y0, h);
            y0 = states[i];
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
     * @param y current state; getPosition(0) - current position, getPosition(1) - prev position
     * @param h step size
     * @return new state
     */
    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        // next position
        Vector3dInterface part1 = y.getPositions().get(0).mul(2); // 2*(x_n)
        Vector3dInterface part2 = f.call(t, y).getVelocities().get(0).mul(h*h).add(part1); // + f(x_n,t_n)*h^2
        Vector3dInterface part3 = part2.sub(y.getPositions().get(1)); // - x_n-1
        StateInterface next_y = StateInterface.clone(y);
        next_y.getPositions().set(0, part3);
        next_y.getPositions().set(1, y.getPositions().get(0));
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