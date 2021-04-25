package group17.phase1.Titan.Simulations.NumericalSimulation;

import group17.phase1.Titan.Graphics.DialogFrame;
import group17.phase1.Titan.Interfaces.GraphicsInterface;
import group17.phase1.Titan.Physics.Bodies.CelestialBody;
import group17.phase1.Titan.Simulations.Simulation;
import group17.phase1.Titan.Simulations.SolarSystemSimulation.SolarSystem;

import java.util.concurrent.atomic.AtomicReference;

import static group17.phase1.Titan.Config.SOLVER;

public class NumericalSimulation extends Simulation {

    @Override
    public void initSystem(int solver) {
        SOLVER = solver;
        this.system = new SolarSystem();
        this.system.initPlanets();
        this.system.initProbe();
        this.solveAndWait();
    }

    @Override
    public CelestialBody getBody(String name) {
        for (CelestialBody c : this.system.getCelestialBodies())
            if (c.toString().equals(name))
                return c;
        return null;
    }

    @Override
    public void initCPU(int level) {
        this.updater.set(new TrajectoriesUpdater());
        this.updater.get().setDaemon(true);
    }


    @Override
    public void initGraphics(boolean enable_graphics, boolean enable_assist) {
        if (enable_graphics)
            System.err.println("Bad Configuration for [ENABLE_GRAPHICS]");
        if (enable_assist)
            System.err.println("Bad Configuration for [ENABLE_ASSIST]");
    }

    @Override
    public AtomicReference<GraphicsInterface> graphics() {
        throw new UnsupportedOperationException("No graphics for numerical simulation");
    }

    @Override
    public DialogFrame assist() {
        throw new UnsupportedOperationException("No graphics for numerical simulation");
    }

    @Override
    public void startGraphics() {

    }

    @Override
    public void stop() {
        this.system.stop();
        this.updater.get().tryStop();
    }

    public void solveAndWait() {
        this.system.reset();
        this.setRunning();
        this.system.startSolver(SOLVER);
        this.updater.get().launch();
        while (!this.updater.get().isKilled()) {

        }
    }

    @Override
    public String toString() {
        return this.system.toString();
    }
}
