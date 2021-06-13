package phase3.Simulation;

import phase3.Graphics.GraphicsInterface;
import phase3.System.SystemInterface;

public interface SimulationInterface {
    GraphicsInterface getGraphics();

    SystemInterface getSystem();

    RunnerInterface getRunner();

    void init();

    void start();

    boolean isRunning();


}
