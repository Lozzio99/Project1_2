package group17.Math.Solvers;

import group17.Interfaces.ODEFunctionInterface;
import group17.Interfaces.ODESolverInterface;
import group17.Interfaces.RateInterface;
import group17.Interfaces.StateInterface;
import group17.System.GravityFunction;
import org.jetbrains.annotations.Contract;

/**
 * The type Runge kutta 4 th solver.
 */
public class NewTryRungeKutta implements ODESolverInterface {


    private final ODEFunctionInterface f;

    /**
     * Instantiates a new Runge kutta 4 th solver.
     *
     * @param f the function
     */
    @Contract(pure = true)
    public NewTryRungeKutta(final ODEFunctionInterface f) {
        this.f = f;
    }



    private boolean checkDifferent(StateInterface a, StateInterface b) {
        for (int i = 0; i < a.getPositions().size(); i++) {
            if (!a.getPositions().get(i).equals(b.getPositions().get(i))) {
                return false;
            }
        }
        for (int i = 0; i < a.getRateOfChange().getVelocities().size(); i++) {
            if (!a.getRateOfChange().getVelocities().get(i).equals(b.getRateOfChange().getVelocities().get(i)))
                return false;
        }
        return true;
    }

    /**
     * Testing rk state interface.
     *
     * @param f the f
     * @param t the t
     * @param y the y
     * @param h the h
     * @return the state interface
     */
    public StateInterface step(ODEFunctionInterface f, double t, final StateInterface y, double h) {
        GravityFunction.setCurrentTime(t);
        GravityFunction.setChecked(false);
        RateInterface k1, k2, k3, k4, k5;
        k1 = f.call(t, y);
        k2 = f.call(t + (h / 2), y.addMul(0.5 * h, k1));
        k3 = f.call(t + (h / 2), y.addMul(0.5 * h, k2));
        k4 = f.call(t + h, y.addMul(h, k3));
        k5 = k1.sumOf(k2, k2, k3, k3, k4);
        return y.addMul(h, k5.div(6));
    }




    @Override
    public ODEFunctionInterface getFunction() {
        return this.f;
    }

    @Override
    public String toString() {
        return "RungeKutta4thSolver{" +
                "function used =" + f +
                '}';
    }


}
