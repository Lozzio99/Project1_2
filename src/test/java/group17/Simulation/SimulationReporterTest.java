package group17.Simulation;

import group17.Interfaces.ReporterInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationReporterTest {

    ReporterInterface reporter;

    @BeforeEach
    void setUp() {
        reporter = new SimulationReporter();
    }

    @Test
    @DisplayName("Run")
    void Run() {
        reporter.init();
        reporter.start();
    }

    @Test
    @DisplayName("Init")
    void Init() {
        reporter.init();
        assertAll(
                () -> assertNotNull(reporter),
                () -> assertNotNull(reporter.parseException("")),
                () -> assertEquals("EXCEPTION", reporter.parseException("anything"))
        );
    }

    @Test
    @DisplayName("Start")
    void Start() {
        reporter.init();
        assertDoesNotThrow(reporter::start);
    }

    @Test
    @DisplayName("Report")
    void Report() {
        assertThrows(NullPointerException.class, () -> reporter.report());
        reporter.init();
        assertEquals("EXCEPTION", reporter.parseException(null));
        reporter.report();

    }

    @Test
    @DisplayName("TestReport")
    void TestReport() {
        assertThrows(NullPointerException.class, () -> reporter.report("String"));
    }

    @Test
    @DisplayName("TestReport1")
    void TestReport1() {
        assertThrows(NullPointerException.class, () -> reporter.report(new NullPointerException()));
    }

    @Test
    @DisplayName("TestReport2")
    void TestReport2() {
        assertThrows(NullPointerException.class, () -> reporter.report(List.of("String1", "String2")));
    }

    @Test
    @DisplayName("ParseException")
    void ParseException() {
        ReporterInterface reporter = new SimulationReporter();
        reporter.init();
        assertAll(
                () -> assertEquals("\tStopped simulation due to bad initialisation configuration",
                        reporter.parseException("STOP")),
                () -> assertEquals("\tMissing solver configuration or wrong level selected, select in range [1,4]\n ~ will switch to default : EULER",
                        reporter.parseException("UPDATER/DEFAULT_SOLVER/1")),
                () -> assertEquals("EXCEPTION", reporter.parseException("Anything else"))
        );
    }
}