package group17.phase1.Titan.Bodies.CelestialBodies;

abstract class CelestialBody
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

    public void setMASS(double mass){
        this.MASS = mass;
    }

    public void setRADIUS(double RADIUS) {
        this.RADIUS = RADIUS;
    }

    public void setDENSITY(double DENSITY) {
        this.DENSITY = DENSITY;
    }

    public void setX_LOCATION(double x_LOCATION) {
        this.X_LOCATION = x_LOCATION;
    }

    public void setY_LOCATION(double y_LOCATION) {
        this.Y_LOCATION = y_LOCATION;
    }

    public void setZ_LOCATION(double z_LOCATION) {
        this.Z_LOCATION = z_LOCATION;
    }

    public void setX_VELOCITY(double x_VELOCITY) {
        this.X_VELOCITY = x_VELOCITY;
    }

    public void setY_VELOCITY(double y_VELOCITY) {
        this.Y_VELOCITY = y_VELOCITY;
    }

    public void setZ_VELOCITY(double z_VELOCITY) {
        this.Z_VELOCITY = z_VELOCITY;
    }
}
