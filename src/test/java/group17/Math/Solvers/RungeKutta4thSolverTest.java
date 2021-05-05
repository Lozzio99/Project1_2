package group17.Math.Solvers;

import group17.Interfaces.ODEFunctionInterface;
import group17.Interfaces.StateInterface;
import group17.System.RateOfChange;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RungeKutta4thSolverTest {

    ODEFunctionInterface f = (t, y) -> {
        y = StateInterface.clone(y);
        return new RateOfChange();
    };

    @Test
    @DisplayName("Solve")
    void Solve() {
    }

    @Test
    @DisplayName("TestSolve")
    void TestSolve() {
    }

    @Test
    @DisplayName("Step")
    void Step() {

    }

    @Test
    @DisplayName("GetFunction")
    void GetFunction() {

    }

    //y1 = y0 + h*f(t,y);

    abstract class State implements StateInterface {

        double x;
    }
}