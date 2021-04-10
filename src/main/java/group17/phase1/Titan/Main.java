package group17.phase1.Titan;

import group17.phase1.Titan.MetaSimulation.MetaSimulation;
import group17.phase1.Titan.Simulation.Simulation;
import group17.phase1.Titan.interfaces.SimulationInterface;

public class Main
{
    public static SimulationInterface simulation;

    public static void main(String[] args)
    {
        if (1>2)
            simulation = new MetaSimulation();
        else
            simulation = new Simulation();

        simulation.init();
        simulation.initGraphics();
        simulation.start();
    }
}
