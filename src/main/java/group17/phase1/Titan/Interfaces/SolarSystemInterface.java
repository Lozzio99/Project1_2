package group17.phase1.Titan.Interfaces;

import group17.phase1.Titan.SolarSystem.Bodies.CelestialBody;

import java.util.List;

public interface SolarSystemInterface
{
    List<CelestialBody> getCelestialBodies();

    RateInterface getRateOfChange();

    StateInterface getState();
}
