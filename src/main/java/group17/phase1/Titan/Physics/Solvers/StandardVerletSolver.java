package group17.phase1.Titan.Physics.Solvers;

import group17.phase1.Titan.Interfaces.ODEFunctionInterface;
import group17.phase1.Titan.Interfaces.ODESolverInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.Main;
import group17.phase1.Titan.Physics.Math.Vector3D;

import static group17.phase1.Titan.Config.G;
import static java.lang.Double.NaN;

public class StandardVerletSolver implements ODESolverInterface {

    private StateInterface prevState;
    private boolean first = true;

    public static double currTime = 0;
    public static double endTime = NaN;
    private final ODEFunctionInterface singleCoreF;

    public StandardVerletSolver() {
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
        // init
        endTime = tf;
        StateInterface[] path = new StateInterface[(int)(Math.round(tf/h))+1];
        currTime = 0;
        // solve
        for (int i = 1; i < path.length - 1; i++) {
            path[i] = this.step(f, currTime+=h, y0, h);
            y0 = path[i];
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

        for (int i = 1; i < ts.length - 1; i++) {
            double h = ts[i + 1] - ts[i];
            states[i] = this.step(f, currTime+=h, y0, h);
            y0 = states[i];
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
        StateInterface diff;
        if (first) {
            RungeKutta4thSolver rk4 = new RungeKutta4thSolver();
            diff = rk4.step(f, t, y, h);
            first = false;
        }
        else {
            StateInterface subPrev = StateInterface.clone(prevState).multiply(-1);
            diff = (StateInterface.clone(y).rateMul(h*h,f.call(t,StateInterface.clone(y)))).add(subPrev).add(StateInterface.clone(y)).add(y);
        }

        prevState = StateInterface.clone(diff);
        return diff;
    }

    @Override
    public ODEFunctionInterface getFunction() {
        return singleCoreF;
    }
}