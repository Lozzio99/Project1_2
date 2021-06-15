package phase3.Module.Controllers;

import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Forces.ModuleFunction;
import phase3.Math.Solvers.ODESolverInterface;
import phase3.Math.Solvers.VerletVelocitySolver;
import phase3.System.State.StateInterface;

import static java.lang.Math.*;
import static phase3.Config.STEP_SIZE;
import static phase3.Math.Forces.ModuleFunction.G;
import static phase3.Math.Forces.ModuleFunction.V_MAX;

public class OpenLoopController implements ControllerInterface {

    private StateInterface<Vector3dInterface> initState;
    private boolean init = true;
    private static boolean initP1 = true;
    private static boolean initP4 = true;
    private int updateInit = 0;

    private double tF1;
    private double tF2;
    private double tF3;
    private double tF4;
    private double tR1;
    private double tR2;

    private double tF0;

    private double x_0;
    private double y_0;
    private double theta_0;

    private double x_0_dot;
    private double y_0_dot;
    private double theta_0_dot;

    private double temp_u;

    private double u = 0;
    private double v = 0;

    public OpenLoopController() {

    }

    @Override
    public double[] controlFunction(double t, StateInterface<Vector3dInterface> y) {
        if (init) initBounds(y);
        updateControls(t);
        return new double[]{getU(), getV()};
    }

    private void initBounds(StateInterface<Vector3dInterface> y) {
        initState = y;
        x_0 = y.get().getX();
        y_0 = y.get().getY();
        theta_0 = y.get().getZ();

        x_0_dot = y.getRateOfChange().get().getX();
        y_0_dot = y.getRateOfChange().get().getY();
        theta_0_dot = y.getRateOfChange().get().getZ();

        init = false;

        tR1 = sqrt((PI/4.0)/V_MAX)*2-STEP_SIZE;
        tF1 = Double.MAX_VALUE;

        tF2 = Double.MAX_VALUE;
        tF3 = Double.MAX_VALUE;
        tR2 = Double.MAX_VALUE;
        tF4 = Double.MAX_VALUE;

    }

    private void updateControls(double t) {

        if (t < tR1) {
            if (t < tR1/2) R1Phase1();
            else if (t > tR1/2) R1Phase2(t);
        }
        else if (t > tR1 && t < tF1-STEP_SIZE) F1Phase(t);
        else if (t > tF1-STEP_SIZE && t < tF3) {
            if (t < tF3-tR2/2) R2Phase1();
            else if (t > tF3-tR2/2) R2Phase2();
        }
        else if (t > tF3 && t < tF4-STEP_SIZE) {
            F4Phase(t);
        }
        else DefaultPhase();

    }

    private void F4Phase(double t) {
        u = temp_u;
        v = 0;
        if (initP4) initPhase4(t);
    }

    private void initPhase4(double t) {
        initP4 = false;
        StateInterface<Vector3dInterface> update = simulateModule(t+STEP_SIZE);
        double y_1 = update.get().getY();
        double y_1_dot = update.getRateOfChange().get().getY();
        tF4 = abs((2*y_1)/y_1_dot)+t;
        temp_u =abs(abs(pow(y_1_dot,2)/(2*y_1))+G);
    }

    private void R2Phase1() {
        u = 0;
        v = V_MAX;
        temp_u = 0;
        initP1 = true;
    }

    private void R2Phase2() {
        u = 0;
        v = -V_MAX;
    }

    private void DefaultPhase() {
        u = 0.0;
        v = 0.0;
    }

    private void F1Phase(double t) {
        u = temp_u;
        v = 0;
        if (initP1) initPhase1(t);
        //precompute for next phase
        tR2 = sqrt((PI/4.0)/V_MAX)*2;
        tF3 = t + tR2;
    }

    private void initPhase1(double t) {
        initP1 = false;
        StateInterface<Vector3dInterface> update = simulateModule(t+STEP_SIZE);
        double x_1 = update.get().getX();
        double x_1_dot = update.getRateOfChange().get().getX();
        tF1 = abs((2*x_1)/x_1_dot)+t;
        temp_u = pow(x_1_dot,2)/(2*x_1*sin(update.get().getZ()));
    }

    private void R1Phase1() {
        u = abs(G/cos(theta_0));
        v = -V_MAX;
    }

    private void R1Phase2(double t) {
        u = abs(G/cos(theta_0));
        v = V_MAX;
        //precompute for next phase
    }

    private double getU() {
        return u;
    }

    private double getV() {
        return v;
    }

    private StateInterface<Vector3dInterface> simulateModule(double t) {
        StateInterface<Vector3dInterface> currentState = initState.copy();
        ControllerInterface C = new OpenLoopController();
        ModuleFunction function = new ModuleFunction();
        ODESolverInterface<Vector3dInterface> solver = new VerletVelocitySolver<>(function.evaluateCurrentAccelerationFunction());
        double t0 = 0;
        while (t0 < t) {
            function.setControls(C.controlFunction(t0, currentState));
            currentState = solver.step(function.evaluateCurrentAccelerationFunction(), t0, currentState, STEP_SIZE);
            t0 += STEP_SIZE;
        }
        return currentState;
    }
}
