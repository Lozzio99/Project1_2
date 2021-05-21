package group17;

import group17.Graphics.Assist.ErrorWindow;
import group17.Simulation.Simulation;
import group17.System.GravityFunction;
import group17.Utils.ErrorData;
import group17.Utils.ErrorReport;
import org.junit.jupiter.api.parallel.Isolated;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static group17.Main.simulation;
import static group17.Utils.Config.*;
import static java.lang.Thread.onSpinWait;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Isolated
class SolversAccuracyTest {

    static String[] solvers;
    static File file;
    static volatile AtomicReference<FileWriter> fileWriter;
    static int MONTH_REPORT_STOP;

    static {
        solvers = new String[]{"", "EULER", "NEW RUNGE KUTTA", "VERLET VEL", "VERLET STD", "MIDPOINT", "OLD RUNGE K", "LAZY RUNGE K"};
        DEBUG = false;
        ENABLE_GRAPHICS = false;
        REPORT = false;
        LAUNCH_ASSIST = false;
        CHECK_COLLISIONS = false;
        INSERT_ROCKET = false;
        ERROR_EVALUATION = true;
        new ErrorWindow();
        MONTH_REPORT_STOP = 3;
    }


    @ParameterizedTest(name = "testing step size {0}")
    //@ValueSource(ints = {20,60,360,86400})
    @ValueSource(ints = {86400})
    public void testStepSizes(int step) {
        assumeTrue(simulation == null);
        try {
            testStepSize(step);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void testStepSize(int stepSize) throws IOException {
        STEP_SIZE = stepSize; //convert to double

        file = new File(Objects.requireNonNull(
                this.getClass().getClassLoader()   // if working in intellij this will be updated in
                        .getResource("STEPSIZE" + ((int) STEP_SIZE) + ".txt")).getFile());  //build/resources/test

        fileWriter = new AtomicReference<>(new FileWriter(file));
        testSolver(fileWriter, EULER_SOLVER);
        testSolver(fileWriter, RUNGE_KUTTA_SOLVER);
        testSolver(fileWriter, VERLET_VEL_SOLVER);
        testSolver(fileWriter, VERLET_STD_SOLVER);
        testSolver(fileWriter, MIDPOINT_SOLVER);
        testSolver(fileWriter, OLD_RUNGE);
        //testSolver(fileWriter, LAZY_RUNGE);
        fileWriter.get().write("END OF TEST");
        fileWriter.get().close();
    }

    private synchronized void testSolver(AtomicReference<FileWriter> writer, int solver) {
        try {
            simulation = null;
            simulation = new Simulation();
            ErrorReport.monthIndex.set(-1);
            DEFAULT_SOLVER = solver;
            writer.get().write("RUNNING TEST ON SOLVER : " + solvers[solver] + "\n");
            GravityFunction.setCurrentTime(0);
            simulation.initSystem();
            simulation.initUpdater();
            simulation.getUpdater().setFileWriter(writer);
            assumeTrue(ErrorReport.monthIndex.get() == 0);
            new ErrorReport(fileWriter, new ErrorData(simulation.getSystem().systemState())).start();
            while (ErrorReport.monthIndex.get() < MONTH_REPORT_STOP) {
                simulation.startUpdater();
                onSpinWait();
            }
            sleep(3000); //give the time to the file writer to successfully close the stream
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            simulation = null;
        }
    }
}