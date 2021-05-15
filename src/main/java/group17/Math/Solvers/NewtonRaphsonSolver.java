package group17.Math.Solvers;

import group17.Interfaces.NewtRaphFunction;
import group17.Interfaces.Vector3dInterface;
import group17.Math.Utils.Matrix;
import group17.Math.Utils.PartialDerivative;
import group17.Math.Utils.Vector3D;
import org.jetbrains.annotations.Contract;

import static group17.Config.INSERT_ROCKET;
import static group17.Config.STEP_SIZE;
import static group17.Math.Solvers.RocketSimModel.pF;
import static group17.Math.Solvers.RocketSimModel.targetPosition;

public class NewtonRaphsonSolver {

    final boolean LOG_ITERATION = true;
    private int i = 0;
    public static Vector3dInterface initPos;
    public static double endTime;

    static {
        INSERT_ROCKET = true;
    }

    final double STOPPING_CRITERION = 100;
    private final NewtRaphFunction fX;
    public Vector3dInterface initSol;
    public Vector3dInterface targetSol;

    /**
     * Constructor of a basic Newton-Raphson solver to approximate solution of a function
     *
     * @param fX 3-d vector function
     */
    @Contract(pure = true)
    public NewtonRaphsonSolver(NewtRaphFunction fX) {
        this.fX = fX;
    }

    /**
     * Constructor of Newton-Raphson solver for the rocket-simulation problem
     * @param initPos initial position of a rocket
     * @param endTime fixed time at which rocket must reach its target
     */
    public NewtonRaphsonSolver(Vector3dInterface initPos, double endTime) {
        this.fX = pF;
        NewtonRaphsonSolver.initPos = initPos;
        NewtonRaphsonSolver.endTime = endTime;
    }

    /**
     * Method for single-norm relative error of a vector
     *
     * @param v1 approximated vector
     * @param v2 target vector
     * @return relative error
     */
    public static double getRelativeError(Vector3dInterface v1, Vector3dInterface v2) {
        return (v1.sub(v2)).norm() / v2.norm();
    }

    public static void main(String[] args) {
        NewtonRaphsonSolver nrSolver = new NewtonRaphsonSolver(new Vector3D(), 2678400.0);
        Vector3dInterface aprxVel = nrSolver.NewtRhapSolution(new Vector3D(-7485730.186, 6281273.438, -8172135.798), new Vector3D(-1.160685142387819E+00, 1.189131565321710E+00, 5.323467155528554E-02));
    }

    /**
     * Approximation of a solution to a function using Newton-Rhapson method
     * @param initSol initial solution
     * @param targetSol target function solution
     * @return approximated solution i.e. fX(aprxSol) ~ targetSol
     */
    public Vector3dInterface NewtRhapSolution(Vector3dInterface initSol, Vector3dInterface targetSol) {
        this.initSol = initSol.clone();
        this.targetSol = targetSol.clone();
        targetPosition = targetSol.clone();
        Vector3dInterface aprxSol = initSol.clone();
        // iteratively apply newton-raphson
        while (i < STOPPING_CRITERION) {
            aprxSol = NewtRhapStep(aprxSol);
            if (LOG_ITERATION) {
                System.out.println("Iteration #" + (i++) + ": " + aprxSol.toString());
                System.out.println("Velocity: " + aprxSol);
                Vector3dInterface aprxPos = fX.stateFX(new Vector3D(), aprxSol, 2678400.0);
                System.out.println("Position: " + aprxPos.toString());
                double error = getRelativeError(aprxPos, new Vector3D(-1.160685142387819E+00, 1.189131565321710E+00, 5.323467155528554E-02));
                System.out.println("Relative error: " + error);
            }
            i++;
        }
        i = 0;
        return aprxSol;
    }

    /**
     * Newton-raphson step to approximate the velocity
     *
     * @param vector interim velocity value
     * @return approximated velocity
     */
    public Vector3dInterface NewtRhapStep(Vector3dInterface vector) {
        double[][] D = PartialDerivative.getJacobianMatrix(fX, (Vector3D) vector.clone(), STEP_SIZE); // Compute matrix of partial derivatives D
        Matrix DI = new Matrix(Matrix.invert(D)); // Compute DI inverse of D
        Vector3dInterface modelVector = fX.modelFx(vector);
        Vector3dInterface DIProd = DI.multiplyVector(modelVector);
        return vector.sub(DIProd); // V1 = velocity - DI * function(velocity)
    }

}

