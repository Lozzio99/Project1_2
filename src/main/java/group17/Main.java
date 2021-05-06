package group17;

import group17.Graphics.MainMenu;
import group17.Interfaces.SimulationInterface;
import group17.Simulation.Simulation;

public class Main {
    public static SimulationInterface simulationInstance;

    public static void main(String[] args) {
        new MainMenuTitan();
    }

    static class MainMenuTitan extends MainMenu {
        @Override
        public void startSimulation() {
            // TODO : we could move the launch phase in an external module to select which
            //  package to pick the simulation from, this is just a beginning but we should keep
            //  the LIBRARY module the most generic as possible so that nobody should modify the
            //  files in there ( if not for corrections )
            simulationInstance = new Simulation();
            // Also the testing in that module will be way much easier considering that we only need
            // to test unit cases (single methods, not the overall simulation)
            simulationInstance.init();
        }
    }
}
