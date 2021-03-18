package group17.phase1.Titan.Interfaces;
import group17.phase1.Titan.Graphics.GraphicsManager;
import group17.phase1.Titan.SolarSystem.Bodies.CelestialBody;

public interface SimulationInterface
{
    SolarSystemInterface getSolarSystemRepository();

    GraphicsManager getGraphicsManager();

    CelestialBody getBody(String name);

    void runSimulation();

    void runStepSimulation();

    void calculateTrajectories();

    void initProbe();

    void initSimulation();
}
