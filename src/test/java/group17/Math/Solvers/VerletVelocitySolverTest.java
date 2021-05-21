package group17.Math.Solvers;


import group17.Interfaces.StateInterface;
import group17.Math.Lib.FreeFallFunction;
import group17.Math.Lib.Vector3D;
import group17.System.GravityFunction;
import group17.System.State.SystemState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static group17.Utils.Config.DEBUG;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VerletVelocitySolverTest {


    @Test
    @DisplayName("SolveODE")
    void SolveODE() {
        FreeFallFunction yd = new FreeFallFunction();
        VerletVelocitySolver solver = new VerletVelocitySolver(new GravityFunction().getEvaluateRateOfChange());
        // init parameters
        Vector3D initPos = new Vector3D(0, 0, 0); // at t_0 = 0
        Vector3D initVelocity = new Vector3D(0, 0, 0);
        SystemState initState = new SystemState();
        initState.getPositions().add(initPos);
        initState.getRateOfChange().getVelocities().add(initVelocity);
        // get solution
        double tf = 6.0; // final time
        StateInterface[] aprxStates = solver.solve(yd, initState, tf, 0.5);

        double aprxSolution = aprxStates[aprxStates.length - 1].getPositions().get(0).getZ();
        double expectedSol = initVelocity.getZ() * tf - 0.5 * FreeFallFunction.CONSTANT_G * tf * tf; // free fall equation
        if (DEBUG) {
            System.out.println(expectedSol);
            for (StateInterface aprxState : aprxStates) {
                System.out.println(aprxState.getPositions().get(0).getZ());
            }
        }


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
