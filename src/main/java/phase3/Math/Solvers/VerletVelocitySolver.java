package phase3.Math.Solvers;


import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Functions.ODEFunctionInterface;
import phase3.System.State.RateInterface;
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
        E a = f.call(t, y).get()[1];
        E a1 = f.call(t + h, next).get()[1];
        RateInterface<E> nextDy;
        if (y.get() instanceof Vector3dInterface[]) {
            Vector3dInterface y0, dy1;
            StateInterface<Vector3dInterface> step;
            RateInterface<Vector3dInterface> stepDy;
            y0 = ((Vector3dInterface) y.get()[0]).addMul(h, ((Vector3dInterface) y.get()[1]));
            dy1 = ((Vector3dInterface) a1).add(((Vector3dInterface) a)).div(h);
            step = new SystemState<>(y0, ((Vector3dInterface) y.get()[1]));
            next = (SystemState<E>) step;
            stepDy = new RateOfChange<>(((Vector3dInterface) a), (dy1));
            nextDy = (RateOfChange<E>) stepDy;
        } else if (y.get() instanceof Double[]) {
            Double y0, dy1;
            StateInterface<Double> step;
            RateInterface<Double> stepDy;
            y0 = (((Double) y.get()[0] + (h * (Double) y.get()[1])));
            dy1 = ((Double) a1 + (Double) a) / h;
            step = new SystemState<>(y0, ((Double) y.get()[1]));
            next = (SystemState<E>) step;
            stepDy = new RateOfChange<>(((Double) a), dy1);
            nextDy = (RateOfChange<E>) stepDy;
        } else throw new UnsupportedOperationException();
        return next.addMul((h * h) / 2, nextDy);
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