package group17.phase1.Titan;

import group17.phase1.Titan.Interfaces.SimulationInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Simulation.SimulationRepository;

public class Main
{
    public static SimulationInterface simulation = new SimulationRepository();

    public static void main(String[] args)
    {
        System.out.println(simulation.getSolarSystemRepository().getCelestialBodies().get(0).getVectorLocation().getX());
        System.out.println(simulation.getSolarSystemRepository().getCelestialBodies().get(0).getVectorLocation().getY());
        System.out.println(simulation.getSolarSystemRepository().getCelestialBodies().get(0).getVectorLocation().getZ());

        System.out.println("earth 0");
        System.out.println(simulation.getSolarSystemRepository().getCelestialBodies().get(3).getVectorLocation());
        System.out.println(simulation.getSolarSystemRepository().getCelestialBodies().get(3).getVectorLocation().getX());
        System.out.println(simulation.getSolarSystemRepository().getCelestialBodies().get(3).getVectorLocation().getY());
        System.out.println(simulation.getSolarSystemRepository().getCelestialBodies().get(3).getVectorLocation().getZ());


        System.out.println("\n\n\n\n\n\n"+11313113e301+ "\n\n\n\n\n\n");
        int i = 0;
        int hour = 360,year = hour*24*365;//one hour
        while(i<year){ // one year
            Main.simulation.getSolver().step(Main.simulation.getFunction(),hour,(StateInterface) Main.simulation,hour);
            i+=360;
            //System.out.println(simulation.getSolarSystemRepository().getCelestialBodies().get(3).getVectorLocation());
        }
    }
}
