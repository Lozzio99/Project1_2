package group17.phase1.Titan.Physics.SolarSystem;


import group17.phase1.Titan.Graphics.Geometry.Point3D;
import group17.phase1.Titan.Physics.Vector3D;
import group17.phase1.Titan.interfaces.Vector3dInterface;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class CelestialBody
{
    Color color;


    double MASS;
    double RADIUS;
    double DENSITY;


    double X_ROTATION;
    double Y_ROTATION;
    double Z_ROTATION;


    Vector3dInterface LocationVector;
    Vector3dInterface VelocityVector = new Vector3D(0,0,0);


    List<Point3D> path;

    public CelestialBody(){ this.path = new ArrayList<>();  }

    // --- Set-Methods ---

    /**
     * Sets the mass.
     * @param mass
     */
    public void setMASS(double mass)
    {
        this.MASS = mass;
    }

    /**
     * Sets the radius.
     * @param RADIUS
     */
    public void setRADIUS(double RADIUS)
    {
        this.RADIUS = RADIUS;
    }

    /**
     * Sets the density.
     * @param DENSITY
     */
    public void setDENSITY(double DENSITY)
    {
        this.DENSITY = DENSITY;
    }

    /**
     * Sets the X rotation.
     * @param x_ROTATION
     */
    public void setX_ROTATION(double x_ROTATION)
    {
        this.X_ROTATION = x_ROTATION;
    }

    /**
     * Sets the Y rotation.
     * @param y_ROTATION -
     */
    public void setY_ROTATION(double y_ROTATION) {
        this.Y_ROTATION = y_ROTATION;
    }

    /**
     * Sets the Z rotation.
     * @param z_ROTATION -
     */
    public void setZ_ROTATION(double z_ROTATION) {
        this.Z_ROTATION = z_ROTATION;
    }

    /**
     * Sets the current location as a vector.
     * @param newDirection
     */
    public void setVectorLocation(Vector3dInterface newDirection) {
        LocationVector = newDirection;
    }

    /**
     * Sets the current velocity as a vector.
     * @param newVelocity
     */
    public void setVectorVelocity(Vector3dInterface newVelocity){
        VelocityVector = newVelocity;
    }

    /**
     * Sets the colour of a body.
     * @param col
     */
    public void setColour(Color col){
        this.color = col;
    }

    // --- Get-Methods ---

    /**
     * Gets the mass.
     * @return
     */
    public double getMASS()
    {
        return this.MASS;
    }

    /**
     * Gets the radius.
     * @return
     */
    public double getRADIUS()
    {
        return this.RADIUS;
    }

    /**
     * Gets the density.
     */
    public double getDensity()
    {
        return this.DENSITY;
    }

    /**
     * Gets the X rotation.
     * @return
     */
    public double getX_ROTATION()
    {
        return this.X_ROTATION;
    }

    /**
     * Gets the Y rotation.
     * @return
     */
    public double getY_ROTATION()
    {
        return this.Y_ROTATION;
    }

    /**
     * Gets the Z rotation.
     * @return
     */
    public double getZ_ROTATION()
    {
        return this.Z_ROTATION;
    }

    /**
     * Returns the current position as a vector.
     * @return
     */
    public Vector3dInterface getVectorLocation()
    {
        return this.LocationVector;
    }

    /**
     * Returns the current velocity as a vector.
     * @return
     */
    public Vector3dInterface getVectorVelocity()
    {
        return this.VelocityVector;
    }

    /**
     * Returns the colour of a body.
     * @return
     */
    public Color getColour()
    {
        return color;
    }


    /**
     * Converts the body to a string.
     */
    public abstract String toString();

    public abstract void initPosition();
}
