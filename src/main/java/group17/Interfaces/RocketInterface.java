package group17.Interfaces;

public interface RocketInterface extends ProbeSimulatorInterface {


    double getFuelMass();

    void setFuelMass(double m);

    String toString();

    void update();

}
