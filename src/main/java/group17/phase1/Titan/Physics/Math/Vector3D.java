package group17.phase1.Titan.Physics.Math;


import group17.phase1.Titan.Interfaces.Vector3dInterface;


public class Vector3D implements Vector3dInterface
{


    /* TODO : think if we need to create new objects or if we can just modify the current vector */

    /**
     * Adds another vector onto this vector.
     *
     * @return a new Vector3d
     */
    @Override
    public Vector3dInterface add(Vector3dInterface other) {
        return new Vector3D
                (this.getX() + other.getX(),
                        this.getY() + other.getY(),
                        this.getZ() + other.getZ());
    }

    /**
     * Subtracts this vector from another vector.
     *
     * @return a new Vector3d
     */
    @Override
    public Vector3dInterface sub(Vector3dInterface other) {

        return new Vector3D(this.getX() - other.getX(),
                this.getY() - other.getY(),
                this.getZ() - other.getZ());
    }


    /**
     * Multiplies this vector by a scalar.
     *
     * @return a new Vector3d
     */
    @Override
    public Vector3dInterface mul(double scalar) {
        return new Vector3D(this.x * scalar,
                this.y * scalar,
                this.z * scalar);
    }


    /**
     * Divides this vector by a scalar.
     *
     * @return a new Vector3d
     */
    @Override
    public Vector3dInterface div(double scalar) {
        return new Vector3D(this.x / scalar,
                this.y / scalar,
                this.z / scalar);
    }

    /**
     * Adds another vector onto this one after it is multiplied by a scalar.
     *
     * @return a new Vector3d
     */
    @Override
    public Vector3dInterface addMul(double scalar, Vector3dInterface other) {
        Vector3dInterface newV = new Vector3D(other.getX() * scalar, other.getY() * scalar, other.getZ() * scalar);
        return this.add(newV);
    }

    /**
     * Gets the norm of a vector.
     */
    @Override
    public double norm() {
        double x = Math.pow(this.getX(),2);
        double y = Math.pow(this.getY(),2);
        double z = Math.pow(this.getZ(),2);
        return Math.sqrt(x+y+z);
    }

    /**
     * Gets the distance between this and another vector.
     */
    @Override
    public double dist(Vector3dInterface other) {
        double v1 =Math.pow(other.getX()-this.getX(),2);
        double v2 =Math.pow(other.getY()-this.getY(),2);
        double v3 =Math.pow(other.getZ()-this.getZ(),2);
        return Math.sqrt(v1+v2+v3);
    }

    /**
     * Gets the distance between two vectors.
     * @param first
     * @param other
     * @return
     */
    public static double dist(Vector3dInterface first, Vector3dInterface other) {
        double v1 =Math.pow(other.getX()-first.getX(),2);
        double v2 =Math.pow(other.getY()-first.getY(),2);
        double v3 =Math.pow(other.getZ()-first.getZ(),2);
        return Math.sqrt(v1+v2+v3);
    }




    /**
     * Gets the unit vector between two vectors.
     * @param from
     * @param to
     * @return
     */
    public static Vector3dInterface unitVectorDistance(Vector3dInterface from, Vector3dInterface to){
        return normalize(new Vector3D(
                from.getX()-to.getX(),
                from.getY()-to.getY(),
                from.getZ()-to.getZ()));
    }

    /**
     * Converts a vector to a string.
     */
    @Override
    public String toString() {
        String s = "(" +
                this.getX() + "," +
                this.getY() + "," +
                this.getZ() + ")";
        return s;
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

    public Vector3D(Point3D p){
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }

    public Vector3D(Point3D p1, Point3D p2) {
        this.x = p2.x - p1.x;
        this.y = p2.y - p1.y;
        this.z = p2.z - p1.z;
    }

    /**
     * Returns the dot product of two vectors.
     * @param v1
     * @param v2
     * @return
     */
    public static double dot(Vector3dInterface v1, Vector3dInterface v2) {
        return v1.getX()*v2.getX()+ v1.getY()*v2.getY() + v1.getZ()*v2.getZ();
    }


    /**
     * Returns the cross of two vectors.
     * @param v1
     * @param v2
     * @return
     */
    public static Vector3dInterface cross(Vector3dInterface v1, Vector3dInterface v2)
    {
        return new Vector3D(
                v1.getY()*v2.getZ() - v1.getZ()*v2.getY(),
                v1.getZ()*v2.getX() - v1.getX()*v2.getZ(),
                v1.getX()*v2.getY() - v1.getY()*v2.getX());
    }

    /**
     * Normalises the vector.
     * @param v
     * @return
     */
    public static Vector3dInterface normalize(Vector3dInterface v)
    {
        Vector3dInterface v2 = new Vector3D();
        double magnitude = Math.sqrt(v.getX() * v.getX() + v.getY() * v.getY() + v.getZ() * v.getZ());
        v2.setX(v.getX() / magnitude);
        v2.setY(v.getY() / magnitude);
        v2.setZ(v.getZ() / magnitude);
        return v2;
    }

    /**
     * Return a point3D from a Vector
     * @return
     */
    @Override
    public Point3D fromVector(){
        return new Point3D(this.getX(),this.getY(),this.getZ());
    }

    /**
     * Clones a Vector3dInterface.
     */
    @Override
    public Vector3dInterface clone() {
        return new Vector3D(this.getX(), this.getY(), this.getZ());
    }


    /**
     * Radian mode
     *
     * @return Angle of rotation for 2D vectors
     */
    @Override
    public double heading() {
        return Math.atan2(this.getY(), this.getX());
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
