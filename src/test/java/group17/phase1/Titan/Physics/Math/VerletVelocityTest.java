package group17.phase1.Titan.Physics.Math;

import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.System.SystemState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class VerletVelocityTest {

    @Test
    @DisplayName("SolveODE")
    void SolveODE() {
        FreeFallFunction yd = new FreeFallFunction();
        VerletVelocity solver = new VerletVelocity();
        // init parameters
        Vector3D initPos = new Vector3D(0, 0, 0); // at t_0 = 0
        Vector3D initVelocity = new Vector3D(0,0, 0);
        ArrayList<Vector3dInterface> stateList = new ArrayList<>();
        stateList.add(initPos);
        stateList.add(initVelocity);
        SystemState initState = new SystemState(stateList);
        // get solution
        double tf = 6.0; // final time
        StateInterface[] aprxSolution = solver.solve(yd, initState, tf,0.05);
        double aprxDouble = aprxSolution[aprxSolution.length-1].getPositions().get(0).getZ();
        assertTrue(1e-9 > Math.abs((initVelocity.getZ()*tf-0.5*FreeFallFunction.CONSTANT_G*tf*tf) - aprxDouble));
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
