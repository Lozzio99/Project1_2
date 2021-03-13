package group17.phase1.Titan.Graphics.Engine;

import group17.phase1.Titan.Bodies.SolarSystemInterface;
import group17.phase1.Titan.Physics.TimeSequence.SolverInterface;
import group17.phase1.Titan.Physics.Trajectories.FunctionInterface;

public class BodyLocationUpdater
{
    SolarSystemInterface allBodies;
    SolverInterface solver;
    FunctionInterface movement;

    void updateLocations()
    {
        allBodies.updateLocation(allBodies.allCelestialBodies().get(0), solver);
        //...
    }
}
