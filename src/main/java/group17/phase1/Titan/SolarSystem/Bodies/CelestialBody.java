package group17.phase1.Titan.SolarSystem.Bodies;

import group17.phase1.Titan.Graphics.Geometry.Point3D;
import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.Simulation.Gravity.Vector3D;

public abstract class CelestialBody
{


    double MASS;
    double RADIUS;
    double DENSITY;



    double X_LOCATION;
    double Y_LOCATION;
    double Z_LOCATION;



    double X_VELOCITY;
    double Y_VELOCITY;
    double Z_VELOCITY;



    double DX;
    double DY;
    double DZ;


    double X_ROTATION;
    double Y_ROTATION;
    double Z_ROTATION;



    Vector3dInterface LocationVector;
    Vector3dInterface VelocityVector;
    Vector3dInterface ShiftVector;





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
    public double getMASS() {
        return this.MASS;
    }

    // Radius
    public double getRADIUS() {
        return this.RADIUS;
    }

    // Density
    public double getDensity() {
        return this.DENSITY;
    }

    // Location
    public double getX_LOCATION() {
        return this.X_LOCATION;
    }

    public double getY_LOCATION() {
        return this.Y_LOCATION;
    }

    public double getZ_LOCATION() {
        return this.Z_LOCATION;
    }

    // Velocity
    public double getX_VELOCITY() {
        return this.X_VELOCITY;
    }

    public double getY_VELOCITY() {
        return this.Y_VELOCITY;
    }

    public double getZ_VELOCITY() {
        return this.Z_VELOCITY;
    }

    // Rotation
    public double getX_ROTATION() {
        return this.X_ROTATION;
    }

    public double getY_ROTATION() {
        return this.Y_VELOCITY;
    }

    public double getZ_ROTATION() { return this.Z_ROTATION; }

    public Point3D getPointLocation(){
        return new Point3D(this.X_LOCATION,this.Y_LOCATION,this.Z_LOCATION);
    }



    public Vector3dInterface getVectorLocation()
    {
        return new Vector3D(this.X_LOCATION,this.Y_LOCATION,this.Z_LOCATION);
    }

    public void setVectorLocation(Vector3dInterface newDirection)
    {
        this.setX_LOCATION(newDirection.getX());
        this.setY_LOCATION(newDirection.getY());
        this.setZ_LOCATION(newDirection.getZ());
    }



    public void setVectorVelocity(Vector3dInterface newVelocity){
        this.setX_VELOCITY(newVelocity.getX());
        this.setY_VELOCITY(newVelocity.getY());
        this.setZ_VELOCITY(newVelocity.getZ());
    }

    public double getDistanceRadius(CelestialBody other){
        return Vector3D.dist(other.getVectorLocation(),this.getVectorLocation());
    }



    public void setShiftVector(Vector3dInterface dxyz){
        this.ShiftVector = dxyz;
    }



    //TODO: check if this actually effects the vector

    public void applyAttractionVector()
    {
        this.VelocityVector.add(this.ShiftVector);
        this.LocationVector.add(this.VelocityVector);
    }
}
