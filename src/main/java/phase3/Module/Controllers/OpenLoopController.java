package phase3.Module.Controllers;

import phase3.Math.ADT.Vector3D;
import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Forces.ModuleFunction;
import phase3.Math.Functions.Function;
import phase3.System.State.StateInterface;

import static java.lang.Math.*;
import static phase3.Math.Forces.ModuleFunction.G;
import static phase3.Math.Forces.ModuleFunction.V_MAX;

public class OpenLoopController implements ControllerInterface {

    private final double[] controls = new double[]{0.0, 0.0};
    private StateInterface<Vector3dInterface> currentState;
    private StateInterface<Vector3dInterface> initState;
    private boolean init = true;

    private double tF1;
    private double tF2;
    private double tF3;
    private double tR1;
    private double tR2;
    private double tR0;

    private double x_0;
    private double y_0;
    private double theta_0;

    private double x_0_dot;
    private double y_0_dot;
    private double theta_0_dot;

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

        tF1 = abs(x_0/2)/x_0_dot;
        double x_1 = abs(x_0/2);
        double x_1_dot = x_0_dot;
        tF2 = (2*x_1)/x_1_dot + tF1;
        tF3 = 0.0;

        tR1 = sqrt((45)/V_MAX)*2;
        System.out.println(tR1);
        tR2 = tR1;

        tR0 = sqrt(45.0/V_MAX);
    }

    private void updateControls(double t) {

        /*
        if (t < tF1) {
            // Part 1
            v = 0;
            u = G/cos(theta_0);
        }
        else if (t > tF1 && t < tF2) {
            if (t < (tF1+tR1/2)) {
                v = -V_MAX;
                u = G/cos(theta_0);
            }
            else if (t > (tF1 + tR1/2) && t < tF2) {
                if (t < tF1 + tR1) {
                    v = V_MAX;
                }
                else {
                    v = 0;
                }
                u = (x_0_dot*x_0_dot)/(2*x_0*sin(theta_0));
                System.out.println(u);
            }
        }
        else if (t > tF2 && t < tF3) {
            // Part 3
            if (t < tF3) {
                // Wait for the rotation
                u = 0;
            }
            else {
                u = 0;
                //u = pow(y_0_dot,2)/(2*y_0)-G;
            }
        }
        else {
            u = 0;
        }

         */


        if (t < tR0/2) {
            v = V_MAX;
        }
        else if (t > tR0/2 && t < tR0) {
            v = -V_MAX;
        }
        else if (t > tR0){
            v = 0;
        }
        //theta_0 += theta_0+theta_0_dot*t+v*t*t/2;
        //u = G/cos(theta_0);
        //System.out.println(u);
        //System.out.println(v);
        //System.out.println(theta_0);

        // update
        x_0 = x_0+x_0_dot*t+u*sin(theta_0)*t*t/2;
        y_0 = y_0+y_0_dot*t+(u*cos(theta_0)-G)*t*t/2;
        x_0_dot = x_0_dot+sin(theta_0)*u*t;
        y_0_dot = y_0_dot+(u*cos(theta_0)-G)*t;
        theta_0_dot = theta_0_dot+v*t;
    }

    private double getU() {
        return u;
    }

    private double getV() {
        return v;
    }
}
