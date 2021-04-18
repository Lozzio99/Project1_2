package group17.phase1.Titan.Simulation;

import group17.phase1.Titan.Graphics.DialogFrame;
import group17.phase1.Titan.Interfaces.GraphicsInterface;
import group17.phase1.Titan.Interfaces.SimulationInterface;
import group17.phase1.Titan.Interfaces.SystemInterface;
import group17.phase1.Titan.Simulation.SolarSystemSimulation.SolarSystemSimulation;

import java.util.Set;

public abstract class Simulation implements SimulationInterface {
    private static SimulationInterface instance;
    protected GraphicsInterface graphics;
    protected DialogFrame assist;
    protected SystemInterface system;

    public static SimulationInterface create(int level) {
        switch (level) {
            case 0 -> {
                return instance = new SolarSystemSimulation();
            }
            default -> {
                throw new RuntimeException("Select a valid level for the simulation instance");
            }
        }
    }

    @Override
    public GraphicsInterface graphics() {
        return graphics;
    }

    @Override
    public DialogFrame assist() {
        return assist;
    }

    @Override
    public SystemInterface system() {
        return system;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("\n");
        s.append(this.system.toString())
                .append("\n")
                .append("Threads : ")
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
