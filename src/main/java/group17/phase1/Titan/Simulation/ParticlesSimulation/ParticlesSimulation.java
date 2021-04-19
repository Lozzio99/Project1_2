package group17.phase1.Titan.Simulation.ParticlesSimulation;

import group17.phase1.Titan.Physics.Bodies.CelestialBody;
import group17.phase1.Titan.Simulation.Simulation;
import group17.phase1.Titan.System.ParticlesSystem.ParticlesSystem;

public class ParticlesSimulation extends Simulation {
    @Override
    public void start() {
        this.reset();
        this.graphics.launch();
        this.assist.launch();
    }

    @Override
    public void reset() {
        this.system.reset();
        this.system.start();
    }

    @Override
    public void stop() {
        this.graphics.stop();
        this.assist.stop();
        this.system.stop();
    }

    @Override
    public void initSystem() {
        this.system = new ParticlesSystem();
    }


    @Override
    public CelestialBody getBody(String name) {
        for (CelestialBody c : this.system.getCelestialBodies())
            if (c.toString().equals(name))
                return c;
        return null;
    }
}
