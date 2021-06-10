package Module.Simulation;

import Module.Math.Functions.ModuleFunction;
import Module.Math.Functions.ODEFunctionInterface;
import Module.Math.Solvers.EulerSolver;
import Module.Math.Solvers.ODESolverInterface;
import Module.Math.Vector3dInterface;
import org.jetbrains.annotations.Contract;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static Module.Config.CURRENT_TIME;
import static Module.Config.STEP_SIZE;


public class Runner implements RunnerInterface {
    private final SimulationInterface simulation;
    private ODESolverInterface<Vector3dInterface> solver;
    private ODEFunctionInterface<Vector3dInterface> function;
    private ScheduledExecutorService service;

    @Contract(pure = true)
    public Runner(SimulationInterface simulation) {
        this.simulation = simulation;
    }

    @Override
    public ODESolverInterface<Vector3dInterface> getSolver() {
        return this.solver;
    }

    @Override
    public ScheduledExecutorService getExecutor() {
        return this.service;
    }

    @Override
    public void init() {
        this.solver = new EulerSolver<>(
                this.function = new ModuleFunction()
                        .evaluateCurrentAccelerationFunction());

        this.service = Executors.newSingleThreadScheduledExecutor(Executors.privilegedThreadFactory());
    }

    @Override
    public void runSimulation() {
        if (simulation.isRunning()) {
            service.scheduleWithFixedDelay(this::loop, 10, 9, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public synchronized void loop() {
        simulation.getGraphics().start(simulation.getSystem().getState().get()); //draw the state
        simulation.getSystem().updateState(solver.step(function, CURRENT_TIME, simulation.getSystem().getState(), STEP_SIZE));
        CURRENT_TIME += STEP_SIZE;
        simulation.getSystem().start();
        /*

        New state 1 = solver.step(state 0);
        New state 2 = moduleControllerOpen.makeDecision(New State 2);

        New state 3 = wind.modify(New State 1);
        New state 3 = moduleControllerClosed.optimizeDecision(New State 2);

        this state = New State 3;
        */


        // evaluate next state ]
        // decision-thrusts    ] -> LOOP
        // perturbation        ]
        //     *fix decision   ]
        //
        // update state
    }

}
