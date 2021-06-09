package Module.System.Bodies;

import Module.Math.Vector3D;

/**
 * The type Satellite.
 */
public class Satellite extends CelestialBody {

    SatellitesEnum name;

    /**
     * Instantiates a new Satellite.
     * Instantiates a new Planet.
     * Repository Class for satellites used in the Simulation
     * @param name the name
     */
    public Satellite(SatellitesEnum name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name.name();
    }

    @Override
    public void initProperties() {
        this.setVectorLocation(new Vector3D(0, 0, 0));
    }

    /**
     * The enum Satellites enum.
     */
    public enum SatellitesEnum {
        /**
         * Moon satellites enum.
         */
        MOON,
        /**
         * Titan satellites enum.
         */
        TITAN,
        /**
         * Asteroid satellites enum.
         */
        ASTEROID
    }
}
