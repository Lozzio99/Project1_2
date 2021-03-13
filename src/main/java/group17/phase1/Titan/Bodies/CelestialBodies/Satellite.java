package group17.phase1.Titan.Bodies.CelestialBodies;

import java.util.List;

public class Satellite extends CelestialBody implements CelestialBodyInterface
{

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

    public enum SatellitesEnum {
        MOON,
        TITAN,
        ASTEROID
    }

}
