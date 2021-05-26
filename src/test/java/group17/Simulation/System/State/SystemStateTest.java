package group17.Simulation.System.State;

import group17.Interfaces.RateInterface;
import group17.Interfaces.StateInterface;
import group17.Math.Lib.Vector3D;
import group17.Simulation.System.Bodies.CelestialBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static group17.Main.simulation;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class SystemStateTest {

    private StateInterface test, operator;

    @BeforeEach
    void setUp() {
        test = new SystemState();
        test.setPositions(List.of(new Vector3D(), new Vector3D(1, 1, 1)));
        test.getRateOfChange().setVelocities(List.of(new Vector3D(), new Vector3D(1, 1, 1)));

        operator = new SystemState();
        operator.setPositions(List.of(new Vector3D(5, 5, 5), new Vector3D(5, 5, 5)));
        operator.getRateOfChange().setVelocities(List.of(new Vector3D(5, 5, 5), new Vector3D(5, 5, 5)));
    }

    @Test
    @DisplayName("Copy")
    void Copy() {
        StateInterface testCopy = test.copy();
        assertEquals(List.of(new Vector3D(), new Vector3D(1, 1, 1)), testCopy.getPositions());
        test.setPositions(null);
        assertEquals(List.of(new Vector3D(), new Vector3D(1, 1, 1)), testCopy.getPositions());
    }

    @Test
    @DisplayName("State0")
    void State0() {
        List<CelestialBody> bodies = new ArrayList<>();
        CelestialBody body1, body2;
        body1 = new CelestialBody() {
            @Override
            public String toString() {
                return "test";
            }

            @Override
            public void initProperties() {
                this.setVectorVelocity(new Vector3D());
                this.setVectorLocation(new Vector3D(-1, -1, -1));
            }
        };
        bodies.add(body1);
        bodies.add(body2 = body1);
        test = new SystemState();
        test.state0(bodies);
        assertEquals(List.of(new Vector3D(), new Vector3D()), test.getRateOfChange().getVelocities());
        assertEquals(List.of(new Vector3D(-1, -1, -1), new Vector3D(-1, -1, -1)), test.getPositions());
    }

    @Test
    @DisplayName("AddMul")
    void AddMul() {
        //0 + 2*5  ,   1+ 2*5
        StateInterface test2 = test.addMul(2, operator.getRateOfChange());
        assertEquals(List.of(new Vector3D(10, 10, 10), new Vector3D(11, 11, 11)), test2.getPositions());
        assertEquals(List.of(new Vector3D(5, 5, 5), new Vector3D(5, 5, 5)), operator.getRateOfChange().getVelocities());
        assertEquals(List.of(new Vector3D(), new Vector3D(1, 1, 1)), test.getPositions());
        assertEquals(List.of(new Vector3D(), new Vector3D(1, 1, 1)), test.getRateOfChange().getVelocities());
    }

    @Test
    @DisplayName("RateMul")
    void RateMul() {
    }


    @Test
    @DisplayName("Multiply")
    void Multiply() {
        assertThrows(RuntimeException.class, () -> new SystemState().multiply(1));

        StateInterface test2 = test.multiply(10);
        assertEquals(List.of(new Vector3D(), new Vector3D(10, 10, 10)), test2.getPositions());
        assertEquals(List.of(new Vector3D(), new Vector3D(10, 10, 10)), test2.getRateOfChange().getVelocities());

        assertEquals(List.of(new Vector3D(), new Vector3D(1, 1, 1)), test.getPositions());
        assertEquals(List.of(new Vector3D(), new Vector3D(1, 1, 1)), test.getRateOfChange().getVelocities());
    }

    @Test
    @DisplayName("Div")
    void Div() {
        assertThrows(RuntimeException.class, () -> new SystemState().div(2));

        StateInterface test2 = test.div(2);
        assertEquals(List.of(new Vector3D(), new Vector3D(.5, .5, .5)), test2.getPositions());
        assertEquals(List.of(new Vector3D(), new Vector3D(.5, .5, .5)), test2.getRateOfChange().getVelocities());

        assertEquals(List.of(new Vector3D(), new Vector3D(1, 1, 1)), test.getPositions());
        assertEquals(List.of(new Vector3D(), new Vector3D(1, 1, 1)), test.getRateOfChange().getVelocities());
    }

    @Test
    @DisplayName("Add")
    void Add() {
        assertThrows(RuntimeException.class, () -> new SystemState().add(new SystemState()));

        StateInterface test2 = test.add(operator);
        assertEquals(List.of(new Vector3D(5, 5, 5), new Vector3D(6, 6, 6)), test2.getPositions());
        assertEquals(List.of(new Vector3D(5, 5, 5), new Vector3D(6, 6, 6)), test2.getRateOfChange().getVelocities());
        assertEquals(List.of(new Vector3D(), new Vector3D(1, 1, 1)), test.getPositions());
        assertEquals(List.of(new Vector3D(), new Vector3D(1, 1, 1)), test.getRateOfChange().getVelocities());
    }

    @Test
    @DisplayName("SumOf")
    void SumOf() {
        StateInterface test2 = test.sumOf(operator, operator, operator);
        assertEquals(List.of(new Vector3D(15, 15, 15), new Vector3D(16, 16, 16)), test2.getPositions());
        assertEquals(List.of(new Vector3D(15, 15, 15), new Vector3D(16, 16, 16)), test2.getRateOfChange().getVelocities());
        assertEquals(List.of(new Vector3D(), new Vector3D(1, 1, 1)), test.getPositions());
        assertEquals(List.of(new Vector3D(), new Vector3D(1, 1, 1)), test.getRateOfChange().getVelocities());
    }

    @Test
    @DisplayName("Update")
    void Update() {
        test.update(operator);
        assertNotEquals(List.of(new Vector3D(), new Vector3D(1, 1, 1)), test.getPositions());
        assertNotEquals(List.of(new Vector3D(), new Vector3D(1, 1, 1)), test.getRateOfChange().getVelocities());
        assertEquals(List.of(new Vector3D(5, 5, 5), new Vector3D(5, 5, 5)), test.getPositions());
        assertEquals(List.of(new Vector3D(5, 5, 5), new Vector3D(5, 5, 5)), test.getRateOfChange().getVelocities());
    }

    @Test
    @DisplayName("GetPositions")
    void GetPositions() {
        StateInterface test2 = new SystemState();
        assertEquals(new ArrayList<>(), test2.getPositions());
        assertEquals(List.of(new Vector3D(), new Vector3D(1, 1, 1)), test.getPositions());
        assertEquals(List.of(new Vector3D(5, 5, 5), new Vector3D(5, 5, 5)), operator.getPositions());
    }

    @Test
    @DisplayName("SetPositions")
    void SetPositions() {
        StateInterface test2 = new SystemState();
        assertEquals(new ArrayList<>(), test2.getPositions());
        test2.setPositions(test.getPositions());
        assertEquals(List.of(new Vector3D(), new Vector3D(1, 1, 1)), test2.getPositions());
        test2.setPositions(operator.getPositions());
        assertEquals(List.of(new Vector3D(5, 5, 5), new Vector3D(5, 5, 5)), test2.getPositions());
    }

    @Test
    @DisplayName("TestToString")
    void TestToString() {
        assumeTrue(simulation == null);
        assertDoesNotThrow(() -> test.toString());
        assertDoesNotThrow(() -> operator.toString());
        assertDoesNotThrow(() -> new SystemState().toString());
    }

    @Test
    @DisplayName("GetRateOfChange")
    void GetRateOfChange() {
        StateInterface test2 = new SystemState();
        assertEquals(RateOfChange.class, test2.getRateOfChange().getClass());
        RateInterface rate = new RateOfChange();
        rate.getVelocities().add(new Vector3D());
        rate.getVelocities().add(new Vector3D(1, 1, 1));
        assertEquals(rate.getVelocities(), test.getRateOfChange().getVelocities());
    }

    @Test
    @DisplayName("TestHashCode")
    void TestHashCode() {
        assertNotEquals(test.hashCode(), operator.hashCode());
        StateInterface test2 = new SystemState(List.of(new Vector3D(), new Vector3D(1, 1, 1)), List.of(new Vector3D(), new Vector3D(1, 1, 1)));
        assertEquals(test.hashCode(), test2.hashCode());
    }

    @Test
    @DisplayName("TestEquals")
    void TestEquals() {
        assertNotEquals(test, operator);
        assertNotEquals(test, List.of(List.of(new Vector3D(), new Vector3D(1, 1, 1)), List.of(new Vector3D(), new Vector3D(1, 1, 1))));

        StateInterface test2 = new SystemState(List.of(new Vector3D(), new Vector3D(1, 1, 1)), List.of(new Vector3D(), new Vector3D(1, 1, 1)));
        assertEquals(test, test2);
    }

    @Test
    @DisplayName("testConstructorWithState")
    void testConstructorWithState() {

        StateInterface test2 = new SystemState(test);
        assertEquals(List.of(new Vector3D(), new Vector3D(1, 1, 1)), test2.getPositions());
        assertEquals(List.of(new Vector3D(), new Vector3D(1, 1, 1)), test2.getRateOfChange().getVelocities());
        assertEquals(List.of(new Vector3D(), new Vector3D(1, 1, 1)), test.getPositions());
        assertEquals(List.of(new Vector3D(), new Vector3D(1, 1, 1)), test.getRateOfChange().getVelocities());

    }
}