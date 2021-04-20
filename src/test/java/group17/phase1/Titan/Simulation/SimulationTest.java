package group17.phase1.Titan.Simulation;

import group17.phase1.Titan.Interfaces.SimulationInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimulationTest {

    // Variables
    SimulationInterface testSimulation;

    @Test
    @DisplayName("Create")
    void Create() {
        testSimulation = Simulation.create(0);
    }

    @Test
    @DisplayName("Graphics")
    void Graphics() {
        testSimulation.initGraphics(true, true);
    }

    @Test
    @DisplayName("Assist")
    void Assist() {
        testSimulation.assist();
    }

    @Test
    @DisplayName("System")
    void System() {
        testSimulation.system();
    }

    @Test
    @DisplayName("TestToString")
    void TestToString() {
        // Expected needs to be changed here:
        assertEquals("",testSimulation.toString());
    }
}