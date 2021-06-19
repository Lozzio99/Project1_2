package phase3.Simulation.Runner;

import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Forces.ModuleFunction;
import phase3.Math.Solvers.ODESolverInterface;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public interface RunnerInterface {

    ScheduledExecutorService service = Executors.newScheduledThreadPool(10, Executors.privilegedThreadFactory());

    ModuleFunction getControls();

    ODESolverInterface<Vector3dInterface> getSolver();

    default ScheduledExecutorService getExecutor() {
        return service;
    }

    void init();

    void runSimulation();

    void loop();
}
