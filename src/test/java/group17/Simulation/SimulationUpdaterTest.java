package group17.Simulation;

import group17.Interfaces.UpdaterInterface;
import group17.Math.Solvers.*;
import group17.Simulation.Rocket.RocketSchedule;
import group17.Simulation.System.Clock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static group17.Main.simulation;
import static group17.Utils.Config.*;
import static org.junit.jupiter.api.Assertions.*;

class SimulationUpdaterTest {


    static {
        ERROR_EVALUATION = LAUNCH_ASSIST = ENABLE_GRAPHICS = false;
        simulation = new Simulation();
        simulation.initReporter();
    }

    @Test
    @DisplayName("Init")
    void Init() {
        DEFAULT_SOLVER = EULER_SOLVER;
        UpdaterInterface updater = new SimulationUpdater();
        updater.init();
        assertEquals(EulerSolver.class, updater.getSolver().getClass());
        DEFAULT_SOLVER = RUNGE_KUTTA_SOLVER;
        updater = new SimulationUpdater();
        updater.init();
        assertEquals(RungeKuttaSolver.class, updater.getSolver().getClass());
        DEFAULT_SOLVER = VERLET_VEL_SOLVER;
        updater = new SimulationUpdater();
        updater.init();
        assertEquals(VerletVelocitySolver.class, updater.getSolver().getClass());
        DEFAULT_SOLVER = VERLET_STD_SOLVER;
        updater = new SimulationUpdater();
        updater.init();
        assertEquals(StandardVerletSolver.class, updater.getSolver().getClass());
        DEFAULT_SOLVER = MIDPOINT_SOLVER;
        updater = new SimulationUpdater();
        updater.init();
        assertEquals(MidPointSolver.class, updater.getSolver().getClass());
        DEFAULT_SOLVER = OLD_RUNGE;
        updater = new SimulationUpdater();
        updater.init();
        assertEquals(OldRungeKutta.class, updater.getSolver().getClass());
        DEFAULT_SOLVER = LAZY_RUNGE;
        updater = new SimulationUpdater();
        updater.init();
        assertEquals(LazyRungeKutta.class, updater.getSolver().getClass());
        DEFAULT_SOLVER = -1;
        updater = new SimulationUpdater();
        REPORT = false;
        updater.init();
        REPORT = true;
        updater.init();
        assertEquals(EulerSolver.class, updater.getSolver().getClass());
    }

    @Test
    @DisplayName("GetSolver")
    void GetSolver() {
        UpdaterInterface updater = new SimulationUpdater();
        updater.init();
        assertNotNull(updater.getSolver());
    }

    @Test
    @DisplayName("GetSchedule")
    void GetSchedule() {
        UpdaterInterface updater = new SimulationUpdater();
        updater.init();
        assertNotNull(updater.getSchedule());
        assertEquals(RocketSchedule.class, updater.getSchedule().getClass());
        assertDoesNotThrow(() -> updater.getSchedule().getDesiredVelocity((Clock) null));
    }
}