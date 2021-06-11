package Module.Math;

import Module.Math.ADT.Vector3D;
import Module.Math.ADT.Vector3dInterface;
import Module.Math.Functions.ModuleFunction;
import Module.Math.Functions.ODEFunctionInterface;
import Module.Math.Solvers.*;
import Module.System.State.StateInterface;
import Module.System.State.SystemState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        while(t<=tf+stepSize){
            nextY = solver.step(solver.getFunction(), t, y, stepSize);
            y = nextY.copy();
            t+=stepSize;
        }
        System.out.println("EULER");
        System.out.println("POSITION DIFF: " +Math.abs(exactYPosition - y.get().get(1)));
        System.out.println("VELOCITY DIFF: " +Math.abs(exactYVelocity - y.getRateOfChange().get().get(1)));

        assertTrue(() -> Math.abs(exactYPosition - y.get().get(1))<1);
        assertTrue(() -> Math.abs(exactYVelocity - y.getRateOfChange().get().get(1))<1e-1);
    }

    @Test
    @DisplayName("MidPointSolve")
    void midPointSolve(){
        this.solver = new MidPointSolver<>(this.function);

        StateInterface<Vector3dInterface> nextY;
        while(t<=tf+stepSize){
            nextY = solver.step(solver.getFunction(), t, y, stepSize);
            y = nextY.copy();
            t+=stepSize;
        }
        System.out.println("MIDPOINT");
        System.out.println("POSITION DIFF: " +Math.abs(exactYPosition - y.get().get(1)));
        System.out.println("VELOCITY DIFF: " +Math.abs(exactYVelocity - y.getRateOfChange().get().get(1)));

        assertTrue(() -> Math.abs(exactYPosition - y.get().get(1))<1);
        assertTrue(() -> Math.abs(exactYVelocity - y.getRateOfChange().get().get(1))<1e-1);
    }

    @Test
    @DisplayName("RK4Solve")
    void rk4Solve(){
        this.solver = new RungeKuttaSolver<>(this.function);

        StateInterface<Vector3dInterface> nextY;
        while(t<=tf+stepSize){
            nextY = solver.step(solver.getFunction(), t, y, stepSize);
            y = nextY.copy();
            t+=stepSize;
        }
        System.out.println("RK4");
        System.out.println("POSITION DIFF: " +Math.abs(exactYPosition - y.get().get(1)));
        System.out.println("VELOCITY DIFF: " +Math.abs(exactYVelocity - y.getRateOfChange().get().get(1)));

        assertTrue(() -> Math.abs(exactYPosition - y.get().get(1))<1);
        assertTrue(() -> Math.abs(exactYVelocity - y.getRateOfChange().get().get(1))<1e-1);
    }

    @Test
    @DisplayName("VerletSTDSolve")
    void verletSTDSolve(){
        this.solver = new StandardVerletSolver<>(this.function);

        StateInterface<Vector3dInterface> nextY;
        while(t<=tf+stepSize){
            nextY = solver.step(solver.getFunction(), t, y, stepSize);
            y = nextY.copy();
            t+=stepSize;
        }
        System.out.println("VERLET STD");
        System.out.println("POSITION DIFF: " +Math.abs(exactYPosition - y.get().get(1)));
        System.out.println("VELOCITY DIFF: " +Math.abs(exactYVelocity - y.getRateOfChange().get().get(1)));

        assertTrue(() -> Math.abs(exactYPosition - y.get().get(1))<1);
        assertTrue(() -> Math.abs(exactYVelocity - y.getRateOfChange().get().get(1))<1e-1);
    }

    @Test
    @DisplayName("VerletVelSolve")
    void verletVelSolve(){
        this.solver = new VerletVelocitySolver<>(this.function);

        StateInterface<Vector3dInterface> nextY;
        while(t<=tf+stepSize){
            nextY = solver.step(solver.getFunction(), t, y, stepSize);
            y = nextY.copy();
            t+=stepSize;
        }
        System.out.println("POSITION DIFF: " +Math.abs(exactYPosition - y.get().get(1)));
        System.out.println("VELOCITY DIFF: " +Math.abs(exactYVelocity - y.getRateOfChange().get().get(1)));

       // assertTrue(() -> Math.abs(exactYPosition - y.get().get(1))<1);
       // assertTrue(() -> Math.abs(exactYVelocity - y.getRateOfChange().get().get(1))<1e-1);
    }

}
