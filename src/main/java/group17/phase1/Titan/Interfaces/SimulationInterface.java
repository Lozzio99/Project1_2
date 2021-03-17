package group17.phase1.Titan.Interfaces;
import group17.phase1.Titan.Graphics.GraphicsManager;

public interface SimulationInterface
{
    SolarSystemInterface getSolarSystemRepository();

    GraphicsManager getGraphicsManager();

    void runSimulation();
}
