package phase3.Simulation;

import phase3.Graphics.GraphicsInterface;
import phase3.Simulation.Runner.RunnerInterface;
import phase3.System.Systems.SystemInterface;

public interface SimulationInterface {
    GraphicsInterface getGraphics();

    SystemInterface getSystem();

    RunnerInterface getRunner();

    void init();

    void start();

    boolean isRunning();


}
