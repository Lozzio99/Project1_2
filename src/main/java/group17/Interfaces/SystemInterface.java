package group17.Interfaces;


import group17.System.Bodies.CelestialBody;
import group17.System.Clock;

import java.util.List;

public interface SystemInterface {


    // Properties
    List<CelestialBody> getCelestialBodies();


    Clock getClock();

    void initClock();


    // Initial State
    StateInterface systemState();

    RocketInterface getRocket();

    void initPlanets();

    void initRocket();


    // simulationInstance actions
    void start();

    void reset();

    void stop();

    String toString();

    void step();


}
