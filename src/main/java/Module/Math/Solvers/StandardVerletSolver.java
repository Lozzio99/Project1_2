package Module.Math.Solvers;

import Module.Math.Functions.ModuleFunction;
import Module.Math.Functions.ODEFunctionInterface;
import Module.Math.Vector3dInterface;
import Module.System.State.StateInterface;
import Module.System.State.SystemState;

/**
 * The type Standard verlet solver.
 * Second Order Solver
 */
public class StandardVerletSolver<E> implements ODESolverInterface<E> {

    private boolean first;
    private final ODEFunctionInterface<E> f;
    private StateInterface<E> prevState;


    /**
     * Instantiates a new Standard verlet solver.
     *
     * @param f the f
     */
    public StandardVerletSolver(final ODEFunctionInterface<E> f) {
        first = true;
        this.f = f;
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
    public StateInterface<E> step(ODEFunctionInterface<E> f, double t, StateInterface<E> y, double h) {
        // next position
        if (y.get() instanceof Vector3dInterface) {
            StateInterface<Vector3dInterface> diff;
            if (first) {
                ODESolverInterface<Vector3dInterface> solver = new RungeKuttaSolver<>(new ModuleFunction().evaluateCurrentAccelerationFunction());
                diff = solver.step(solver.getFunction(), t, new SystemState<>((Vector3dInterface) y.get()), h);
                first = false;
            } else {
                StateInterface<Vector3dInterface> subPrev = new SystemState<>(((Vector3dInterface) prevState.get()).mul(-1));
                StateInterface<Vector3dInterface> twiceY = new SystemState<>(((Vector3dInterface) y.get()).mul(2));
                StateInterface<E> rateMulPart = y.addMul(h * h, f.call(1, y));
                diff = new SystemState<>(subPrev.get().sumOf(twiceY.get(), (Vector3dInterface) rateMulPart.get()));
            }
            prevState = new SystemState<>(y.get());
            return ((SystemState<E>) diff);
        } else throw new UnsupportedOperationException();

    }


    @Override
    public ODEFunctionInterface<E> getFunction() {
        return this.f;
    }


    /**
     * Sets first.
     */
    public void setFirst() {
        this.first = true;
    }
}