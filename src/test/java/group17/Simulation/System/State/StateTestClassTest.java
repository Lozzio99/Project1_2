package group17.Simulation.System.State;

import group17.Interfaces.RateTest;
import group17.Interfaces.StateTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.Double.NaN;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StateTestClassTest {

    @Test
    @DisplayName("SetRateOfChange")
    void SetRateOfChange() {
        StateTest state = new StateTestClass();
        RateTest rate = new RateTestClass();
        rate.setDy(NaN);
        state.setRateOfChange(rate);
        assertEquals(NaN, state.getRateOfChange().getDy());
    }

    @Test
    @DisplayName("RateMul")
    void RateMul() {
        StateTest state = new StateTestClass();
        RateTest rate = new RateTestClass();
        rate.setDy(NaN);
        state.setRateOfChange(rate);
        assertEquals(NaN, ((StateTest) state.rateMul(1, rate)).getY());
        assertEquals(0, ((RateTest) state.rateMul(1, rate).getRateOfChange()).getDy());
    }

    @Test
    @DisplayName("Copy")
    void Copy() {
        StateTest state = new StateTestClass();
        RateTest rate = new RateTestClass();
        rate.setDy(NaN);
        state.setRateOfChange(rate);
        assertEquals(0, ((StateTest) state.copy()).getY());
        assertEquals(NaN, ((RateTest) state.copy().getRateOfChange()).getDy());

    }

    @Test
    @DisplayName("Multiply")
    void Multiply() {
        StateTest state = new StateTestClass();
        state.setY(1);
        assertEquals(3, ((StateTest) state.multiply(3)).getY());
        assertEquals(0, ((RateTest) state.multiply(3).getRateOfChange()).getDy());
    }

    @Test
    @DisplayName("Div")
    void Div() {
        StateTest state = new StateTestClass();
        state.setY(6);
        assertEquals(2, ((StateTest) state.div(3)).getY());
        assertEquals(0, ((RateTest) state.div(3).getRateOfChange()).getDy());
    }

    @Test
    @DisplayName("Add")
    void Add() {
        StateTest state = new StateTestClass();
        state.setY(6);
        StateTest addend = new StateTestClass();
        addend.setY(-3);
        assertEquals(3, ((StateTest) state.add(addend)).getY());
        assertEquals(0, (state.getRateOfChange()).getDy());

    }

    @Test
    @DisplayName("SumOf")
    void SumOf() {
        StateTest state = new StateTestClass();
        state.setY(6);
        StateTest addend = new StateTestClass();
        addend.setY(3);
        assertEquals(15, ((StateTest) state.sumOf(addend, addend, addend)).getY());
        assertEquals(0, (state.getRateOfChange()).getDy());
    }

    @Test
    @DisplayName("TestToString")
    void TestToString() {
        StateTest state = new StateTestClass();
        state.setY(6);
        state.getRateOfChange().setDy(3);
        assertEquals("State{y=6.0 ,\n" +
                " {dy=3.0}}", state.toString());
    }

    @Test
    @DisplayName("GetPositions")
    void GetPositions() {
        assertDoesNotThrow(() -> new StateTestClass().getPositions());
    }

    @Test
    @DisplayName("SetPositions")
    void SetPositions() {
        assertDoesNotThrow(() -> new StateTestClass().setPositions(List.of()));
    }

    @Test
    @DisplayName("GetState")
    void GetState() {
        StateTest state = new StateTestClass();
        state.getRateOfChange().setDy(6);
        assertEquals(6, ((StateTest) state.getState(state.getRateOfChange())).getY());
    }
}