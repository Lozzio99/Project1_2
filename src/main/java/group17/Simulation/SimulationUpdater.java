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
        this.schedule.init();
        //make all tha planning
        switch (SOLVER) {
            case EULER_SOLVER -> this.solver = new EulerSolver();
            case RUNGE_KUTTA_SOLVER -> this.solver = new RungeKutta4thSolver();
            case VERLET_VEL_SOLVER -> this.solver = new VerletVelocitySolver();
            case VERLET_STD_SOLVER -> this.solver = new StandardVerletSolver();
            default -> {
                this.solver = new EulerSolver();
                if (REPORT)
                    simulationInstance.getReporter().report(new IllegalStateException("UPDATER/SOLVER/" + EULER_SOLVER));
            }
        }

        if (!ENABLE_ASSIST)
            simulationInstance.setWaiting(false);

    }

    public void start() {
        this.updaterThread = new Thread(this, "Simulation Updater");
        this.updaterThread.setDaemon(true);
        this.updaterThread.start();
    }


    @Override
    public synchronized void run() {
        // ROCKET DECISION
        simulationInstance.getSystem().getRocket().setLocalAcceleration(this.schedule.shift(simulationInstance.getSystem()));
        simulationInstance.getSystem().systemState().update(this.solver.step(this.solver.getFunction(), STEP_SIZE, simulationInstance.getSystem().systemState(), STEP_SIZE));
        simulationInstance.getSystem().getClock().step(STEP_SIZE);
    }
}
