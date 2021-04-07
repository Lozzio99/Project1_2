package group17.phase1.Titan.interfaces;

import group17.phase1.Titan.Physics.SolarSystem.CelestialBody;

import java.util.List;

public interface SolarSystemInterface
{
    List<CelestialBody> getCelestialBodies();

    void initPlanetPositions();

    String toString();
}
