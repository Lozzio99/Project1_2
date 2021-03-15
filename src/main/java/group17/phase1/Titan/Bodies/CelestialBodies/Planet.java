package group17.phase1.Titan.Bodies.CelestialBodies;

public class Planet extends CelestialBody
{
    PlanetsEnum planet;

    public Planet(PlanetsEnum planet){
        this.planet = planet;
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
