package phase3.Module.Controllers;

import phase3.Math.ADT.Vector3D;
import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Forces.ModuleFunction;
import phase3.Math.Functions.Function;
import phase3.Math.Solvers.*;
import phase3.System.State.StateInterface;

import static java.lang.Math.*;
import static phase3.Config.CURRENT_TIME;
import static phase3.Config.STEP_SIZE;
import static phase3.Math.Forces.ModuleFunction.G;
import static phase3.Math.Forces.ModuleFunction.V_MAX;

public class OpenLoopController implements ControllerInterface {

    private final double[] controls = new double[]{0.0, 0.0};
    private StateInterface<Vector3dInterface> initState;
    private static Integration integration = new TrapezoidIntegration();
    private boolean init = true;
    private boolean initP1 = true;
    private int updateInit = 0;

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


        double x_1 = abs(x_0/2);
        double x_1_dot = x_0_dot;
        tF2 = (2*x_1)/x_1_dot + tF1;
        tF3 = 0.0;

        tR1 = sqrt((PI/4.0)/V_MAX)*2;
        tF1 = Double.MAX_VALUE;
    }

    private void updateControls(double t) {

        if (t < tR1) {
            if (t < tR1/2) R1Phase1();
            else if (t > tR1/2) R1Phase2(t);
        }
        else if (t < tF1) F1Phase(t);
        else if (t > tF1 && t < tR2) {
            if (t < tR2-tR1/2) R2Phase1();
            else R2Phase2();
        }
        else DefaultPhase();

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
    }

    private double[] getControls(double t) {

        if (t < tR1) {
            if (t < tR1/2) R1Phase1();
            else if (t > tR1/2) R1Phase2(t);
        }
        else if (t > tR1 && t < tF1) F1Phase(t);
        else if (t > tF1 && t < tR2) {
            if (t < tR2-tR1/2) R2Phase1();
            else R2Phase2();
        }
        else DefaultPhase();

       return new double[]{u,v};
    }

    private void R2Phase2() {
        v = -V_MAX;
    }

    private void R2Phase1() {
        v = V_MAX;
    }

    private void F2Phase() {
    }

    private void DefaultPhase() {
        u = 0.0;
        v = 0.0;
    }

    private void F1Phase(double t) {
        if (initP1) initPhase1(t);
        u = temp_u;
        v = 0;
        //precompute for next phase
        tR2 = tR1+t;
    }

    private void initPhase1(double t) {
        StateInterface<Vector3dInterface> update = simulateModule(t);
        double x_1 = update.get().getX();
        double x_1_dot = update.getRateOfChange().get().getX();
        System.out.println(x_1 + " ### " + x_1_dot);
        initP1 = false;
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

    private Function<Double> getXPos = new Function<Double>() {
        @Override
        public Double apply(Double t) {
            return integration.apply(getXVel, STEP_SIZE, 0.0, t);
        }
    };

    private Function<Double> getXVel = new Function<>() {
        @Override
        public Double apply(Double t) {
            double theta = integration.apply(getTheta, STEP_SIZE, 0.0, t);
            return getControls(t)[0] * sin(theta);
        }
    };

    private Function<Double> getTheta = new Function<Double>() {
        @Override
        public Double apply(Double t) {
            return  integration.apply(getThetaVel, STEP_SIZE, 0, t);
        }
    };

    private Function<Double> getThetaVel = t -> {
        return getControls(t)[1];
    };

    private StateInterface<Vector3dInterface> simulateModule(double t) {
        StateInterface<Vector3dInterface> currentState = initState.copy();
        ControllerInterface C = new OpenLoopController();
        ModuleFunction function = new ModuleFunction();
        ODESolverInterface<Vector3dInterface> solver = new VerletVelocitySolver<>(function.evaluateCurrentAccelerationFunction());
        double t0 = 0;
        t -= STEP_SIZE;
        while (t0 < t) {
            function.setControls(C.controlFunction(t, currentState));
            currentState = solver.step(function.evaluateCurrentAccelerationFunction(), t0, currentState, STEP_SIZE);
            t0 += STEP_SIZE;
        }
        return currentState;
    }
}
