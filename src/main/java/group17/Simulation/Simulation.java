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
    private volatile SystemInterface system;
    private SimulationReporter reporter;
    private volatile boolean running, paused = true, stopped = false;


    @Override
    public void init() {
        if (REPORT)
            this.initReporter();   //first thing, will check all exceptions

        this.initSystem();  // before graphics and assist (clock, positions init, ...)

        if (ENABLE_ASSIST)
            this.initAssist();
        if (ENABLE_GRAPHICS)
            this.initGraphics();

        this.initUpdater();  //last thing, will start the simulation if it's the only one running

    }

    @Override
    public void start() {
        if (!this.stopped) {  // there may be some errors in the initialisation
            this.setRunning();
            if (ENABLE_ASSIST)
                this.getAssist().showAssistParameters();
            ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(Executors.privilegedThreadFactory());
            service.scheduleWithFixedDelay(this::loop, 30, 13, MILLISECONDS);
        } else {
            if (DEBUG || REPORT)
                this.getReporter().report(new RuntimeException("STOP"));
        }
    }

    @Override
    public void reset() {

    }

    @Override
    public void stop() {
        this.stopped = true;
        this.running = false;
        this.paused = false;
    }


    @Override
    public synchronized void loop() {
        if (this.running) {
            if (REPORT)
                this.startReport();
            if (ENABLE_ASSIST)
                this.startAssist();
            if (ENABLE_GRAPHICS)
                this.startGraphics();
            if (!waiting()) {
                this.startUpdater();
                this.startSystem();
            }
            this.updateState();
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
        if (INSERT_ROCKET)
            this.system.initRocket();
        this.system.initialState();
    }

    @Override
    public void startSystem() {
        this.system.start();
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
        if (!this.running)
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

    @Override
    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    @Override
    public synchronized void updateState() {

    }


    @Override
    public String toString() {
        return "Simulation{" +
                "running=" + running +
                ",paused=" + paused +
                ",nThreads=" + Thread.currentThread().getThreadGroup().activeCount() +
                '}';
    }
}
