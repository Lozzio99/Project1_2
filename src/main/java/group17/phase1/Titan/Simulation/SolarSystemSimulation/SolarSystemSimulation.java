package group17.phase1.Titan.Simulation.SolarSystemSimulation;
import group17.phase1.Titan.Physics.Bodies.CelestialBody;
import group17.phase1.Titan.Simulation.Simulation;
import group17.phase1.Titan.System.SolarSystem.SolarSystem;

import java.io.FileNotFoundException;

public class SolarSystemSimulation extends Simulation {


    @Override
    public void initSystem() throws FileNotFoundException {
        this.system = new SolarSystem();
        this.system.initPlanets();
        this.system.initProbe();
    }

    @Override
    public CelestialBody getBody(String name) {
        for (CelestialBody c : this.system.getCelestialBodies())
            if (c.toString().equals(name))
                return c;
        return null;
    }


}
