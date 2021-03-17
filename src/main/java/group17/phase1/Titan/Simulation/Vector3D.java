package group17.phase1.Titan.Simulation;


import group17.phase1.Titan.Graphics.Geometry.Point3D;
import group17.phase1.Titan.Interfaces.Vector3dInterface;

public class Vector3D implements Vector3dInterface
{

    @Override
    public Vector3dInterface add(Vector3dInterface other)
    {
        this.setX(this.getX()+ other.getX());
        this.setY(this.getY()+other.getY());
        this.setZ(this.getZ()+other.getZ());
        return this;
    }

    @Override
    public Vector3dInterface sub(Vector3dInterface other)
    {
        this.x = other.getX() - this.x;
        this.y = other.getY() - this.y;
        this.z = other.getZ() - this.z;
        return this;
    }

    @Override
    public Vector3dInterface mul(double scalar)
    {
        this.x = this.x * scalar;
        this.y = this.y * scalar;
        this.z = this.z * scalar;
        return this;
    }

    @Override
    public Vector3dInterface div(double scalar)
    {
        this.x = this.x/scalar;
        this.y = this.y/scalar;
        this.z = this.z/scalar;
        return this;
    }

    @Override
    public Vector3dInterface addMul(double scalar, Vector3dInterface other) {
        Vector3dInterface newV = new Vector3D(other.getX() * scalar, other.getY() * scalar, other.getZ() * scalar);
        return this.add(newV);
    }

    @Override
    public double norm() {
        double x = Math.pow(this.getX(),2);
        double y = Math.pow(this.getY(),2);
        double z = Math.pow(this.getZ(),2);
        return Math.sqrt(x+y+z);
    }

    @Override
    public double dist(Vector3dInterface other) {
        double v1 =Math.pow(other.getX()-this.getX(),2);
        double v2 =Math.pow(other.getY()-this.getY(),2);
        double v3 =Math.pow(other.getZ()-this.getZ(),2);
        return Math.sqrt(v1+v2+v3);
    }

    public static double dist(Vector3dInterface first, Vector3dInterface other) {
        double v1 =Math.pow(other.getX()-first.getX(),2);
        double v2 =Math.pow(other.getY()-first.getY(),2);
        double v3 =Math.pow(other.getZ()-first.getZ(),2);
        return Math.sqrt(v1+v2+v3);
    }

    public static Vector3dInterface unitVectorDistance(Vector3dInterface from, Vector3dInterface to){
        return normalize(new Vector3D(
                from.getX()-to.getX(),
                from.getY()-to.getY(),
                from.getZ()-to.getZ()));
    }

    @Override
    public String toString(){
        return "(" + this.getX() + "," + this.getY() + "," + this.getZ() + ")";
    }

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

    public static double dot(Vector3dInterface v1, Vector3dInterface v2) {
        return v1.getX()*v2.getX()+ v1.getY()*v2.getY() + v1.getZ()*v2.getZ();
    }

    public static Vector3dInterface cross(Vector3dInterface v1, Vector3dInterface v2)
    {
        return new Vector3D(
                v1.getY()*v2.getZ() - v1.getZ()*v2.getY(),
                v1.getZ()*v2.getX() - v1.getX()*v2.getZ(),
                v1.getX()*v2.getY() - v1.getY()*v2.getX());
    }

    public static Vector3dInterface normalize(Vector3dInterface v)
    {
        double magnitude = Math.sqrt(v.getX()*v.getX() + v.getY()*v.getY() + v.getZ()*v.getZ());
        v.setX(v.getX()/magnitude);
        v.setY(v.getY()/magnitude);
        v.setZ(v.getZ()/magnitude);
        return v;
    }

    @Override
    public Point3D fromVector(){
        return new Point3D(this.getX(),this.getY(),this.getZ());
    }

    @Override
    public Vector3dInterface clone() {
        return new Vector3D(this.getX(),this.getY(),this.getZ());
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
