package phase3.Math.Solvers;


import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Functions.ODEFunctionInterface;
import phase3.System.State.RateOfChange;
import phase3.System.State.StateInterface;
import phase3.System.State.SystemState;

/**
 * Verlet Velocity Solver (both positions and velocity, less round-off errors)
 * Second order Solver
 */
public class VerletVelocitySolver<E> implements ODESolverInterface<E> {

    private final ODEFunctionInterface<E> f;

    /**
     * Instantiates a new Verlet velocity solver.
     *
     * @param f the f
     */
    public VerletVelocitySolver(final ODEFunctionInterface<E> f) {
        this.f = f;
    }


    /**
     * Step of a Verlet Velocity Algorithm:
     * Consists of two equations:
     * x_n+1 = x_n + (v_n) *delta_t+1/2*(a_n)*(delta_t^2)
     * v_n+1 = v_n + 1/2*((a_n+1)+(a_n))*(delta_t)
     * <p>
     * next(0) = (y(0) + [y(1) * h])  + [1/2   * h^2 * (f.call(t,y))]
     * next(1) = (y(1))               + [1/2  * h^2 * (f.call(t+h,y) + f.call(t,y))/h]
     * <p>
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
    public StateInterface<E> step(ODEFunctionInterface<E> f, double t, StateInterface<E> y, double h) {
        StateInterface<E> next = new RungeKuttaSolver<>(f).step(f, t, y, h);
        int s = y.get().length / 2; //take state symmetry
        E[] state = (E[]) (new Object[y.get().length]);
        E[] rate = (E[]) (new Object[y.get().length]);
        for (int i = 0; i < s; i++) {
            E current_acc = f.call(t, y).get()[i + s];
            E acc_next = f.call(t + h, next).get()[i + s];
            E step_pos, step_acc;
            if (y.get() instanceof Vector3dInterface[]) {
                step_pos = (E) ((Vector3dInterface) y.get()[i]).addMul(h, ((Vector3dInterface) y.get()[i + s]));
                step_acc = (E) ((Vector3dInterface) acc_next).add(((Vector3dInterface) current_acc)).div(h);
            } else if (y.get() instanceof Double[]) {
                step_pos = (E) ((Double) ((Double) y.get()[0] + (h * (Double) y.get()[1])));
                step_acc = (E) ((Double) (((Double) acc_next + (Double) current_acc) / h));
            } else throw new UnsupportedOperationException();
            state[i] = step_pos;
            state[i + s] = y.get()[i + s]; //velocity is given in y
            rate[i] = current_acc;  // because then we addMul it
            rate[i + s] = step_acc;   // same but this has been added to the next one
        }
        next = new SystemState<>(state);
        return next.addMul((h * h) / 2, new RateOfChange<>(rate));
    }

    @Override
    public ODEFunctionInterface<E> getFunction() {
        return f;
    }

    @Override
    public String toString() {
        return "VerletVelocitySolver";
    }
}