package group17.System.State;

import group17.Interfaces.*;

import java.util.List;

public class StateTestClass implements StateTest {
    private RateTest testDy;
    private double y;

    public StateTestClass() {
    }

    @Override
    public StateTest addMul(double h, RateInterface r) {
        StateTestClass state = new StateTestClass();
        double dy = ((RateTest) r).getDy();
        state.setY(this.getY() + (h * dy));
        state.getRateOfChange().setDy(h * dy);
        return state;
    }

    public double getY() {
        return y;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public RateTest getRateOfChange() {
        if (testDy == null) testDy = new RateTestClass();
        return testDy;
    }

    @Override
    public void setRateOfChange(RateInterface newRate) {
        this.testDy = (RateTest) newRate;
    }

    @Override
    public StateInterface rateMul(double step, RateInterface rate) {
        StateTest newState = new StateTestClass();
        double dy = ((RateTest) rate).getDy();
        newState.setY(dy * step);
        newState.getRateOfChange().setDy(0);
        return newState;
    }

    @Override
    public StateInterface copy() {
        StateTest state = new StateTestClass();
        state.setY(this.getY());
        state.getRateOfChange().setDy(this.getRateOfChange().getDy());
        return state;
    }

    @Override
    public StateInterface multiply(double scalar) {
        StateTestClass state = new StateTestClass();
        state.setY(this.getY() * scalar);
        return state;
    }

    @Override
    public StateInterface div(double scalar) {
        StateTestClass state = new StateTestClass();
        state.setY(this.getY() / scalar);
        return state;
    }

    @Override
    public StateInterface add(StateInterface scalar) {
        StateTestClass state = new StateTestClass();
        state.setY(this.getY() + ((StateTest) scalar).getY());
        return state;
    }

    @Override
    public StateInterface sumOf(StateInterface... states) {
        StateTest rate = new StateTestClass();
        rate.setY(0);
        for (StateInterface r : states) {
            rate.setY(rate.getY() + ((StateTest) r).getY());
        }
        return rate;
    }

    @Override
    public String toString() {
        return "State{" +
                "y=" + y +
                " ,\n " + testDy +
                '}';
    }

    @Override
    public List<Vector3dInterface> getPositions() {
        return null;
    }

    @Override
    public void setPositions(List<Vector3dInterface> v) {
    }

    @Override
    public StateInterface getState(RateInterface rate) {
        StateTest newState = new StateTestClass();
        newState.setY(((RateTest) rate).getDy());
        return newState;
    }
}
