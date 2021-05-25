package group17.Interfaces;


/**
 * Test class for the StateInterface
 */
public interface StateTest extends StateInterface {
    double getY();

    void setY(double y);

    @Override
    RateTest getRateOfChange();
}
