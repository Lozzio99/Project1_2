package group17.phase1.Titan.Bodies.CelestialBodies;

public class Satellite extends CelestialBody
{
    SatellitesEnum name;
    public Satellite(SatellitesEnum name){
        this.name = name;
    }

    public enum SatellitesEnum {
        MOON,
        TITAN,
        ASTEROID
    }

}
