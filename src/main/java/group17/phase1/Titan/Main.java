package group17.phase1.Titan;

import group17.phase1.Titan.Interfaces.SimulationInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Simulation.Probe.ProbeSimulator;
import group17.phase1.Titan.Simulation.SimulationRepository;

public class Main
{
    public static SimulationInterface simulation = new SimulationRepository();

    public static void main(String[] args)
    {
        simulation.initProbe();
        //simulation.calculateTrajectories();
        simulation.runSimulation();
    }
}
