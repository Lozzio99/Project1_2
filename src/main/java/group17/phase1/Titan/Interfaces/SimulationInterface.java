package group17.phase1.Titan.Interfaces;


import group17.phase1.Titan.Graphics.DialogFrame;
import group17.phase1.Titan.Graphics.GraphicsManager;
import group17.phase1.Titan.Physics.Bodies.CelestialBody;
import group17.phase1.Titan.Simulations.SolarSystemSimulation.SimulationUpdater;

import java.util.concurrent.atomic.AtomicReference;

public interface SimulationInterface {
    /**
     * Starts the Graphics Thread
     * this will only update and render the starting scene
     */
    void startGraphics();

    /**
     * Starts the Updater Thread
     * from now onwards, the state of the simulation is in a constant
     * change, given by the solver step
     */

    void startUpdater();

    /**
     * Reset the system state to be the initial state
     * as well as all the initial conditions and the initial date
     */
    void reset();

    /**
     * "Safe" attempt to shut down all active and waiting threads
     */
    void stop();

    /**
     * the system which is being simulated
     *
     * @return the instance of the system in the simulation
     */
    SystemInterface system();

    /**
     * Non Thread-Safe reference of the graphics object,
     * race-condition is very likely to happen between
     * {@link GraphicsManager#update()} and {@link ODESolverInterface#step(ODEFunctionInterface, double, StateInterface, double)}
     * <p>
     * Threads like {@link SimulationUpdater} or an eventual IO thread will constantly modify or access
     * the objects that the graphics is rendering, but without modifying the graphics object itself,
     * indeed, is this one which is responsible of grabbing the current state
     * (which will be accessed and modified only by CompletableFuture or the SimulationUpdater thread itself) and render it.
     *
     * @return the atomic reference of the graphics object and thread owner
     */
    AtomicReference<GraphicsInterface> graphics();


    /**
     * @return the reference to the assist frame
     * @implNote this may be converted into atomic for managing
     * io streams updates
     */
    DialogFrame assist();

    /**
     * @return the reference to the updater thread
     * @implNote this may be converted into atomic for managing
     * external threads access
     */
    AtomicReference<SimulationUpdater> updater();


    /**
     * Initialises the specified number of threads for the bodies
     * calculations, as from the app configurations,
     * from implementations the following settings are
     * MIN_CPU : 4 threads ( graphics, updater, awt-event-queue, garbage-collector )
     * CPU_LEVEL_2 : 8  ( +4 workers)
     * CPU_LEVEL_3 : 10 ( +6 workers)
     * CPU_LEVEL_4 : 12 ( +8 workers)
     * MAX_CPU : up to all available threads
     *
     * @param cpu the configuration setting
     * @implNote throws runtime exception for missing cpu settings
     * @see group17.phase1.Titan.Config#CPU_LEVEL
     */
    void initCPU(int cpu);

    void initSystem(int solver);

    void initGraphics(boolean graphics, boolean assist);

    CelestialBody getBody(String probe);


    void startSystem();

    boolean running();


    void setRunning();


    boolean waiting();

    void setWaiting(boolean isWaiting);

}
