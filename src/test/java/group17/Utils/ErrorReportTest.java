package group17.Utils;

import group17.Graphics.Assist.UserDialogWindow;
import group17.Interfaces.StateInterface;
import group17.Main;
import group17.Math.Lib.Vector3D;
import group17.System.State.SystemState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static group17.Utils.Config.*;
import static java.lang.Double.NaN;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorReportTest {

    static ErrorReport report;
    static StateInterface stateTest;

    @BeforeEach
    void setUp() {
        stateTest = new SystemState(List.of(new Vector3D()), List.of(new Vector3D(NaN, NaN, NaN)));
        ORIGINAL_DATA[0] = new ErrorData(stateTest);
        ErrorReport.monthIndex = 0;
        DEBUG = false;
    }

    @Test
    @DisplayName("Start")
    void Start() {
        Main.userDialog = null;
        DEBUG = false;
        int nThreads = Thread.activeCount();
        report = new ErrorReport(new ErrorData(stateTest));
        report.start();
        assertEquals(nThreads + 1, Thread.activeCount());
    }


    @Test
    @DisplayName("Start")
    void testWrongMonthIndex() {
        ErrorReport.monthIndex = 14;
        ERROR_EVALUATION = true;
        int nThreads = Thread.activeCount();
        report = new ErrorReport(new ErrorData(stateTest));
        report.start();
        assertEquals(nThreads, Thread.activeCount());
    }

    @Test
    @DisplayName("Run")
    void Run() {
        DEBUG = true;

        Main.userDialog = new UserDialogWindow();
        report = new ErrorReport(new ErrorData(stateTest));
        Thread t = new Thread(report);
        t.setUncaughtExceptionHandler(Thread.getDefaultUncaughtExceptionHandler());
        try {
            t.start();
        } catch (Exception e) {
            assertEquals("java.lang.NullPointerException: Cannot invoke " +
                    "\"group17.Interfaces.SimulationInterface.getSystem()\" " +
                    "because \"group17.Main.simulation\" is null", e.getMessage());
        }
    }
}