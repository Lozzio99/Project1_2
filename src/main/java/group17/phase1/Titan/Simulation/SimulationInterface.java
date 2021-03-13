package group17.phase1.Titan.Simulation;

import group17.phase1.Titan.Bodies.SolarSystemInterface;
import group17.phase1.Titan.Graphics.Engine.BodyLocationUpdater;
import group17.phase1.Titan.Graphics.Engine.GraphicsManager;
import group17.phase1.Titan.Graphics.Renderer.VisibilityUpdater;

public interface SimulationInterface
{
    BodyLocationUpdater planetsPositionUpdater();


    VisibilityUpdater planetsVisibilityUpdater();


    SolarSystemInterface solarSystemRepository();

    GraphicsManager graphicsManager();
}
