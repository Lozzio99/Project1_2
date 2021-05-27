package group17.Solvers;

import group17.Interfaces.StateInterface;
import group17.Math.FreeFallFunction;
import group17.Math.GravityFunction;
import group17.Math.Vector3D;
import group17.Simulation.System.State.SystemState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static group17.Utils.Config.DEBUG;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StandardVerletSolverTest {

    @Test
    @DisplayName("SolveODE")
    void SolveODE() {
        FreeFallFunction yd = new FreeFallFunction();
        StandardVerletSolver solver = new StandardVerletSolver(new GravityFunction().evaluateCurrentAccelerationFunction());
        solver.setFirst();
        // init parameters
        Vector3D initPos = new Vector3D(0, 0, 0); // at t_0 = 0
        Vector3D initVelocity = new Vector3D(0, 0, 0);
        SystemState initState = new SystemState();
        initState.getPositions().add(initPos);
        initState.getRateOfChange().getVelocities().add(initVelocity);
        // get solution
        double tf = 6.0; // final time
        StateInterface[] aprxStates = solver.solve(yd, initState, tf, 0.0001);

        double aprxSolution = aprxStates[aprxStates.length - 1].getPositions().get(0).getZ();
        double expectedSol = initVelocity.getZ() * tf - 0.5 * FreeFallFunction.CONSTANT_G * tf * tf; // free fall equation
        if (DEBUG) {
            System.out.println(expectedSol);
            System.out.println(aprxSolution);
        }
        // very and very inaccurate :(
        assertTrue(1e2 > Math.abs(expectedSol - aprxSolution));
    }

}
