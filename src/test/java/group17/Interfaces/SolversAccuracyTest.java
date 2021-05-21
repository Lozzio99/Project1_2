package group17.Interfaces;

import group17.Graphics.Assist.ErrorWindow;
import group17.Simulation.Simulation;
import group17.System.GravityFunction;
import group17.Utils.ErrorData;
import group17.Utils.ErrorReport;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static group17.Main.simulation;
import static group17.Utils.Config.*;
import static java.lang.Thread.onSpinWait;
import static java.lang.Thread.sleep;

class SolversAccuracyTest {

    static String[] solvers;
    static File file;
    static volatile AtomicReference<FileWriter> fileWriter;

    static {
        solvers = new String[]{"", "EULER", "NEW RUNGE KUTTA", "VERLET VEL", "VERLET STD", "MIDPOINT", "OLD RUNGE K", "LAZY RUNGE K"};
        DEBUG = false;
        ENABLE_GRAPHICS = false;
        REPORT = false;
        LAUNCH_ASSIST = false;
        CHECK_COLLISIONS = false;
        INSERT_ROCKET = false;
    }


    public SolversAccuracyTest() throws IOException {
        new ErrorWindow();
        //testStepSize(20);
        //testStepSize(60);
        testStepSize(360);
        //testStepSize(86400);

    }

    public static void main(String[] args) throws IOException {
        new SolversAccuracyTest();
    }

    private void testStepSize(double stepSize) throws IOException {
        STEP_SIZE = stepSize;
        file = new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource("STEPSIZE" + ((int) stepSize) + ".txt")).getFile());
        fileWriter = new AtomicReference<>(new FileWriter(file));
        testSolver(fileWriter, EULER_SOLVER);
        testSolver(fileWriter, RUNGE_KUTTA_SOLVER);
        testSolver(fileWriter, VERLET_VEL_SOLVER);
        testSolver(fileWriter, VERLET_STD_SOLVER);
        testSolver(fileWriter, MIDPOINT_SOLVER);
        testSolver(fileWriter, OLD_RUNGE);
        testSolver(fileWriter, LAZY_RUNGE);
        fileWriter.get().write("END OF TEST");
        fileWriter.get().close();
    }

    private synchronized void testSolver(AtomicReference<FileWriter> writer, int solver) {
        try {
            simulation = new Simulation();
            ErrorReport.monthIndex = -1;
            simulation.initSystem();
            DEFAULT_SOLVER = solver;
            writer.get().write("RUNNING TEST ON SOLVER : " + solvers[solver] + "\n");
            GravityFunction.setCurrentTime(0);
            simulation.initUpdater();
            simulation.getUpdater().setFileWriter(writer);
            new ErrorReport(fileWriter, new ErrorData(simulation.getSystem().systemState())).start();
            while (ErrorReport.monthIndex < 4) {
                simulation.startUpdater();
                onSpinWait();
            }
            sleep(3000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}