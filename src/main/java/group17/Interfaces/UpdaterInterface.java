package group17.Interfaces;

import group17.Simulation.Rocket.RocketSchedule;

/**
 * The interface Updater interface.
 */
public interface UpdaterInterface extends Runnable {

    /**
     * Init.
     */
    void init();

    /**
     * Start.
     */
    void start();

    /**
     * Gets solver.
     *
     * @return the solver
     */
    ODESolverInterface getSolver();

    /**
     * Gets schedule.
     *
     * @return the schedule
     */
    RocketSchedule getSchedule();
}
