package group17.Simulation;

import group17.Interfaces.SimulationInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    static SimulationInterface simulation = new Simulation();

    @Test
    @DisplayName("InitUpdater")
    void InitUpdater() {
        simulation.initUpdater();
        assertNotNull(simulation.getUpdater());
        assertNotEquals("null", simulation.getUpdater().toString());
    }

    @Test
    @DisplayName("InitGraphics")
    void InitGraphics() {
        simulation.initGraphics();
        assertNotNull(simulation.getGraphics());
        assertNotEquals("null", simulation.getGraphics().toString());
    }

    @Test
    @DisplayName("InitAssist")
    void InitAssist() {
        simulation.initAssist();
        assertNotNull(simulation.getAssist());
        assertNotEquals("null", simulation.getAssist().toString());
    }

    @Test
    @DisplayName("InitSystem")
    void InitSystem() {
        simulation.initSystem();
        assertNotNull(simulation.getSystem());
        assertNotEquals("null", simulation.getSystem().toString());
        assertEquals("SolarSystem{Clock { [ 00 : 00 : 00 ]     00 / 00 / 0  }, bodies=[SUN, MERCURY, VENUS, EARTH, MARS, JUPITER, SATURN, TITAN, URANUS, NEPTUNE, MOON], systemState=null, rocket=null}", simulation.getSystem().toString());
    }

    @Test
    @DisplayName("InitReporter")
    void InitReporter() {
        simulation.initReporter();
        assertNotNull(simulation.getReporter());
        assertNotEquals("null", simulation.getReporter().toString());
    }


}