package Module.Math.Solvers;

import Module.Math.Functions.ODEFunctionInterface;
import Module.System.State.StateInterface;

public class SolverDouble {
    public static void main(String[] args) {
        ODESolverInterface<Double> solver = new ODESolverInterface<Double>() {
            @Override
            public StateInterface<Double> step(ODEFunctionInterface<Double> f, double t, StateInterface<Double> y, double h) {
                return null;
            }

            @Override
            public ODEFunctionInterface<Double> getFunction() {
                return null;
            }
        };
    }
}
