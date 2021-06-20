package phase3.Simulation.Runner;

import org.jetbrains.annotations.Contract;
import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Forces.ModuleFunction;
import phase3.Math.Forces.WindInterface;
import phase3.Math.Forces.WindModel;
import phase3.Math.Functions.ODEFunctionInterface;
import phase3.Math.Solvers.ODESolverInterface;
import phase3.Rocket.Controllers.DecisionMaker;
import phase3.Simulation.SimulationInterface;
import phase3.System.State.StateInterface;

import static phase3.Config.*;


public class TitanSimRunner implements RunnerInterface {
    private final SimulationInterface simulation;
    private ODESolverInterface<Vector3dInterface> solver;
    private ODEFunctionInterface<Vector3dInterface> gravityFunction;
    private ModuleFunction moduleFunction;
    private ODEFunctionInterface<Vector3dInterface> windFunction;
    private DecisionMaker moduleController;
    private WindInterface wind;

    @Contract(pure = true)
    public TitanSimRunner(SimulationInterface simulation) {
        this.simulation = simulation;
    }

    @Override
    public ModuleFunction getControls() {
        return this.moduleFunction;
    }

    @Override
    public ODESolverInterface<Vector3dInterface> getSolver() {
        return this.solver;
    }


    @Override
    public void init() {
        this.wind = new WindModel();
        this.wind.init();
        this.windFunction = this.wind.getWindFunction();
        this.moduleController = new DecisionMaker(CONTROLLER);
        this.moduleFunction = new ModuleFunction();
        this.gravityFunction = this.moduleFunction.evaluateAcceleration();
        this.solver = initSolver(this.gravityFunction);
    }

    @Override
    public SimulationInterface simInstance() {
        return simulation;
    }

    @Override
    public synchronized void loop() {
        // draw graphics
        // update module gravityFunction
        StateInterface<Vector3dInterface> currentState = simulation.getSystem().getState();
        System.out.println("###");
        if (currentState.get()[0].getY() < 0.0001) {
            System.out.println("\nTime: " + CURRENT_TIME);
            System.out.println("Position: " + currentState.get()[0]);
            System.out.println("Velocity: " + currentState.get()[1]);
            System.exit(0);
        }

        simulation.getGraphics().start(currentState);
        System.out.println("###");
        moduleFunction.setControls(moduleController.getControls(CURRENT_TIME, currentState));

        System.out.println("###");
        StateInterface<Vector3dInterface> afterGravity = solver.step(gravityFunction, CURRENT_TIME, currentState, MODULE_STEP_SIZE);

        System.out.println("###");
        if (WIND) {
            StateInterface<Vector3dInterface> afterWind = solver.step(windFunction, CURRENT_TIME, afterGravity, MODULE_STEP_SIZE);
            simulation.getSystem().updateState(afterWind);
        } else {
            simulation.getSystem().updateState(afterGravity);
        }
        System.out.println("###");
        CURRENT_TIME += MODULE_STEP_SIZE;

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
