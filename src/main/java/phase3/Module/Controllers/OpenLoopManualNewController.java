package phase3.Module.Controllers;

import phase3.Math.ADT.Vector3dInterface;
import phase3.System.State.StateInterface;

import static java.lang.Math.*;
import static phase3.Config.DEBUG;
import static phase3.Config.MODULE_STEP_SIZE;
import static phase3.Math.Forces.ModuleFunction.G;
import static phase3.Math.Forces.ModuleFunction.V_MAX;

public class OpenLoopManualNewController implements ControllerInterface {

    private boolean ROTATION;
    private boolean initRotationFlag;
    private boolean initHorizontalMoveFlag;
    private boolean init = true;

    private double x_0;
    private double y_0;
    private double theta_0;

    private double x_0_dot;
    private double y_0_dot;
    private double theta_0_dot;

    private double u = 0;
    private double v = 0;

    private double rotation = 1.0;
    private double hMvTime;
    private double rtTime;
    private double tempT;

    public OpenLoopManualNewController() {

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
        ROTATION = true;
        initRotationFlag = true;
        initHorizontalMoveFlag = true;

        if (x_0 < 0.0) rotation = -1.0;

        hMvTime = Double.MAX_VALUE;
        rtTime = Double.MAX_VALUE;
    }

    /**
     * Main method that updates control values
     *
     * @param t current time
     */
    private void updateControls(double t) {
        // update local module simulation
        updateState();
        // adjust horizontal position/velocity
        if (abs(x_0) > 0.1) if (startHorizontalMove(t)) initHorizontalMoveFlag = true;
            // adjust vertical position/velocity
        else if (abs(y_0) < 0.1 || startVerticalMove()) DefaultPhase();
        if (DEBUG) System.out.println(u + " ### " + v);
    }

    private boolean startHorizontalMove(double t) {
        if (ROTATION) startRotation(PI / 4.0, t);
        else {
            if (initHorizontalMoveFlag) initHorizontalMove(t);
            else if (t > hMvTime) {
                if (DEBUG) System.out.println("Horizontal move phase");
                return startRotation(PI / 4.0, t);
            }
        }
        return false;
    }

    private void initHorizontalMove(double t) {
        setControls(pow(x_0_dot, 2) / (2 * x_0 * sin(theta_0)), 0.0);
        hMvTime = t + abs((2 * x_0) / x_0_dot);
    }

    private boolean startRotation(double degrees, double t) {
        if (DEBUG) System.out.println("Rotation phase");
        if (initRotationFlag) initRotation(degrees, t);
        if (t < tempT + rtTime) {
            setControls(abs(G / cos(theta_0)), V_MAX * rotation);
        } else if (t > tempT + rtTime && t < tempT + rtTime * 2.0) {
            setControls(abs(G / cos(theta_0)), -V_MAX * rotation);
        } else {
            ROTATION = false;
            initRotationFlag = true;
            rotation *= -1.0;
            setControls(0.0, 0.0);
            return true;
        }
        return false;
    }

    private void initRotation(double degrees, double t) {
        rtTime = sqrt((abs(degrees % (2 * PI))) / V_MAX);
        tempT = t;
        initRotationFlag = false;
    }

    private boolean startVerticalMove() {
        u = abs(abs(pow(y_0_dot, 2) / (2 * y_0)) + G);
        return false;
    }

    private void DefaultPhase() {
        setControls(abs(G / cos(theta_0)), 0.0);
    }

    private double getU() {
        return u;
    }

    private double getV() {
        return v;
    }

    private void setControls(double u, double v) {
        this.u = u;
        this.v = v;
    }

    private void setStateValues(StateInterface<Vector3dInterface> state) {
        x_0 = state.get()[0].getX();
        y_0 = state.get()[0].getY();
        theta_0 = state.get()[0].getZ();

        x_0_dot = state.get()[1].getX();
        y_0_dot = state.get()[1].getY();
        theta_0_dot = state.get()[1].getZ();
    }

    private void updateState() {
        x_0 += +x_0_dot * MODULE_STEP_SIZE + 0.5 * u * sin(theta_0) * MODULE_STEP_SIZE * MODULE_STEP_SIZE;
        x_0_dot += u * sin(theta_0) * MODULE_STEP_SIZE;

        y_0 += y_0_dot * MODULE_STEP_SIZE + 0.5 * (u * cos(theta_0) - G) * MODULE_STEP_SIZE * MODULE_STEP_SIZE;
        y_0_dot += (u * cos(theta_0) - G) * MODULE_STEP_SIZE;

        theta_0 += theta_0_dot * MODULE_STEP_SIZE + 0.5 * v * MODULE_STEP_SIZE * MODULE_STEP_SIZE;
        theta_0_dot += v * MODULE_STEP_SIZE;
    }
}
