package group17.phase1.Titan.Simulation;

import group17.phase1.Titan.Config;
import group17.phase1.Titan.Graphics.DialogFrame;
import group17.phase1.Titan.Graphics.GraphicsManager;
import group17.phase1.Titan.Graphics.Scenes.Scene;
import group17.phase1.Titan.Interfaces.GraphicsInterface;
import group17.phase1.Titan.Interfaces.SimulationInterface;
import group17.phase1.Titan.Interfaces.SystemInterface;
import group17.phase1.Titan.Physics.Solvers.MaxCPUSolver;
import group17.phase1.Titan.Simulation.ParticlesSimulation.ParticlesSimulation;
import group17.phase1.Titan.Simulation.SolarSystemSimulation.NumericalSimulation;
import group17.phase1.Titan.Simulation.SolarSystemSimulation.SolarSystemSimulation;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static group17.phase1.Titan.Config.*;

public abstract class Simulation implements SimulationInterface {
    protected final AtomicReference<GraphicsInterface> graphics = new AtomicReference<GraphicsInterface>();
    protected DialogFrame assist;
    protected SystemInterface system;
    protected final AtomicReference<SimulationUpdater> updater = new AtomicReference<SimulationUpdater>();

    private volatile boolean onPause = true;
    private volatile boolean running = false;

    /**
     * Instantiate the Simulation specified from level
     *
     * @param level the desired simulation to create
     * @return the unique instance of the SimulationInterface interface
     * @throws RuntimeException for a non valid level
     * @see SimulationInterface
     * @see Config#SIMULATION_LEVEL
     */
    public static SimulationInterface create(int level) {
        SIMULATION_LEVEL = level;
        //why is it slower? mass? initial velocity?
        STEP_SIZE = SIMULATION_LEVEL == PARTICLES_SIMULATION ? 8 : 8e3;

        //too heavy for all particles, but 50 looks fine
        TRAJECTORY_LENGTH = SIMULATION_LEVEL == SOLAR_SYSTEM_SIMULATION ? 1000 : 50;

        //TODO: do we want this forever? ->
        INSERT_PROBE = SIMULATION_LEVEL != PARTICLES_SIMULATION && INSERT_PROBE;

        //TODO: implement this in a better way
        NAMES = (SIMULATION_LEVEL != PARTICLES_SIMULATION && NAMES);

        //TODO: yo let's put all the cases here
        switch (level) {
            case 0 -> {
                return new SolarSystemSimulation();
            }
            case 1 -> {
                return new ParticlesSimulation();
            }
            case 2 -> {
                return new NumericalSimulation();
            }
            default -> {
                throw new RuntimeException("Select a valid level for the simulation instance");
            }
        }
    }


    /**
     * Enable the graphics thread and all the computational thread
     *
     * @param enable_graphics if enabling graphics
     * @param enable_assist   if enabling assist
     * @// FIXME: 19/04/2021 enable_assist doesn't make the computations threadGroup working alone
     */
    public void initGraphics(boolean enable_graphics, boolean enable_assist) {

        if (enable_graphics) {
            this.graphics.set(new GraphicsManager());
            this.graphics.get().init();
        }
        if (enable_assist) {
            this.assist = new DialogFrame();
            this.assist.init();
        }
    }


    /**
     * Will set the global config to the desired level,
     * from here onwards the desired number of threads will be
     * called and set in an awaiting state.
     *
     * @param cpu the level of the cpu usage desired
     * @implSpec MAX_CPU will try to awaken all available threads
     * @see Config#CPU_LEVEL
     * @see MaxCPUSolver#setCPULevel(int)
     */
    public void initCPU(int cpu) {
        CPU_LEVEL = cpu;

        //TODO : is this really what we want?
        TRAJECTORIES = SIMULATION_LEVEL == PARTICLES_SIMULATION ? (CPU_LEVEL <= 3 && TRAJECTORIES) : TRAJECTORIES;

        this.updater.set(new SimulationUpdater());
        this.updater.get().setDaemon(true);

    }

    /**
     * @return the main graphics object which is owner of the graphics thread
     * @see GraphicsInterface
     */
    @Override
    public AtomicReference<GraphicsInterface> graphics() {
        return this.graphics;
    }

    /**
     * @return the assist graphics object which is owner of the computational threadGroup
     * @see DialogFrame
     */
    @Override
    public DialogFrame assist() {
        return this.assist;
    }

    /**
     * @return the system containing all important states and rate of change of the simulation
     * @see SystemInterface
     * @see group17.phase1.Titan.Interfaces.StateInterface
     * @see group17.phase1.Titan.Interfaces.RateInterface
     */
    @Override
    public SystemInterface system() {
        return this.system;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("\n");
        if (SIMULATION_LEVEL == SOLAR_SYSTEM_SIMULATION) {
            s.append(this.system.toString()).append("\n");
        }
        s.append("Threads : ")
                .append(Thread.currentThread().getThreadGroup().activeCount())
                .append("\n");
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread t : threadSet) {
            if (t.getThreadGroup() == Thread.currentThread().getThreadGroup())
                s.append("Thread :")
                        .append(t).append(":")
                        .append("state:")
                        .append(t.getState())
                        .append("\n");
        }
        return s.toString();
    }

    @Override
    public AtomicReference<SimulationUpdater> updater() {
        return this.updater;
    }

    @Override
    public void startGraphics() {
        if (!ENABLE_GRAPHICS)
            throw new RuntimeException("Bad configuration input");

        this.system.reset();
        this.system.startSolver();
        this.graphics.get().launch();
        if (ENABLE_ASSIST)
            this.assist.showAssistParameters();
        else {
            this.setWaiting(false);
            this.graphics.get().changeScene(Scene.SceneType.SIMULATION_SCENE);
            this.startUpdater();
            System.out.println("Commence simulation...");
        }
    }

    @Override
    public void startUpdater() {
        this.updater.get().launch();
    }

    @Override
    public void stop() {
        this.system.stop();
        this.updater.get().tryStop();
        this.graphics.get().stop();
    }

    @Override
    public void reset() {
        this.updater.get().tryStop();
        this.system.reset();
        this.system.startSolver();
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
        return this.onPause;
    }

    @Override
    public void setWaiting(boolean isWaiting) {
        this.onPause = isWaiting;
    }

}
