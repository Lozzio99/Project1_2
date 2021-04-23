package group17.phase1.Titan.Simulation.SolarSystemSimulation;

import group17.phase1.Titan.Interfaces.SimulationInterface;
import group17.phase1.Titan.Main;
import group17.phase1.Titan.Simulation.Simulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NumericalSimulationTest {
    static SimulationInterface simulation;

    @BeforeEach
    void BeforeEach() {
        simulation = Main.simulation = Simulation.create(2);//numerical
        simulation.initCPU(0); //min cpu
        simulation.initSystem(1);
    }

    @Test
    @DisplayName("InitSystem")
    void InitSystem() {
        simulation.system().initPlanets();
        assertEquals(
                "[SUN]\n" +
                        "Position :(-6.414002873981876E8,1.0662391756671048E9,7668627.152519185)\n" +
                        "Velocity :(14.20602359289784,-4.972217756927642,0.39949722835220813)\n" +
                        "[MERCURY]\n" +
                        "Position :(1.1168511595902832E11,-5.0648879254037796E10,4.581342916635908E9)\n" +
                        "Velocity :(40377.574457337556,5366.599011749361,3642.0379384768917)\n" +
                        "[VENUS]\n" +
                        "Position :(-1.392088847418328E11,-3.4184204032325882E10,5.912511983168266E9)\n" +
                        "Velocity :(-16547.52522812646,-31528.25057306113,-57.84141298764092)\n" +
                        "[EARTH]\n" +
                        "Position :(-1.3014037158029242E11,-1.0956247890049261E11,5448341.518790834)\n" +
                        "Velocity :(5977.6483155218775,-29317.321859243915,-0.9880535182138449)\n" +
                        "[MARS]\n" +
                        "Position :(3.2671406344896637E10,-2.2092641458205093E11,-5.463103461524778E9)\n" +
                        "Velocity :(24879.70722144798,-1592.759095918946,-643.6212257471491)\n" +
                        "[JUPITER]\n" +
                        "Position :(2.128364486990575E11,-7.450254895226907E11,-1.6712245749004154E9)\n" +
                        "Velocity :(12554.487835395461,3641.202056838741,-295.8485885575881)\n" +
                        "[SATURN]\n" +
                        "Position :(6.556008080320258E11,-1.3469554704233284E12,-2.677479048575987E9)\n" +
                        "Velocity :(8224.344351454796,4055.350428792706,-397.43961025809847)\n" +
                        "[TITAN]\n" +
                        "Position :(5.770721325377887E11,-1.3246941507611096E12,-6.33519802075446E9)\n" +
                        "Velocity :(-20758.659832975296,12194.727456477916,-1707.8583707557748)\n" +
                        "[URANUS]\n" +
                        "Position :(2.383968146185174E12,1.7587903513566375E12,-2.435253977139137E10)\n" +
                        "Velocity :(-4060.526927417209,5186.700560956765,71.83602353746211)\n" +
                        "[NEPTUNE]\n" +
                        "Position :(4.3856447534573364E12,-8.945443293500538E11,-8.264881582956648E10)\n" +
                        "Velocity :(1067.8501493999079,5355.076523319483,-134.3813110191169)\n" +
                        "[MOON]\n" +
                        "Position :(-1.3724645187718985E11,-1.0860849904390659E11,6.33450459074878E8)\n" +
                        "Velocity :(3449.660458618376,-29032.394084163898,222.59600552995255)\n" +
                        "[PROBE]\n" +
                        "Position :(-6.26078863023741E13,8.420645223482458E13,-2.168771261917669E13)\n" +
                        "Velocity :(-2.2644632281992223E7,3.045661406593149E7,-7844221.912008095)",
                simulation.system().toString());
    }

    @ParameterizedTest(name = "Testing body {0}")
    @ValueSource(strings = {"SUN", "MERCURY", "VENUS", "EARTH", "MARS", "JUPITER", "SATURN", "TITAN", "URANUS", "NEPTUNE", "MOON"})
    @DisplayName("GetBody")
    void GetBody(String name) {

        assertNotNull(simulation.getBody(name));
    }

    @Test
    @DisplayName("InitCPU")
    void InitCPU() {

    }

    @Test
    @DisplayName("InitGraphics")
    void InitGraphics() {
    }

    @Test
    @DisplayName("Graphics")
    void Graphics() {

    }

    @Test
    @DisplayName("Assist")
    void Assist() {
    }

    @Test
    @DisplayName("StartGraphics")
    void StartGraphics() {
    }

    @Test
    @DisplayName("Stop")
    void Stop() {
    }

    @Test
    @DisplayName("SolveAndWait")
    void SolveAndWait() {
        simulation.system().solver().step(simulation.system().solver().getFunction(), 0.3, simulation.system().systemState(), 0.3);
    }

    @Test
    @DisplayName("TestToString")
    void TestToString() {
    }
}