package group17.Simulation;

import group17.Graphics.Assist.UserDialogWindow;
import group17.Interfaces.SimulationInterface;
import group17.Main;
import group17.System.SolarSystem;
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
        simulationTest.initSystem();
        Main.simulation = simulationTest;
        simulationTest.initSystem();
        assertDoesNotThrow(() -> simulationTest.initAssist());
        assertNotNull(simulationTest.getAssist());
    }

    @Test
    @DisplayName("InitSystem")
    void InitSystem() {
        simulationTest.initSystem();
        assertNotNull(simulationTest.getSystem());
        assertNotEquals("null", simulationTest.getSystem().toString());
        System.out.println(new SolarSystem());
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

}