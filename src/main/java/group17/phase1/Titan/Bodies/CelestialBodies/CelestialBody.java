package group17.phase1.Titan.Bodies.CelestialBodies;

import group17.phase1.Titan.Main;
import group17.phase1.Titan.Physics.TimeSequence.GalacticClock;
import group17.phase1.Titan.Physics.Trajectories.Forces.Vector3D;
import group17.phase1.Titan.Physics.Trajectories.Forces.Vector3DInterface;

import java.util.concurrent.locks.Lock;

public abstract class CelestialBody
{
    static final double G = 6.67e-11;

    double MASS;
    double RADIUS;
    double DENSITY;
    double X_LOCATION;
    double Y_LOCATION;
    double Z_LOCATION;
    double X_VELOCITY;
    double Y_VELOCITY;
    double Z_VELOCITY;
    double X_ROTATION;
    double Y_ROTATION;
    double Z_ROTATION;

    // --- Set-Methods ---
    
    // Mass
    public void setMASS(double mass){
        this.MASS = mass;
    }

    // Radius
    public void setRADIUS(double RADIUS) {
        this.RADIUS = RADIUS;
    }

    // Density
    public void setDENSITY(double DENSITY) {
        this.DENSITY = DENSITY;
    }

    // Location
    public void setX_LOCATION(double x_LOCATION) {
        this.X_LOCATION = x_LOCATION;
    }

    public void setY_LOCATION(double y_LOCATION) {
        this.Y_LOCATION = y_LOCATION;
    }

    public void setZ_LOCATION(double z_LOCATION) {
        this.Z_LOCATION = z_LOCATION;
    }

    // Velocity
    public void setX_VELOCITY(double x_VELOCITY) {
        this.X_VELOCITY = x_VELOCITY;
    }

    public void setY_VELOCITY(double y_VELOCITY) {
        this.Y_VELOCITY = y_VELOCITY;
    }

    public void setZ_VELOCITY(double z_VELOCITY) {
        this.Z_VELOCITY = z_VELOCITY;
    }
    
    // Rotation
    public void setX_ROTATION(double x_ROTATION) {
        this.X_ROTATION = x_ROTATION;
    }

    public void setY_ROTATION(double y_ROTATION) {
        this.Y_ROTATION = y_ROTATION;
    }

    public void setZ_ROTATION(double z_ROTATION) {
        this.Z_ROTATION = z_ROTATION;
    }
    
    // --- Get-Methods ---
    
    // Mass
    double getMASS() {
    	return this.MASS;
    }
    
    // Radius
    double getRADIUS() {
    	return this.RADIUS;
    }
    
    // Density
    double getDensity() {
    	return this.DENSITY;
    }
    
    // Location
    double getX_LOCATION() {
    	return this.X_LOCATION;
    }
    
    double getY_LOCATION() {
    	return this.Y_LOCATION;
    }
    
    double getZ_LOCATION() {
    	return this.Z_LOCATION;
    }
    
    // Velocity
    double getX_VELOCITY() {
    	return this.X_VELOCITY;
    }
    
    double getY_VELOCITY() {
    	return this.Y_VELOCITY;
    }
    
    double getZ_VELOCITY() {
    	return this.Z_VELOCITY;
    }
    
    // Rotation
    double getX_ROTATION() {
    	return this.X_ROTATION;
    }
    
    double getY_ROTATION() {
    	return this.Y_VELOCITY;
    }
    
    double getZ_ROTATION() { return this.Z_ROTATION; }

    Vector3D getVectorPosition(){
        return new Vector3D(this.X_LOCATION,this.Y_LOCATION,this.Z_LOCATION);
    }

    void move(Vector3DInterface newDirection)
    {
        this.setX_LOCATION(newDirection.getX());
        this.setY_LOCATION(newDirection.getY());
        this.setZ_LOCATION(newDirection.getZ());
    }

    void setVectorVelocity(Vector3DInterface newVelocity){
        this.setX_VELOCITY(newVelocity.getX());
        this.setY_VELOCITY(newVelocity.getY());
        this.setZ_VELOCITY(newVelocity.getZ());
    }

    double getDistanceRadius(CelestialBody other){
        return Vector3D.dist(other.getVectorPosition(),this.getVectorPosition());
    }

    public static class Slave extends Thread
    {
        static Lock syncLock;
        private final CelestialBody planet;
        public Slave(CelestialBody p){
            this.planet = p;
        }

        public static void setSyncLock(Lock sync){ syncLock = sync;}

        @Override
        public void run(){

            synchronized (syncLock){
                for (CelestialBody p : Main.simulation.solarSystemRepository().allCelestialBodies())
                {
                    if (p!= this.planet){
                        double dist = planet.getDistanceRadius(p);
                        Vector3DInterface forceDirection = Vector3D.unitVectorDistance(planet.getVectorPosition(),p.getVectorPosition());
                        Vector3DInterface force = forceDirection.mul(G).mul(planet.getMASS()).mul(p.getMASS()).div(dist);
                        Vector3DInterface acceleration = force.mul(1./planet.getMASS());
                        planet.setVectorVelocity(acceleration.mul(GalacticClock.GALACTIC_TIME_STEP));
                    }
                }
            }
        }

    }
}
