package group17.Math.Solvers;

import group17.Interfaces.Function;
import group17.Interfaces.NewtRaphFunction;
import group17.Interfaces.Vector3dInterface;
import group17.Math.Utils.Vector3D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static group17.Config.CHECK_COLLISIONS;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NewtonRaphsonSolverTest {
    static {

    }

    @Test
    @DisplayName("Newton-Raphson solver")
    void NewtRaphSolve() {

        NewtRaphFunction testFx = (pos, vel, time) -> null;
        Function<Vector3dInterface> testF = vector -> {
            double x = vector.getX(), y = vector.getY(), z = vector.getZ();
            return new Vector3D(
                    3.0 * x - Math.cos(y * z) - 3.0 / 2.0,
                    4.0 * x * x - 625.0 * y * y + 2.0 * z - 1.0,
                    20.0 * z + Math.exp(-1.0 * x * y) + 9.0
            );
        };

        CHECK_COLLISIONS = false;
        Vector3dInterface testRes = new NewtonRaphsonSolver(testFx, testF).NewtRhapSolution(new Vector3D(1, 1, 1), new Vector3D(0, 0, 0));
        Vector3dInterface testFxSol = new Vector3D(0.0, 0.0, 0.0);
        Vector3dInterface testFxRes = testF.apply(testRes);
        System.out.println(testRes.toString());
        System.out.println(testFxRes);
        assertTrue(Math.abs(testFxRes.getX() - testFxSol.getX()) < 0.001
                && Math.abs(testFxRes.getY() - testFxSol.getY()) < 0.001
                && Math.abs(testFxRes.getZ() - testFxSol.getZ()) < 0.001);
    }
}