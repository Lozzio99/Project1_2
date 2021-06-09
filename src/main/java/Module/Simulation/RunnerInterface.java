package Module.Simulation;

import Module.Math.Solvers.ODESolverInterface;
import Module.Math.Vector3dInterface;

import java.util.concurrent.ScheduledExecutorService;

public interface RunnerInterface {

    ODESolverInterface<Vector3dInterface> getSolver();

    ScheduledExecutorService getExecutor();

    void init();

    void runSimulation();

    void loop();
}
