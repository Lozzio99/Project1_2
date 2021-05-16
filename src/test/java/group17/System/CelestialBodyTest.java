package group17.System;

import group17.Math.Utils.Vector3D;
import group17.System.Bodies.CelestialBody;
import group17.System.Bodies.Particle;
import group17.System.Bodies.Star;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class CelestialBodyTest {

    CelestialBody planet;

    @Test
    @DisplayName("SetMASS")
    void SetMASS() {
        assertNotNull(planet);
        planet.setMASS(100);
        assertEquals(100, planet.getMASS());
    }

    @Test
    @DisplayName("SetRADIUS")
    void SetRADIUS() {
        assertNotNull(planet);
        planet.setRADIUS(100);
        assertEquals(100, planet.getRADIUS());
    }

    @Test
    @DisplayName("SetColour")
    void SetColour() {
        assertNotNull(planet);
        planet.setColour(new Color(0, 0, 0, 0));
        assertEquals(0, planet.getColour().getRed());
        assertEquals(0, planet.getColour().getBlue());
        assertEquals(0, planet.getColour().getGreen());
        assertEquals(0, planet.getColour().getAlpha());
    }

    @Test
    @DisplayName("GetMASS")
    void GetMASS() {
        assertNotNull(planet);
        planet.setMASS(1e10);
        assertEquals(1e10, planet.getMASS());
    }

    @Test
    @DisplayName("GetRADIUS")
    void GetRADIUS() {
        assertNotNull(planet);
        assertEquals(0, planet.getRADIUS());
    }

    @Test
    @DisplayName("GetDensity")
    void GetDensity() {
        assertNotNull(planet);
        assertEquals(0, planet.getRADIUS());
    }

    @Test
    @DisplayName("GetColour")
    void GetColour() {
        assertNotNull(planet);
        assertNull(planet.getColour());
    }

    @Test
    @DisplayName("TestToString")
    void TestToString() {
        assertNotNull(planet);
        planet = new Star();
        assertEquals("SUN", planet.toString());
    }

    @Test
    @DisplayName("InitProperties")
    void InitProperties() {
        assertNotNull(planet);
        planet = new Star();
        planet.initProperties();
        assertEquals("SUN", planet.toString());
        assertEquals(6.96342e7, planet.getRADIUS());
        assertEquals(1.988500e+30, planet.getMASS());
        assertEquals(Color.yellow, planet.getColour());
        assertEquals(-6.806783239281648e+08, planet.getVectorLocation().getX());
        assertEquals(1.080005533878725e+09, planet.getVectorLocation().getY());
        assertEquals(6.564012751690170e+06, planet.getVectorLocation().getZ());
        assertEquals(-14.20511660519414, planet.getVectorVelocity().getX());
        assertEquals(-4.954714684919104, planet.getVectorVelocity().getY());
        assertEquals(0.399423759988592, planet.getVectorVelocity().getZ());
    }

    @Test
    @DisplayName("SetVectorVelocity")
    void SetVectorVelocity() {
        planet.setVectorVelocity(new Vector3D(0, 0, 0));
        assertEquals(0, planet.getVectorVelocity().getX());
        assertEquals(0, planet.getVectorVelocity().getY());
        assertEquals(0, planet.getVectorVelocity().getZ());

    }

    @Test
    @DisplayName("SetVectorLocation")
    void SetVectorLocation() {
        planet.setVectorVelocity(new Vector3D(0, 0, 0));
        assertEquals(0, planet.getVectorVelocity().getX());
        assertEquals(0, planet.getVectorVelocity().getY());
        assertEquals(0, planet.getVectorVelocity().getZ());
    }

    @Test
    @DisplayName("GetVectorLocation")
    void GetVectorLocation() {
        CelestialBody c = new Star();
        assertNull(c.getVectorLocation());
        c.setVectorLocation(new Vector3D(0, 0, 0));
        assertEquals(0, c.getVectorLocation().getX());
        assertEquals(0, c.getVectorLocation().getY());
        assertEquals(0, c.getVectorLocation().getZ());
        planet.initProperties();
        assertNotEquals(c.getVectorLocation(), planet.getVectorLocation());
    }

    @Test
    @DisplayName("GetVectorVelocity")
    void GetVectorVelocity() {
        planet.initProperties();
        assertNotEquals(planet.getVectorLocation(), planet.getVectorVelocity());
        planet.setVectorVelocity(new Vector3D(0, 0, 0));
        assertEquals(0, planet.getVectorVelocity().getX());
        assertEquals(0, planet.getVectorVelocity().getY());
        assertEquals(0, planet.getVectorVelocity().getZ());
    }

    @BeforeEach
    void setUp() {
        planet = new Particle(0);
    }
}