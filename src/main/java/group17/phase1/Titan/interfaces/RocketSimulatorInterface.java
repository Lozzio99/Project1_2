package group17.phase1.Titan.Interfaces;

public interface RocketSimulatorInterface extends ProbeSimulatorInterface {

    void startBurn(Vector3dInterface direction, double percentage);

    void startBurn(Vector3dInterface direction, double percentage, int duration);


    void stopBurn();

    double getFuelMass();

    void setFuelMass(double m);

    String toString();


}
