package group17.phase1.Titan;

import group17.phase1.Titan.Simulation.Simulation;
import group17.phase1.Titan.interfaces.SimulationInterface;

public class Main
{
    public static SimulationInterface simulation = new Simulation();

    public static void main(String[] args)
    {
        simulation.init();
        simulation.initGraphics();
        simulation.start();
    }
}
