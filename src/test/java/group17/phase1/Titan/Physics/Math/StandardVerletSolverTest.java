package group17.phase1.Titan.Physics.Math;

import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Physics.Solvers.StandardVerletSolver;
import group17.phase1.Titan.System.Clock;
import group17.phase1.Titan.System.SystemState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StandardVerletSolverTest {

    @Test
    @DisplayName("SolveODE")
    void SolveODE() {
        FreeFallFunction yd = new FreeFallFunction();
        StandardVerletSolver solver = new StandardVerletSolver();
        solver.setClock(new Clock().setInitialTime(0, 0, 0));
        // init parameters
        Vector3D initPos = new Vector3D(0, 0, 0); // at t_0 = 0
        Vector3D initVelocity = new Vector3D(0,0, 1);
        SystemState initState = new SystemState();
        initState.getPositions().add(initPos);
        initState.getRateOfChange().getVelocities().add(initVelocity);
        // get solution
        double tf = 6.0; // final time
        StateInterface[] aprxStates = solver.solve(yd, initState, tf,0.05);

        double aprxSolution = aprxStates[aprxStates.length-1].getPositions().get(0).getZ();
        double expectedSol = initVelocity.getZ()*tf-0.5*FreeFallFunction.CONSTANT_G*tf*tf; // free fall equation
        System.out.println(expectedSol);
        System.out.println(aprxSolution);
        assertTrue(1e-12 > Math.abs(expectedSol - aprxSolution));
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
