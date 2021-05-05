package group17.Math.Solvers;

import group17.Interfaces.ODEFunctionInterface;
import group17.Interfaces.ODESolverInterface;
import group17.Interfaces.StateInterface;
import group17.Interfaces.Vector3dInterface;
import group17.Main;
import group17.Math.Vector3D;
import group17.System.Clock;

import static group17.Config.G;
import static java.lang.Double.NaN;

public class StandardVerletSolver implements ODESolverInterface {

    public static double currTime = 0;
    public static double endTime = NaN;
    private StateInterface prevState;
    private boolean first = true;
    private ODEFunctionInterface singleCoreF;
    private Clock clock;

    public StandardVerletSolver() {
        this.singleCoreF = (h, y) -> {
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
                        acc = acc.mul((G * Main.simulationInstance.getSystem().getCelestialBodies().get(k).getMASS()) / (squareDist == 0 ? 0.0000001 : squareDist)); // Convert force to acceleration
                        totalAcc = totalAcc.addMul(h, acc);
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
        StateInterface diff;
        if (first) {
            RungeKutta4thSolver rk4 = new RungeKutta4thSolver();
            diff = rk4.step(f, t, y, h);
            first = false;
        } else {
            StateInterface subPrev = StateInterface.clone(prevState).multiply(-1);
            diff = (StateInterface.clone(y).rateMul(h * h, f.call(1, StateInterface.clone(y)))).add(subPrev).add(StateInterface.clone(y)).add(y);
        }

        prevState = StateInterface.clone(diff);
        this.clock.step(h);

        y = diff;
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

    @Override
    public Clock getClock() {
        return this.clock;
    }

    @Override
    public void setClock(Clock clock) {
        this.clock = clock;
    }
}