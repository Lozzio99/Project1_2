package group17.Interfaces;

import group17.Simulation.Rocket.RocketSchedule;

import java.io.FileWriter;
import java.util.concurrent.atomic.AtomicReference;

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
     * Set the file Writer for the error evaluation.
     *
     * @param fileWriter the file writer
     */
    void setFileWriter(AtomicReference<FileWriter> fileWriter);

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
