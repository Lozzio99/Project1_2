package Module.Math.Solvers;


import Module.Math.Functions.ODEFunctionInterface;
import Module.Math.Vector3dInterface;
import Module.System.State.StateInterface;

/**
 * The type Euler solver.
 * First Order Solver
 */
public class EulerSolver implements ODESolverInterface<Vector3dInterface> {

    /**
     * The Single core f.
     */
    private final ODEFunctionInterface<Vector3dInterface> f;

    /**
     * Instantiates a new Euler solver.
     *
     * @param f the f
     */
    public EulerSolver(final ODEFunctionInterface<Vector3dInterface> f) {
        this.f = f;
    }

    /**
     * Formula
     * y1 = y0 + h*f(t,y0);
     *
     * @param f the function representing Newton's Gravitational law
     * @param y the instance  of the Simulation
     * @return the next state of the simulation based on a Euler Step
     */
    @Override
    public StateInterface<Vector3dInterface> step(ODEFunctionInterface<Vector3dInterface> f, double currentTime, StateInterface<Vector3dInterface> y, double stepSize) {
        return y.addMul(stepSize, f.call(currentTime, y));
    }

    @Override
    public ODEFunctionInterface<Vector3dInterface> getFunction() {
        return f;
    }


    @Override
    public String toString() {
        return "EulerSolver{" +
                "function used =" + f +
                '}';
    }


}
