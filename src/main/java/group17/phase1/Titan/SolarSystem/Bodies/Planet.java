/**
 * Class representing a planet of the type celestial body.
 * @author 	Dan Parii, Lorenzo Pompigna, Nikola Prianikov, Axel Rozental, Konstantin Sandfort, Abhinandan Vasudevan​
 * @version 1.0
 * @since	19/02/2021
 */

package group17.phase1.Titan.SolarSystem.Bodies;

public class Planet extends CelestialBody
{
    PlanetsEnum planet;

    public Planet(PlanetsEnum planet){
        this.planet = planet;
    }

    @Override
    public String toString() {
        return this.planet.toString();
    }

    /**
     * Enumeration of all planets in our solar system. #plutoWeMissYou
     */

    public enum PlanetsEnum {
        MERCURY,
        VENUS,
        EARTH,
        MARS,
        JUPITER,
        SATURN,
        URANUS,
        NEPTUNE
    }

}
