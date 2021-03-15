package group17.phase1.Titan.Simulation;

import group17.phase1.Titan.Bodies.SolarSystem;
import group17.phase1.Titan.Bodies.SolarSystemInterface;
import group17.phase1.Titan.Graphics.Engine.GraphicsManager;
import group17.phase1.Titan.Graphics.Engine.SystemSimulationUpdater;
import group17.phase1.Titan.Graphics.Renderer.VisibilityUpdater;

public class Simulation implements SimulationInterface
{
    SystemSimulationUpdater movementUpdater;
    VisibilityUpdater renderingUpdater;
    SolarSystemInterface solarSystem;
    GraphicsManager graphicsManager;

    public Simulation(){
        this.solarSystem = new SolarSystem();
        this.movementUpdater = new SystemSimulationUpdater();
        this.renderingUpdater = new VisibilityUpdater();
        this.start();
    }

    public void start()
    {
        this.graphicsManager = new GraphicsManager();
        this.movementUpdater = this.graphicsManager.getSimulationUpdater();
        this.graphicsManager.startMainThread();
    }

    public void reset(){}

    public void stopTime(){}

    @Override
    public SystemSimulationUpdater planetsPositionUpdater() {
        return this.movementUpdater;
    }


    @Override
    public VisibilityUpdater planetsVisibilityUpdater() {
        return this.renderingUpdater;
    }

    @Override
    public SolarSystemInterface solarSystemRepository() {
        return this.solarSystem;
    }

    @Override
    public GraphicsManager graphicsManager() {
        return this.graphicsManager;
    }
}
