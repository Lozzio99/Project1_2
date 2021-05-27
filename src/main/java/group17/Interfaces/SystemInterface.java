package group17.Interfaces;


import group17.Rocket.RocketSimulator;
import group17.Simulation.System.Bodies.CelestialBody;
import group17.Simulation.System.Clock;

import java.util.List;

/**
 * An interface for simulating Solar System.
 */
public interface SystemInterface {


    /**
     * Gets celestial bodies.
     *
     * @return the celestial bodies
     */
// Properties
    List<CelestialBody> getCelestialBodies();


    /**
     * Gets clock.
     *
     * @return the clock
     */
    Clock getClock();

    /**
     * Init clock.
     */
    void initClock();


    /**
     * System state state interface.
     *
     * @return the state interface
     */
// Initial State
    StateInterface systemState();

    /**
     * Gets rocket.
     *
     * @return the rocket
     */
    RocketSimulator getRocket();

    /**
     * Init planets.
     */
    void initPlanets();

    /**
     * Init rocket.
     */
    void initRocket();


    /**
     * Start.
     */
// simulation actions
    void start();

    /**
     * Reset.
     */
    void reset();

    /**
     * Stop.
     */
    void stop();

    String toString();

    /**
     * Step.
     */
    void step();

    /**
     * Initial state.
     */
    void initialState();
}
