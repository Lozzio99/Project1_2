package group17.Solvers;


import group17.Interfaces.ODEFunctionInterface;
import group17.Interfaces.ODESolverInterface;
import group17.Interfaces.StateInterface;
import group17.Math.GravityFunction;


/**
 * The type Euler solver.
 * First Order Solver
 */
public class EulerSolver implements ODESolverInterface {

    /**
     * The Single core f.
     */
    private final ODEFunctionInterface f;

    /**
     * Instantiates a new Euler solver.
     *
     * @param f the f
     */
    public EulerSolver(final ODEFunctionInterface f) {
        this.f = f;
    }

    /**
     *  Formula
     *  y1 = y0 + h*f(t,y0);
     *
     * @param f the function representing Newton's Gravitational law
     * @param y the instance  of the Simulation
     * @return the next state of the simulation based on a Euler Step
     */
    @Override
    public StateInterface step(ODEFunctionInterface f, double currentTime, StateInterface y, double stepSize) {
        GravityFunction.setChecked(false);
        GravityFunction.setCurrentTime(currentTime - stepSize);
        return y.addMul(stepSize, f.call(currentTime, y));
    }

    @Override
    public ODEFunctionInterface getFunction() {
        return f;
    }


    @Override
    public String toString() {
        return "EulerSolver{" +
                "function used =" + f +
                '}';
    }


}
