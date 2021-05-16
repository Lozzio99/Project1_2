package group17.Math.Solvers;

import group17.Interfaces.NewtRaphFunction;
import group17.Interfaces.Vector3dInterface;
import group17.Math.Utils.Vector3D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static group17.Config.CHECK_COLLISIONS;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NewtonRaphsonSolverTest {

    @Test
    @DisplayName("Newton-Raphson solver")
    void NewtRaphSolve() {
        // Example from: http://fourier.eng.hmc.edu/e176/lectures/NM/node21.html
        NewtRaphFunction testFx = vector -> {
            double x = vector.getX(), y = vector.getY(), z = vector.getZ();
            return new Vector3D(
                    3.0 * x - Math.cos(y * z) - 3.0 / 2.0,
                    4.0 * x * x - 625.0 * y * y + 2.0 * z - 1.0,
                    20.0 * z + Math.exp(-1.0 * x * y) + 9.0
            );
        };

        Vector3dInterface testRes = (new NewtonRaphsonSolver(testFx)).NewtRhapSolution(new Vector3D(1, 1, 1), new Vector3D(0, 0, 0), 0.01);
        System.out.println(testRes.toString());
        Vector3dInterface testFxSol = new Vector3D(0.0, 0.0, 0.0);
        Vector3dInterface testFxRes = testFx.modelFx(testRes);
        System.out.println(testFxRes.toString());
        assertTrue(Math.abs(testFxRes.getX() - testFxSol.getX()) < 0.001
                && Math.abs(testFxRes.getY() - testFxSol.getY()) < 0.001
                && Math.abs(testFxRes.getZ() - testFxSol.getZ()) < 0.001);
    }

    @Test
    @DisplayName("Newton-Raphson Step")
    void NewtRaphStep() {
        // Example from: http://fourier.eng.hmc.edu/e176/lectures/NM/node21.html
        NewtRaphFunction testFx = vector -> {
            double x = vector.getX(), y = vector.getY(), z = vector.getZ();
            return new Vector3D(
                    3.0 * x - Math.cos(y * z) - 3.0 / 2.0,
                    4.0 * x * x - 625.0 * y * y + 2.0 * z - 1.0,
                    20.0 * z + Math.exp(-1.0 * x * y) + 9.0
            );
        };

        Vector3dInterface testRes = (new NewtonRaphsonSolver(testFx)).NewtRhapStep(new Vector3D(1.0,1.0,1.0), 0.01);
        Vector3dInterface testSol = new Vector3D(1.2326915251615438,0.50313202046925,-0.4732533073667555);
        assertTrue(Math.abs(testRes.getX() - testSol.getX()) < 0.001
                && Math.abs(testRes.getY() - testSol.getY()) < 0.001
                && Math.abs(testRes.getZ() - testSol.getZ()) < 0.001);
    }
}