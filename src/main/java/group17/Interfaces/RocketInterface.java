package group17.Interfaces;

/**
 * The interface Rocket interface.
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
