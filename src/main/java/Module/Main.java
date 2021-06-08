package Module;

import Module.Simulation.Simulation;
import Module.Simulation.SimulationInterface;

public class Main {
    public static SimulationInterface simulation;

    public static void main(String[] args) {
        simulation = new Simulation();
        simulation.init();
        simulation.start();
    }
}
