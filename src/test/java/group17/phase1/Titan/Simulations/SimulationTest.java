package group17.phase1.Titan.Simulations;

import group17.phase1.Titan.Interfaces.SimulationInterface;
import group17.phase1.Titan.Main;
import group17.phase1.Titan.Simulation.SolarSystemSimulation.SolarSystemSimulation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SimulationTest {

    static SimulationInterface simulation = Main.simulation;

    @BeforeAll
    static void initialize() {
        simulation = new SolarSystemSimulation();
    }

    @AfterEach
    void tearDown() {
        //create a default stop method
        //dispose the graphics if created
        //shut down all threads,
        //check for runtime activity
        //exit code
    }

    @Test
    @DisplayName("CPU")
    void CPU() {
    }

    @Test
    @DisplayName("Init")
    void Init() {
    }

    @Test
    @DisplayName("InitGraphics")
    void InitGraphics() {
    }

    @Test
    @DisplayName("Reset")
    void Reset() {
    }

    @Test
    @DisplayName("Step")
    void Step() {
    }

    @Test
    @DisplayName("Start")
    void Start() {
    }

    @Test
    @DisplayName("SolarSystem")
    void SolarSystem() {
    }

    @Test
    @DisplayName("Graphics")
    void Graphics() {
    }

    @Test
    @DisplayName("SystemState")
    void SystemState() {
    }

    @Test
    @DisplayName("GetSolution")
    void GetSolution() {
    }

    @Test
    @DisplayName("RateOfChange")
    void RateOfChange() {
    }

    @Test
    @DisplayName("GetBody")
    void GetBody() {
    }

    @Test
    @DisplayName("SetStepSize")
    void SetStepSize() {
    }

    @Test
    @DisplayName("GetStepSize")
    void GetStepSize() {
    }

    @Test
    @DisplayName("TestToString")
    void TestToString() {
    }
}