package group17.Solvers;

import group17.Interfaces.StateInterface;
import group17.Math.GravityFunction;
import group17.Math.Vector3D;
import group17.Simulation.System.State.SystemState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.StrictMath.abs;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * SOURCE CODE FOR THE IMPLEMENTATION OF THIS TEST :
 * <a href= src/test/resources/rkTest.m>matlab code for test</a>
 */
class RungeKuttaSolverGreedyTest {
    static RungeKuttaSolverGreedy rk;
    double accuracy;
    double step;
    StateInterface Earth;

    @BeforeEach
    void setup() {
        step = 2160;
        accuracy = 0.2;
        rk = new RungeKuttaSolverGreedy(new GravityFunction().evaluateCurrentAccelerationFunction());
        Earth = new SystemState();
        Earth.setPositions(List.of(new Vector3D(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06)));
        Earth.getRateOfChange().setVelocities(List.of(new Vector3D(5427.193371063864, -29310.56603506259, 0.6575428116074852)));
    }

    //0.000000054271934  -0.000000293105660   0.000000000006575
    @Test
    @DisplayName("Solver")
    void Solve() {
        StateInterface step1 = rk.step(rk.getFunction(), step, Earth, step);

        assertTrue(() -> abs(step1.getPositions().get(0).getX() + 1.471804874286367e+11) < accuracy);
        assertTrue(() -> abs(step1.getPositions().get(0).getY() + 0.286732689852920e+11) < accuracy);
        assertTrue(() -> abs(step1.getPositions().get(0).getZ() - 0.00008279603486e+11) < accuracy);

        assertTrue(() -> abs(step1.getRateOfChange().getVelocities().get(0).getX() - 0.000000054271934e+11) < accuracy);
        assertTrue(() -> abs(step1.getRateOfChange().getVelocities().get(0).getY() + 0.000000293105660e+11) < accuracy);
        assertTrue(() -> abs(step1.getRateOfChange().getVelocities().get(0).getZ() - 0.000000000006575e+11) < accuracy);


        StateInterface step2 = rk.step(rk.getFunction(), step, step1, step);
        assertTrue(() -> abs(step2.getPositions().get(0).getX() + 1.471687646908335e+11) < accuracy);
        assertTrue(() -> abs(step2.getPositions().get(0).getY() + 0.287365798079040e+11) < accuracy);
        assertTrue(() -> abs(step2.getPositions().get(0).getZ() - 0.000082810237785e+11) < accuracy);

        assertTrue(() -> abs(step2.getRateOfChange().getVelocities().get(0).getX() - 0.000000054271934e+11) < accuracy);
        assertTrue(() -> abs(step2.getRateOfChange().getVelocities().get(0).getY() + 0.000000293105660e+11) < accuracy);
        assertTrue(() -> abs(step2.getRateOfChange().getVelocities().get(0).getZ() - 0.000000000006575e+11) < accuracy);

    }
}