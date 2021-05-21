package group17.Utils;

import group17.Graphics.Assist.UserDialogWindow;
import group17.Interfaces.StateInterface;
import group17.Math.Lib.Vector3D;
import group17.System.State.SystemState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static group17.Main.userDialog;
import static group17.Utils.Config.*;
import static java.lang.Double.NaN;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorReportTest {

    static ErrorReport report;
    static StateInterface stateTest;

    @BeforeAll
    static void setUp() {
        stateTest = new SystemState(List.of(new Vector3D()), List.of(new Vector3D(NaN, NaN, NaN)));
        ORIGINAL_DATA[0] = new ErrorData(stateTest);
        ErrorReport.monthIndex.getAndSet(0);
        report = new ErrorReport(new ErrorData(stateTest));
    }

    @Test
    @DisplayName("Start")
    void Start() {
        DEBUG = false;
        userDialog = new UserDialogWindow();
        int nThreads = Thread.activeCount();
        try {
            report.start();
        } catch (Exception e) {
        }
        assertEquals(nThreads + 1, Thread.activeCount());
    }


    @Test
    @DisplayName("Start")
    void testWrongMonthIndex() {
        ErrorReport.monthIndex.getAndSet(14);
        ERROR_EVALUATION = true;
        int nThreads = Thread.activeCount();
        report.start();
        assertEquals(nThreads, Thread.activeCount());
    }

    @Test
    @DisplayName("Run")
    void Run() {
        DEBUG = false;
        userDialog = null;
        Thread t = new Thread(report);
        t.setUncaughtExceptionHandler(Thread.getDefaultUncaughtExceptionHandler());
        try {
            t.start();
            Thread.sleep(500);
            t.join();
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }
    }
}