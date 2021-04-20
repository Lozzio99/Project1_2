package group17.phase1.Titan.Simulation.ParticlesSimulation;

import group17.phase1.Titan.Physics.Bodies.CelestialBody;
import group17.phase1.Titan.Simulation.Simulation;
import group17.phase1.Titan.System.ParticlesSystem.ParticlesSystem;

public class ParticlesSimulation extends Simulation {
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
