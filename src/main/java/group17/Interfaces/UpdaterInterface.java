package group17.Interfaces;

import group17.Simulation.Rocket.RocketSchedule;

import java.io.Writer;
import java.util.concurrent.atomic.AtomicReference;

/**
 * An interface for updating simulation instance of Solar System.
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
    void setFileWriter(AtomicReference<Writer> fileWriter);

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

    AtomicReference<Writer> initWriter();
}
