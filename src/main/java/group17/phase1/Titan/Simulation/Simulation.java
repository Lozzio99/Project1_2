package group17.phase1.Titan.Simulation;

import group17.phase1.Titan.Config;
import group17.phase1.Titan.Graphics.DialogFrame;
import group17.phase1.Titan.Graphics.GraphicsManager;
import group17.phase1.Titan.Interfaces.GraphicsInterface;
import group17.phase1.Titan.Interfaces.SimulationInterface;
import group17.phase1.Titan.Interfaces.SystemInterface;
import group17.phase1.Titan.Simulation.ParticlesSimulation.ParticlesSimulation;
import group17.phase1.Titan.Simulation.SolarSystemSimulation.SolarSystemSimulation;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static group17.phase1.Titan.Config.*;

public abstract class Simulation implements SimulationInterface {
    private static SimulationInterface instance;
    protected final AtomicReference<GraphicsInterface> graphics = new AtomicReference<GraphicsInterface>();
    protected DialogFrame assist;
    protected SystemInterface system;
    protected final AtomicReference<SimulationUpdater> updater = new AtomicReference<SimulationUpdater>();

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
        STEP_SIZE = SIMULATION_LEVEL == SOLAR_SYSTEM_SIMULATION ? 8e3 : 8;
        TRAJECTORY_LENGTH = SIMULATION_LEVEL == SOLAR_SYSTEM_SIMULATION ? 1000 : 50;

        //TODO: do we want this forever? ->
        INSERT_PROBE = SIMULATION_LEVEL != PARTICLES_SIMULATION && INSERT_PROBE;

        switch (level) {
            case 0 -> {
                return instance = new SolarSystemSimulation();
            }
            case 1 -> {
                return instance = new ParticlesSimulation();
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
     * @see group17.phase1.Titan.Physics.Math.MaxCPUSolver#setCPULevel(int)
     */
    public void initCPU(int cpu) {
        CPU_LEVEL = cpu;
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
    public SimulationUpdater updater() {
        return this.updater.get();
    }


    @Override
    public void initSystem() {
        this.updater.set(new SimulationUpdater());
        this.updater.get().setDaemon(true);
    }

    @Override
    public void stop() {
        this.updater.get().tryStop();
        this.graphics.get().stop();
        this.system.stop();
    }


}
