package group17.phase1.Titan.Interfaces;

import group17.phase1.Titan.Config;
import group17.phase1.Titan.Physics.Bodies.*;
import group17.phase1.Titan.System.SolarSystem.SolarSystem;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SystemInterfaceTest {

    static SystemInterface system;


    @BeforeEach
    void beforeAll() {
        system = new SolarSystem();
        System.out.println(system);
    }


    @Test
    @DisplayName("GetCelestialBodies")
    void GetCelestialBodies() {
        system.initPlanets();
        assertEquals("[SUN, MERCURY, VENUS, EARTH, MARS, JUPITER, SATURN, TITAN, URANUS, NEPTUNE, MOON]", system.getCelestialBodies().toString());
    }

    @Test
    @DisplayName("InitPlanets")
    void InitPlanets() {
        system.initPlanets();
        for (CelestialBody c : system.getCelestialBodies()) {
            if (c instanceof Planet) {
                CelestialBody test;
                test = new Planet(Planet.PlanetsEnum.valueOf(c.toString()));
                test.initProperties();
                assertAll(
                        () -> assertEquals(test.getVectorLocation().toString(), c.getVectorLocation().toString()),
                        () -> assertEquals(test.getVectorVelocity().toString(), c.getVectorVelocity().toString()),
                        () -> assertEquals(test.getColour(), c.getColour()),
                        () -> assertEquals(test.toString(), c.toString()),
                        () -> assertEquals(test.getMASS(), c.getMASS())
                );
            } else if (c instanceof Satellite) {
                CelestialBody finalTest = new Satellite(Satellite.SatellitesEnum.valueOf(c.toString()));
                finalTest.initProperties();
                assertAll(
                        () -> assertEquals(finalTest.getVectorLocation().toString(), c.getVectorLocation().toString()),
                        () -> assertEquals(finalTest.getVectorVelocity().toString(), c.getVectorVelocity().toString()),
                        () -> assertEquals(finalTest.getColour(), c.getColour()),
                        () -> assertEquals(finalTest.toString(), c.toString()),
                        () -> assertEquals(finalTest.getMASS(), c.getMASS())
                );
            } else {
                CelestialBody test;
                test = new Star();
                test.initProperties();
                assertAll(
                        () -> assertEquals(test.getVectorLocation().toString(), c.getVectorLocation().toString()),
                        () -> assertEquals(test.getVectorVelocity().toString(), c.getVectorVelocity().toString()),
                        () -> assertEquals(test.getColour(), c.getColour()),
                        () -> assertEquals(test.toString(), c.toString()),
                        () -> assertEquals(test.getMASS(), c.getMASS())
                );
            }
        }
    }

    @Test
    @DisplayName("InitProbe")
    void InitProbe() {
        Config.INSERT_PROBE = true;
        Assumptions.assumeTrue(Config.INSERT_PROBE);
        system.initProbe();
        CelestialBody test = new ProbeSimulator();
        test.initProperties();
        assertAll(
                () -> assertEquals(test.getVectorLocation().toString(), system.getCelestialBodies().get(0).getVectorLocation().toString()),
                () -> assertEquals(test.getVectorVelocity().toString(), system.getCelestialBodies().get(0).getVectorVelocity().toString()),
                () -> assertEquals(test.getColour(), system.getCelestialBodies().get(0).getColour()),
                () -> assertEquals(test.toString(), system.getCelestialBodies().get(0).toString()),
                () -> assertEquals(test.getMASS(), system.getCelestialBodies().get(0).getMASS())
        );
    }

    @Test
    @DisplayName("InitClock")
    void InitClock() {
    }

    @Test
    @DisplayName("InitDetector")
    void InitDetector() {
    }

    @Test
    @DisplayName("InitReport")
    void InitReport() {
    }

    @Test
    @DisplayName("Reset")
    void Reset() {
    }

    @Test
    @DisplayName("Start")
    void Start() {
    }

    @Test
    @DisplayName("Stop")
    void Stop() {
    }

    @Test
    @DisplayName("TestToString")
    void TestToString() {
    }

    @Test
    @DisplayName("SystemState")
    void SystemState() {
    }

    @Test
    @DisplayName("SystemRateOfChange")
    void SystemRateOfChange() {
    }

    @Test
    @DisplayName("Solver")
    void Solver() {
    }

    @Test
    @DisplayName("GetClock")
    void GetClock() {
    }

    @Test
    @DisplayName("Step")
    void Step() {
    }


}