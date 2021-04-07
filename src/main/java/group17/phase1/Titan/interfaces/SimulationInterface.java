package group17.phase1.Titan.interfaces;

import group17.phase1.Titan.Physics.SolarSystem.CelestialBody;

public interface SimulationInterface
{

    void init();

    void initGraphics();


    void reset();

    void step();

    void start();

    SolarSystemInterface solarSystem();

    GraphicsInterface graphics();

    StateInterface systemState();

    RateInterface rateOfChange();

    CelestialBody getBody(String name);

    void setStepSize(double timeStepSize);

    double getStepSize();
}
