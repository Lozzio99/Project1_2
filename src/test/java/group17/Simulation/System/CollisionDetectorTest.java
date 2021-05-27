package group17.Simulation.System;

import group17.Interfaces.SystemInterface;
import group17.Math.Vector3D;
import group17.Simulation.Simulation;
import group17.Simulation.System.Bodies.CelestialBody;
import group17.Simulation.System.Bodies.Particle;
import group17.Utils.CollisionDetector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static group17.Main.simulation;
import static group17.Utils.Config.CHECK_COLLISIONS;
import static group17.Utils.Config.REPORT;
import static org.junit.jupiter.api.Assertions.*;

class CollisionDetectorTest {


    @BeforeAll
    static void init() {
        REPORT = false;
        CHECK_COLLISIONS = true;
        new CollisionDetector();
    }


    static List<CelestialBody> createBodies() {
        CelestialBody a, b;
        a = new Particle(0) {
            @Override
            public void initProperties() {
                this.setRADIUS(10);
                this.setMASS(2);
                this.setCollided(false);
            }
        };
        b = new Particle(1) {
            @Override
            public void initProperties() {
                this.setRADIUS(10);
                this.setMASS(3);
                this.setCollided(false);
            }
        };
        a.initProperties();
        b.initProperties();
        return List.of(a, b);
    }

    static SystemInterface createSystem() {
        List<CelestialBody> test = createBodies();
        return new SolarSystem() {
            @Override
            public void initPlanets() {
                this.bodies = new ArrayList<>();
                this.bodies.add(test.get(0));
                this.bodies.add(test.get(1));
                this.bodies.get(0).setVectorLocation(new Vector3D());
                this.bodies.get(0).setVectorVelocity(new Vector3D()); //for the state
                this.bodies.get(1).setVectorLocation(new Vector3D(20, 20, 20));
                this.bodies.get(1).setVectorVelocity(new Vector3D()); //for the state
            }
        };
    }

    @Test
    @DisplayName("CheckCollided")
    void CheckBody0Collided() {

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
    @DisplayName("CheckCollided")
    void CheckBody1Collided() {

        CHECK_COLLISIONS = true;
        List<CelestialBody> test = new ArrayList<>(createBodies());
        CelestialBody temp = test.get(0);
        test.set(0, test.get(1));
        test.set(1, temp);
        CollisionDetector.checkCollided(test.get(0), test.get(1), 19);
        assertAll(
                () -> assertTrue(test.get(1).isCollided()),
                () -> assertFalse(test.get(0).isCollided()),
                () -> assertEquals(5, test.get(0).getMASS())
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
    void CheckNoCollisions() {
        SystemInterface system = createSystem();
        system.initPlanets();
        system.initialState();
        CollisionDetector.checkCollisions(system, null);
        for (CelestialBody c : system.getCelestialBodies()) {
            assertFalse(c.isCollided());
        }
    }

    @Test
    @DisplayName("CheckCollisions")
    void CheckCollisionsAndReport() {
        SystemInterface system = createSystem();
        system.initPlanets();
        system.initialState();
        system.systemState().getPositions().set(1, system.systemState().getPositions().get(0).clone());
        REPORT = true;
        CHECK_COLLISIONS = true;
        simulation = new Simulation();
        simulation.initReporter();
        CollisionDetector.checkCollisions(system, simulation.getReporter());
        assertTrue(system.getCelestialBodies().get(0).isCollided());
    }

    @Test
    @DisplayName("CheckCollisions")
    void CheckCollisionsAndNotReport() {
        SystemInterface system = createSystem();
        system.initPlanets();
        system.initialState();
        system.systemState().getPositions().set(1, system.systemState().getPositions().get(0).clone());
        REPORT = false;
        CHECK_COLLISIONS = true;
        simulation = new Simulation();
        simulation.initReporter();
        CollisionDetector.checkCollisions(system, null);
        assertTrue(system.getCelestialBodies().get(0).isCollided());
    }

    @Test
    @DisplayName("TestDoNotCheck")
    void TestDoNotCheck() {
        CHECK_COLLISIONS = false;
        List<CelestialBody> test = createBodies();
        CollisionDetector.checkCollided(test.get(1), test.get(0), 19);
        assertAll(
                () -> assertFalse(test.get(0).isCollided()),
                () -> assertFalse(test.get(1).isCollided()),
                () -> assertEquals(2, test.get(0).getMASS()),
                () -> assertEquals(3, test.get(1).getMASS())
        );
    }

    @Test
    @DisplayName("TestReportCollision")
    void checkBody1CollidedAndReport() {
        REPORT = true;
        simulation = new Simulation();
        simulation.initReporter();
        CHECK_COLLISIONS = true;
        List<CelestialBody> test = createBodies();
        CollisionDetector.checkCollided(test.get(0), test.get(1), 19);
        assertAll(
                () -> assertTrue(test.get(0).isCollided()),
                () -> assertFalse(test.get(1).isCollided()),
                () -> assertEquals(5, test.get(1).getMASS())
        );
    }

    @Test
    @DisplayName("TestReportCollision")
    void checkBodiesAlreadyCollided() {

        List<CelestialBody> test = createBodies();
        test.get(0).setCollided(true);
        test.get(1).setCollided(true);
        CollisionDetector.checkCollided(test.get(0), test.get(1), 19);
        assertAll(
                () -> assertTrue(test.get(0).isCollided()),
                () -> assertTrue(test.get(1).isCollided())
        );
    }

}