package group17.Math;

import group17.Interfaces.NewtRaphFunction;
import group17.Interfaces.Vector3dInterface;
import group17.Math.Utils.PartialDerivative;
import group17.Math.Utils.Vector3D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PartialDerivativeTest {

    @Test
    @DisplayName("Solve partial derivatives")
    void SolvePD() {
        NewtRaphFunction testFx = new NewtRaphFunction() {
            @Override
            public Vector3dInterface modelFx(Vector3dInterface vector) {
                return new Vector3D(
                        3 * vector.getX() + 4 * vector.getY() + 5 * vector.getZ(),
                        7 * vector.getX() + 8 * Math.pow(vector.getY(), 2) + 2 * vector.getZ(),
                        16 * vector.getY() + Math.pow(vector.getZ(), 3)
                );
            }

            @Override
            public Vector3dInterface stateFX(Vector3dInterface initPos, Vector3dInterface initVelocity, double time) {
                return null;
            }
        };

        Vector3D testRes = PartialDerivative.getPartialDerivatives(testFx, new Vector3D(1, 2, 3), 0.1, 0);
        Vector3D testSol = new Vector3D(3, 7, 0);
        assertTrue(Math.abs(testRes.getX() - testSol.getX()) < 0.1
                && Math.abs(testRes.getY() - testSol.getY()) < 0.1
                && Math.abs(testRes.getZ() - testSol.getZ()) < 0.1);
    }

    @Test
    @DisplayName("Solve Jacobian matrix")
    void SolveJacobian() {
        NewtRaphFunction testFx = new NewtRaphFunction() {
            @Override
            public Vector3dInterface modelFx(Vector3dInterface vector) {
                return new Vector3D(
                        3 * vector.getX() + 4 * vector.getY() + 5 * vector.getZ(),
                        7 * vector.getX() + 8 * Math.pow(vector.getY(), 2) + 2 * vector.getZ(),
                        16 * vector.getY() + Math.pow(vector.getZ(), 3)
                );
            }

            @Override
            public Vector3dInterface stateFX(Vector3dInterface initPos, Vector3dInterface initVelocity, double time) {
                return null;
            }
        };

        double[][] testRes = PartialDerivative.getJacobianMatrix(testFx, new Vector3D(1, 2, 3), 0.01);
        double[][] testSol = new double[][]{{3, 4, 5}, {7, 32, 2}, {0, 16, 27}};
        boolean valid = true;
        for (int i = 0; i < testSol.length; i++) {
            for (int j = 0; j < testSol.length; j++) {
                if (Math.abs(testRes[i][j] - testSol[i][j]) > 0.1) valid = false;
            }
        }
        assertTrue(valid);
    }
}
