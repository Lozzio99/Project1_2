package group17.System;

import group17.System.Bodies.CelestialBody;
import group17.System.Bodies.Particle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static group17.Config.REPORT;
import static org.junit.jupiter.api.Assertions.*;

class CollisionDetectorTest {

    @Test
    @DisplayName("CheckCollided")
    void CheckCollided() {
        REPORT = false;
        CelestialBody a, b;
        a = new Particle(0);
        b = new Particle(1);
        a.setRADIUS(10);
        a.setMASS(2);
        b.setRADIUS(10);
        b.setMASS(3);
        CollisionDetector.checkCollided(a, b, 19);
        assertAll(
                () -> assertTrue(a.isCollided()),
                () -> assertFalse(b.isCollided()),
                () -> assertEquals(5, b.getMASS())
        );
    }

    @Test
    @DisplayName("CheckCollisions")
    void CheckCollisions() {

        REPORT = false;
        CelestialBody a, b;
        a = new Particle(0);
        b = new Particle(1);
        a.setRADIUS(10);
        a.setMASS(2);
        b.setRADIUS(10);
        b.setMASS(3);
        CollisionDetector.checkCollided(a, b, 21);
        assertAll(
                () -> assertFalse(a.isCollided()),
                () -> assertFalse(b.isCollided()),
                () -> assertEquals(2, a.getMASS()),
                () -> assertEquals(3, b.getMASS())
        );
    }
}