package group17.phase1.Titan.Interfaces;


import group17.phase1.Titan.Physics.Bodies.CelestialBody;
import group17.phase1.Titan.Physics.Math.MaxCPUSolver;
import group17.phase1.Titan.Simulation.SimulationUpdater;
import group17.phase1.Titan.System.Clock;

import java.io.FileNotFoundException;
import java.util.List;

public interface SystemInterface {
    /**
     * Contains all necessary properties, like mass , radius,
     * color, initial position and initial velocity
     *
     * @return all body with their properties
     */
    List<CelestialBody> getCelestialBodies();

    /**
     * Initialises all planets and set their initial positions and velocities
     */
    void initPlanets() throws FileNotFoundException;

    /**
     * Initialises the probe if specified from configurations
     *
     * @see group17.phase1.Titan.Config
     * @see group17.phase1.Titan.Config#INSERT_PROBE
     */
    void initProbe();

    /**
     * Initialises the system clock to the initial date of
     * the launch, as specified from configuration
     *
     * @see group17.phase1.Titan.Config
     * @see group17.phase1.Titan.Config#LAUNCH_DATE
     */
    void initClock();


    void initDetector();

    void initReport();

    /**
     * Reset the system state to be the initial state
     * and reset the rate of change of all bodies
     */
    void reset();

    /**
     *
     */
    void startSolver();

    /**
     * Safely shuts down all current group thread which are running
     * this will propagate to all graphics monitor and the waiting
     * threads eventually waiting in the solver process
     *
     * @see GraphicsInterface#stop()
     * @see MaxCPUSolver#shutDown()
     * @see SimulationUpdater#tryStop()
     */
    void stop();

    /**
     * Provides an useful description of the current state of the simulation,
     * included position and velocity vectors, as well as all the running or
     * waiting, or timed waiting threads
     *
     * @return the status of the system
     */
    String toString();

    /**
     * Implementation of the system state at a given point in time
     *
     * @return the state of the system
     * @see group17.phase1.Titan.System.SystemState
     */
    StateInterface systemState();

    /**
     * Implementation of the system rate of change at a given point in time
     *
     * @return the state of the system
     * @see group17.phase1.Titan.System.RateOfChange
     */
    RateInterface systemRateOfChange();

    /**
     * Implementation of the function solver
     *
     * @return the solver that is being used
     */
    ODESolverInterface solver();


    /**
     * System clock which is deployed and updated
     * synchronously with the simulation
     *
     * @return the instance of the clock in the simulation
     */
    Clock getClock();

    /**
     * Computes the state of the system within a step in time,
     * and updates the current state to be the new one
     *
     * @see StateInterface#addMul(double, RateInterface)
     * @see ODESolverInterface#step(ODEFunctionInterface, double, StateInterface, double)
     */
    void step();
}
