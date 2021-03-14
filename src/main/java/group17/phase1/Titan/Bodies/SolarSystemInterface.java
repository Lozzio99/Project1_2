package group17.phase1.Titan.Bodies;

import group17.phase1.Titan.Bodies.CelestialBodies.CelestialBodyInterface;
import group17.phase1.Titan.Physics.TimeSequence.SolverInterface;

import java.util.List;

public interface SolarSystemInterface
{
    List<CelestialBodyInterface> allCelestialBodies();

    void updateLocation(CelestialBodyInterface body, SolverInterface functionSolver);

}
