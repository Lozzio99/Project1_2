package group17.Math.Solvers;

import group17.Interfaces.RateInterface;
import group17.Interfaces.Vector3dInterface;

import java.util.List;

public class RateTestClass implements RateTest {
    private double dy;

    @Override
    public List<Vector3dInterface> getVelocities() {
        return null;
    }

    @Override
    public void setVel(List<Vector3dInterface> vel) {

    }

    @Override
    public RateInterface multiply(double scalar) {
        RateTestClass rate = new RateTestClass();
        rate.setDy(this.getDy() * scalar);
        return rate;
    }

    @Override
    public RateInterface add(RateInterface tobeAdded) {
        RateTestClass rate = new RateTestClass();
        rate.setDy(this.getDy() + ((RateTest) tobeAdded).getDy());
        return rate;
    }


    @Override
    public RateInterface div(int div) {
        RateTestClass rate = new RateTestClass();
        rate.setDy(this.getDy() / div);
        return rate;
    }


    @Override
    public RateInterface sumOf(RateInterface... states) {
        RateTestClass rate = new RateTestClass();
        rate.setDy(0);
        for (RateInterface r : states) {
            rate.setDy(rate.getDy() + ((RateTest) r).getDy());
        }
        return rate;
    }


    @Override
    public RateInterface clone(RateInterface rate) {
        RateTest clone = new RateTestClass();
        clone.setDy(((RateTest) rate).getDy());
        return clone;
    }

    @Override
    public double getDy() {
        return dy;
    }

    @Override
    public void setDy(double dy) {
        this.dy = dy;
    }

    @Override
    public String toString() {
        return "{" +
                "dy=" + dy +
                '}';
    }
}
