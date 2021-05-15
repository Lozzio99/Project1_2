package group17.System;

import group17.Interfaces.SystemInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static group17.Config.ERROR_EVALUATION;
import static group17.Config.LAUNCH_DATE;
import static org.junit.jupiter.api.Assertions.*;

class SolarSystemTest {

    SystemInterface solarSystem;

    @BeforeEach
    void setUp() {
        solarSystem = new SolarSystem();
    }

    @Test
    @DisplayName("GetCelestialBodies")
    void GetCelestialBodies() {
        assertEquals("[]", solarSystem.getCelestialBodies().toString());
    }

    @Test
    @DisplayName("SystemState")
    void SystemState() {
        assertNull(solarSystem.systemState());
    }

    @Test
    @DisplayName("GetRocket")
    void GetRocket() {
        assertNull(solarSystem.getRocket());
    }

    @Test
    @DisplayName("InitPlanets")
    void InitPlanets() {
        solarSystem.initPlanets();
        assertEquals("[SUN, MERCURY, VENUS, EARTH, MARS, JUPITER, SATURN, TITAN, URANUS, NEPTUNE, MOON]", solarSystem.getCelestialBodies().toString());
    }

    @Test
    @DisplayName("InitRocket")
    void InitRocket() {
        solarSystem.initRocket();
        assertNull(solarSystem.getRocket().getVectorLocation());
        solarSystem.getRocket().initProperties();
        assertNotNull(solarSystem.getRocket().getVectorLocation());
    }

    @Test
    @DisplayName("GetClock")
    void GetClock() {
        ERROR_EVALUATION = false;
        assertNull(solarSystem.getClock());
        solarSystem.initClock();
        assertNotNull(solarSystem.getClock());
        assertEquals(LAUNCH_DATE, solarSystem.getClock());
    }

    @Test
    @DisplayName("InitClock")
    void InitClock() {
        ERROR_EVALUATION = false;
        solarSystem.initClock();
        assertNotNull(solarSystem.getClock());
        assertNotEquals("null", solarSystem.getClock().toString());
        assertEquals("Clock { [ 00 : 00 : 00 ]     01 / 04 / 2020  }", solarSystem.getClock().toString());
    }
}