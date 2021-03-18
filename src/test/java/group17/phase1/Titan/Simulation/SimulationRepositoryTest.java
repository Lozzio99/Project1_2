package group17.phase1.Titan.Simulation;

import group17.phase1.Titan.Interfaces.SimulationInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Main;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationRepositoryTest {

    @Test
    @DisplayName("Solve")
    void testSimulation()
    {
        Main main = new Main();
        Main.simulation.initProbe();
        Main.simulation.calculateTrajectories();
        Main.simulation.initSimulation();
    }


}