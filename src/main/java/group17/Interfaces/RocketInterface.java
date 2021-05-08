package group17.Interfaces;

public interface RocketInterface extends ProbeSimulatorInterface {


    double getFuelMass();

    void setFuelMass(double m);

    void startBurn(Vector3dInterface direction, double percentage);

    void startBurn(Vector3dInterface direction, double percentage, int duration);

    void stopBurn();

    void updateDV();

    String toString();

    void update();

}
