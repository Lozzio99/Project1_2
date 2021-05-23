package group17.Simulation;

import group17.Interfaces.ODESolverInterface;
import group17.Interfaces.UpdaterInterface;
import group17.Interfaces.Vector3dInterface;
import group17.Math.Solvers.*;
import group17.Simulation.Rocket.RocketSchedule;
import group17.System.GravityFunction;
import group17.Utils.ErrorData;
import group17.Utils.ErrorExportCSV;
import group17.Utils.ErrorReport;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static group17.Graphics.Scenes.Scene.SceneType.SIMULATION_SCENE;
import static group17.Main.simulation;
import static group17.Utils.Config.*;
import static group17.Utils.ErrorReport.testingAccuracy;

/**
 * The type Simulation updater.
 */
public class SimulationUpdater implements UpdaterInterface {

    private RocketSchedule schedule;
    private ODESolverInterface solver;
    private Thread updaterThread;
    private volatile AtomicReference<Writer> fileWriter = new AtomicReference<>();

    @Override
    public void init() {
        //make all the planning
        this.schedule = new RocketSchedule();
        this.schedule.init();
        this.schedule.prepare();


        switch (DEFAULT_SOLVER) {
            case EULER_SOLVER -> this.solver = new EulerSolver(new GravityFunction().getEvaluateCurrentVelocity());
            case RUNGE_KUTTA_SOLVER -> this.solver = new NewTryRungeKutta(new GravityFunction().getEvaluateCurrentVelocity());
            case VERLET_VEL_SOLVER -> this.solver = new VerletVelocitySolver(new GravityFunction().getEvaluateRateOfChange());
            case VERLET_STD_SOLVER -> this.solver = new StandardVerletSolver(new GravityFunction().getEvaluateRateOfChange());
            case MIDPOINT_SOLVER -> this.solver = new MidPointSolver(new GravityFunction().getEvaluateCurrentVelocity());
            case OLD_RUNGE -> this.solver = new OldRungeKutta(new GravityFunction().getEvaluateRateOfChange());
            case LAZY_RUNGE -> this.solver = new LazyRungeKutta(new GravityFunction().getEvaluateCurrentVelocity());
            default -> {
                this.solver = new EulerSolver(new GravityFunction().getEvaluateCurrentVelocity());
                if (REPORT)
                    simulation.getReporter().report(new IllegalStateException("UPDATER/DEFAULT_SOLVER/" + EULER_SOLVER));
            }
        }

        if (ERROR_EVALUATION) {
            this.initWriter();
            new ErrorReport(fileWriter, new ErrorData(simulation.getSystem().systemState())).start();

        }

        if (!LAUNCH_ASSIST) {
            if (ENABLE_GRAPHICS) simulation.getGraphics().changeScene(SIMULATION_SCENE);
            if (REPORT) simulation.getReporter().report("START SIMULATION");
            simulation.setWaiting(false);
        }

    }

    @Override
    public synchronized void start() {
        this.updaterThread = new Thread(Thread.currentThread().getThreadGroup(), this, "Simulation Updater", 6);
        this.updaterThread.setDaemon(true);
        this.updaterThread.setPriority(8);
        this.updaterThread.start();
    }


    @Override
    public void setFileWriter(AtomicReference<Writer> fileWriter) {
        this.fileWriter = fileWriter;
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
            //ErrorData prev = new ErrorData(simulation.getSystem().systemState());
            /*
             * Technically here in systemState.update we could also pass the result of a more complex evaluation
             * like the last state of the solve method (with new stepsize = prevStepsize / size of solution)
             * but i think this would be a problem for the graphics + here we check if bodies are collided maybe
             * better to do that in system (in main thread from executor) and then pass it in here once solved
             */

            simulation.getSystem().systemState().update(this.solver.step(this.solver.getFunction(), CURRENT_TIME, simulation.getSystem().systemState(), STEP_SIZE));
            CURRENT_TIME += STEP_SIZE;
            if (simulation.getSystem().getClock().step(STEP_SIZE) && ERROR_EVALUATION) {
                if (!testingAccuracy) initWriter(); //will make a different output from the testing one
                new ErrorReport(Objects.requireNonNullElseGet(fileWriter, AtomicReference::new), new ErrorData(simulation.getSystem().systemState())).start();
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

    @Override
    public AtomicReference<Writer> initWriter() {
        String path = ErrorReport.makeMonthDirectories(getClass());  //build/resources/main
        File file;
        try {
            switch (FORMAT) {
                case TXT -> {
                    path += "txt";
                    file = new File(path);
                    fileWriter.set(new FileWriter(file));
                }
                case CSV -> {
                    path += "csv";
                    file = new File(path);
                    fileWriter.set(new ErrorExportCSV(file));
                }
                default -> {
                }
            }
        } catch (Exception e) {
            if (REPORT) simulation.getReporter().report(Thread.currentThread(), e);
        }
        return fileWriter;
    }


}
