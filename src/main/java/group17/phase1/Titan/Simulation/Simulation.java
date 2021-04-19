package group17.phase1.Titan.Simulation;

import group17.phase1.Titan.Graphics.DialogFrame;
import group17.phase1.Titan.Graphics.GraphicsManager;
import group17.phase1.Titan.Interfaces.GraphicsInterface;
import group17.phase1.Titan.Interfaces.SimulationInterface;
import group17.phase1.Titan.Interfaces.SystemInterface;
import group17.phase1.Titan.Simulation.ParticlesSimulation.ParticlesSimulation;
import group17.phase1.Titan.Simulation.SolarSystemSimulation.SolarSystemSimulation;

import java.util.Set;

import static group17.phase1.Titan.Config.*;

public abstract class Simulation implements SimulationInterface {
    private static SimulationInterface instance;
    protected GraphicsInterface graphics;
    protected DialogFrame assist;
    protected SystemInterface system;

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

    public void initGraphics(boolean enable_graphics, boolean enable_assist) {
        if (enable_graphics) {
            this.graphics = new GraphicsManager();
            this.graphics.init();
        }
        if (enable_assist) {
            this.assist = new DialogFrame();
            this.assist.init();
        }
    }


    public void initCPU(int cpu) {
        CPU_LEVEL = cpu;
    }

    @Override
    public GraphicsInterface graphics() {
        return this.graphics;
    }

    @Override
    public DialogFrame assist() {
        return this.assist;
    }

    @Override
    public SystemInterface system() {
        return this.system;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("\n");
        if (SIMULATION_LEVEL == SOLAR_SYSTEM_SIMULATION)
            s.append(this.system.toString()).append("\n");

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
}
