package group17.Simulation;

import group17.Graphics.Assist.ErrorWindow;
import group17.Graphics.Assist.LaunchAssistWindow;
import group17.Graphics.GraphicsManager;
import group17.Graphics.UserDialogWindow;
import group17.Interfaces.GraphicsInterface;
import group17.Interfaces.SimulationInterface;
import group17.Interfaces.SystemInterface;
import group17.Interfaces.UpdaterInterface;
import group17.Math.Solvers.StandardVerletSolver;
import group17.System.Bodies.CelestialBody;
import group17.System.SolarSystem;
import group17.Utils.ErrorReport;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static group17.Main.simulation;
import static group17.Utils.Config.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * One of base classes of program, the simulation of the solar system
 */
public class Simulation implements SimulationInterface {
    private UpdaterInterface updater;
    private GraphicsInterface graphics;
    private LaunchAssistWindow assist;
    private ErrorWindow errorUI;
    private volatile SystemInterface system;
    private SimulationReporter reporter;
    private volatile boolean running, paused = true, stopped = false;


    @Override
    public void init() {
        if (REPORT) this.initReporter();   //first thing, will check all exceptions

        this.initSystem();  // before graphics and userDialog (clock, positions init, ...)
        if (LAUNCH_ASSIST) this.initAssist();
        if (ENABLE_GRAPHICS) this.initGraphics();

        this.initUpdater();  //last thing, will start the simulation if it's the only one running
    }

    @Override
    public void start() {
        if (!this.stopped) {  // there may be some errors in the initialisation
            this.setRunning();
            if (LAUNCH_ASSIST) {
                this.getAssist().showAssistParameters();
            }
            ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(Executors.privilegedThreadFactory());
            service.scheduleWithFixedDelay(this::loop, 30, 8, MILLISECONDS);
        } else {
            if (DEBUG || REPORT)
                this.getReporter().report(new RuntimeException("STOP"));
        }
    }

    /**
     * resets the whole simulation,reverts everything
     * back to initial instance
     */
    @Override
    public void reset() {
        this.setWaiting(true);   //first of all
        this.getSystem().reset();
        CURRENT_TIME = 0;
        ErrorReport.setMonthIndex(0);
        if (!LAUNCH_ASSIST) {
            if (REPORT) this.getReporter().report("START SIMULATION");
            try {
                Thread.sleep(3000);  /* will wait 3 sec */
            } catch (InterruptedException ex) {
                if (REPORT) simulation.getReporter().report(Thread.currentThread(), ex);
            }
            this.setWaiting(false);
        }
        if (DEFAULT_SOLVER == VERLET_STD_SOLVER) ((StandardVerletSolver) this.getUpdater().getSolver()).setFirst();
        this.getUpdater().getSchedule().init();
        this.getUpdater().getSchedule().prepare();
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
            this.updateState();
            if (ENABLE_GRAPHICS)
                this.startGraphics();
            if (REPORT)
                this.startReport();
            if (LAUNCH_ASSIST)
                this.startAssist();
            if (!waiting()) {
                this.startUpdater();
                this.startSystem();
            }
        }
    }

    @Override
    public void initUpdater() {
        this.updater = new SimulationUpdater();
        this.updater.init();
    }

    @Override
    public synchronized void startUpdater() {
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
    public synchronized void startGraphics() {
        this.graphics.start();
    }

    @Override
    public GraphicsInterface getGraphics() {
        return this.graphics;
    }


    @Override
    public void initAssist() {
        this.assist.init();
        this.errorUI.makeTable();
        this.errorUI.initButtons();
        //making the first report to check if positions are correct
    }


    @Override
    public synchronized void startAssist() {
        this.assist.start();
    }

    @Override
    public LaunchAssistWindow getAssist() {
        return this.assist;
    }

    @Override
    public void setAssist(UserDialogWindow assist) {
        this.assist = assist.getLaunchAssist();
        this.assist.setOutputWindow(assist.getOutputWindow());
        this.errorUI = assist.getErrorWindow();
    }

    @Override
    public void initSystem() {
        this.system = new SolarSystem();
        this.system.initClock();
        this.system.initPlanets();
        if (INSERT_ROCKET) this.system.initRocket();
        this.system.initialState();
    }

    @Override
    public synchronized void startSystem() {
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
    public synchronized void startReport() {
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
    public synchronized void setRunning() {
        if (!this.running)
            this.running = true;
    }

    @Override
    public boolean waiting() {
        return this.paused;
    }

    @Override
    public synchronized void setWaiting(boolean isWaiting) {
        this.paused = isWaiting;
    }

    @Override
    public synchronized void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    /**
     *  updates instance of simulation to the next state
     */
    @Override
    public synchronized void updateState() {
        for (int i = 0; i < getSystem().getCelestialBodies().size(); i++) {
            if (getSystem().getCelestialBodies().get(i).isCollided()) {
                getSystem().systemState().getPositions().remove(i);
                getSystem().systemState().getRateOfChange().getVelocities().remove(i);
            }
        }
        getSystem().getCelestialBodies().removeIf(CelestialBody::isCollided);
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
