import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import phase3.Math.ADT.Vector3D;
import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Forces.ModuleFunction;
import phase3.Math.Functions.ODEFunctionInterface;
import phase3.Math.Solvers.*;
import phase3.System.State.StateInterface;
import phase3.System.State.SystemState;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModuleTest {
    double stepSize;
    double t,tf;

    double exactYVelocity = -40.56000;
    double exactYPosition = 391.60000;

    private ODESolverInterface<Vector3dInterface> solver;
    private ODEFunctionInterface<Vector3dInterface> function;
    private StateInterface<Vector3dInterface> y;


    @BeforeEach
    void setup(){
        this.function = new ModuleFunction()
                .evaluateCurrentAccelerationFunction();
        stepSize = 0.01;
       y = new SystemState<>(new Vector3D(0.0,1e3,0.0),new Vector3D(0.0,0.0,0.0));

       t = 0;
       tf=30;
    }



    @Test
    @DisplayName("EulerSolve")
    void eulerSolve(){
        this.solver = new EulerSolver<>(this.function);

        StateInterface<Vector3dInterface> nextY;
        while (t <= tf + stepSize) {
            nextY = solver.step(solver.getFunction(), t, y, stepSize);
            y = nextY.copy();
            t += stepSize;
        }
        System.out.println("EULER");
        System.out.println("POSITION DIFF: " + Math.abs(exactYPosition - y.get()[0].getY()));
        System.out.println("VELOCITY DIFF: " + Math.abs(exactYVelocity - y.get()[1].getY()));

        assertTrue(() -> Math.abs(exactYPosition - y.get()[0].getY()) < 1);
        assertTrue(() -> Math.abs(exactYVelocity - y.get()[1].getY()) < 1e-1);
    }

    @Test
    @DisplayName("MidPointSolve")
    void midPointSolve() {
        this.solver = new MidPointSolver<>(this.function);

        StateInterface<Vector3dInterface> nextY;
        while (t <= tf + stepSize) {
            nextY = solver.step(solver.getFunction(), t, y, stepSize);
            y = nextY.copy();
            t += stepSize;
        }
        System.out.println("MIDPOINT");
        System.out.println("POSITION DIFF: " + Math.abs(exactYPosition - y.get()[0].getY()));
        System.out.println("VELOCITY DIFF: " + Math.abs(exactYVelocity - y.get()[1].getY()));

        assertTrue(() -> Math.abs(exactYPosition - y.get()[0].getY()) < 1);
        assertTrue(() -> Math.abs(exactYVelocity - y.get()[1].getY()) < 1e-1);
    }

    @Test
    @DisplayName("RK4Solve")
    void rk4Solve() {
        this.solver = new RungeKuttaSolver<>(this.function);

        StateInterface<Vector3dInterface> nextY;
        while (t <= tf + stepSize) {
            nextY = solver.step(solver.getFunction(), t, y, stepSize);
            y = nextY.copy();
            t += stepSize;
        }
        System.out.println("RK4");
        System.out.println("POSITION DIFF: " + Math.abs(exactYPosition - y.get()[0].getY()));
        System.out.println("VELOCITY DIFF: " + Math.abs(exactYVelocity - y.get()[1].getY()));

        assertTrue(() -> Math.abs(exactYPosition - y.get()[0].getY()) < 1);
        assertTrue(() -> Math.abs(exactYVelocity - y.get()[1].getY()) < 1e-1);
    }

    @Test
    @DisplayName("VerletSTDSolve")
    void verletSTDSolve() {
        this.solver = new StandardVerletSolver<>(this.function);

        StateInterface<Vector3dInterface> nextY;
        while (t <= tf + stepSize) {
            nextY = solver.step(solver.getFunction(), t, y, stepSize);
            y = nextY.copy();
            t += stepSize;
        }
        System.out.println("VERLET STD");
        System.out.println("POSITION DIFF: " + Math.abs(exactYPosition - y.get()[0].getY()));
        System.out.println("VELOCITY DIFF: " + Math.abs(exactYVelocity - y.get()[1].getY()));

        assertTrue(() -> Math.abs(exactYPosition - y.get()[0].getY()) < 1);
        assertTrue(() -> Math.abs(exactYVelocity - y.get()[1].getY()) < 1e-1);
    }

    @Test
    @DisplayName("VerletVelSolve")
    void verletVelSolve() {
        this.solver = new VerletVelocitySolver<>(this.function);

        StateInterface<Vector3dInterface> nextY;
        while (t <= tf + stepSize) {
            nextY = solver.step(solver.getFunction(), t, y, stepSize);
            y = nextY.copy();
            t += stepSize;
        }
        System.out.println("POSITION DIFF: " + Math.abs(exactYPosition - y.get()[0].getY()));
        System.out.println("VELOCITY DIFF: " + Math.abs(exactYVelocity - y.get()[1].getY()));

        // assertTrue(() -> Math.abs(exactYPosition - vectorState.get().get(1))<1);
        // assertTrue(() -> Math.abs(exactYVelocity - vectorState.getRateOfChange().get().get(1))<1e-1);
    }

}
