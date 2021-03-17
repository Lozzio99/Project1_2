package group17.phase1.Titan.SolarSystem.Bodies;

import group17.phase1.Titan.Interfaces.Vector3dInterface;


import java.awt.*;



public abstract class CelestialBody
{

    Color color;


    double MASS;
    double RADIUS;
    double DENSITY;


    double X_VELOCITY;
    double Y_VELOCITY;
    double Z_VELOCITY;



    double X_ROTATION;
    double Y_ROTATION;
    double Z_ROTATION;


    Vector3dInterface LocationVector;
    Vector3dInterface VelocityVector;

    CelestialBody(){

    }


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



    // Rotation
    public double getX_ROTATION() {
        return this.X_ROTATION;
    }

    public double getY_ROTATION() {
        return this.Y_ROTATION;
    }

    public double getZ_ROTATION() { return this.Z_ROTATION; }


    // Rotation
    public double getX_VELOCITY() {
        return this.X_VELOCITY;
    }

    public double getY_VELOCITY() {
        return this.Y_VELOCITY;
    }

    public double getZ_VELOCITY() { return this.Z_VELOCITY; }


    public Vector3dInterface getVectorLocation() {
        return this.LocationVector;
    }
    
    public Vector3dInterface getVectorVelocity() {
    	return this.VelocityVector;
    }

    public void setVectorLocation(Vector3dInterface newDirection) {
        LocationVector = newDirection;
    }

    public Vector3dInterface getVelocityVector(){
        return this.VelocityVector;
    }

    public void setVectorVelocity(Vector3dInterface newVelocity){
        VelocityVector = newVelocity;
    }


    public void setColour(Color col){
        this.color = col;
    }

    public Color getColour() {
        return color;
    }

}
