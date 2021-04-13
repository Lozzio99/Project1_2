package group17.phase1.Titan;

import group17.phase1.Titan.MetaSimulation.MetaSimulation;
import group17.phase1.Titan.Simulation.Simulation;
import group17.phase1.Titan.interfaces.SimulationInterface;

import static group17.phase1.Titan.Config.MAX_CPU;
import static group17.phase1.Titan.Config.META_SIMULATION;

public class Main
{
    public static SimulationInterface simulation;

    public static void main(String[] args)
    {

        /**
         *   Intel(R) Core(TM) i7-9750H CPU @ 2.60GHz   2.60 GHz
         *   x64-based processor ( loz pc )
         *
         *   CPU INFO :
         *      case meta simulation :
         *      - max cpu = 100% usage
         *      - min cpu = 15% usage
         *
         *      case solar system simulation :
         *      - max cpu = 45%   -> can we improve this? (space for more calculations)
         *      - min cpu = 35%
         *
         */

        if (META_SIMULATION)
            simulation = new MetaSimulation();
        else
            simulation = new Simulation();

        simulation.init(MAX_CPU);
        simulation.initGraphics();
        simulation.start();
    }
}
