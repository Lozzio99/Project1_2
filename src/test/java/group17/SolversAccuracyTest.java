package group17;

import group17.Graphics.Assist.ErrorWindow;
import group17.Math.Lib.GravityFunction;
import group17.Simulation.Simulation;
import group17.Utils.ErrorData;
import group17.Utils.ErrorExportCSV;
import group17.Utils.ErrorReport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.parallel.Isolated;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import static group17.Main.simulation;
import static group17.Utils.Config.*;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Isolated   //to be run isolated
@Disabled("Run only when we have new data or new solvers to check")
class SolversAccuracyTest {

    static int MONTH_REPORT_STOP = 13;

    static {
        DEBUG = false;  //don't run this with debug will go out of heap size
        //gradle build file is set to use a maximum of 512mb in the heap
        ENABLE_GRAPHICS = false;
        REPORT = false;
        LAUNCH_ASSIST = false;
        CHECK_COLLISIONS = false;
        INSERT_ROCKET = false;
        ERROR_EVALUATION = true;
        new ErrorWindow();
    }

    final AtomicReference<Writer> fileWriter = new AtomicReference<>();
    File file;

    @BeforeEach
    void opening() {
        Runtime.getRuntime().gc();
        simulation = null;
    }

    @AfterEach
    void closing() {
        try {
            Thread.sleep(1000);
            simulation = null;
            Runtime.getRuntime().gc();
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @ParameterizedTest(name = "testing step size {0}")
    //@ValueSource(ints = {60, 360, 86400})    //stepsize 60 is pretty tough
    @ValueSource(ints = {86400})
    void testAndOutputToTXT(int stepSize) {
        STEP_SIZE = stepSize; //convert to double
        testSolversAndOutputFile(EULER_SOLVER, "ErrorTestLog.txt");
        testSolversAndOutputFile(RUNGE_KUTTA_SOLVER, "ErrorTestLog.txt");
        testSolversAndOutputFile(VERLET_VEL_SOLVER, "ErrorTestLog.txt");
        testSolversAndOutputFile(VERLET_STD_SOLVER, "ErrorTestLog.txt");
        testSolversAndOutputFile(MIDPOINT_SOLVER, "ErrorTestLog.txt");
        testSolversAndOutputFile(OLD_RUNGE, "ErrorTestLog.txt");
    }

    @ParameterizedTest(name = "testing step size {0}")
    //@ValueSource(ints = {60, 360, 86400})
    @ValueSource(ints = {86400})
    void testAndOutputToCSV(int stepSize) {
        STEP_SIZE = stepSize; //convert to double
        testSolversAndOutputFile(EULER_SOLVER, "ErrorTestLog.csv");
        testSolversAndOutputFile(RUNGE_KUTTA_SOLVER, "ErrorTestLog.csv");
        testSolversAndOutputFile(VERLET_VEL_SOLVER, "ErrorTestLog.csv");
        testSolversAndOutputFile(VERLET_STD_SOLVER, "ErrorTestLog.csv");
        testSolversAndOutputFile(MIDPOINT_SOLVER, "ErrorTestLog.csv");
        testSolversAndOutputFile(OLD_RUNGE, "ErrorTestLog.csv");
    }

    private synchronized void testSolversAndOutputFile(int solver, String appendix) {
        try {
            simulation = new Simulation();
            ErrorReport.setMonthIndex(-1);
            DEFAULT_SOLVER = solver;
            GravityFunction.setCurrentTime(0);
            simulation.initSystem();
            ERROR_EVALUATION = false;
            simulation.initUpdater();
            ERROR_EVALUATION = true;  //avoid creating another fileWriter
            setUpWriter(appendix);
            new ErrorReport(fileWriter, new ErrorData(simulation.getSystem().systemState())).start();
            simulation.getUpdater().setFileWriter(fileWriter);
            ErrorReport.testingAccuracy = true;
            assertEquals(0, ErrorReport.monthIndex());
            CompletableFuture<?> future;
            while (ErrorReport.monthIndex() < MONTH_REPORT_STOP) {
                //it's hard to do this concurrently, but setting month stop
                // to 13 (the maximum data provided)
                // makes sure this won't exceed this number
                future = CompletableFuture.runAsync(simulation::startUpdater);
                future.get(2000, TimeUnit.MILLISECONDS);
            }
            assertEquals(ErrorReport.monthIndex(), MONTH_REPORT_STOP);
            sleep(6000); //give the time to the file writer to successfully close the stream
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            simulation = null;
        }
    }

    private void setUpWriter(String appendix) {
        file = new File(this.extractDir() + "\\" + appendix);  //   ..src/test/resources/ErrorData/..
        if (DEBUG) System.out.println(file.getPath());
        try {
            switch (appendix) {
                case "ErrorTestLog.txt" -> fileWriter.set(new FileWriter(file));
                case "ErrorTestLog.csv" -> fileWriter.set(new ErrorExportCSV(file));
                default -> {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String extractDir() {
        String path = ("src\\test\\resources\\ErrorData");
        path += "\\" + ErrorReport.solvers[DEFAULT_SOLVER] + "\\" + ((int) STEP_SIZE);
        File dir = new File(path);
        dir.mkdirs();
        return path;
    }


}