package phase3.Simulation;

import phase3.Graphics.GraphicsInterface;
import phase3.Graphics.GraphicsManager;
import phase3.Simulation.Runner.RunnerInterface;
import phase3.Simulation.Runner.SolarSystemRunner;
import phase3.Simulation.Runner.TitanSimRunner;
import phase3.System.SolarSystem;
import phase3.System.SystemInterface;
import phase3.System.TitanSystem;

import static phase3.Config.*;

public class Simulation implements SimulationInterface {

    private final GraphicsInterface graphics;
    private final SystemInterface system;
    private final RunnerInterface runner;
    private boolean running = false;

    public Simulation() {
        if (GRAPHICS) this.graphics = new GraphicsManager();
        else this.graphics = null;

        switch (SIMULATION) {
            case FLIGHT_TO_TITAN -> {
                this.system = new SolarSystem();
                this.runner = new SolarSystemRunner(this);
            }
            case LANDING_ON_TITAN -> {
                this.system = new TitanSystem();
                this.runner = new TitanSimRunner(this);
            }
            default -> throw new IllegalStateException();
        }

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
        if (GRAPHICS) this.graphics.init();
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
