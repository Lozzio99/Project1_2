package Module.Math.Solvers;

import Module.Math.Functions.ModuleFunction;
import Module.Math.Functions.ODEFunctionInterface;
import Module.Math.Vector3dInterface;
import Module.System.State.StateInterface;

/**
 * The type Standard verlet solver.
 * Second Order Solver
 */
public class StandardVerletSolver implements ODESolverInterface<Vector3dInterface> {

    private boolean first;
    private final ODEFunctionInterface<Vector3dInterface> f;
    private StateInterface<Vector3dInterface> prevState;


    /**
     * Instantiates a new Standard verlet solver.
     *
     * @param f the f
     */
    public StandardVerletSolver(final ODEFunctionInterface<Vector3dInterface> f) {
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
    public StateInterface<Vector3dInterface> step(ODEFunctionInterface<Vector3dInterface> f, double t, StateInterface<Vector3dInterface> y, double h) {
        // next position
        StateInterface diff;
        if (first) {
            ODESolverInterface solver = new RungeKuttaSolver(new ModuleFunction().evaluateCurrentAccelerationFunction());
            diff = solver.step(solver.getFunction(), t, y, h);
            first = false;
        } else {
            StateInterface subPrev = (StateInterface) ((Vector3dInterface) prevState).mul(-1);
            StateInterface twiceY = (StateInterface) ((Vector3dInterface) y).mul(2.0);
            StateInterface rateMulPart = y.addMul(h * h, f.call(1, y));
            diff = (StateInterface) ((Vector3dInterface) subPrev).sumOf(((Vector3dInterface) twiceY), ((Vector3dInterface) rateMulPart));
        }
        prevState = (StateInterface) ((Vector3dInterface) y).clone();
        return diff;
    }


    @Override
    public ODEFunctionInterface<Vector3dInterface> getFunction() {
        return this.f;
    }


    /**
     * Sets first.
     */
    public void setFirst() {
        this.first = true;
    }
}