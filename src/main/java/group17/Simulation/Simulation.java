package group17.Simulation;

import group17.Graphics.DialogFrame;
import group17.Graphics.GraphicsManager;
import group17.Interfaces.GraphicsInterface;
import group17.Interfaces.SimulationInterface;
import group17.Interfaces.SystemInterface;
import group17.Interfaces.UpdaterInterface;
import group17.System.SolarSystem;

import static group17.Config.*;

public class Simulation implements SimulationInterface {
    private UpdaterInterface updater;
    private GraphicsInterface graphics;
    private DialogFrame assist;
    private SystemInterface system;
    private SimulationReporter reporter;
    private boolean running, paused;


    @Override
    public void init() {
        this.initUpdater();
        this.initSystem();
        if (REPORT) {
            this.initReporter();
        }
        if (ENABLE_ASSIST) {
            this.initAssist();
            this.startAssist();
        }
        if (ENABLE_GRAPHICS) {
            this.initGraphics();
            this.startGraphics();
        }
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
        this.graphics.init();
    }

    @Override
    public void startGraphics() {
        this.graphics.start();
    }

    @Override
    public GraphicsInterface getGraphics() {
        return this.graphics;
    }


    @Override
    public void initAssist() {
        this.assist = new DialogFrame();
        this.assist.init();
    }


    @Override
    public void startAssist() {

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
    public SimulationReporter getReporter() {
        return this.reporter;
    }

    @Override
    public void initReporter() {
        this.reporter = new SimulationReporter();
    }

    @Override
    public void startReport() {

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
