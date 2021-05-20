package group17.Math.Solvers;

import group17.Interfaces.ODESolverInterface;
import group17.Interfaces.StateInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.exp;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * Source for the test is NM lecture on runge kutta methods for solving ODE systems
 */
class MidPointSolverTest {

    static final double exactValue = 0.503347;
    static ODESolverInterface mid;
    static TestODEFunction dydx = (t, y) -> {
        RateTest rate = new RateTestClass();
        double y_ = ((StateTest) y).getY();
        rate.setDy(exp(-t) - (y_ * y_));
        return rate;
    };

    static StateTest y;
    static RateTest dy;
    static double t, tf;
    double[] expected = new double[]
            {0.0, 0.09487, 0.17899, 0.25211, 0.31440, 0.36640, 0.40888, 0.44277, 0.46905, 0.48870, 0.50267};
    StateInterface[] solution;

    @BeforeEach
    void setup() {
        t = 0;
        tf = 1;
        double y0 = 0;
        y = new StateTestClass();
        y.setY(y0);
        mid = new MidPointSolver();
    }

    @Test
    @DisplayName("Solve")
    void Solve() {
        double step = 0.5;

        assertEquals(1.0, ((RateTest) dydx.call(0, y)).getDy());

        StateInterface firstStep = mid.step(dydx, t, y, step);

        assertTrue(() -> abs(0.3582 - ((StateTest) firstStep).getY()) < 1e-4);
        assertTrue(() -> abs(0.7163 - ((StateTest) firstStep).getRateOfChange().getDy()) < 1e-4);
        t += step;
        StateInterface secondStep = mid.step(dydx, t, firstStep, step);
        assertTrue(() -> abs(.4802 - ((StateTest) secondStep).getY()) < 1e-4);
        assertTrue(() -> abs(.2442 - ((StateTest) secondStep).getRateOfChange().getDy()) < 1e-4);
        assertTrue(() -> abs(exactValue - ((StateTest) secondStep).getY()) < 3e-2); //0.03 diff
    }

    @ParameterizedTest(name = "testing stepsize {0}")
    @ValueSource(doubles = {1e-1, 1e-3, 1e-5})
    void TestSolve(double step) {
        double accuracy = step * 2;
        StateInterface[] sol = mid.solve(dydx, y, 1, step);
        assertTrue(() -> abs(exactValue - ((StateTest) sol[sol.length - 1]).getY()) < accuracy);
    }

    @ParameterizedTest(name = "testing step {0}")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void Step(int indexExpected) {
        solution = mid.solve(dydx, y, tf, 0.1);
        assertTrue(() -> abs(((StateTest) solution[indexExpected]).getY() - expected[indexExpected]) < 2e-2);
    }
}