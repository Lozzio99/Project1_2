package group17.Math.Solvers;


import group17.Interfaces.ODEFunctionInterface;
import group17.Interfaces.ODESolverInterface;
import group17.Interfaces.StateInterface;
import group17.Math.Lib.GravityFunction;


/**
 * The type Euler solver.
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


    @Override
    public StateInterface step(ODEFunctionInterface f, double currentTime, StateInterface y, double stepSize) {
        // y1 = y0 + h*f(t,y0);
        GravityFunction.setChecked(false);
        GravityFunction.setCurrentTime(currentTime);
        return y.addMul(stepSize, f.call(currentTime + stepSize, y));
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
