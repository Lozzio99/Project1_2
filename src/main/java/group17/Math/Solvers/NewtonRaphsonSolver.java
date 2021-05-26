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

    public static Vector3D ROCKET_STARTING_POS = new Vector3D(-1.4717856245318698E11,-2.861154627637646E10,8032437.618829092);

    public static Vector3D INTERMEDIATE_TARGET_1A = new Vector3D(1.1282818052942159E11,-8.238101941804728E11,-1.3200423212630061E11);
    /**
     * Position of Titan in 7776000 s
     */
    public static Vector3D TITAN_TARGET = new Vector3D(1.3194651761804377E12,-6.203872902032109E11,-4.138820394098859E10);
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

    final double STOPPING_CRITERION = 1000;
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
        Vector3D v1 = new Vector3D(-1.471858828790318E11,-2.8610694436326927E10,8164251.808805363);
        Vector3D v2 = new Vector3D(7.30644042602411E11,-1.3075921735800641E11,-1.5798353431358152E10);
        double disV1V2 = v1.dist(v2);
        Vector3D unitV = new Vector3D(
                (v2.getX()-v1.getX())/disV1V2,
                (v2.getY()-v1.getY())/disV1V2,
                (v2.getZ()-v1.getZ())/disV1V2
        );
        Vector3D startV1 = new Vector3D(
                v1.getX() + (6.371e6+1e6)*unitV.getX(),
                v1.getY() + (6.371e6+1e6)*unitV.getY(),
                v1.getZ() + (6.371e6+1e6)*unitV.getZ()
        );
        System.out.println(startV1);
        //https://math.stackexchange.com/questions/1784106/how-do-i-compute-the-closest-points-on-a-sphere-given-a-point-outside-the-sphere

         */

        Vector3D initPos = ROCKET_STARTING_POS;
        Vector3D targetPos = TITAN_TARGET; // @
        double FixedTime = 111153600; // @
        //double FixedTime = 7776000;
        //double FixedTime = 2592000;

        //86400
        //43200
        //1440
        //360

        NewtonRaphsonSolver nrSolver = new NewtonRaphsonSolver(ROCKET_STARTING_POS, TITAN_TARGET, FixedTime);
        Vector3dInterface aprxVel = nrSolver.NewtRhapSolution(new Vector3D(0,0,0), new Vector3D(0.0, 0.0, 0.0), 1440);
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
