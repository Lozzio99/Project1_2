package phase3.Simulation.Runner;

import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Forces.GravityFunction;
import phase3.Math.Forces.ModuleFunction;
import phase3.Math.Solvers.ODESolverInterface;
import phase3.Rocket.Controllers.RocketSchedule;
import phase3.Rocket.RocketSimulatorInterface;
import phase3.Simulation.Errors.ErrorData;
import phase3.Simulation.Errors.ErrorReport;
import phase3.Simulation.SimulationInterface;
import phase3.System.State.StateInterface;

import static phase3.Config.*;

public class SolarSystemRunner implements RunnerInterface {
    private final SimulationInterface simulation;
    private ODESolverInterface<Vector3dInterface> solver;
    private final RocketSchedule schedule;
    private GravityFunction gravityFunction;

    public SolarSystemRunner(SimulationInterface simulation) {
        this.simulation = simulation;
        this.schedule = new RocketSchedule();
    }

    @Override
    public SimulationInterface simInstance() {
        return simulation;
    }

    @Override
    public ModuleFunction getControls() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ODESolverInterface<Vector3dInterface> getSolver() {
        return this.solver;
    }

    @Override
    public void init() {
        double[] masses = new double[simulation.getSystem().getCelestialBodies().size()];
        for (int i = 0; i < masses.length; i++) {
            masses[i] = simulation.getSystem().getCelestialBodies().get(i).getMASS();
        }
        this.gravityFunction = new GravityFunction(masses);
        this.schedule.init();
        this.solver = initSolver(this.gravityFunction.evaluateAcceleration());
    }

    @Override
    public synchronized void loop() {
        StateInterface<Vector3dInterface> currentState = simulation.getSystem().getState();
        simulation.getGraphics().start(currentState);

        Vector3dInterface rocketDecision = this.schedule.shift();
        if (!rocketDecision.isZero()) {
            System.out.println("Rocket decision :");
            System.out.println(SIMULATION_CLOCK + " -> " + rocketDecision);
            ((RocketSimulatorInterface) simulation.getSystem().getCelestialBodies().get(11))
                    .evaluateLoss(rocketDecision, simulation.getSystem().getState().get()[23]);
            simulation.getSystem().getState().get()[23] = rocketDecision;
            this.gravityFunction.getMASSES()[11] = simulation.getSystem().getCelestialBodies().get(11).getMASS();
        }
        simulation.getSystem().updateState(
                solver.step(solver.getFunction(), currentTime[0], currentState, SS_STEP_SIZE));


        //will make anyway clock step
        if (simulation.getSystem().getClock().step(SS_STEP_SIZE)) {
            if (ERROR_EVALUATION) {
                System.out.println(simulation.getSystem().getClock());
                new ErrorReport(new ErrorData(simulation.getSystem().getState()), ERROR_MONTH_INDEX).start();
            }
        }


    }



}
