package group17.Interfaces;

import group17.Simulation.Rocket.RocketSchedule;

public interface UpdaterInterface extends Runnable {

    void init();

    void start();

    ODESolverInterface getSolver();

    RocketSchedule getSchedule();
}
