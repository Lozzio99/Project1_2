/**
 * Class representing a satellite of the type celestial body.
 * @author 	Dan Parii, Lorenzo Pompigna, Nikola Prianikov, Axel Rozental, Konstantin Sandfort, Abhinandan Vasudevan​
 * @version 1.0
 * @since	19/02/2021
 */

package group17.phase1.Titan.SolarSystem.Bodies;

public class Satellite extends CelestialBody
{

    SatellitesEnum name;
    public Satellite(SatellitesEnum name){
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name.toString();
    }

    /**
     * Enumeration of satellites in our solar system.
     */
    public enum SatellitesEnum {
        MOON,
        TITAN,
        ASTEROID
    }
}
