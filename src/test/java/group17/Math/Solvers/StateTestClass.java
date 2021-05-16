package group17.Math.Solvers;

import group17.Interfaces.RateInterface;
import group17.Interfaces.Vector3dInterface;

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
        state.getRateOfChange().setDy(dy);
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
    public String toString() {
        return "State{" +
                "y=" + y +
                " , " + testDy +
                '}';
    }

    @Override
    public List<Vector3dInterface> getPositions() {
        return null;
    }

    @Override
    public void setPositions(List<Vector3dInterface> v) {
    }
}
