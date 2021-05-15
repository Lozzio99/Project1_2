package group17.System;

import group17.Math.Utils.Vector3D;
import group17.System.Bodies.CelestialBody;
import group17.System.Bodies.Particle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static group17.Config.CHECK_COLLISIONS;
import static group17.Config.REPORT;
import static org.junit.jupiter.api.Assertions.*;

class CollisionDetectorTest {


    @BeforeAll
    static void init() {
        REPORT = false;
        CHECK_COLLISIONS = true;
    }


    static List<CelestialBody> createBodies() {
        CelestialBody a, b;
        a = new Particle(0) {
            @Override
            public void initProperties() {
                this.setRADIUS(10);
                this.setMASS(2);
            }
        };
        b = new Particle(1) {
            @Override
            public void initProperties() {
                this.setRADIUS(10);
                this.setMASS(3);
            }
        };
        a.initProperties();
        b.initProperties();
        return List.of(a, b);
    }


    @Test
    @DisplayName("CheckCollided")
    void CheckCollided() {

        List<CelestialBody> test = createBodies();
        CollisionDetector.checkCollided(test.get(0), test.get(1), 19);
        assertAll(
                () -> assertTrue(test.get(0).isCollided()),
                () -> assertFalse(test.get(1).isCollided()),
                () -> assertEquals(5, test.get(1).getMASS())
        );

        List<CelestialBody> test2 = createBodies();

        CollisionDetector.checkCollided(test2.get(0), test2.get(1), 21);
        assertAll(
                () -> assertFalse(test2.get(0).isCollided()),
                () -> assertFalse(test2.get(1).isCollided()),
                () -> assertEquals(2, test2.get(0).getMASS()),
                () -> assertEquals(3, test2.get(1).getMASS())
        );
    }

    @Test
    @DisplayName("CheckCollisions")
    void CheckCollisions() {
        List<CelestialBody> test = createBodies();
        SolarSystem system = new SolarSystem() {
            @Override
            public void initPlanets() {
                this.bodies = new ArrayList<>();
                this.bodies.add(test.get(0));
                this.bodies.add(test.get(1));
                this.bodies.get(0).setCollided(false);
                this.bodies.get(1).setCollided(false);
                this.bodies.get(0).setVectorLocation(new Vector3D());
                this.bodies.get(0).setVectorVelocity(new Vector3D()); //for the state
                this.bodies.get(1).setVectorLocation(new Vector3D(20, 20, 20));
                this.bodies.get(1).setVectorVelocity(new Vector3D()); //for the state
            }
        };
        system.initPlanets();
        system.initialState();
        CollisionDetector.checkCollisions(system, null);
        for (CelestialBody c : system.getCelestialBodies()) {
            assertFalse(c.isCollided());
        }
        system.initPlanets();
        system.initialState();
        system.systemState().getPositions().set(1, system.systemState().getPositions().get(1).sub(new Vector3D(10, 10, 10)));
        CollisionDetector.checkCollisions(system, null);
        assertTrue(system.getCelestialBodies().get(0).isCollided());

    }

}