package group17.phase1.Titan.Simulations.SolarSystemSimulation;

import group17.phase1.Titan.Physics.Bodies.CelestialBody;
import group17.phase1.Titan.Simulations.Simulation;

import static group17.phase1.Titan.Config.SOLVER;

public class SolarSystemSimulation extends Simulation {


    @Override
    public void initSystem(int solver) {
        SOLVER = solver;
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
