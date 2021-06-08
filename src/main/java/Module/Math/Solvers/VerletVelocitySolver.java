package Module.Math.Solvers;


import Module.Math.Functions.ODEFunctionInterface;
import Module.Math.Vector3dInterface;
import Module.System.State.RateInterface;
import Module.System.State.RateOfChange;
import Module.System.State.StateInterface;
import Module.System.State.SystemState;

/**
 * Verlet Velocity Solver (both positions and velocity, less round-off errors)
 * Second order Solver
 */
public class VerletVelocitySolver implements ODESolverInterface<Vector3dInterface> {

    private final ODEFunctionInterface<Vector3dInterface> f;

    /**
     * Instantiates a new Verlet velocity solver.
     *
     * @param f the f
     */
    public VerletVelocitySolver(final ODEFunctionInterface<Vector3dInterface> f) {
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
    public StateInterface<Vector3dInterface> step(ODEFunctionInterface<Vector3dInterface> f, double t, StateInterface<Vector3dInterface> y, double h) {
        // next position
        RateInterface<Vector3dInterface> velocity = y.getRateOfChange();
        // next position
        StateInterface<Vector3dInterface> part2 = (y.addMul(0.5 * h * h, f.call(1, y)));
        part2.set(part2.get().add(y.get()));
        StateInterface<Vector3dInterface> part3 = y.addMul(h, velocity);
        StateInterface<Vector3dInterface> part4 = new SystemState(part2.get().add(part3.get()));

        RateInterface<Vector3dInterface> part5 = f.call(1, part4);
        part5.set(part5.get().add(f.call(1, y).get()));
        RateInterface<Vector3dInterface> part6 = new RateOfChange(part5.get().mul(0.5 * h));
        RateInterface<Vector3dInterface> part7 = new RateOfChange(velocity.get().add(part6.get()));
        part4.getRateOfChange().set(part7.get());
        return part4;
    }


    @Override
    public ODEFunctionInterface<Vector3dInterface> getFunction() {
        return f;
    }

}