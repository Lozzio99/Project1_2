package group17.phase1.Titan;
import group17.phase1.Titan.Simulation.Simulation;
import group17.phase1.Titan.Simulation.SimulationInterface;

public class Main
{
    public static SimulationInterface simulation = new Simulation();

    public static void main(String[] args)
    {

        //init is been called in simulation, in solarSystem
        System.out.println(simulation.solarSystemRepository().allCelestialBodies().get(0).getX_LOCATION());
        //.102 expected
    }
}
