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
                "SUN\tPV:\t(-6.806783239281648E8,1.080005533878725E9,6564012.75169017)\n" +
                "\tVV:\t(-14.20511660519414,-4.954714684919104,0.399423759988592)\n" +
                "MERCURY\tPV:\t(6047855.986424127,-6.801800047868888E10,-5.702742359714534E9)\n" +
                "\tVV:\t(38925.85164132107,2978.342227951605,-3327.96413011577)\n" +
                "VENUS\tPV:\t(-9.435345478592035E10,5.35035955103367E10,6.131453014410347E9)\n" +
                "\tVV:\t(-17264.04276675418,-30734.32498568155,574.1783348533565)\n" +
                "EARTH\tPV:\t(-1.471922101663588E11,-2.860995816266412E10,8278183.19359608)\n" +
                "\tVV:\t(5427.193371063864,-29310.56603506259,0.6575428116074852)\n" +
                "MOON\tPV:\t(-1.472343904597218E11,-2.822578361503422E10,1.052790970065631E7)\n" +
                "\tVV:\t(4433.121575882713,-29484.53616031408,88.96594867343843)\n" +
                "MARS\tPV:\t(-3.615638921529161E10,-2.167633037046744E11,-3.687670305939779E9)\n" +
                "\tVV:\t(24815.51959239763,-1816.367993839315,-646.7321577627249)\n" +
                "JUPITER\tPV:\t(1.781303138592156E11,-7.551118436250294E11,-8.532838524470329E8)\n" +
                "\tVV:\t(12558.52546626294,3622.680182938116,-295.8620385189845)\n" +
                "SATURN\tPV:\t(6.328646641500651E11,-1.358172804527507E12,-1.57852013793081E9)\n" +
                "\tVV:\t(8220.8421339415,4052.137353045929,-397.6224693819078)\n" +
                "TITAN\tPV:\t(6.33287311852789E11,-1.357175556995868E12,-2.13463704145366E9)\n" +
                "\tVV:\t(3056.87794615761,6125.612917224867,-952.3587319894634)\n" +
                "URANUS\tPV:\t(2.395195786685187E12,1.744450959214586E12,-2.455116324031639E10)\n" +
                "\tVV:\t(-4059.468609332644,5187.467321685034,71.82516190869795)\n" +
                "NEPTUNE\tPV:\t(4.382692942729203E12,-9.093501655486243E11,-8.227728929479486E10)\n" +
                "\tVV:\t(1051.98951225787,5358.319815712676,-134.4948707739122)\n" +
                "ROCKET\tPV:\t(-1.471922101663588E11,-2.860995816266412E10,8278183.19359608)\n" +
                "\tVV:\t(5427.193405797901,-29310.56622265021,0.6575428158157592)\n" +
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