package Module.Simulation;

import Module.Math.Solvers.ODESolverInterface;

import java.util.concurrent.ScheduledExecutorService;

public interface RunnerInterface {

    ODESolverInterface getSolver();

    ScheduledExecutorService getExecutor();

    void init();

    void runSimulation();

    void loop();
}
