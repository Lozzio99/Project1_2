package group17.phase1.Titan.Physics.Math;

import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.System.SystemState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class VerletSolverTest {

    @Test
    @DisplayName("SolveODE")
    void SolveODE() {
        ODESolvable yd = new ODESolvable();
        VerletSolver solver = new VerletSolver();
        // init parameters
        Vector3D initPos = new Vector3D(0, 0, 2); // at t_0 = 0
        Vector3D initVelocity = new Vector3D(0,0, 0);
        ArrayList<Vector3dInterface> stateList = new ArrayList<>();
        SystemState initState = new SystemState();
        stateList.add(initPos);
        stateList.add(initVelocity);
        initState.setPositions(stateList);
        // get solution
        SystemState[] solution = (SystemState[]) solver.solve(yd, initState, 6.0,0.1);
        // TODO: check accuracy of calculations
    }

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

}
