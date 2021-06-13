import phase3.Math.Functions.Function;
import phase3.Math.Functions.ODEFunctionInterface;
import phase3.Math.Solvers.EulerSolver;
import phase3.Math.Solvers.MidPointSolver;
import phase3.Math.Solvers.ODESolverInterface;
import phase3.Math.Solvers.RungeKuttaSolver;
import phase3.System.State.RateOfChange;
import phase3.System.State.StateInterface;
import phase3.System.State.SystemState;

import static java.lang.StrictMath.abs;

public class ODE2Test {
    static ODESolverInterface<Double> solver;

    static StateInterface<Double> y;
    static double t, tf, stepSize;


    static Function<Double> position = t -> {
        return (t * t * t) - (4 * t) + 3;   // vectorState (t)
    };
    static Function<Double> velocity = t -> {
        return 3 * t * t - 4;   // dy/dt
    };
    static ODEFunctionInterface<Double> acceleration = (t, y) -> {
        return new RateOfChange<>(6. * t);  // d2y / dt2
    };

    public static void main(String[] args) {
        test(new EulerSolver<>(acceleration));
        test(new MidPointSolver<>(acceleration));
        test(new RungeKuttaSolver<>(acceleration));
        //test(new StandardVerletSolver<>(acceleration));
        //test(new VerletVelocitySolver<>(acceleration));

    }

    public static void test(ODESolverInterface<Double> solver) {
        System.out.println(solver);
        t = 0;
        tf = 2;
        y = new SystemState<>(position.apply(t), velocity.apply(t));
        stepSize = 0.00001;
        while (t < tf) {
            y = solver.step(acceleration, t, y, stepSize);
            t += stepSize;
        }
        test(y, tf);
        System.out.println("-------------------------------");
    }

    public static void test(StateInterface<Double> y, double time) {
        System.out.println("ABSOLUTE\npos : " + absolute(y.get(), position.apply(time)));
        System.out.println("vel : " + absolute(y.getRateOfChange().get(), velocity.apply(time)));
        System.out.println();
        System.out.println("RELATIVE\npos : " + relative(y.get(), position.apply(time)));
        System.out.println("vel : " + relative(y.getRateOfChange().get(), velocity.apply(time)));
    }

    public static double absolute(double actual, double expected) {
        return abs(actual - expected);
    }

    public static double relative(double actual, double expected) {
        return (absolute(actual, expected)) / expected;
    }

}
