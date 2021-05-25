package group17.Math.Solvers;

import group17.Interfaces.ODEFunctionInterface;
import group17.Interfaces.ODESolverInterface;
import group17.Interfaces.RateInterface;
import group17.Interfaces.StateInterface;
import group17.Math.Lib.GravityFunction;
import group17.System.State.RateOfChange;
import org.jetbrains.annotations.Contract;

/**
 * Verlet Velocity Solver (both positions and velocity, less round-off errors)
 * Second order Solver
 */
public class VerletVelocitySolver implements ODESolverInterface {

    private final ODEFunctionInterface f;

    /**
     * Instantiates a new Verlet velocity solver.
     *
     * @param f the f
     */
    @Contract(pure = true)
    public VerletVelocitySolver(final ODEFunctionInterface f) {
        this.f = f;
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
        GravityFunction.setChecked(false);
        // next position
        RateInterface velocity = new RateOfChange();
        velocity.setVelocities(y.getRateOfChange().getVelocities());
        // next position
        StateInterface part2 = y.rateMul(0.5 * h * h, f.call(1, y)).add(y);
        StateInterface part3 = y.rateMul(h, velocity);
        StateInterface part4 = part2.add(part3);

        RateInterface part5 = f.call(1, part4).add(f.call(1, y));
        RateInterface part6 = part5.multiply(0.5 * h);
        RateInterface part7 = velocity.add(part6);

        part4.getRateOfChange().setVelocities(part7.getVelocities());
        return part4;
    }


    @Override
    public ODEFunctionInterface getFunction() {
        return f;
    }

}