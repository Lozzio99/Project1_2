package phase3.Module.Controllers;

import phase3.Math.ADT.Vector3D;
import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Functions.ODEFunctionInterface;
import phase3.System.State.RateOfChange;
import phase3.System.State.StateInterface;
import static java.lang.Math.*;
import static phase3.Math.Forces.ModuleFunction.G;

public class OpenLoopController implements ControllerInterface {

    private final Vector3dInterface V;
    private StateInterface<Vector3dInterface> currentState;
    private StateInterface<Vector3dInterface> initState;
    private boolean init = true;

    private double tF1;
    private double tF2;
    private double tF3;
    private double tF4;

    private double x_0;
    private double y_0;
    private double theta_0;

    private double x_0_dot;
    private double y_0_dot;
    private double theta_0_dot;

    public OpenLoopController() {
        V = new Vector3D(0, 0, 0);
    }

    @Override
    public double[] controlFunction(double t, StateInterface<Vector3dInterface> y) {
        if (init) initBounds(y);
        return new double[]{getU(t), getV(t)};
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
    }

    private double getU(double t) {
        if (t < tF1) {
            // Part 1
            return G/cos(getTheta(t));
        }
        else if (t > tF1 && t < tF2) {
            // Part 2
            if (t < tF2/2) {
                return G/cos(getTheta(t));
            }
            else {
                return (x_0_dot*x_0_dot)/(2*x_0*sin(getTheta(t)));
            }
        }
        else if (t > tF2 && t < tF4) {
            // Part 3
            if (t < tF3) {
                // Wait for the rotation
                return 0;
            }
            else {
                return pow(y_0_dot,2)/(2*y_0)-G;
            }
        }
        else {
            return 0;
        }
    }

    private double getV(double t) {
        return 0.0;
    }

    private double getX(double t) {
        return x_0+x_0_dot*t+getU(t)*sin(getTheta(t))*t*t/2;
    }

    private double getY(double t) {
        return y_0+y_0_dot*t+(getU(t)*cos(getTheta(t))-G)*t*t/2;
    }
    private double getTheta(double t) {
        return theta_0+theta_0_dot*t+getV(t)*t*t/2;
    }

    private double getXDot(double t) {
        return x_0_dot+sin(getTheta(t))*getU(t)*t;
    }

    private double getYDot(double t) {
        return y_0_dot+(getU(t)*cos(getTheta(t))-G)*t;
    }
    private double getThetaDot(double t) {
        return theta_0_dot+getV(t)*t;
    }
}
