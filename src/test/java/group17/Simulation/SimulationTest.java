package group17.Simulation;

import group17.Graphics.Assist.LaunchAssistWindow;
import group17.Graphics.UserDialogWindow;
import group17.Interfaces.SimulationInterface;
import group17.Main;
import group17.Utils.ErrorData;
import group17.Utils.ErrorReport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Isolated;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static group17.Main.simulation;
import static group17.Main.userDialog;
import static group17.Utils.Config.*;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Isolated
//@Disabled("Won't never work if tests are running at the same time")
class SimulationTest {


    @BeforeEach
    void setUp() {
        simulation = null;
        LAUNCH_ASSIST = true; //simulation otherwise would start the loop
        INSERT_ROCKET = false;
        REPORT = false;
        ERROR_EVALUATION = false;
    }

    @AfterEach
    void tearDown() {
        try {
            sleep(100);
            simulation = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    @DisplayName("InitUpdater")
    void InitUpdater() {
        assumeTrue(simulation == null);
        SimulationInterface simulation = new Simulation();
        simulation.initUpdater();
        simulation.initSystem();
        simulation.getSystem().initRocket();
        assertNotNull(simulation.getUpdater());
        assertNotEquals("null", simulation.getUpdater().toString());
    }

    @Test
    @DisplayName("InitGraphics")
    void InitGraphics() {
        assumeTrue(simulation == null);
        SimulationInterface simulation = new Simulation();
        simulation.initGraphics();
        assertNotNull(simulation.getGraphics());
        assertNotEquals("null", simulation.getGraphics().toString());
        simulation.getGraphics().getFrame().dispose();
    }

    @Test
    @DisplayName("InitAssist")
    void InitAssist() {
        ENABLE_GRAPHICS = false;
        SimulationInterface simulation = new Simulation();
        simulation.setAssist(userDialog = new UserDialogWindow());
        assertDoesNotThrow(simulation::getAssist);
        assertNotNull(simulation.getAssist());
        userDialog.getFrame().dispose();
    }


    @Test
    @DisplayName("InitSystem")
    void InitSystem() {
        ERROR_EVALUATION = false;
        SimulationInterface simulation = new Simulation();
        simulation.initSystem();
        assertNotNull(simulation.getSystem());
        assertNotEquals("null", simulation.getSystem().toString());
        assertNotEquals("SOLAR SYSTEM\tnull,\n" +
                "\tSTATE\n" +
                "null\n" +
                "\tnull\n", simulation.getSystem().toString());
    }

    @Test
    @DisplayName("InitReporter")
    void InitReporter() {
        SimulationInterface simulation = new Simulation();
        simulation.initReporter();
        assertNotNull(simulation.getReporter());
        assertNotEquals("null", simulation.getReporter().toString());
    }

    @Test
    @DisplayName("InitialErrorReport")
    void InitialError() {
        assumeTrue(simulation == null);
        simulation = new Simulation();
        simulation.initSystem();
        DEBUG = true;
        ErrorReport.setMonthIndex(0);
        ErrorData data = ORIGINAL_DATA[0] = new ErrorData(simulation.getSystem().systemState());
        simulation.setAssist(userDialog = new UserDialogWindow());
        simulation.initAssist();  //should make automatically the first error report
        userDialog.getFrame().dispose();
    }

    @Test
    @DisplayName("Init")
    void InitOnlyUpdater() {
        assumeTrue(simulation == null);
        REPORT = LAUNCH_ASSIST = ENABLE_GRAPHICS = false;
        simulation = new Simulation();
        simulation.init();
        assertFalse(simulation.running());
        assertNotNull(simulation.getUpdater());
        assertFalse(simulation.waiting());
    }


    @Test
    @DisplayName("Init")
    void init() {
        assumeTrue(simulation == null);
        REPORT = LAUNCH_ASSIST = ENABLE_GRAPHICS = true;
        simulation = new Simulation();
        assertNull(simulation.getAssist());
        assertThrows(NullPointerException.class, () -> simulation.init());
        assertFalse(simulation.running());
        assertTrue(simulation.waiting());
        simulation = null;
    }

    @Test
    @DisplayName("Start")
    void Start() {
        assumeTrue(simulation == null);
        REPORT = true;
        SimulationInterface simulation = new Simulation();
        simulation.initReporter();
        simulation.setStopped(true);
        simulation.start();
        assertFalse(simulation.running());
        assertTrue(simulation.waiting());
        simulation.setStopped(false);
        LAUNCH_ASSIST = false;
        simulation.start();
        Main.simulation = null;
    }

    @Test
    @DisplayName("Reset")
    void Reset() {
        assumeTrue(simulation == null);
        SimulationInterface simulation = new Simulation();
        simulation.initSystem();
        simulation.initUpdater();
        simulation.initReporter();
        LAUNCH_ASSIST = true;
        simulation.reset();
        assertTrue(simulation.waiting());
        LAUNCH_ASSIST = false;
        simulation.reset();
        assertFalse(simulation.waiting());
    }

    @Test
    @DisplayName("Stop")
    void Stop() {
        assumeTrue(simulation == null);
        simulation = new Simulation();
        simulation.stop();
        assertFalse(simulation.running());
        assertFalse(simulation.waiting());
        simulation = null;
    }

    @Test
    @DisplayName("Loop")
    void Loop() {
        assumeTrue(simulation == null);
        ERROR_EVALUATION = false;
        simulation = new Simulation();
        simulation.stop();
        assertDoesNotThrow(() -> simulation.loop());
        simulation.setRunning();
        assertThrows(NullPointerException.class, simulation::loop);
        simulation = null;
    }

    @Test
    @DisplayName("Loop")
    void TestLoop() {
        assumeTrue(simulation == null);
        ERROR_EVALUATION = false;
        LAUNCH_ASSIST = false;
        ENABLE_GRAPHICS = true;
        userDialog = new UserDialogWindow();
        userDialog.getMainMenu().startSimulation();
        //simulation.init();
        try {
            sleep(500);
            simulation.setWaiting(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CompletableFuture<SimulationInterface> future =
                CompletableFuture.runAsync(simulation::start)
                        .thenApply(e -> {
                            simulation.setWaiting(true);
                            return simulation;
                        });
        assertAll(
                () -> assertDoesNotThrow(simulation::getAssist),
                () -> assertDoesNotThrow(simulation::getGraphics),
                () -> assertDoesNotThrow(simulation::getSystem),
                () -> assertDoesNotThrow(simulation::getReporter),
                () -> assertDoesNotThrow(simulation::getUpdater));

        try {
            SimulationInterface res = future.get(100, TimeUnit.MILLISECONDS);
            userDialog.getFrame().dispose();
            simulation.getGraphics().getFrame().dispose();
            sleep(3000);
            res = null;
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        simulation = null;
    }


    @Test
    @DisplayName("StartUpdater")
    void StartUpdater() {
        assumeTrue(simulation == null);
        simulation = new Simulation();
        simulation.initUpdater();
        REPORT = false;
        assertDoesNotThrow(simulation::startUpdater);

        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        INSERT_ROCKET = true;
        LAUNCH_ASSIST = false;
        REPORT = true;
        simulation.initReporter();
        simulation.initSystem();
        REPORT = false;
        simulation.getUpdater().run();
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        simulation = null;
    }

    @Test
    @DisplayName("GetUpdater")
    void GetUpdater() {
        assumeTrue(simulation == null);
        simulation = new Simulation();
        assertNull(simulation.getUpdater());
        simulation.initUpdater();
        assertNotNull(simulation.getUpdater());
        simulation = null;
    }


    @Test
    @DisplayName("StartGraphics")
    void StartGraphics() {
        assumeTrue(simulation == null);
        simulation = new Simulation();
        simulation.initGraphics();
        assertDoesNotThrow(() -> simulation.startGraphics());
        simulation.getGraphics().getFrame().dispose();
        simulation = null;
    }

    @Test
    @DisplayName("GetGraphics")
    void GetGraphics() {
        assumeTrue(simulation == null);
        simulation = new Simulation();
        assertNull(simulation.getGraphics());
        simulation.initGraphics();
        assertNotNull(simulation.getGraphics());
        simulation.getGraphics().getFrame().dispose();
        simulation = null;
    }


    @Test
    @DisplayName("StartAssist")
    void StartAssist() {
        assumeTrue(simulation == null);
        simulation = new Simulation();
        assertNull(simulation.getAssist());
        assertThrows(NullPointerException.class, simulation::initAssist);
        assertThrows(NullPointerException.class, simulation::startAssist);
        simulation = null;
    }

    @Test
    @DisplayName("GetAssist")
    void GetAssist() {
        assumeTrue(simulation == null);
        simulation = new Simulation();
        assertNull(simulation.getAssist());
        Assist assist = new Assist();
        simulation.setAssist(assist);
        assertNotNull(simulation.getAssist());
        assertNotEquals("wrong", simulation.getAssist().toString());
        assertEquals(LaunchAssistWindow.class, simulation.getAssist().getClass());
        assist.getFrame().dispose();
        simulation = null;
    }

    @Test
    @DisplayName("SetAssist")
    void SetAssist() {
        assumeTrue(simulation == null);
        simulation = new Simulation();
        simulation.setAssist(userDialog = new UserDialogWindow());
        assertNotNull(simulation.getAssist());
        userDialog.getFrame().dispose();
        simulation = null;
    }

    @Test
    @DisplayName("StartSystem")
    void StartSystem() {
        assumeTrue(simulation == null);
        simulation = new Simulation();
        simulation.initSystem();
        assertDoesNotThrow(simulation::startSystem);
        simulation = null;
    }

    @Test
    @DisplayName("GetReporter")
    void GetReporter() {
        assumeTrue(simulation == null);
        simulation = new Simulation();
        assertNull(simulation.getReporter());
        simulation.initReporter();
        assertNotNull(simulation.getReporter());
        assertEquals(SimulationReporter.class, simulation.getReporter().getClass());
        simulation = null;
    }

    @Test
    @DisplayName("StartReport")
    void StartReport() {
        simulation = new Simulation();
        assertThrows(NullPointerException.class, simulation::startReport);
        simulation = null;
    }

    @Test
    @DisplayName("GetSystem")
    void GetSystem() {
        SimulationInterface simulation = new Simulation();
        assertNull(simulation.getSystem());
        simulation.initSystem();
        assertNotNull(simulation.getSystem());
    }

    @Test
    @DisplayName("Running")
    void Running() {
        SimulationInterface simulation = new Simulation();
        simulation.setRunning();
        assertTrue(simulation::running);
    }

    @Test
    @DisplayName("SetRunning")
    void SetRunning() {
        SimulationInterface simulation = new Simulation();
        assertFalse(simulation::running);
        simulation.setRunning();
        assertTrue(simulation::running);
    }

    @Test
    @DisplayName("Waiting")
    void Waiting() {
        SimulationInterface simulation = new Simulation();
        assertTrue(simulation::waiting);
        simulation.setWaiting(false);
        assertFalse(simulation::waiting);
    }

    @Test
    @DisplayName("SetWaiting")
    void SetWaiting() {
        SimulationInterface simulation = new Simulation();
        assertTrue(simulation::waiting);
        simulation.setWaiting(false);
        assertFalse(simulation::waiting);
    }

    @Test
    @DisplayName("SetStopped")
    void SetStopped() {
        SimulationInterface simulation = new Simulation();
        assertTrue(simulation::waiting);
        simulation.setWaiting(false);
        assertFalse(simulation::waiting);
    }

    @Test
    @DisplayName("UpdateState")
    void UpdateState() {
        SimulationInterface simulation = new Simulation();
        assertThrows(NullPointerException.class, simulation::updateState);
        simulation.initSystem();
        assertDoesNotThrow(simulation::updateState);
    }

    @Test
    @DisplayName("TestToString")
    void TestToString() {
        SimulationInterface simulation = new Simulation();
        assertNotNull(simulation.toString());
    }

    static class Assist extends UserDialogWindow {
        @Override
        public String toString() {
            return "wrong";
        }
    }


}
