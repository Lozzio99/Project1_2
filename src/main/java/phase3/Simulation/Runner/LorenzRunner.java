package phase3.Simulation.Runner;

import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Forces.LorenzGravityODE1;
import phase3.Math.Forces.ModuleFunction;
import phase3.Math.Functions.ODEFunctionInterface;
import phase3.Math.Solvers.ODESolverInterface;
import phase3.Simulation.SimulationInterface;

import static phase3.Config.LORENZ_STEP_SIZE;

public class LorenzRunner implements RunnerInterface {

    private final SimulationInterface simulation;
    private final ODEFunctionInterface<Vector3dInterface> function;
    private ODESolverInterface<Vector3dInterface> solver;

    public LorenzRunner(SimulationInterface simulation) {
        this.simulation = simulation;
        this.function = new LorenzGravityODE1().getVelocityFunction();
        step_size[0] = LORENZ_STEP_SIZE;
    }

    @Override
    public ModuleFunction getControls() {
        return null;
    }

    @Override
    public ODESolverInterface<Vector3dInterface> getSolver() {
        return this.solver;
    }

    @Override
    public void init() {
        this.solver = initSolver(this.function);
    }

    @Override
    public SimulationInterface simInstance() {
        return simulation;
    }
}
