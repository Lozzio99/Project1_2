package Module.Simulation;

import Module.Math.Gravity.GravityFunction;
import Module.Math.Gravity.ODEFunctionInterface;
import Module.Math.Solvers.EulerSolver;
import Module.Math.Solvers.ODESolverInterface;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Runner implements RunnerInterface {
    private final SimulationInterface simulation;
    private ODESolverInterface solver;
    private ODEFunctionInterface function;
    private ScheduledExecutorService service;

    public Runner(SimulationInterface simulation) {
        this.simulation = simulation;
    }

    @Override
    public ODESolverInterface getSolver() {
        return this.solver;
    }

    @Override
    public ScheduledExecutorService getExecutor() {
        return this.service;
    }

    @Override
    public void init() {
        this.solver = new EulerSolver(
                this.function = new GravityFunction()
                        .evaluateCurrentAccelerationFunction());
        this.service = Executors.newSingleThreadScheduledExecutor(Executors.privilegedThreadFactory());
    }

    @Override
    public void runSimulation() {
        if (simulation.isRunning()) {
            service.scheduleWithFixedDelay(this::loop, 10, 16, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public synchronized void loop() {
        simulation.getGraphics().start();
        simulation.getSystem().start();
    }
}
