package group17.phase1.Titan;

import group17.phase1.Titan.Simulation.SimulationInterface;
import group17.phase1.Titan.Simulation.SimulationRepository;

public class Main
{

    public static SimulationInterface simulation = new SimulationRepository();

    public static void main(String[] args) {
        System.out.println(simulation.getSolarSystemRepository());
    }
}
