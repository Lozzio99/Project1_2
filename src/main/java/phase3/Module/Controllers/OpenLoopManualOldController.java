package phase3.Module.Controllers;

import phase3.Math.ADT.Vector3dInterface;
import phase3.System.State.StateInterface;

import static java.lang.Math.*;
import static phase3.Config.STEP_SIZE;
import static phase3.Math.Forces.ModuleFunction.G;
import static phase3.Math.Forces.ModuleFunction.V_MAX;

public class OpenLoopManualOldController implements ControllerInterface {

    private boolean ROTATION = true;
    private boolean init = true;
    private static boolean initP1 = true;
    private static boolean initP4 = true;

    private double tF1;
    private double tF2;
    private double tF3;
    private double tF4;
    private double tR1;
    private double tR2;

    private double x_0;
    private double y_0;
    private double theta_0;

    private double x_0_dot;
    private double y_0_dot;
    private double theta_0_dot;

    private double temp_u;

    private double u = 0;
    private double v = 0;

    private double rotation = 1.0;

    public OpenLoopManualOldController() {

    }

    @Override
    public double[] controlFunction(double t, StateInterface<Vector3dInterface> y) {
        if (init) initBounds(y);
        updateControls(t);
        return new double[]{getU(), getV()};
    }

    private void initBounds(StateInterface<Vector3dInterface> y) {
        setStateValues(y);

        init = false;

        tR1 = sqrt((PI/4.0)/V_MAX)*2;
        tF1 = Double.MAX_VALUE;

        tF2 = Double.MAX_VALUE;
        tF3 = Double.MAX_VALUE;
        tR2 = Double.MAX_VALUE;
        tF4 = Double.MAX_VALUE;

    }

    private void updateControls(double t) {

        x_0 = x_0 + x_0_dot*STEP_SIZE + 0.5*u*sin(theta_0)*STEP_SIZE*STEP_SIZE;
        x_0_dot = x_0_dot + u*sin(theta_0)*STEP_SIZE;

        y_0 = y_0 + y_0_dot*STEP_SIZE + 0.5*(u*cos(theta_0)-G)*STEP_SIZE*STEP_SIZE;
        y_0_dot = y_0_dot + (u*cos(theta_0)-G)*STEP_SIZE;

        theta_0 = theta_0 + theta_0_dot*STEP_SIZE + 0.5*v*STEP_SIZE*STEP_SIZE;
        theta_0_dot = theta_0_dot + v*STEP_SIZE;

        if (t < tR1) {
            if (t < tR1/2) R1Phase1();
            else if (t > tR1/2) R1Phase2(t);
        }
        else if (t > tR1 && t < tF1-STEP_SIZE) F1Phase(t);
        else if (t > tF1-STEP_SIZE && t < tF3) {
            if (t < tF3-tR2/2) R2Phase1();
            else if (t > tF3-tR2/2) R2Phase2();
        }
        else if (t > tF3 && t < tF4) {
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
        tF4 = abs((2*y_0)/y_0_dot)+t;
        temp_u =abs(abs(pow(y_0_dot,2)/(2*y_0))+G);
    }

    private void R2Phase1() {
        u = 0;
        v = V_MAX*rotation;
        temp_u = 0;
        initP1 = true;
    }

    private void R2Phase2() {
        u = 0;
        v = -V_MAX*rotation;
    }

    private void DefaultPhase() {
        u = abs(G/cos(theta_0));
        v = 0.0;
    }

    private void F1Phase(double t) {
        u = temp_u;
        v = 0;
        if (initP1) initPhase1(t);
        tR2 = sqrt((PI/4.0)/V_MAX)*2+STEP_SIZE;
        tF3 = t + tR2;
    }

    private void initPhase1(double t) {
        initP1 = false;
        tF1 = abs((2*x_0)/x_0_dot)+t;
        temp_u = pow(x_0_dot,2)/(2*x_0*sin(theta_0));
    }

    private void R1Phase1() {
        if (x_0 < 0) rotation = 1.0;
        else rotation = -1.0;
        u = abs(G/cos(theta_0));
        v = -V_MAX*rotation;
    }

    private void R1Phase2(double t) {
        u = abs(G/cos(theta_0));
        v = V_MAX*rotation;
    }

    private double getU() {
        return u;
    }

    private double getV() {
        return v;
    }

    private void setStateValues(StateInterface<Vector3dInterface> state) {
        x_0 = state.get().getX();
        y_0 = state.get().getY();
        theta_0 = state.get().getZ();

        x_0_dot = state.getRateOfChange().get().getX();
        y_0_dot = state.getRateOfChange().get().getY();
        theta_0_dot = state.getRateOfChange().get().getZ();
    }
}
