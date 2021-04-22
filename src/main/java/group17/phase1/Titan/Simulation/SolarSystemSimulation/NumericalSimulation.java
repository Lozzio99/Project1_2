package group17.phase1.Titan.Simulation.SolarSystemSimulation;

import group17.phase1.Titan.Graphics.DialogFrame;
import group17.phase1.Titan.Interfaces.GraphicsInterface;
import group17.phase1.Titan.Physics.Bodies.CelestialBody;
import group17.phase1.Titan.Simulation.Simulation;
import group17.phase1.Titan.System.SolarSystem.SolarSystem;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicReference;

public class NumericalSimulation extends Simulation {
    @Override
    public void initSystem() throws FileNotFoundException {
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
    public void initGraphics(boolean enable_graphics, boolean enable_assist) {
        throw new UnsupportedOperationException("No graphics for numerical simulation");
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
        throw new UnsupportedOperationException("No graphics for numerical simulation");
    }

    @Override
    public void stop() {
        this.system.stop();
        this.updater.get().tryStop();
    }

    public void solveAndWait() {
        this.system.reset();
        this.setRunning();
        this.system.startSolver();
        this.updater.get().launch();

    }

    @Override
    public String toString() {
        return this.system.toString();
    }
}
