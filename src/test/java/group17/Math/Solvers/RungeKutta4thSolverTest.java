package group17.Math.Solvers;

import group17.Interfaces.ODESolverInterface;
import group17.Interfaces.StateInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.exp;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RungeKutta4thSolverTest {
    static final double exactValue = 0.503347;
    static ODESolverInterface rk;
    static TestODEFunction dydx = (t, y) -> {
        RateTest rate = new RateTestClass();
        double y_ = ((StateTest) y).getY();
        rate.setDy(exp(-t) - (y_ * y_));
        return rate;
    };
    static StateTest y;
    static RateTest dy;
    static double t, tf, stepSize;

    @BeforeEach
    void setup() {
        stepSize = 0.0001;
        t = 0;
        tf = 1;
        double y0 = 0;
        y = new StateTestClass();
        y.setY(y0);
        rk = new RungeKutta4thSolver();
    }

    @Test
    @DisplayName("Solve")
    void Solve() {
        assertTrue(() -> abs(1.0 - ((StateTest) rk.step(dydx, t, y, 1)).getY()) < 1e-4);
        assertTrue(() -> abs(1.0 - ((RateTest) rk.step(dydx, t, y, 1).getRateOfChange()).getDy()) < 1e-4);
        StateInterface[] solution = rk.solve(dydx, y, tf, stepSize);
        assertTrue(() -> abs(exactValue - ((StateTest) solution[solution.length - 1]).getY()) < stepSize); //1e-4

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

}