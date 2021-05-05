package group17.System.Bodies;


import group17.Interfaces.Vector3dInterface;
import group17.Math.Point3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class CelestialBody {
    Color color;

    double MASS;
    double RADIUS;
    double DENSITY;


    List<Point3D> path;
    Vector3dInterface vectorVelocity;
    Vector3dInterface vectorLocation;

    public CelestialBody() {
        this.path = new ArrayList<>();
    }

    // --- Set-Methods ---

    /**
     * Sets the density.
     *
     * @param DENSITY
     */
    public void setDENSITY(double DENSITY) {
        this.DENSITY = DENSITY;
    }

    /**
     * Gets the mass.
     *
     * @return
     */
    public double getMASS() {
        return this.MASS;
    }

    /**
     * Sets the mass.
     *
     * @param mass
     */
    public void setMASS(double mass) {
        this.MASS = mass;
    }

    /**
     * Gets the radius.
     *
     * @return
     */
    public double getRADIUS() {
        return this.RADIUS;
    }

    // --- Get-Methods ---

    /**
     * Sets the radius.
     *
     * @param RADIUS
     */
    public void setRADIUS(double RADIUS) {
        this.RADIUS = RADIUS;
    }

    /**
     * Gets the density.
     */
    public double getDensity() {
        return this.DENSITY;
    }

    /**
     * Returns the colour of a body.
     *
     * @return
     */
    public Color getColour() {
        return color;
    }

    /**
     * Sets the colour of a body.
     *
     * @param col
     */
    public void setColour(Color col) {
        this.color = col;
    }

    /**
     * Converts the body to a string.
     */
    public abstract String toString();

    public abstract void initProperties();

    public Vector3dInterface getVectorLocation() {
        return this.vectorLocation;
    }

    public void setVectorLocation(Vector3dInterface vector3D) {
        this.vectorLocation = vector3D;
    }

    public Vector3dInterface getVectorVelocity() {
        return this.vectorVelocity;
    }

    public void setVectorVelocity(Vector3dInterface vector3D) {
        this.vectorVelocity = vector3D;
    }

}
