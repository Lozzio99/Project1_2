package group17.System.Bodies;


import group17.Interfaces.Vector3dInterface;

import java.awt.*;

public abstract class CelestialBody {
    private Color color;

    private double MASS;
    private double RADIUS;
    private double DENSITY;


    private Vector3dInterface vectorVelocity;
    private Vector3dInterface vectorLocation;

    private boolean collided;

    public CelestialBody() {
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

    public void initProperties() {
        this.setCollided(false);
    }

    public boolean isCollided() {
        return collided;
    }

    public void setCollided(boolean bool) {
        this.collided = bool;
    }

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
