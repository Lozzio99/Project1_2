package group17.Graphics.Assist;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PerformanceWindowTest {

    static PerformanceWindow tab = new PerformanceWindow();

    @Test
    @DisplayName("PrintUsage")
    void PrintUsage() {
        tab.init();
        assertDoesNotThrow(() -> PerformanceWindow.printUsage(Runtime.getRuntime()));
    }

    @Test
    @DisplayName("Log")
    void Log() {
        tab.init();
        assertDoesNotThrow(() -> PerformanceWindow.log(""));
    }

    @Test
    @DisplayName("CalcCPU")
    void CalcCPU() {
        tab.init();
        assertDoesNotThrow(() -> PerformanceWindow.calcCPU(0, 0, 0, 1));
    }

    @Test
    @DisplayName("TestPhysicalMemory")
    void TestPhysicalMemory() {
        tab.init();
        assertDoesNotThrow(PerformanceWindow::testPhysicalMemory);
    }


    @Test
    @DisplayName("FreeRuntimeGarbage")
    void FreeRuntimeGarbage() {
        tab.init();
        assertDoesNotThrow(PerformanceWindow::freeRuntimeGarbage);
    }

    @Test
    @DisplayName("Init")
    void Init() {
        assertDoesNotThrow(tab::init);
    }

    @ParameterizedTest(name = "testing start of {0}")
    @ValueSource(ints = {0, 2, 3})
    void Start(int i) {
        assertDoesNotThrow(() -> tab.start(i));
    }
}