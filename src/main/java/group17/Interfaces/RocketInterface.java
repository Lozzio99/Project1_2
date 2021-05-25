package group17.Interfaces;

/**
 * An interface for simulating a rocket launched from Earth
 */
public interface RocketInterface extends ProbeSimulatorInterface {


    /**
     * Gets fuel mass.
     *
     * @return the fuel mass
     */
    double getFuelMass();

    /**
     * Sets fuel mass.
     *
     * @param m the m
     */
    void setFuelMass(double m);

    String toString();

    /**
     * Update.
     */
    void update();

}
