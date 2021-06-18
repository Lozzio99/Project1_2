
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import phase3.Config;
import phase3.Math.ADT.Vector3D;
import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Functions.ODEFunctionInterface;
import phase3.Math.Solvers.*;
import phase3.System.State.RateInterface;
import phase3.System.State.RateOfChange;
import phase3.System.State.StateInterface;
import phase3.System.State.SystemState;

import java.util.Arrays;

import static phase3.Config.*;
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
    private boolean isStep;


    static StateInterface<Vector3dInterface> vectorState;
    static StateInterface<Double> doubleState;

    static double t, tf, stepSize;
    static ODEFunctionInterface<Vector3dInterface> dydx = (t, y) -> {
        Vector3dInterface position = y.get()[0];
        Vector3dInterface rateOfChange = y.get()[1];
        double acceleration = -rateOfChange.getX() - sin(position.getX());
        double next_velocity = rateOfChange.getX() + stepSize*acceleration;

        return new RateOfChange<>(new Vector3D(next_velocity, 0, 0),new Vector3D(acceleration, 0, 0));
    };
    static ODEFunctionInterface<Double> dy = (t, y) -> {
        double x = y.get()[0];
        double dx = y.get()[1];
        double acceleration = -1 * dx - sin(x);
        double velocity = dx + acceleration*stepSize;


        return new RateOfChange<>(velocity,acceleration);
    };
    double exact1stepPos, exact1stepVel, exactFinalPos, exactFinalVel;

    @BeforeEach
    void setup() {
        exact1stepPos = 3.146580195184224;
        exact1stepVel = 0.995024937596275;

        exactFinalPos = 3.324069835410512;
        exactFinalVel = 0.836261857680801;

        stepSize = 0.005;
        t = 0;
        tf = 0.2;
        double x1 = Math.PI;
        double x2 = 1;
        vectorState = new SystemState<>(new Vector3D(x1, 0, 0),new Vector3D(x2,0,0));
        //RateInterface<Vector3dInterface> rate = new RateOfChange<>(new Vector3D(x2, 0, 0));
        //vectorState.get()[1] = (rate.get()[0]);
        doubleState = new SystemState<>(x1, x2);
    }

    @Test
    @DisplayName("midPointSolve")
    void midPointSolve() {
        isStep = false;
        pickSolver(MIDPOINT);
    }
    @Test
    @DisplayName("rkSolve")
    void rkSolve() {
        isStep = false;
        pickSolver(RK4);
    }
    @Test
    @DisplayName("eulerSolve")
    void eulerSolve() {
        isStep = false;
        pickSolver(EULER);
    }
    @Test
    @DisplayName("verletVelocitySolve")
    void verletVelSolve() {
        isStep = false;
        pickSolver(VERLET_VEL);
    }
    @Test
    @DisplayName("verletStandardSolve")
    void verletStdSolve() {
        isStep = false;
        pickSolver(VERLET_STD);
    }
    @Test
    @DisplayName("midPointStep")
    void midPointStep() {
        isStep = true;
        pickSolver(MIDPOINT);
    }
    @Test
    @DisplayName("rkStep")
    void rkStep() {
        isStep = true;
        pickSolver(RK4);
    }
    @Test
    @DisplayName("eulerStep")
    void eulerStep() {
        isStep = true;
        pickSolver(EULER);
    }


    void pickSolver(int SOLVER){
        switch (SOLVER) {
            case EULER -> {
                solver = new EulerSolver<>(dydx);
                setupSolve("Euler");
            }
            case RK4 -> {
                solver = new RungeKuttaSolver<>(dydx);
                setupSolve("RK");
            }
            case VERLET_STD -> {
                solver = new StandardVerletSolver<>(dydx);
                setupSolve("StandardVerlet");
            }
            case VERLET_VEL -> {
                solver = new VerletVelocitySolver<>(dydx);
                setupSolve("VelocityVerlet");
            }
            case MIDPOINT -> {
                solver = new VerletVelocitySolver<>(dydx);
                setupSolve("Midpoint");
            }
        }
    }
    private void setupSolve(String solverName){
        if(isStep){
            step(solverName);
        } else {
            this.solveVector(solverName);
        }
    }

    private void printVecResults(String solver, double exactSolutionPos, double exactSolutionVel, StateInterface<Vector3dInterface> y) {
        System.out.println(solver + " : " + Arrays.toString(y.get()));
        System.out.println(solver + " EXACT POS: " + (exactSolutionPos - y.get()[0].getX()));
        System.out.println(solver + " EXACT VEL: " + (exactSolutionVel - y.get()[1].getX()));
        assertTrue(() -> Math.abs(exactSolutionPos - y.get()[0].getX()) < 1e-2);
        assertTrue(() -> Math.abs(exactSolutionVel - y.get()[1].getX()) < 1e-2);
    }

    private void printDoubleResults(String solver, double exactSolutionPos, double exactSolutionVel, StateInterface<Double> y) {
        System.out.println(solver + " : " + y.get());
        System.out.println(solver + " EXACT POS: " + (exactSolutionPos - y.get()[0]));
        System.out.println(solver + " EXACT VEL: " + (exactSolutionVel - y.get()[1]));
        assertTrue(() -> Math.abs(exactSolutionPos - y.get()[0]) < 1e-2);
        assertTrue(() -> Math.abs(exactSolutionVel - y.get()[1]) < 1e-2);
    }

    private StateInterface<Vector3dInterface> solveVector(String solverName) {
        while (t <= tf + stepSize) {
            vectorState = solver.step(dydx, t, vectorState, stepSize);
            t += stepSize;
        }
        printVecResults(solverName, exactFinalPos, exactFinalVel, vectorState);
        return vectorState;
    }
    private StateInterface<Vector3dInterface> step(String solverName) {
        vectorState = solver.step(dydx, stepSize, vectorState, stepSize);
        printVecResults(solverName, exact1stepPos, exact1stepVel, vectorState);
        return vectorState;
    }
    /*
    private StateInterface<Double> solveDouble() {
      //  t = time;
        while (t <= tf + stepSize) {
            doubleState = solver.step(dy, time, doubleState, stepSize);
            t += stepSize;
        }
        return doubleState;
    }

     */
}
