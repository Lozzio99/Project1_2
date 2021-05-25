package group17.Math.Solvers;

import group17.Interfaces.Function;
import group17.Interfaces.NewtRaphFunction;
import group17.Interfaces.Vector3dInterface;
import group17.Math.Lib.Matrix;
import group17.Math.Lib.PartialDerivative;
import group17.Math.Lib.Vector3D;
import org.jetbrains.annotations.Contract;

import java.util.Arrays;

import static group17.Math.Solvers.RocketSimModel.pF;
import static group17.Math.Solvers.RocketSimModel.targetPosition;
import static group17.Utils.Config.CHECK_COLLISIONS;
import static group17.Utils.Config.INSERT_ROCKET;

/**
 * The type Newton raphson solver.
 */
public class NewtonRaphsonSolver {

    static {
        INSERT_ROCKET = true;
        CHECK_COLLISIONS = false;
    }

    /**
     * The constant initPos.
     */
    public static Vector3dInterface initPos;
    /**
     * The constant endTime.
     */
    public static double endTime;

    /**
     * The Log iteration.
     */
    public final boolean LOG_ITERATION = false;
    private int i = 0;

    /**
     * The Stopping criterion.
     */

    final double STOPPING_CRITERION = 100;
    private final NewtRaphFunction fX;
    private Function<Vector3dInterface> f;
    /**
     * The Init sol.
     */
    public Vector3dInterface initSol;
    /**
     * The Target sol.
     */
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
     *
     * @param initPos   initial position of a rocket
     * @param targetPos target position of a rocket
     * @param endTime   fixed time at which rocket must reach its target
     */
    public NewtonRaphsonSolver(Vector3dInterface initPos, Vector3dInterface targetPos, double endTime) {
        this.fX = pF;
        NewtonRaphsonSolver.initPos = initPos;
        targetPosition = targetPos.clone();
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

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
   /* public static void main(String[] args) {
        Vector3D initPos = new Vector3D(-9.839198644659751E-01, -1.912460575714917E-01, 5.542821181619438E-05);
        Vector3D targetPos = new Vector3D(1.363555710400778E+00, 3.201330747536073E-01, -2.693888301471804E-02);
        double FixedTime = DAY * 30 * 6;
        NewtonRaphsonSolver nrSolver = new NewtonRaphsonSolver(initPos, targetPos, FixedTime);
        Vector3dInterface aprxVel = nrSolver.NewtRhapSolution(new Vector3D(5000.0, 5000.0, 5000.0), new Vector3D(0.0, 0.0, 0.0), 600);
    }*/

    /**
     * Approximation of a solution to a function using Newton-Rhapson method
     *
     * @param initSol   initial solution
     * @param targetSol target solution of the model function
     * @param h         step-size
     * @return approximated solution i.e. fX(aprxSol) ~ targetSol
     */
    public Vector3dInterface NewtRhapSolution(Vector3dInterface initSol, Vector3dInterface targetSol, double h) {
        this.initSol = initSol.clone();
        this.targetSol = targetSol.clone();
        Vector3dInterface aprxSol = initSol.clone();
        // iteratively apply newton-raphson
        while (i < STOPPING_CRITERION) {
            aprxSol = NewtRhapStep(aprxSol, h);
            if (LOG_ITERATION) System.out.println("Iteration #" + (i) + ": " + aprxSol.toString());
            i++;
        }
        i = 0;
        return aprxSol;
    }

    /**
     * Newton-raphson step to approximate the velocity
     *
     * @param vector interim velocity value
     * @param h      step-size
     * @return approximated velocity
     */
    public Vector3dInterface NewtRhapStep(Vector3dInterface vector, double h) {
        double[][] D = PartialDerivative.getJacobianMatrix(fX, (Vector3D) vector.clone(), h); // Compute matrix of partial derivatives D
        if (LOG_ITERATION) System.out.println(Arrays.deepToString(D));
        Matrix DI = new Matrix(Matrix.invert(D)); // Compute DI inverse of D
        Vector3dInterface modelVector = fX.modelFx(vector);
        Vector3dInterface DIProd = DI.multiplyVector(modelVector);
        return vector.sub(DIProd); // V1 = velocity - DI * function(velocity)
    }

}
