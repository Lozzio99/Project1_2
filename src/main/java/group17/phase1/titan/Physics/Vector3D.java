package group17.phase1.titan.Physics;


import group17.phase1.titan.interfaces.Vector3dInterface;

public class Vector3D extends group17.phase1.titan.lib.geometry.Vector3D implements Vector3dInterface
{

    public Vector3D(double x, double y, double z) {
        super(x,y,z);
    }

    /* TODO : think if we need to create new objects or if we can just modify the current vector */

    @Override
    public Vector3dInterface add(Vector3dInterface other)
    {
        return new Vector3D(this.getX()+other.getX(),
                            this.getY()+other.getY(),
                            this.getZ()+other.getZ());
    }

    @Override
    public Vector3dInterface sub(Vector3dInterface other) {
        return new Vector3D(this.getX()-other.getX(),
                            this.getY()-other.getY(),
                            this.getZ()-other.getZ());
    }

    @Override
    public Vector3dInterface mul(double scalar) {
        return new Vector3D(this.getX()*scalar,
                            this.getY()*scalar,
                            this.getZ()*scalar);
    }

    @Override
    public Vector3dInterface addMul(double scalar, Vector3dInterface other) {
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
    public double dist(Vector3dInterface other) {
        double v1 =Math.pow(other.getX()-this.getX(),2);
        double v2 =Math.pow(other.getY()-this.getY(),2);
        double v3 =Math.pow(other.getZ()-this.getZ(),2);
        return Math.sqrt(v1+v2+v3);
    }

    @Override
    public String toString(){
        return "( %s ,%s ,%s )".formatted(this.getX(), this.getY(), this.getZ());
    }
}
