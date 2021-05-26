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
import java.io.PrintWriter;
import java.util.Arrays;

import static group17.Math.Solvers.RocketSimModel.pF;
import static group17.Math.Solvers.RocketSimModel.targetPosition;
import static group17.System.Clock.DAY;
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

    public static Vector3D ROCKET_STARTING_POS = new Vector3D(-1.4718605986293106E11,-2.8611609660793198E10,8088541.556074465);

    public static Vector3D INTERMEDIATE_TARGET_1A = new Vector3D(1.1282818052942159E11,-8.238101941804728E11,-1.3200423212630061E11);
    /**
     * Position of Titan in 7776000 s
     */
    public static Vector3D TITAN_TARGET = new Vector3D(6.965120125571067E11,-1.326029551335418E12,-4.213729666566596E9);
    /**
     * Position of Saturn on 31/05/2024 (131 414 400 s)
     */
    public static Vector3D SATURN_TARGET = new Vector3D(1.438943971812315E12,-5.1199779496024976E11,-4.69637284525731E10);

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
    public final boolean LOG_ITERATION = true;
    private int i = 0;

    /**
     * The Stopping criterion.
     */

    final double STOPPING_CRITERION = 100;
    private final NewtRaphFunction fX;
    private Function<Vector3dInterface> f;
    private static PrintWriter printWriter;

    static {
        try {
            printWriter = new PrintWriter("report.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static PrintWriter printWriter2;

    static {
        try {
            printWriter2 = new PrintWriter("report_errors.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


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
    public static void main(String[] args) {
        /*
        Vector3D v1 = new Vector3D(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06);
        Vector3D v2 = new Vector3D(1.3795800752854993E12,-4.3858348265323E11,-4.706900828533791E10);
        double disV1V2 = v1.dist(v2);
        Vector3D unitV = new Vector3D(
                (v2.getX()-v1.getX())/disV1V2,
                (v2.getY()-v1.getY())/disV1V2,
                (v2.getZ()-v1.getZ())/disV1V2
        );
        Vector3D startV1 = new Vector3D(
                v1.getX() + (6.371e6+0.1)*unitV.getX(),
                v1.getY() + (6.371e6+0.1)*unitV.getY(),
                v1.getZ() + (6.371e6+0.1)*unitV.getZ()
        );
        System.out.println(startV1);

         */


        Vector3D initPos = ROCKET_STARTING_POS;
        Vector3D targetPos = TITAN_TARGET;
        double FixedTime = 131414400;
        //double FixedTime = 7776000;
        //double FixedTime = 2592000;
        NewtonRaphsonSolver nrSolver = new NewtonRaphsonSolver(initPos, new Vector3D(1.3795800752854993E12,-4.3858348265323E11,-4.706900828533791E10), FixedTime);
        Vector3dInterface aprxVel = nrSolver.NewtRhapSolution(new Vector3D(27796.24753416469,-108539.07976675793,-17776.928127736475), new Vector3D(0.0, 0.0, 0.0), 360);
    }

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
            if (LOG_ITERATION) {
                System.out.println("Result - Iteration #" + (i) + ": " + aprxSol.toString());
                printWriter.println(aprxSol);
            }
            i++;
        }
        printWriter.close();
        printWriter2.close();
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
        Matrix DI = new Matrix(Matrix.invert(D)); // Compute DI inverse of D
        Vector3dInterface modelVector = fX.modelFx(vector);
        if (LOG_ITERATION) {
            System.out.println("Error - Iteration #" + i + ": " + modelVector);
            printWriter2.println(modelVector);
        }
        Vector3dInterface DIProd = DI.multiplyVector(modelVector);
        return vector.sub(DIProd); // V1 = velocity - DI * function(velocity)
    }

}
