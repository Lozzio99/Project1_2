package group17.Math.Solvers;

import group17.Interfaces.StateInterface;

public interface StateTest extends StateInterface {
    double getY();

    void setY(double y);

    @Override
    RateTest getRateOfChange();
}
