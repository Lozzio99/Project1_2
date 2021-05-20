package group17.Simulation;

import group17.Graphics.Assist.UserDialogWindow;
import group17.Interfaces.SimulationInterface;
import group17.Main;
import group17.Utils.ErrorData;
import group17.Utils.ErrorReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static group17.Utils.Config.*;
import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    static SimulationInterface simulationTest = new Simulation();

    @BeforeEach
    void setUp() {
        ERROR_EVALUATION = false;
        LAUNCH_ASSIST = true; //simulation otherwise would start the loop
        INSERT_ROCKET = false;
    }

    @Test
    @DisplayName("InitUpdater")
    void InitUpdater() {
        simulationTest.initUpdater();
        simulationTest.initSystem();
        simulationTest.getSystem().initRocket();

        assertNotNull(simulationTest.getUpdater());
        assertNotEquals("null", simulationTest.getUpdater().toString());
    }

    @Test
    @DisplayName("InitGraphics")
    void InitGraphics() {
        simulationTest.initGraphics();
        assertNotNull(simulationTest.getGraphics());
        assertNotEquals("null", simulationTest.getGraphics().toString());
    }

    @Test
    @DisplayName("InitAssist")
    void InitAssist() {
        simulationTest.setAssist(new UserDialogWindow());
        assertDoesNotThrow(() -> simulationTest.getAssist());
        assertNotNull(simulationTest.getAssist());
        Main.simulation = simulationTest;
        simulationTest.initSystem();
        assertDoesNotThrow(() -> simulationTest.initAssist());
        assertNotNull(simulationTest.getAssist());
    }


    @Test
    @DisplayName("InitSystem")
    void InitSystem() {
        ERROR_EVALUATION = false;
        simulationTest.initSystem();
        assertNotNull(simulationTest.getSystem());
        assertNotEquals("null", simulationTest.getSystem().toString());
        assertNotEquals("SOLAR SYSTEM\tnull,\n" +
                "\tSTATE\n" +
                "null\n" +
                "\tnull\n", simulationTest.getSystem().toString());


    }

    @Test
    @DisplayName("InitReporter")
    void InitReporter() {
        simulationTest.initReporter();
        assertNotNull(simulationTest.getReporter());
        assertNotEquals("null", simulationTest.getReporter().toString());
    }

    @Test
    @DisplayName("InitialErrorReport")
    void InitialError() {
        Main.simulation = simulationTest;
        simulationTest.initSystem();
        ERROR_EVALUATION = true;
        DEBUG = true;
        ErrorReport.monthIndex = 0;
        ErrorData data = ORIGINAL_DATA[0] = new ErrorData(simulationTest.getSystem().systemState());
        simulationTest.setAssist(new UserDialogWindow());
        simulationTest.initAssist();  //should make automatically the first error report
    }
}
