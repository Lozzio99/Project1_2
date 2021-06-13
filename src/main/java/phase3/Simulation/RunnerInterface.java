package phase3.Simulation;

import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Solvers.ODESolverInterface;

import java.util.concurrent.ScheduledExecutorService;

public interface RunnerInterface {

    ODESolverInterface<Vector3dInterface> getSolver();

    ScheduledExecutorService getExecutor();

    void init();

    void runSimulation();

    void loop();
}
