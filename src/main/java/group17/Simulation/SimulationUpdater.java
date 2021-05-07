package group17.Simulation;

import group17.Interfaces.ODESolverInterface;
import group17.Interfaces.UpdaterInterface;
import group17.Math.Solvers.EulerSolver;
import group17.Math.Solvers.RungeKutta4thSolver;
import group17.Math.Solvers.StandardVerletSolver;
import group17.Math.Solvers.VerletVelocitySolver;

import static group17.Config.*;
import static group17.Main.simulationInstance;

public class SimulationUpdater implements UpdaterInterface {

    private RocketSchedule schedule;
    private ODESolverInterface solver;
    private Thread updaterThread;

    @Override
    public void init() {
        this.schedule = new RocketSchedule();
        //make all tha planning
        switch (SOLVER) {
            case EULER_SOLVER -> this.solver = new EulerSolver();
            case RUNGE_KUTTA_SOLVER -> this.solver = new RungeKutta4thSolver();
            case VERLET_VEL_SOLVER -> this.solver = new VerletVelocitySolver();
            case VERLET_STD_SOLVER -> this.solver = new StandardVerletSolver();
            default -> {
                this.solver = new EulerSolver();
                simulationInstance.getReporter().report(new IllegalStateException("UPDATER/SOLVER/" + EULER_SOLVER));
            }
        }
    }

    public void start() {
        this.updaterThread = new Thread(this, "Simulation Updater");
        this.updaterThread.setDaemon(true);
        this.updaterThread.start();
    }


    @Override
    public void run() {
        // ROCKET DECISION
        this.solver.step(this.solver.getFunction(), STEP_SIZE, simulationInstance.getSystem().systemState(), STEP_SIZE);
    }
}
