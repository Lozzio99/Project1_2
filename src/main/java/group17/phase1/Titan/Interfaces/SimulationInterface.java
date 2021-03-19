/**
 * Interface representing a simulation of a solar system.
 * @author 	Dan Parii, Lorenzo Pompigna, Nikola Prianikov, Axel Rozental, Konstantin Sandfort, Abhinandan Vasudevan​
 * @version 1.0
 * @since	19/02/2021
 */
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
