package phase3.Simulation;

import phase3.Graphics.GraphicsInterface;
import phase3.Graphics.GraphicsManager;
import phase3.System.SystemInterface;
import phase3.System.TitanSystem;

public class Simulation implements SimulationInterface {

    private final GraphicsInterface graphics;
    private final SystemInterface system;
    private final RunnerInterface runner;
    private boolean running = false;

    public Simulation() {
        this.graphics = new GraphicsManager();
        this.system = new TitanSystem();
        this.runner = new Runner(this);
    }

    @Override
    public GraphicsInterface getGraphics() {
        return this.graphics;
    }

    @Override
    public SystemInterface getSystem() {
        return this.system;
    }

    @Override
    public RunnerInterface getRunner() {
        return this.runner;
    }

    @Override
    public void init() {
        this.system.init(); //init system and give the runner the initial state ready
        this.graphics.init();
        this.runner.init();
    }

    @Override
    public void start() {
        this.running = true;
        this.runner.runSimulation();
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}
