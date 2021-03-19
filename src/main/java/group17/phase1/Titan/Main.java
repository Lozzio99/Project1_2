package group17.phase1.Titan;

import group17.phase1.Titan.Interfaces.SimulationInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Simulation.Probe.ProbeSimulator;
import group17.phase1.Titan.Simulation.SimulationRepository;

/**
 * This class gets executed upon start and triggers all the needed steps to start the whole simulation.
 * @author 	Dan Parii, Lorenzo Pompigna, Nikola Prianikov, Axel Rozental, Konstantin Sandfort, Abhinandan Vasudevan
 * * @version 1.0
 * @since	19/03/2021
 */

public class Main
{
    public static SimulationInterface simulation = new SimulationRepository();

    public Main(){

    }
    /**
     * Main method to execute.
     * @param args -
     */

    public static void main(String[] args)
    {
        simulation.initProbe();
        //simulation.calculateTrajectories();
        simulation.initSimulation();

    }
}
