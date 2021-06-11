package Module.Math.Solvers;

import Module.Math.ADT.Vector3dInterface;
import Module.Math.Functions.ModuleFunction;
import Module.Math.Functions.ODEFunctionInterface;
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
    @SuppressWarnings("{unchecked,unsafe}")
    public StateInterface<E> step(ODEFunctionInterface<E> f, double t, StateInterface<E> y, double h) {
        // next position
        if (y.get() instanceof Vector3dInterface) {
            StateInterface<E> next;
            if (first) {
                ODESolverInterface<Vector3dInterface> solver = new RungeKuttaSolver<>(new ModuleFunction().evaluateCurrentAccelerationFunction());
                next = (SystemState<E>) solver.step(solver.getFunction(), t, (SystemState<Vector3dInterface>) y, h);
                first = false;
            } else {
                /*
                Vector3dInterface pos = ((Vector3dInterface) y.get()).mul(2).sub(((Vector3dInterface) prevState.get()));
                Vector3dInterface fp = ((Vector3dInterface) f.call(t, y).get());
                pos = pos.addMul(h*h,fp);
                // (x_n+1 - x_n)/h
                Vector3dInterface veln1 = ((Vector3dInterface) y.getRateOfChange().get()).sub((Vector3dInterface) prevState.getRateOfChange().get());
                veln1 = veln1.div(h);
                //Vector3dInterface vel =  ((Vector3dInterface) y.get()).addMul(h,fp).sub(((Vector3dInterface) y.get()));
                next = (SystemState<E>) new SystemState<>(pos,veln1);
                 */
                // 2*(x_n) - (x_n-1) + f(x_n,t_n)*h^2
                StateInterface<E> nextF = y.addMul(h * h, f.call(t, y));
                Vector3dInterface p = ((Vector3dInterface) nextF.get());
                p = p.add((Vector3dInterface) y.get()).sub((Vector3dInterface) prevState.get());
                Vector3dInterface v = ((Vector3dInterface) nextF.getRateOfChange().get());
                v = v.add((Vector3dInterface) y.getRateOfChange().get()).sub((Vector3dInterface) prevState.getRateOfChange().get());
                next = (SystemState<E>) new SystemState<>(p, v);
            }
            prevState = new SystemState<>(y.get(), y.getRateOfChange().get());
            return next;
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

    @Override
    public String toString() {
        return "StandardVerletSolver";
    }
}