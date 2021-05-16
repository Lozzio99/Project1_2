package group17.Math.Solvers;

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
