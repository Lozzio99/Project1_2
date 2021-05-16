package group17.Simulation;

import group17.Graphics.Assist.UserDialogWindow;
import group17.Interfaces.SimulationInterface;
import group17.Main;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static group17.Config.ERROR_EVALUATION;
import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    static SimulationInterface simulationTest = new Simulation();

    static {
        ERROR_EVALUATION = false;
    }

    @Test
    @DisplayName("InitUpdater")
    void InitUpdater() {
        simulationTest.initUpdater();

        simulationTest.initSystem();
        simulationTest.getSystem().initRocket();
        simulationTest.getSystem().getCelestialBodies().get(11).getVectorLocation();
        simulationTest.getSystem().systemState().getPositions().get(11).getX();
        simulationTest.getSystem().systemState().getRateOfChange().getVelocities().get(11).getX();

        assertNotNull(simulationTest.getUpdater());
        assertNotEquals("null", simulationTest.getUpdater().toString());
    }

    @Test
    @DisplayName("InitGraphics")
    void InitGraphics() {
        simulationTest.initGraphics();
        assertNotNull(simulationTest.getGraphics());
        assertNotEquals("null", simulationTest.getGraphics().toString());
    }

    @Test
    @DisplayName("InitAssist")
    void InitAssist() {
        simulationTest.setAssist(new UserDialogWindow());
        assertThrows(NullPointerException.class, () -> simulationTest.initAssist());
        assertNotNull(simulationTest.getAssist());
        simulationTest.initSystem();
        Main.simulation = simulationTest;
        simulationTest.initSystem();
        assertDoesNotThrow(() -> simulationTest.initAssist());
        assertNotNull(simulationTest.getAssist());
    }

    @Test
    @DisplayName("InitSystem")
    void InitSystem() {
        simulationTest.initSystem();
        assertNotNull(simulationTest.getSystem());
        assertNotEquals("null", simulationTest.getSystem().toString());
        assertEquals("SOLAR SYSTEM\tClock { [ 00 : 00 : 00 ]     01 / 04 / 2020  },\n" +
                "\tSTATE\n" +
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "PV:\t(-6.806783239281648E8,1.080005533878725E9,6564012.75169017)\n" +
                "PV:\t(6047855.986424127,-6.801800047868888E10,-5.702742359714534E9)\n" +
                "PV:\t(-9.435345478592035E10,5.35035955103367E10,6.131453014410347E9)\n" +
                "PV:\t(-1.471922101663588E11,-2.860995816266412E10,8278183.19359608)\n" +
                "PV:\t(-1.472343904597218E11,-2.822578361503422E10,1.052790970065631E7)\n" +
                "PV:\t(-3.615638921529161E10,-2.167633037046744E11,-3.687670305939779E9)\n" +
                "PV:\t(1.781303138592156E11,-7.551118436250294E11,-8.532838524470329E8)\n" +
                "PV:\t(6.328646641500651E11,-1.358172804527507E12,-1.57852013793081E9)\n" +
                "PV:\t(6.33287311852789E11,-1.357175556995868E12,-2.13463704145366E9)\n" +
                "PV:\t(2.395195786685187E12,1.744450959214586E12,-2.455116324031639E10)\n" +
                "PV:\t(4.382692942729203E12,-9.093501655486243E11,-8.227728929479486E10)\n" +
                "PV:\t(-1.471922101663588E11,-2.860995816266412E10,8278183.19359608)\n" +
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "\tROCKET\n" +
                "Dry Mass: 98000.0\n" +
                "Fuel Mass: 20000.0\n", simulationTest.getSystem().toString());
    }

    @Test
    @DisplayName("InitReporter")
    void InitReporter() {
        simulationTest.initReporter();
        assertNotNull(simulationTest.getReporter());
        assertNotEquals("null", simulationTest.getReporter().toString());
    }

}