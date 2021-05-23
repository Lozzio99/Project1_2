package group17.Math.Solvers;

import group17.Interfaces.*;
import group17.System.State.RateTestClass;
import group17.System.State.StateTestClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.exp;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Source for the test is NM lecture on euler method for solving ODE systems
 */
class EulerSolverTest {
    static final double exactValue = 0.5033467;
    static ODESolverInterface euler;
    static ODEFunctionInterface dydx = (t, y) -> {
        RateTest rate = new RateTestClass();
        double y_ = ((StateTest) y).getY();
        rate.setDy(exp(-t) - (y_ * y_));
        return rate;
    };
    static StateTest y;
    static double t, tf, stepSize;

    @BeforeEach
    void setup() {
        stepSize = 0.0001;
        t = 0;
        tf = 1;
        double y0 = 0;
        y = new StateTestClass();
        y.setY(y0);
        euler = new EulerSolver(dydx);
    }


    private static StateTest[] getSolution(double step) {
        StateTest[] sol = new StateTest[(int) (Math.round(tf / step)) + 2];
        double currTime = 0;
        sol[0] = y;
        for (int i = 1; i < sol.length - 1; i++) {
            sol[i] = (StateTest) euler.step(dydx, currTime, y, step);
            y = sol[i];
            currTime += step;
        }
        sol[sol.length - 1] = (StateTest) euler.step(dydx, tf - currTime, y, tf - currTime);
        return sol;
    }

    @Test
    @DisplayName("Solve")
    void Solve() {
        assertTrue(() -> abs(1.0 - ((StateTest) euler.step(dydx, t, y, 1)).getY()) < 1e-4);
        assertTrue(() -> abs(1.0 - ((RateTest) euler.step(dydx, t, y, 1).getRateOfChange()).getDy()) < 1e-4);
        StateInterface[] solution = euler.solve(dydx, y, tf, stepSize);
        assertTrue(() -> abs(exactValue - ((StateTest) solution[solution.length - 1]).getY()) < 1e-4); //1e-4
    }

    @Test
    @DisplayName("TestSolve")
    void TestSolve() {
        StateInterface[] solution = euler.solve(dydx, y, tf, stepSize);
        StateTest[] step = getSolution(stepSize);
        assertEquals(((StateTest) solution[solution.length - 1]).getY(), step[step.length - 1].getY());
    }

    @Test
    @DisplayName("Step")
    void Step() {
        StateTest[] test = getSolution(0.1);
        StateTest y = test[test.length - 1];
        double absErr, relErr, accuracy = 1.2e-1;
        absErr = abs(y.getY() - exactValue);
        relErr = absErr / exactValue;
        System.out.println(absErr);
        System.out.println(exactValue);
        assertTrue(relErr < accuracy);
    }

    @Test
    @DisplayName("GetFunction")
    void GetFunction() {
        assertNotNull(euler.getFunction());
    }

}