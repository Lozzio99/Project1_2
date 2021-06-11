package Module.Simulation;

import Module.Math.ADT.Vector3dInterface;
import Module.Math.Functions.ModuleFunction;
import Module.Math.Functions.ODEFunctionInterface;
import Module.Math.Solvers.*;
import Module.System.Bodies.WindInterface;
import Module.System.Module.DecisionMaker;
import Module.System.State.StateInterface;
import org.jetbrains.annotations.Contract;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static Module.Config.*;


public class Runner implements RunnerInterface {
    private final SimulationInterface simulation;
    private ODESolverInterface<Vector3dInterface> solver;
    private ODEFunctionInterface<Vector3dInterface> gravityFunction;
    private ScheduledExecutorService service;
    private DecisionMaker moduleController;
    private WindInterface wind;

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
        switch (SOLVER) {
            case EULER -> this.solver = new EulerSolver<>(
                    this.gravityFunction = new ModuleFunction()
                            .evaluateCurrentAccelerationFunction());

            case RK4 -> this.solver = new RungeKuttaSolver<>(
                    this.gravityFunction = new ModuleFunction()
                            .evaluateCurrentAccelerationFunction());

            case VERLET_STD -> this.solver = new StandardVerletSolver<>(
                    this.gravityFunction = new ModuleFunction()
                            .evaluateCurrentAccelerationFunction());

            case VERLET_VEL -> this.solver = new VerletVelocitySolver<>(
                    this.gravityFunction = new ModuleFunction()
                            .evaluateCurrentAccelerationFunction());

            case MIDPOINT -> this.solver = new MidPointSolver<>(
                    this.gravityFunction = new ModuleFunction()
                            .evaluateCurrentAccelerationFunction());
        }
        this.moduleController = new DecisionMaker(CONTROLLER);
        this.service = Executors.newSingleThreadScheduledExecutor(Executors.privilegedThreadFactory());
    }

    @Override
    public void runSimulation() {
        if (simulation.isRunning()) {
            service.scheduleWithFixedDelay(this::loop, 10, 8, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public synchronized void loop() {
        // draw graphics
        simulation.getGraphics().start(simulation.getSystem().getState().get());
        // update module gravityFunction
        StateInterface<Vector3dInterface> afterModuleDecision = solver.step(moduleController.getController().controlFunction(), CURRENT_TIME, simulation.getSystem().getState(), STEP_SIZE);
        StateInterface<Vector3dInterface> afterGravity = solver.step(gravityFunction, CURRENT_TIME, afterModuleDecision, STEP_SIZE);
        StateInterface<Vector3dInterface> afterWind = solver.step(wind.getWindFunction(), CURRENT_TIME, afterModuleDecision, STEP_SIZE);

        simulation.getSystem().updateState(afterWind);
        CURRENT_TIME += STEP_SIZE;
        simulation.getSystem().start();



        /*

        New state 1 = solver.step(state 0);
        New state 2 = moduleControllerOpen.makeDecision(New State 2);

        New state 3 = wind.modify(New State 1);
        New state 3 = moduleControllerClosed.optimizeDecision(New State 2);

        this state = New State 3;
        */


        // decision-thrusts    ] -> LOOP
        // evaluate next state ]
        // perturbation        ]
        //     *fix decision   ]
        //
        // update state
    }

}
