package Module.Simulation;

import Module.Graphics.GraphicsInterface;
import Module.System.SystemInterface;

public interface SimulationInterface {
    GraphicsInterface getGraphics();

    SystemInterface getSystem();

    RunnerInterface getRunner();

    void init();

    void start();

    boolean isRunning();


}
