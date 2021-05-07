package group17.Simulation;

import group17.Graphics.DialogFrame;
import group17.Graphics.GraphicsManager;
import group17.Interfaces.GraphicsInterface;
import group17.Interfaces.SimulationInterface;
import group17.Interfaces.SystemInterface;
import group17.Interfaces.UpdaterInterface;
import group17.System.SolarSystem;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static group17.Config.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class Simulation implements SimulationInterface {
    private UpdaterInterface updater;
    private GraphicsInterface graphics;
    private DialogFrame assist;
    private SystemInterface system;
    private SimulationReporter reporter;
    private volatile boolean running, paused;


    @Override
    public void init() {
        this.initUpdater();
        this.initSystem();
        if (REPORT)
            this.initReporter();
        if (ENABLE_ASSIST)
            this.initAssist();
        if (ENABLE_GRAPHICS)
            this.initGraphics();

    }

    @Override
    public void start() {
        this.setRunning();
        this.setWaiting(true);
        this.getAssist().showAssistParameters();

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(this::loop, 30, 8, MILLISECONDS);

    }

    @Override
    public void reset() {
    }

    @Override
    public void stop() {
    }

    @Override
    public synchronized void loop() {
        if (REPORT)
            this.startReport();
        if (ENABLE_ASSIST)
            this.startAssist();
        if (ENABLE_GRAPHICS)
            this.startGraphics();
        if (!waiting()) {
            this.startUpdater();
            this.startSystem();
            this.getSystem().getClock().step(STEP_SIZE);
        }
    }

    @Override
    public void initUpdater() {
        this.updater = new SimulationUpdater();
        this.updater.init();
    }

    @Override
    public void startUpdater() {
        this.updater.start();
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
        this.assist.start();
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
        this.system.initialState();
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
        this.reporter.init();
    }

    @Override
    public void startReport() {
        this.reporter.start();
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
