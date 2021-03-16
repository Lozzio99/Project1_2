package group17.phase1.Titan.Simulation;

import group17.phase1.Titan.Graphics.GraphicsManager;
import group17.phase1.Titan.SolarSystem.SolarSystemInterface;

public interface SimulationInterface
{
    SolarSystemInterface getSolarSystemRepository();

    GraphicsManager getGraphicsManager();
}
