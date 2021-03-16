package group17.phase1.Titan.Simulation;

import group17.phase1.Titan.Graphics.GraphicsManager;
import group17.phase1.Titan.SolarSystem.SolarSystem;
import group17.phase1.Titan.SolarSystem.SolarSystemInterface;

/*
 * A class for solving a general differential equation dy/dt = f(t,y)
 *     y(t) describes the state of the system at time t
 *     f(t,y(t)) defines the derivative of y(t) with respect to time t
 */


public class SimulationRepository implements SimulationInterface
{
    SolarSystemInterface repository;
    GraphicsManager graphicsManager;

    public SimulationRepository(){
        this.repository = new SolarSystem();
        this.graphicsManager = new GraphicsManager();
        this.graphicsManager.startMainThread();
    }

    @Override
    public SolarSystemInterface getSolarSystemRepository() {
        return this.repository;
    }

    @Override
    public GraphicsManager getGraphicsManager() {
        return this.graphicsManager;
    }

    void loopSimulation(){  }

}
