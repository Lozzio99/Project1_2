package group17.Math.Solvers;

import group17.Interfaces.StateInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.exp;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
class TestNewTryRungeKuttaTest {

    static final double exactValue = 0.503347;
    static NewTryRungeKutta rk;
    static TestODEFunction dydx = (t, y) -> {
        RateTest rate = new RateTestClass();
        double y_ = ((StateTest) y).getY();
        rate.setDy(exp(-t) - (y_ * y_));
        return rate;
    };

    static StateTest y;
    static RateTest dy;
    static double t, tf;

    @BeforeEach
    void setup() {
        t = 0;
        tf = 1;
        double y0 = 0;
        y = new StateTestClass();
        y.setY(y0);
        rk = new NewTryRungeKutta(dydx);
    }

    @Test
    @DisplayName("Solve")
    void Solve() {
        double step = 0.5;
        double accuracy = 1e-4;
        StateInterface firstStep = rk.step(dydx, t, y, step);
        assertTrue(() -> abs(0.36609962 - ((StateTest) firstStep).getY()) < accuracy);
        assertTrue(() -> abs(0.36609962 - ((StateTest) firstStep).getRateOfChange().getDy()) < accuracy);
        t += step;
        StateInterface secondStep = rk.step(dydx, t, firstStep, step);
        assertTrue(() -> abs(0.5025005 - ((StateTest) secondStep).getY()) < accuracy);
        assertTrue(() -> abs(0.1364009 - ((StateTest) secondStep).getRateOfChange().getDy()) < accuracy);
        assertTrue(() -> abs(exactValue - ((StateTest) secondStep).getY()) < 1e-3); //0.4 diff
    }


    @ParameterizedTest(name = "testing stepSize {0}")
    @ValueSource(doubles = {1e-2, 1e-3, 1e-4, 1e-5, 1e-6})
    void TestSolve(double step) {
        //this uses the step method
        double accuracy = step * 10;
        StateInterface[] sol = new StateInterface[(int) (Math.round(tf / step)) + 2];
        double currTime = 0;
        sol[0] = y;
        for (int i = 1; i < sol.length - 1; i++) {
            sol[i] = rk.step(dydx, currTime, y, step);
            y = (StateTest) sol[i];
            currTime += step;
        }
        sol[sol.length - 1] = rk.step(dydx, tf - currTime, y, tf - currTime);
        assertTrue(() -> abs(exactValue - ((StateTest) sol[sol.length - 1]).getY()) < accuracy);
    }

    @Test
    @DisplayName("Step")
    void Step() {
        double step = 0.2, accuracy = 1e-4;
        StateInterface step1, step2, step3, step4, step5;
        step1 = rk.step(dydx, t, y, step);
        t += step;
        assertTrue(() -> abs(0.179 - ((StateTest) step1).getRateOfChange().getDy()) < accuracy); //from 0 to 0.17900
        assertTrue(() -> abs(0.179 - ((StateTest) step1).getY()) < accuracy);

        step2 = rk.step(dydx, t, step1, step);
        t += step;
        assertTrue(() -> abs(0.13556 - ((StateTest) step2).getRateOfChange().getDy()) < accuracy);
        assertTrue(() -> abs(0.31456 - ((StateTest) step2).getY()) < accuracy);

        step3 = rk.step(dydx, t, step2, step);
        t += step;
        assertTrue(() -> abs(0.09470 - ((StateTest) step3).getRateOfChange().getDy()) < accuracy);
        assertTrue(() -> abs(0.40925 - ((StateTest) step3).getY()) < accuracy);


        step4 = rk.step(dydx, t, step3, step);
        t += step;
        assertTrue(() -> abs(0.06035 - ((StateTest) step4).getRateOfChange().getDy()) < accuracy);
        assertTrue(() -> abs(0.4696 - ((StateTest) step4).getY()) < accuracy);

        step5 = rk.step(dydx, t, step4, step);
        t += step;
        assertTrue(() -> abs(0.03373 - ((StateTest) step5).getRateOfChange().getDy()) < accuracy);
        assertTrue(() -> abs(0.50333 - ((StateTest) step5).getY()) < accuracy);
    }

    @Test
    @DisplayName("GetFunction")
    void GetFunction() {
        assertDoesNotThrow(() -> rk.getFunction());
    }


}