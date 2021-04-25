package group17.phase1.Titan;

import group17.phase1.Titan.Interfaces.SimulationInterface;
import group17.phase1.Titan.Simulations.Simulation;

import static group17.phase1.Titan.Config.*;

public class Main {
    public static SimulationInterface simulation;

    public static void main(String[] args) {
        simulation = Simulation.create(PENDULUM_SIMULATION);
        simulation.initCPU(MIN_CPU);
        simulation.initSystem(EULER_SOLVER);
        simulation.initGraphics(ENABLE_GRAPHICS, ENABLE_ASSIST);
        simulation.startGraphics();
    }
}
