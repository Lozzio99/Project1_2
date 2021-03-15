package group17.phase1.Titan.Bodies;

import group17.phase1.Titan.Bodies.CelestialBodies.CelestialBody;
import group17.phase1.Titan.Physics.TimeSequence.SolverInterface;

import java.util.List;

public interface SolarSystemInterface
{
    List<CelestialBody> allCelestialBodies();

    void updateLocation(CelestialBody body, SolverInterface functionSolver);

}
