package group17.Interfaces;

public interface StateTest extends StateInterface {
    double getY();

    void setY(double y);

    @Override
    RateTest getRateOfChange();
}
