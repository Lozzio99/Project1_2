package group17.phase1.Titan;

import group17.phase1.Titan.Interfaces.SimulationInterface;
import group17.phase1.Titan.Simulation.Simulation;

import static group17.phase1.Titan.Config.*;

public class Main {
    public static SimulationInterface simulation;

    public static void main(String[] args) {
        simulation = Simulation.create(NUMERICAL_SIMULATION);
        simulation.initCPU(MIN_CPU);
        simulation.initSystem(VERLET_VEL_SOLVER);
        simulation.initGraphics(ENABLE_GRAPHICS, ENABLE_ASSIST);
        simulation.startGraphics();
    }
}
