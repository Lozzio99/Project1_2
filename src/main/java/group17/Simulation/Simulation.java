package group17.Simulation;

import group17.Graphics.DialogFrame;
import group17.Graphics.GraphicsManager;
import group17.Interfaces.GraphicsInterface;
import group17.Interfaces.SimulationInterface;
import group17.Interfaces.SystemInterface;
import group17.Interfaces.UpdaterInterface;
import group17.System.SolarSystem;

import static group17.Config.ENABLE_ASSIST;
import static group17.Config.ENABLE_GRAPHICS;

public class Simulation implements SimulationInterface {
    private UpdaterInterface updater;
    private GraphicsInterface graphics;
    private DialogFrame assist;
    private SystemInterface system;
    private boolean running, paused;


    @Override
    public void init() {
        this.initUpdater();
        this.initSystem();
        if (ENABLE_ASSIST)
            this.initAssist();
        if (ENABLE_GRAPHICS)
            this.initGraphics();
    }

    @Override
    public void reset() {
    }

    @Override
    public void stop() {
    }


    @Override
    public void initUpdater() {
        this.updater = new SimulationUpdater();
    }

    @Override
    public void startUpdater() {
    }

    @Override
    public UpdaterInterface getUpdater() {
        return this.updater;
    }


    @Override
    public void initGraphics() {
        this.graphics = new GraphicsManager();
    }

    @Override
    public void startGraphics() {
    }

    @Override
    public GraphicsInterface getGraphics() {
        return this.graphics;
    }


    @Override
    public void initAssist() {
        this.assist = new DialogFrame();
    }

    @Override
    public DialogFrame getAssist() {
        return this.assist;
    }


    @Override
    public void initSystem() {
        this.system = new SolarSystem();
        this.system.initClock();
        this.system.initPlanets();

        //...

    }

    @Override
    public void startSystem() {
    }

    @Override
    public SystemInterface getSystem() {
        return this.system;
    }


    @Override
    public boolean running() {
        return this.running;
    }

    @Override
    public void setRunning() {
        this.running = true;
    }

    @Override
    public boolean waiting() {
        return this.paused;
    }

    @Override
    public void setWaiting(boolean isWaiting) {
        this.paused = isWaiting;
    }


}
