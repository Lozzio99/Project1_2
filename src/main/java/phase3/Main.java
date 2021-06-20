package phase3;

import phase3.Simulation.Simulation;
import phase3.Simulation.SimulationInterface;

public class Main {
    public static SimulationInterface simulation;

    public static void main(String[] args) {
        simulation.init();
        simulation.start();
    }
}
