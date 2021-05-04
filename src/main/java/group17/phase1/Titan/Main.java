package group17.phase1.Titan;

import group17.phase1.Titan.Interfaces.SimulationInterface;
import group17.phase1.Titan.Simulations.Simulation;

import static group17.phase1.Titan.Config.*;

public class Main {
    public static SimulationInterface simulation = Simulation.create(NUMERICAL_SIMULATION);

    public static void main(String[] args) {

        simulation.initCPU(MIN_CPU);
        simulation.initSystem(VERLET_VEL_SOLVER);

        //for numerical simulation
        simulation.startSystem();


        simulation.initGraphics(ENABLE_GRAPHICS, ENABLE_ASSIST);
        simulation.startGraphics();
    }
}
