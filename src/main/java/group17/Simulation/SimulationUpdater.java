package group17.Simulation;

import group17.Interfaces.ODESolverInterface;
import group17.Interfaces.UpdaterInterface;
import group17.Interfaces.Vector3dInterface;
import group17.Math.Solvers.EulerSolver;
import group17.Math.Solvers.RungeKutta4thSolver;
import group17.Math.Solvers.StandardVerletSolver;
import group17.Math.Solvers.VerletVelocitySolver;
import group17.System.ErrorData;
import group17.System.ErrorReport;

import static group17.Config.*;
import static group17.Graphics.Scenes.Scene.SceneType.SIMULATION_SCENE;
import static group17.Main.simulation;

public class SimulationUpdater implements UpdaterInterface {

    private RocketSchedule schedule;
    private ODESolverInterface solver;
    private Thread updaterThread;

    @Override
    public void init() {
        //make all the planning
        this.schedule = new RocketSchedule();
        this.schedule.init();
        this.schedule.prepare();

        switch (SOLVER) {
            case EULER_SOLVER -> this.solver = new EulerSolver();
            case RUNGE_KUTTA_SOLVER -> this.solver = new RungeKutta4thSolver();
            case VERLET_VEL_SOLVER -> this.solver = new VerletVelocitySolver();
            case VERLET_STD_SOLVER -> this.solver = new StandardVerletSolver();
            default -> {
                this.solver = new EulerSolver();
                if (REPORT)
                    simulation.getReporter().report(new IllegalStateException("UPDATER/SOLVER/" + EULER_SOLVER));
            }
        }

        if (!LAUNCH_ASSIST) {
            if (ENABLE_GRAPHICS) simulation.getGraphics().changeScene(SIMULATION_SCENE);
            if (REPORT) simulation.getReporter().report("START SIMULATION");
            simulation.setWaiting(false);
        }

    }

    public void start() {
        this.updaterThread = new Thread(Thread.currentThread().getThreadGroup(), this, "Simulation Updater", 6);
        this.updaterThread.setDaemon(true);
        this.updaterThread.setPriority(8);
        this.updaterThread.start();
    }


    @Override
    public synchronized void run() {
        // ROCKET DECISION
        try {
            if (INSERT_ROCKET) {
                Vector3dInterface decision = this.schedule.shift(simulation.getSystem());
                if (!decision.isZero() && REPORT) {
                    simulation.getReporter().report("DECISION -> " + decision);
                    simulation.getSystem().getRocket().evaluateLoss(decision,
                            simulation.getSystem().systemState().getRateOfChange().getVelocities().get(11));
                }
            }
            ErrorData prev = new ErrorData(simulation.getSystem().systemState());
            /*
             * Technically here in systemState.update we could also pass the result of a more complex evaluation
             * like the last state of the solve method (with new stepsize = prevStepsize / size of solution)
             * but i think this would be a problem for the graphics + here we check if bodies are collided maybe
             * better to do that in system (in main thread from executor) and then pass it in here once solved
             */
            simulation.getSystem().systemState().update(this.solver.step(this.solver.getFunction(), STEP_SIZE, simulation.getSystem().systemState(), STEP_SIZE));
            if (simulation.getSystem().getClock().step(STEP_SIZE) && ERROR_EVALUATION) {
                new ErrorReport(new ErrorData(simulation.getSystem().systemState()));
            }
        } catch (Exception e) {
            if (REPORT) simulation.getReporter().report(Thread.currentThread(), e);
        }
    }

    @Override
    public ODESolverInterface getSolver() {
        return solver;
    }

    @Override
    public RocketSchedule getSchedule() {
        return schedule;
    }
}
