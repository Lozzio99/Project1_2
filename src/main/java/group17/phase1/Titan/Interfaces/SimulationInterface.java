package group17.phase1.Titan.Interfaces;
import group17.phase1.Titan.Graphics.GraphicsManager;
import group17.phase1.Titan.SolarSystem.Bodies.CelestialBody;

public interface SimulationInterface
{
    SolarSystemInterface getSolarSystemRepository();

    GraphicsManager getGraphicsManager();

    void runSimulation();

    void calculateTrajectories();


    CelestialBody getBody(String name);

    void initProbe();

}
