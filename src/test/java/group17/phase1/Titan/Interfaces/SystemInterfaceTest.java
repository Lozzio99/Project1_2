package group17.phase1.Titan.Interfaces;

import group17.phase1.Titan.Config;
import group17.phase1.Titan.Physics.Bodies.*;
import group17.phase1.Titan.Physics.Solvers.EulerSolver;
import group17.phase1.Titan.Physics.Solvers.MaxCPUSolver;
import group17.phase1.Titan.Physics.Solvers.RungeKutta4thSolver;
import group17.phase1.Titan.Simulation.Simulation;
import group17.phase1.Titan.System.SolarSystem.SolarSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static group17.phase1.Titan.Main.simulation;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class SystemInterfaceTest {

    static SystemInterface system;


    @BeforeEach
    void BeforeEach() {
        simulation = Simulation.create(0);
        simulation.initSystem(1);
        simulation.system().initPlanets();
        system = new SolarSystem();
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
        assumeTrue(Config.INSERT_PROBE);
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
        system.initClock();
        assertEquals("Clock { [ 00 : 00 : 00 ]   °  01 / 04 / 2020 ° }", system.getClock().toString());
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
        //SUN, MERCURY, VENUS, EARTH, MARS, JUPITER, SATURN, TITAN, URANUS, NEPTUNE, MOON

        system.initPlanets();
        List<CelestialBody> test = new ArrayList<>();
        test.add(new Star());
        test.add(new Planet(Planet.PlanetsEnum.MERCURY));
        test.add(new Planet(Planet.PlanetsEnum.VENUS));
        test.add(new Planet(Planet.PlanetsEnum.EARTH));
        test.add(new Planet(Planet.PlanetsEnum.MARS));
        test.add(new Planet(Planet.PlanetsEnum.JUPITER));
        test.add(new Planet(Planet.PlanetsEnum.SATURN));
        test.add(new Satellite(Satellite.SatellitesEnum.TITAN));
        test.add(new Planet(Planet.PlanetsEnum.URANUS));
        test.add(new Planet(Planet.PlanetsEnum.NEPTUNE));
        test.add(new Satellite(Satellite.SatellitesEnum.MOON));
        for (int i = 0; i < test.size(); i++) {
            int finalI = i;
            assertAll(
                    () -> assertEquals(
                            test
                                    .get(finalI).toString(),
                            system
                                    .getCelestialBodies()
                                    .get(finalI)
                                    .toString())
            );
        }

    }

    @Test
    @DisplayName("Start")
    void Start() {
        system.startSolver(1);
        assertEquals("", system.toString());
        assertSame(new EulerSolver().getFunction(), system.solver().getFunction());
        system.startSolver(2);
        assertEquals("", system.toString());
        assertSame(new RungeKutta4thSolver().getFunction(), system.solver().getFunction());
    }

    @Test
    @DisplayName("Stop")
    void Stop() {
        simulation.initCPU(3);
        simulation.initSystem(1);
        simulation.system().startSolver(3);
        assertFalse(((MaxCPUSolver) simulation.system().solver().getFunction())
                .getService().isTerminated());
        simulation.system().stop();
        assertTrue(((MaxCPUSolver) simulation.system().solver().getFunction())
                .getService().isTerminated());
        assertTrue(((MaxCPUSolver) simulation.system().solver().getFunction())
                .getService().isShutdown());

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
        simulation.initCPU(1);
        simulation.initSystem(1);
        simulation.system().reset();
        simulation.system().step();
    }


}