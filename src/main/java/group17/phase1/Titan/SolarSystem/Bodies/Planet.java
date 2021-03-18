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
