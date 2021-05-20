package group17.System.State;

import group17.Interfaces.RateInterface;
import group17.Interfaces.StateInterface;
import group17.Interfaces.SystemInterface;
import group17.Interfaces.Vector3dInterface;
import group17.Math.Lib.Vector3D;
import group17.Simulation.Simulation;
import group17.System.Bodies.Particle;
import group17.System.SolarSystem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static group17.Main.simulation;
import static java.lang.Double.NaN;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class RateOfChangeTest {

    private static List<Vector3dInterface> zero_one() {
        return List.of(new Vector3D(), new Vector3D(1, 1, 1));
    }

    private static List<Vector3dInterface> five() {
        return List.of(new Vector3D(5, 5, 5), new Vector3D(5, 5, 5));
    }

    @Test
    @DisplayName("TestMultiplyScalar")
    void Multiply() {
        assertThrows(RuntimeException.class, () -> new RateOfChange().multiply(1));
        RateInterface test = new RateOfChange();
        test.setVelocities(zero_one());
        test = test.multiply(5);
        assertEquals(List.of(new Vector3D(), new Vector3D(5, 5, 5)), test.getVelocities());
    }

    @Test
    @DisplayName("TestSubScalar")
    void Sub() {
        assertThrows(RuntimeException.class, () -> new RateOfChange().sub(1));
        RateInterface test = new RateOfChange();
        test.setVelocities(zero_one());
        test = test.sub(5);
        assertEquals(List.of(new Vector3D(-5, -5, -5), new Vector3D(-4, -4, -4)), test.getVelocities());
    }

    @Test
    @DisplayName("TestAddScalar")
    void Add() {
        assertThrows(RuntimeException.class, () -> new RateOfChange().add(1));
        RateInterface test = new RateOfChange();
        test.setVelocities(zero_one());
        test = test.add(5);
        assertEquals(List.of(new Vector3D(5, 5, 5), new Vector3D(6, 6, 6)), test.getVelocities());
    }

    @Test
    @DisplayName("TestDivScalar")
    void Div() {
        assertThrows(RuntimeException.class, () -> new RateOfChange().div(1));
        RateInterface test = new RateOfChange();
        test.setVelocities(zero_one());
        test = test.div(5);
        assertEquals(List.of(new Vector3D(), new Vector3D(0.2, .2, .2)), test.getVelocities());
    }

    @Test
    @DisplayName("TestCopy")
    void Copy() {
        RateInterface test = new RateOfChange();
        test.setVelocities(five());
        RateInterface clone = test.copy();
        clone = clone.multiply(5);
        assertNotEquals(clone.getVelocities(), test.getVelocities());
        assertEquals(five(), test.getVelocities());
    }

    @Test
    @DisplayName("AddRate")
    void TestAdd() {
        assertThrows(RuntimeException.class, () -> new RateOfChange().add(new RateOfChange()));
        RateInterface test = new RateOfChange();
        test.setVelocities(zero_one());
        RateInterface addend = new RateOfChange();
        addend.setVelocities(five());
        test = test.add(addend);
        assertEquals(List.of(new Vector3D(5, 5, 5), new Vector3D(6, 6, 6)), test.getVelocities());
    }

    @Test
    @DisplayName("SumOfRates")
    void SumOf() {
        RateInterface test = new RateOfChange();
        test.setVelocities(zero_one());
        RateInterface addend1 = new RateOfChange(), addend2 = new RateOfChange(), addend3 = new RateOfChange();
        addend1.setVelocities(five());
        addend2.setVelocities(five());
        addend3.setVelocities(five());
        test = test.sumOf(addend1, addend2, addend3);
        assertEquals(List.of(new Vector3D(15, 15, 15), new Vector3D(16, 16, 16)), test.getVelocities());
    }

    @Test
    @DisplayName("InitialRate")
    void InitialRate() {
        RateInterface test = new RateOfChange();
        RateInterface testNotEmpty = new RateOfChange();
        testNotEmpty.setVelocities(five());
        simulation = new Simulation() {
            SystemInterface system;

            @Override
            public void initSystem() {
                system = new SolarSystem() {
                    final StateInterface systemState = new SystemState();

                    @Override
                    public StateInterface systemState() {
                        return this.systemState;
                    }
                };
                system.getCelestialBodies().add(new Particle(0));
                system.getCelestialBodies().get(0).setVectorLocation(new Vector3D(NaN, NaN, NaN));
                system.getCelestialBodies().get(0).setVectorVelocity(new Vector3D());
                this.system.systemState().state0(this.system.getCelestialBodies());
            }

            @Override
            public SystemInterface getSystem() {
                return system;
            }
        };
        simulation.initSystem();
        test.initialRate();
        testNotEmpty.initialRate();
        assertEquals("PARTICLE0 : (0.1,0.1,0.1)", test.toString());
        assertEquals("PARTICLE0 : (0.1,0.1,0.1)", testNotEmpty.toString());

    }

    @Test
    @DisplayName("SetVelocities")
    void SetVelocities() {
        RateInterface test = new RateOfChange();
        test.setVelocities(five());
        assertEquals(five(), test.getVelocities());
    }

    @Test
    @DisplayName("GetVelocities")
    void GetVelocities() {
        RateInterface test = new RateOfChange();
        assertEquals(new ArrayList<>(), test.getVelocities());
        test.setVelocities(five());
        assertEquals(five(), test.getVelocities());
    }

    @Test
    @DisplayName("TestToString")
    void TestToString() {
        assumeTrue(simulation == null);
        RateInterface test = new RateOfChange();
        test.setVelocities(five());
        assertEquals("[(5.0,5.0,5.0), (5.0,5.0,5.0)]", test.toString());
    }
}