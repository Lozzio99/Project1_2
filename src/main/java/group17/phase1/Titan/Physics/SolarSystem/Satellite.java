package group17.phase1.Titan.Physics.SolarSystem;


import group17.phase1.Titan.Physics.Vector3D;

public class Satellite extends CelestialBody
{
    SatellitesEnum name;
    public Satellite(SatellitesEnum name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name.name();
    }

    @Override
    public void initPosition() {
        switch (name){
            case MOON -> {
                this.setVectorLocation(new Vector3D(-1.472343904597218e+11, -2.822578361503422e+10, 1.052790970065631e+07));
                this.setVectorVelocity(new Vector3D(4.433121605215677e+03, -2.948453614110320e+04, 8.896598225322805e+01));
            }
            case TITAN -> {
                this.setVectorLocation(new Vector3D(6.332873118527889e+11, -1.357175556995868e+12, -2.134637041453660e+09));
                this.setVectorVelocity(new Vector3D(3.056877965721629e+03, 6.125612956428791e+03, -9.523587380845593e+02));
            }
        }
    }

    public enum SatellitesEnum {
        MOON,
        TITAN,
        ASTEROID
    }
}
