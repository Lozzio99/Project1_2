package group17.phase1.Titan.Bodies.CelestialBodies;

import group17.phase1.Titan.Main;
import group17.phase1.Titan.Physics.TimeSequence.GalacticClock;
import group17.phase1.Titan.Physics.Trajectories.Forces.Vector3D;
import group17.phase1.Titan.Physics.Trajectories.Forces.Vector3DInterface;

import java.util.List;
import java.util.concurrent.locks.Lock;

public class Planet extends CelestialBody implements CelestialBodyInterface
{
    PlanetsEnum planet;

    public Planet(PlanetsEnum planet){
        this.planet = planet;
    }

    @Override
    public double getMass() {
        return this.MASS;
    }

    @Override
    public double getRadius() {
        return this.RADIUS;
    }

    @Override
    public double getDensity() {
        return this.DENSITY;
    }

    @Override
    public List<CelestialBodyInterface> attractors() {
        return null;
    }

    @Override
    public Vector3D getVectorPosition() {
        return super.getVectorPosition();
    }

    @Override
    public double getDistanceRadius(CelestialBodyInterface other) {
        return Vector3D.dist(other.getVectorPosition(),this.getVectorPosition());
    }

    @Override
    public double getXCoordinate() {
        return this.X_LOCATION;
    }

    @Override
    public double getYCoordinate() {
        return this.Y_LOCATION;
    }

    @Override
    public double getZCoordinate() {
        return this.Z_LOCATION;
    }

    @Override
    public double getXVelocity() {
        return this.X_VELOCITY;
    }

    @Override
    public double getYVelocity() {
        return this.Y_VELOCITY;
    }

    @Override
    public double getZVelocity() {
        return this.Z_VELOCITY;
    }

    @Override
    public void setXVelocity(double x) {
        this.X_VELOCITY = x;
    }

    @Override
    public void setYVelocity(double y) {
        this.Y_VELOCITY = y;
    }

    @Override
    public void setZVelocity(double z) {
        this.Z_VELOCITY = z;
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

    public static class Slave extends Thread
    {
        static Lock syncLock;
        private final Planet planet;
        public Slave(Planet p){
            this.planet = p;
        }

        public static void setSyncLock(Lock sync){ syncLock = sync;}

        @Override
        public void run(){

            synchronized (syncLock){
                for (CelestialBodyInterface p : Main.simulation.solarSystemRepository().allCelestialBodies())
                {
                    if (p!= this){
                        double dist = planet.getDistanceRadius(p);
                        Vector3DInterface forceDirection = Vector3D.unitVectorDistance(planet.getVectorPosition(),p.getVectorPosition());
                        Vector3DInterface force = forceDirection.mul(G).mul(planet.getMass()).mul(p.getMass()).div(dist);
                        Vector3DInterface acceleration = force.mul(1./planet.getMass());
                        planet.setVectorVelocity(acceleration.mul(GalacticClock.GALACTIC_TIME_STEP));
                    }
                }
            }
        }

    }
}
