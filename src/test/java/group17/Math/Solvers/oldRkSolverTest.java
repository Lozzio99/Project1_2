package group17.Math.Solvers;

import group17.Interfaces.StateInterface;
import group17.Math.Utils.Vector3D;
import group17.System.SystemState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static java.lang.StrictMath.abs;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class oldRkSolverTest {
    double accuracy;
    double step;
    static RungeKutta4thSolver rk;
            StateInterface Earth;
    @BeforeEach
    void setup() {
        step = 2160;
        accuracy = 0.2;
        rk = new RungeKutta4thSolver();
        Earth = new SystemState();
        Earth.setPositions(List.of(new Vector3D(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06)));
        Earth.getRateOfChange().setVel(List.of(new Vector3D(5427.193371063864, -29310.56603506259, 0.6575428116074852)));
            }
//0.000000054271934  -0.000000293105660   0.000000000006575
    @Test
    @DisplayName("Solver")
    void Solve() {
        StateInterface step1 = rk.old(rk.getOldF(),step,Earth,step);

        assertTrue(() -> abs(step1.getPositions().get(0).getX()+1.471804874286367e+11) < accuracy);
        assertTrue(() -> abs(step1.getPositions().get(0).getY()+0.286732689852920e+11) < accuracy);
        assertTrue(() -> abs(step1.getPositions().get(0).getZ()-0.00008279603486e+11) < accuracy);

        assertTrue(() -> abs(step1.getRateOfChange().getVelocities().get(0).getX()-0.000000054271934e+11) < accuracy);
        assertTrue(() -> abs(step1.getRateOfChange().getVelocities().get(0).getY()+0.000000293105660e+11) < accuracy);
        assertTrue(() -> abs(step1.getRateOfChange().getVelocities().get(0).getZ()-0.000000000006575e+11) < accuracy);


        StateInterface step2 = rk.old(rk.getOldF(),step,step1,step);
        assertTrue(() -> abs(step2.getPositions().get(0).getX()+1.471687646908335e+11) < accuracy);
        assertTrue(() -> abs(step2.getPositions().get(0).getY()+0.287365798079040e+11) < accuracy);
        assertTrue(() -> abs(step2.getPositions().get(0).getZ()-0.000082810237785e+11) < accuracy);

        assertTrue(() -> abs(step2.getRateOfChange().getVelocities().get(0).getX()- 0.000000054271934e+11) < accuracy);
        assertTrue(() -> abs(step2.getRateOfChange().getVelocities().get(0).getY()+0.000000293105660e+11) < accuracy);
        assertTrue(() -> abs(step2.getRateOfChange().getVelocities().get(0).getZ()-0.000000000006575e+11) < accuracy);

    }


}
