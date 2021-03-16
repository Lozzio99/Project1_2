package group17.phase1.Titan.Physics.Trajectories.Forces;

import group17.phase1.Titan.Graphics.Renderer.Point3D;

public class Vector3D implements Vector3DInterface
{

    /* TODO : think if we need to create new objects or if we can just modify the current vector */

    @Override
    public Vector3DInterface add(Vector3DInterface other)
    {
        return new Vector3D(this.getX()+other.getX(),
                            this.getY()+other.getY(),
                            this.getZ()+other.getZ());
    }

    @Override
    public Vector3DInterface sub(Vector3DInterface other) {
        return new Vector3D(this.getX()-other.getX(),
                            this.getY()-other.getY(),
                            this.getZ()-other.getZ());
    }

    @Override
    public Vector3DInterface mul(double scalar) {
        return new Vector3D(this.getX()*scalar,
                            this.getY()*scalar,
                            this.getZ()*scalar);
    }

    @Override
    public Vector3DInterface div(double scalar) {
        return new Vector3D(this.getX()/scalar,
                this.getY()/scalar,
                this.getZ()/scalar);
    }

    @Override
    public Vector3DInterface addMul(double scalar, Vector3DInterface other) {
        return this.add(other.mul(scalar));
    }

    @Override
    public double norm() {
        double x = Math.pow(this.getX(),2);
        double y = Math.pow(this.getY(),2);
        double z = Math.pow(this.getZ(),2);
        return Math.sqrt(x+y+z);
    }

    @Override
    public double dist(Vector3DInterface other) {
        double v1 =Math.pow(other.getX()-this.getX(),2);
        double v2 =Math.pow(other.getY()-this.getY(),2);
        double v3 =Math.pow(other.getZ()-this.getZ(),2);
        return Math.sqrt(v1+v2+v3);
    }

    public static double dist(Vector3DInterface first,Vector3DInterface other) {
        double v1 =Math.pow(other.getX()-first.getX(),2);
        double v2 =Math.pow(other.getY()-first.getY(),2);
        double v3 =Math.pow(other.getZ()-first.getZ(),2);
        return Math.sqrt(v1+v2+v3);
    }

    public static Vector3DInterface unitVectorDistance(Vector3DInterface from,Vector3DInterface to){
        return normalize(new Vector3D(from.getX()-to.getX(),
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

    public static double dot(Vector3DInterface v1, Vector3DInterface v2) {
        return v1.getX()*v2.getX()+ v1.getY()*v2.getY() + v1.getZ()*v2.getZ();
    }

    public static Vector3DInterface cross(Vector3DInterface v1, Vector3DInterface v2) {
        return new Vector3D(
                v1.getY()*v2.getZ() - v1.getZ()*v2.getY(),
                v1.getZ()*v2.getX() - v1.getX()*v2.getZ(),
                v1.getX()*v2.getY() - v1.getY()*v2.getX());
    }

    public static Vector3DInterface normalize(Vector3DInterface v) {
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
