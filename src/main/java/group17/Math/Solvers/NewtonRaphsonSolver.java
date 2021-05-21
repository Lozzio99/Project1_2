package group17.Math.Solvers;

import group17.Interfaces.Function;
import group17.Interfaces.NewtRaphFunction;
import group17.Interfaces.Vector3dInterface;
import group17.Math.Lib.Matrix;
import group17.Math.Lib.PartialDerivative;
import group17.Math.Lib.Vector3D;
import org.jetbrains.annotations.Contract;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

import static group17.Math.Solvers.RocketSimModel.pF;
import static group17.Math.Solvers.RocketSimModel.targetPosition;
import static group17.System.Clock.DAY;
import static group17.Utils.Config.*;

public class NewtonRaphsonSolver {

    static {
        INSERT_ROCKET = true;
        CHECK_COLLISIONS = false;
    }

    public static Vector3dInterface initPos;
    public static double endTime;

    public final boolean LOG_ITERATION = true;
    public final boolean GENERATE_REPORT = true;
    private int i = 0;

    final double STOPPING_CRITERION = 100;
    private final NewtRaphFunction fX;
    public Vector3dInterface initSol;
    public Vector3dInterface targetSol;
    private static PrintWriter report;

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
     * @param initPos initial position of a rocket
     * @param targetPos target position of a rocket
     * @param endTime fixed time at which rocket must reach its target
     */
    public NewtonRaphsonSolver(Vector3dInterface initPos, Vector3dInterface targetPos ,double endTime) {
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
        return (v1.sub(v2)).norm() / v1.norm();
    }

    public static void main(String[] args) throws IOException {
        Vector3D initPos = new Vector3D(-9.839198644659751E-01, -1.912460575714917E-01, 5.542821181619438E-05);
        Vector3D targetPos = new Vector3D(1.363555710400778E+00, 3.201330747536073E-01, -2.693888301471804E-02);
        double FixedTime = DAY * 30 * 6;
        NewtonRaphsonSolver nrSolver = new NewtonRaphsonSolver(initPos, targetPos, FixedTime);
        Vector3dInterface aprxVel = nrSolver.NewtRhapSolution(new Vector3D(5000.0, 5000.0, 5000.0), new Vector3D(0.0, 0.0, 0.0), STEP_SIZE);
    }

    /**
     * Approximation of a solution to a function using Newton-Rhapson method
     * @param initSol initial solution
     * @param targetSol target solution of the model function
     * @param h step-size
     * @return approximated solution i.e. fX(aprxSol) ~ targetSol
     */
    public Vector3dInterface NewtRhapSolution(Vector3dInterface initSol, Vector3dInterface targetSol, double h) throws IOException {
        this.initSol = initSol.clone();
        this.targetSol = targetSol.clone();
        Vector3dInterface aprxSol = initSol.clone();
        // iteratively apply newton-raphson
        if (GENERATE_REPORT) {
            report = new PrintWriter("Report.txt");
            report.println("Approximated solution"+"\t"+"Error magnitude");

        }
        while (i < STOPPING_CRITERION) {
            aprxSol = NewtRhapStep(aprxSol, h);
            if (LOG_ITERATION) {
                System.out.println("Iteration #" + (i) + ": " + aprxSol.toString());
                Vector3D aprxFx = (Vector3D) fX.modelFx(aprxSol);
                //Vector3D errorLog = (Vector3D) aprxFx.sub(targetSol);
                System.out.println("Error log: "+aprxFx.norm());
                if (GENERATE_REPORT) {
                    report.println(aprxSol.toString()+"\t"+String.valueOf(aprxFx.norm()));
                }
            }
            i++;
        }
        if (GENERATE_REPORT) {
            report.close();
        }
        i = 0;
        return aprxSol;
    }

    /**
     * Newton-raphson step to approximate the velocity
     * @param vector interim velocity value
     * @param h step-size
     * @return approximated velocity
     */
    public Vector3dInterface NewtRhapStep(Vector3dInterface vector, double h) {
        double[][] D = PartialDerivative.getJacobianMatrix(fX, (Vector3D) vector.clone(), h); // Compute matrix of partial derivatives D
        Matrix DI = new Matrix(Matrix.invert(D)); // Compute DI inverse of D
        Vector3dInterface modelVector = fX.modelFx(vector);
        Vector3dInterface DIProd = DI.multiplyVector(modelVector);
        return vector.sub(DIProd); // V1 = velocity - DI * function(velocity)
    }

}
