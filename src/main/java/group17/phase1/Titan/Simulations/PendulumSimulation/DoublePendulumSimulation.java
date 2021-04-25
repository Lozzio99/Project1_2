package group17.phase1.Titan.Simulations.PendulumSimulation;

import group17.phase1.Titan.Physics.Bodies.CelestialBody;
import group17.phase1.Titan.Simulations.Simulation;

public class DoublePendulumSimulation extends Simulation {
    private PendulumSystem system;

    @Override
    public void initSystem(int solver) {
        this.system = new PendulumSystem();

    }

    @Override
    public CelestialBody getBody(String name) {
        return null;
    }

    @Override
    public void initCPU(int cpu) {
        this.updater.set(new PendulumUpdater("Updater"));
        this.updater.get().launch();
    }

    @Override
    public void reset() {
        super.reset();
    }
}
