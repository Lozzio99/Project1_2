package phase3.Simulation;

import org.jetbrains.annotations.Contract;
import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Forces.ModuleFunction;
import phase3.Math.Forces.WindInterface;
import phase3.Math.Forces.WindModel;
import phase3.Math.Functions.ODEFunctionInterface;
import phase3.Math.Solvers.*;
import phase3.Module.Controllers.DecisionMaker;
import phase3.System.State.StateInterface;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static phase3.Config.*;


public class Runner implements RunnerInterface {
    private final SimulationInterface simulation;
    private ODESolverInterface<Vector3dInterface> solver;
    private ODEFunctionInterface<Vector3dInterface> gravityFunction;
    private ModuleFunction moduleFunction;
    private ODEFunctionInterface<Vector3dInterface> windFunction;
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
        this.wind = new WindModel();
        this.wind.init();
        this.windFunction = this.wind.getWindFunction();

        this.moduleController = new DecisionMaker(CONTROLLER);

        moduleFunction = new ModuleFunction();
        this.gravityFunction = moduleFunction.evaluateCurrentAccelerationFunction();

        switch (SOLVER) {
            case EULER -> this.solver = new EulerSolver<>(this.gravityFunction);
            case RK4 -> this.solver = new RungeKuttaSolver<>(this.gravityFunction);
            case VERLET_STD -> this.solver = new StandardVerletSolver<>(this.gravityFunction);
            case VERLET_VEL -> this.solver = new VerletVelocitySolver<>(this.gravityFunction);
            case MIDPOINT -> this.solver = new MidPointSolver<>(this.gravityFunction);
        }

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
        // update module gravityFunction
        StateInterface<Vector3dInterface> currentState = simulation.getSystem().getState();

        if (currentState.get()[0].getY() < -0.1) {

            System.exit(0);
        }
        System.out.println("\nTime: " + CURRENT_TIME);
        System.out.println("Position: " + currentState.get()[0]);
        System.out.println("Velocity: " + currentState.get()[1]);

        simulation.getGraphics().start(currentState.get()[0]);

        moduleFunction.setControls(moduleController.getControls(CURRENT_TIME, currentState));

        StateInterface<Vector3dInterface> afterGravity = solver.step(gravityFunction, CURRENT_TIME, currentState, STEP_SIZE);

        if (WIND) {
            StateInterface<Vector3dInterface> afterWind = solver.step(windFunction, CURRENT_TIME, afterGravity, STEP_SIZE);
            simulation.getSystem().updateState(afterWind);
        } else {
            simulation.getSystem().updateState(afterGravity);
        }

        CURRENT_TIME += STEP_SIZE;

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
