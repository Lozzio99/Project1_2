package group17.phase1.Titan.Simulation.SolarSystemSimulation;

import group17.phase1.Titan.Interfaces.SimulationInterface;
import group17.phase1.Titan.Main;
import group17.phase1.Titan.Simulation.Simulation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumericalSimulationTest {
    static SimulationInterface simulation;

    @BeforeAll
    static void beforeAll() {
        simulation = Main.simulation = Simulation.create(2);//numerical
        simulation.initCPU(1);
        simulation.initSystem();
        System.out.println(simulation.toString());
    }

    @Test
    @DisplayName("InitSystem")
    void InitSystem() {
        assertEquals(simulation.system().toString(), simulation.system().toString());
    }

    @Test
    @DisplayName("GetBody")
    void GetBody() {
    }

    @Test
    @DisplayName("InitCPU")
    void InitCPU() {
    }

    @Test
    @DisplayName("InitGraphics")
    void InitGraphics() {
    }

    @Test
    @DisplayName("Graphics")
    void Graphics() {
    }

    @Test
    @DisplayName("Assist")
    void Assist() {
    }

    @Test
    @DisplayName("StartGraphics")
    void StartGraphics() {
    }

    @Test
    @DisplayName("Stop")
    void Stop() {
    }

    @Test
    @DisplayName("SolveAndWait")
    void SolveAndWait() {
        simulation.system().solver().step(simulation.system().solver().getFunction(), 0.3, simulation.system().systemState(), 0.3);
    }

    @Test
    @DisplayName("TestToString")
    void TestToString() {
    }
}