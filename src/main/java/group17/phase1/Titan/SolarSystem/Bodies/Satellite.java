package group17.phase1.Titan.SolarSystem.Bodies;

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
