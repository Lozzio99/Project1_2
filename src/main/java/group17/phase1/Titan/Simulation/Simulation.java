package group17.phase1.Titan.Simulation;

import group17.phase1.Titan.Bodies.SolarSystem;
import group17.phase1.Titan.Bodies.SolarSystemInterface;
import group17.phase1.Titan.Graphics.Engine.BodyLocationUpdater;
import group17.phase1.Titan.Graphics.Renderer.VisibilityUpdater;

public class Simulation implements SimulationInterface
{
    BodyLocationUpdater movementUpdater;
    VisibilityUpdater renderingUpdater;
    SolarSystemInterface solarSystem;

    public Simulation(){
        this.movementUpdater = new BodyLocationUpdater();
        this.renderingUpdater = new VisibilityUpdater();
        this.solarSystem = new SolarSystem();
    }

    public void reset(){}

    public void stopTime(){}

    @Override
    public BodyLocationUpdater planetsPositionUpdater() {
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


}
