package Module.Simulation;

import Module.Math.ADT.Vector3dInterface;
import Module.Math.Solvers.ODESolverInterface;

import java.util.concurrent.ScheduledExecutorService;

public interface RunnerInterface {

    ODESolverInterface<Vector3dInterface> getSolver();

    ScheduledExecutorService getExecutor();

    void init();

    void runSimulation();

    void loop();
}
