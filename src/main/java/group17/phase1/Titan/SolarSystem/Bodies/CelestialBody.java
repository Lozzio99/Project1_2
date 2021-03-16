package group17.phase1.Titan.SolarSystem.Bodies;

import group17.phase1.Titan.Graphics.Geometry.Point3D;
import group17.phase1.Titan.Simulation.Gravity.*;

public abstract class CelestialBody implements ODESolverInterface, StateInterface, RateInterface
{

    public static final double G = 6.67e-11;

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



    public Vector3dInterface getVectorLocation(){
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



    /*
     * A class for solving a general differential equation dy/dt = f(t,y)
     *     y(t) describes the state of the system at time t
     *     f(t,y(t)) defines the derivative of y(t) with respect to time t
     */


    /*
     * Solve the differential equation by taking multiple steps.
     *
     * @param   f       the function defining the differential equation dy/dt=f(t,y)
     * @param   y0      the starting state
     * @param   ts      the times at which the states should be output, with ts[0] being the initial time
     * @return  an array of size ts.length with all intermediate states along the path
     */
    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {
        return new StateInterface[0];
    }


    /*
     * Solve the differential equation by taking multiple steps of equal size, starting at time 0.
     * The final step may have a smaller size, if the step-size does not exactly divide the solution time range
     *
     * @param   f       the function defining the differential equation dy/dt=f(t,y)
     * @param   y0      the starting state
     * @param   tf      the final time
     * @param   h       the size of step to be taken
     * @return  an array of size round(tf/h)+1 including all intermediate states along the path
     */
    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {
        return new StateInterface[0];
    }

    /*
     * Update rule for one step.
     *
     * @param   f   the function defining the differential equation dy/dt=f(t,y)
     * @param   t   the time
     * @param   y   the state
     * @param   h   the step size
     * @return  the new state after taking one step
     */
    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        return null;
    }


    /**
     * Update a state to a new state computed by: this + step * rate
     *
     * @param step   The time-step of the update
     * @param rate   The average rate-of-change over the time-step. Has dimensions of [state]/[time].
     * @return The new state after the update. Required to have the same class as 'this'.
     */
    @Override
    public StateInterface addMul(double step, RateInterface rate)
    {
        return null;
    }


}
