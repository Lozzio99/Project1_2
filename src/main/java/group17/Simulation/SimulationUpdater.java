package group17.Simulation;

import group17.Interfaces.ODESolverInterface;
import group17.Interfaces.UpdaterInterface;
import group17.Math.Solvers.RungeKutta4thSolver;

import static group17.Config.STEP_SIZE;
import static group17.Main.simulationInstance;

public class SimulationUpdater implements UpdaterInterface {

    private RocketSchedule schedule;
    private ODESolverInterface solver;
    private Thread updaterThread;

    @Override
    public void init() {
        this.schedule = new RocketSchedule();
        //make all tha planning
        this.solver = new RungeKutta4thSolver();

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
