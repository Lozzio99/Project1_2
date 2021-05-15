package group17.Math.Solvers;

import group17.Interfaces.NewtRaphFunction;
import group17.Interfaces.Vector3dInterface;
import group17.Math.Utils.Matrix;
import group17.Math.Utils.PartialDerivative;
import group17.Math.Utils.Vector3D;

import static group17.Config.STEP_SIZE;
import static group17.Math.Solvers.RocketSimModel.pF;
import static group17.Math.Solvers.RocketSimModel.targetPosition;

public class NewtonRaphsonSolver {

    public static Vector3D initPos;
    public static double endTime;
    final boolean LOG_ITERATION = true;
    final double STOPPING_CRITERION = 100;
    public Vector3D initSol;
    public Vector3D targetSol;
    public NewtRaphFunction fX;
    private int i = 0;

    /**
     * Constructor of a basic Newton-Raphson solver to approximate solution of a function
     *
     * @param fX 3-d vector function
     */
    public NewtonRaphsonSolver(NewtRaphFunction fX) {
        this.fX = fX;
    }

    /**
     * Constructor of Newton-Raphson solver for the rocket-simulation problem
     *
     * @param initPos initial position of a rocket
     * @param endTime fixed time at which rocket must reach its target
     */
    public NewtonRaphsonSolver(Vector3dInterface initPos, double endTime) {
        this.fX = pF;
        NewtonRaphsonSolver.initPos = (Vector3D) initPos;
        NewtonRaphsonSolver.endTime = endTime;
    }

    /**
     * Method for single-norm relative error of a vector
     *
     * @param v1 approximated vector
     * @param v2 target vector
     * @return relative error
     */
    public static double getRelativeError(Vector3D v1, Vector3D v2) {
        return (v1.sub(v2)).norm() / v2.norm();
    }

    public static void main(String[] args) {
        NewtonRaphsonSolver nrSolver = new NewtonRaphsonSolver(new Vector3D(), 2678400.0);
        Vector3D aprxVel = (Vector3D) nrSolver.NewtRhapSolution(new Vector3D(-7485730.186, 6281273.438, -8172135.798), new Vector3D(-1.160685142387819E+00, 1.189131565321710E+00, 5.323467155528554E-02));
    }

    /**
     * Approximation of a solution to a function using Newton-Rhapson method
     *
     * @param initSol   initial solution
     * @param targetSol target function solution
     * @return approximated solution i.e. fX(aprxSol) ~ targetSol
     */
    public Vector3dInterface NewtRhapSolution(Vector3dInterface initSol, Vector3dInterface targetSol) {
        this.initSol = (Vector3D) initSol.clone();
        this.targetSol = (Vector3D) targetSol.clone();
        targetPosition = targetSol.clone();
        Vector3D aprxSol = (Vector3D) initSol.clone();
        // iteratively apply newton-raphson
        while (i < STOPPING_CRITERION) {
            aprxSol = NewtRhapStep(aprxSol);
            if (LOG_ITERATION) {
                System.out.println("Iteration #" + (i++) + ": " + aprxSol.toString());
                System.out.println("Velocity: " + aprxSol);
                Vector3D aprxPos = (Vector3D) fX.stateFX(new Vector3D(), aprxSol, 2678400.0);
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
    public Vector3D NewtRhapStep(Vector3D vector) {
        double[][] D = PartialDerivative.getJacobianMatrix(fX, (Vector3D) vector.clone(), STEP_SIZE); // Compute matrix of partial derivatives D
        Matrix DI = new Matrix(Matrix.invert(D)); // Compute DI inverse of D
        Vector3D modelVector = (Vector3D) fX.modelFx(vector);
        Vector3D DIProd = DI.multiplyVector(modelVector);
        return (Vector3D) vector.sub(DIProd); // V1 = velocity - DI * function(velocity)
    }

}

