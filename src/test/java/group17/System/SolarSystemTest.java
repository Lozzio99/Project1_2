package group17.System;

import group17.Interfaces.SystemInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        assertEquals("(-7485730.186,6281273.438,-8172135.798)", solarSystem.getRocket().getVectorLocation().toString());
    }

    @Test
    @DisplayName("GetClock")
    void GetClock() {
        assertNull(solarSystem.getClock());
        solarSystem.initClock();
        assertNotNull(solarSystem.getClock());
    }

    @Test
    @DisplayName("InitClock")
    void InitClock() {
        solarSystem.initClock();
        assertNotNull(solarSystem.getClock());
        assertNotEquals("null", solarSystem.getClock().toString());
        assertEquals("Clock { [ 00 : 00 : 00 ]     01 / 04 / 2020  }", solarSystem.getClock().toString());
    }
}