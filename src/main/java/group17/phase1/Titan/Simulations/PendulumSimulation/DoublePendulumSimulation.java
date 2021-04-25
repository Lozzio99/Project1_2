package group17.phase1.Titan.Simulations.PendulumSimulation;

import group17.phase1.Titan.Physics.Bodies.CelestialBody;
import group17.phase1.Titan.Simulations.PendulumSimulation.Graphics.PendulumGraphics;
import group17.phase1.Titan.Simulations.Simulation;

import static group17.phase1.Titan.Config.ENABLE_GRAPHICS;
import static group17.phase1.Titan.Config.SOLVER;

public class DoublePendulumSimulation extends Simulation {

    @Override
    public void initSystem(int solver) {
        this.system = new PendulumSystem();
        this.system.initPlanets();
        this.system.initClock();
        this.system.reset();

    }

    @Override
    public CelestialBody getBody(String name) {
        if (name.equals("1"))
            return this.system.getCelestialBodies().get(0);
        else
            return this.system.getCelestialBodies().get(1);
    }

    @Override
    public void initCPU(int cpu) {
        this.updater.set(new PendulumUpdater("Updater"));
    }

    @Override
    public void initGraphics(boolean graphics, boolean assist) {
        this.graphics.set(new PendulumGraphics());
        this.graphics.get().init();

    }

    @Override
    public void reset() {
        super.reset();
    }

    public void startGraphics() {
        if (!ENABLE_GRAPHICS)
            throw new RuntimeException("Bad configuration input");
        this.system.startSolver(SOLVER);
        this.graphics.get().launch();
        this.setWaiting(false);
        this.startUpdater();
    }

}
