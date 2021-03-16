package group17.phase1.Titan.Simulation;

import group17.phase1.Titan.Graphics.GraphicsManager;
import group17.phase1.Titan.Interfaces.SimulationInterface;
import group17.phase1.Titan.Interfaces.SolarSystemInterface;
import group17.phase1.Titan.SolarSystem.SolarSystem;


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
