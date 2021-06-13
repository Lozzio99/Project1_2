import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import phase3.Math.ADT.Vector3D;
import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Functions.ODEFunctionInterface;
import phase3.Math.Solvers.EulerSolver;
import phase3.Math.Solvers.MidPointSolver;
import phase3.Math.Solvers.ODESolverInterface;
import phase3.Math.Solvers.RungeKuttaSolver;
import phase3.System.State.RateInterface;
import phase3.System.State.RateOfChange;
import phase3.System.State.StateInterface;
import phase3.System.State.SystemState;

import static java.lang.Math.sin;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This Test uses the damped driven pendulum equation of motion, a second order ODE
 * ¨x + delta*˙x + ro*sin(x)
 * <p>
 * MATLAB test code (regarded as exactValue)
 * x0 = pi;
 * v0 = 1;
 * g = @(t,x,v) -v - sin(x);
 * f = @(t,vectorState) [vectorState(2);g(t,vectorState(1),vectorState(2))];
 * [ts,ys] = ode45(f,[0,0.2],y0);
 */
class GeneralSystemTest {
    static ODESolverInterface<Vector3dInterface> solver;

    static StateInterface<Vector3dInterface> vectorState;
    static StateInterface<Double> doubleState;

    static double t, tf, stepSize;
    static ODEFunctionInterface<Vector3dInterface> dydx = (t, y) -> {
        Vector3dInterface position = y.get();
        Vector3dInterface rateOfChange = y.getRateOfChange().get();
        double acceleration = -rateOfChange.get(0) - sin(position.get(0));
        return new RateOfChange<>(new Vector3D(acceleration, 0, 0));
    };
    static ODEFunctionInterface<Double> dy = (t, y) -> {
        double x = y.get();
        double dx = y.getRateOfChange().get();
        return new RateOfChange<>(-1 * dx - sin(x));
    };
    double exact1stepPos, exact1stepVel, exactFinalPos, exactFinalVel;

    @BeforeEach
    void setup() {
        exact1stepPos = 3.146580195184224;
        exact1stepVel = 0.995024937596275;

        exactFinalPos = 3.324069835410512;
        exactFinalVel = 0.836261857680801;

        stepSize = 0.005000000000000;
        t = 0;
        tf = 0.2;
        double x1 = Math.PI;
        double x2 = 1;
        vectorState = new SystemState<>(new Vector3D(x1, 0, 0));
        RateInterface<Vector3dInterface> rate = new RateOfChange<>(new Vector3D(x2, 0, 0));
        vectorState.getRateOfChange().set(rate.get());
        doubleState = new SystemState<>(x1, x2);
    }

    @Test
    @DisplayName("midPointSolve")
    void midPointSolve() {
        vectorState = this.solveVector(new MidPointSolver<>(dydx), 0);
        printVecResults("MIDPOINT", exactFinalPos, exactFinalVel, vectorState);
        doubleState = this.solveDouble(new MidPointSolver<>(dy), 0);
        printDoubleResults("MIDPOINT", exactFinalPos, exactFinalVel, doubleState);
    }


    @Test
    @DisplayName("rkSolve")
    void rkSolve() {
        vectorState = this.solveVector(new RungeKuttaSolver<>(dydx), 0);
        printVecResults("MIDPOINT", exactFinalPos, exactFinalVel, vectorState);
        doubleState = this.solveDouble(new RungeKuttaSolver<>(dy), 0);
        printDoubleResults("MIDPOINT", exactFinalPos, exactFinalVel, doubleState);
    }

    @Test
    @DisplayName("eulerSolve")
    void eulerSolve() {
        vectorState = this.solveVector(new EulerSolver<>(dydx), 0);
        printVecResults("MIDPOINT", exactFinalPos, exactFinalVel, vectorState);
        doubleState = this.solveDouble(new EulerSolver<>(dy), 0);
        printDoubleResults("MIDPOINT", exactFinalPos, exactFinalVel, doubleState);
    }

    @Test
    @DisplayName("midPointStep")
    void midPointStep() {
        solver = new MidPointSolver<>(dydx);
        StateInterface<Vector3dInterface> nextY;
        nextY = solver.step(dydx, 0, vectorState, stepSize);
        printVecResults("MIDPOINT", exact1stepPos, exact1stepVel, nextY);
        assertTrue(() -> Math.abs(exact1stepPos - nextY.get().get(0)) < 1e-4);
        assertTrue(() -> Math.abs(exact1stepVel - nextY.getRateOfChange().get().get(0)) < 1e-2);
    }

    @Test
    @DisplayName("rkStep")
    void rkStep() {
        solver = new RungeKuttaSolver<>(dydx);
        StateInterface<Vector3dInterface> nextY;
        nextY = solver.step(dydx, t, vectorState, stepSize);
        printVecResults("RK", exact1stepPos, exact1stepVel, nextY);

        assertTrue(() -> Math.abs(exact1stepPos - nextY.get().get(0)) < 1e-2);
        assertTrue(() -> Math.abs(exact1stepVel - nextY.getRateOfChange().get().get(0)) < 1e-2);
    }

    @Test
    @DisplayName("eulerStep")
    void eulerStep() {
        solver = new EulerSolver<>(dydx);
        StateInterface<Vector3dInterface> nextY;

        nextY = solver.step(dydx, stepSize, vectorState, stepSize);

        printVecResults("EULER", exact1stepPos, exact1stepVel, nextY);

        assertTrue(() -> Math.abs(exact1stepPos - nextY.get().get(0)) < 1e-2);
        assertTrue(() -> Math.abs(exact1stepVel - nextY.getRateOfChange().get().get(0)) < 1e-2);
    }

    private void printVecResults(String solver, double exactSolutionPos, double exactSolutionVel, StateInterface<Vector3dInterface> y) {
        System.out.println(solver + " : " + y.get());
        System.out.println(solver + " EXACT POS: " + (exactSolutionPos - y.get().get(0)));
        System.out.println(solver + " EXACT VEL: " + (exactSolutionVel - y.getRateOfChange().get().get(0)));
        assertTrue(() -> Math.abs(exactSolutionPos - y.get().get(0)) < 1e-2);
        assertTrue(() -> Math.abs(exactSolutionVel - y.getRateOfChange().get().get(0)) < 1e-2);
    }

    private void printDoubleResults(String solver, double exactSolutionPos, double exactSolutionVel, StateInterface<Double> y) {
        System.out.println(solver + " : " + y.get());
        System.out.println(solver + " EXACT POS: " + (exactSolutionPos - y.get()));
        System.out.println(solver + " EXACT VEL: " + (exactSolutionVel - y.getRateOfChange().get()));
        assertTrue(() -> Math.abs(exactSolutionPos - y.get()) < 1e-2);
        assertTrue(() -> Math.abs(exactSolutionVel - y.getRateOfChange().get()) < 1e-2);
    }

    private StateInterface<Vector3dInterface> solveVector(ODESolverInterface<Vector3dInterface> solver, double time) {
        t = time;
        while (t <= tf + stepSize) {
            vectorState = solver.step(dydx, time, vectorState, stepSize);
            t += stepSize;
        }
        return vectorState;
    }

    private StateInterface<Double> solveDouble(ODESolverInterface<Double> solver, double time) {
        t = time;
        while (t <= tf + stepSize) {
            doubleState = solver.step(dy, time, doubleState, stepSize);
            t += stepSize;
        }
        return doubleState;
    }

}
