package group17.phase1.titan.lib.geometry;

import group17.phase1.titan.lib.interfaces.Vector;

public class Vector3D implements Vector
{
    double x,y,z;
    public Vector3D() {
        this.x = this.y = this.z = 0;
    }

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(Point3D p1, Point3D p2) {
        this.x = p2.x - p1.x;
        this.y = p2.y - p1.y;
        this.z = p2.z - p1.z;
    }

    public static double dot(Vector v1, Vector v2) {
        return v1.getX()*v2.getX()+ v1.getY()*v2.getY() + v1.getZ()*v2.getZ();
    }

    public static Vector cross(Vector v1, Vector v2) {
        return new Vector3D(
                v1.getY()*v2.getZ() - v1.getZ()*v2.getY(),
                v1.getZ()*v2.getX() - v1.getX()*v2.getZ(),
                v1.getX()*v2.getY() - v1.getY()*v2.getX());
    }

    public static Vector normalize(Vector v) {
        double magnitude = Math.sqrt(v.getX()*v.getX() + v.getY()*v.getY() + v.getZ()*v.getZ());
        return new Vector3D(v.getX()/magnitude, v.getY()/magnitude, v.getZ()/magnitude);
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getZ() {
        return this.z;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public void setZ(double z) {
        this.z = z;
    }

}
