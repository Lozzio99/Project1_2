package Module.Math.ADT;

import java.util.Arrays;

import static java.lang.StrictMath.*;


/**
 * The type Vector 3 d.
 */
public class Vector3D implements Vector3dInterface {
    /**
     * The X.
     */
    final private double[] val;
    final private int dim;

    /**
     * Instantiates a new Vector 3 d.
     */
    public Vector3D(int dim) {
        this.val = new double[this.dim = dim];
        this.val[0] = this.val[1] = this.val[2] = 0;
    }


    public Vector3D(double x, double y) {
        this.val = new double[this.dim = 3];
        this.val[0] = x;
        this.val[1] = y;
        this.val[2] = atan2(x, y); //the angle directly initialised
    }

    /**
     * Instantiates a new Vector 3 d.
     *
     * @param x the x
     * @param y the y
     * @param z the z
     */
    public Vector3D(double x, double y, double z) {
        this.val = new double[dim = 3];
        this.val[0] = x;
        this.val[1] = y;
        this.val[2] = z;
    }


    public Vector3D(double... val) {
        if (val.length == 0) {
            this.val = new double[]{0, 0, 0};
            this.dim = 3;
        } else {
            dim = val.length;
            this.val = val;
        }
    }

    /**
     * Normalises the vector.
     *
     * @param v the v
     * @return vector 3 d interface
     */
    public static Vector3dInterface normalize(Vector3dInterface v) {
        Vector3dInterface v2 = new Vector3D(v.getDim());
        double magnitude = sqrt(v.getX() * v.getX() + v.getY() * v.getY() + v.getZ() * v.getZ());
        v2.setX(v.getX() / magnitude);
        v2.setY(v.getY() / magnitude);
        v2.setZ(v.getZ() / magnitude);
        return v2;
    }

    @Override
    public int getDim() {
        return dim;
    }

    @Override
    public double[] getVal() {
        return val;
    }

    @Override
    public double get(int i) {
        if (i > this.val.length - 1) throw new IllegalArgumentException("Out of bounds");
        return this.val[i];
    }

    @Override
    public Vector3dInterface sumOf(Vector3dInterface... k) {
        Vector3dInterface res = new Vector3D(Arrays.copyOf(this.getVal(), this.getVal().length));
        for (int i = 0; i < k.length; i++) {
            for (int j = 0; j < k[i].getVal().length; j++) {
                res.getVal()[j] += k[i].getVal()[j];
            }
        }
        return res;
    }

    @Override
    public void setVal(double[] val) {
        if (val.length != this.dim) throw new IllegalArgumentException("Dimensions not matching");
        System.arraycopy(val, 0, this.val, 0, val.length);
    }

    /**
     * Gets the distance between two vectors.
     *
     * @param first the first
     * @param other the other
     * @return double double
     */
    public static double dist(Vector3dInterface first, Vector3dInterface other) {
        double v1 = pow(other.getX() - first.getX(), 2);
        double v2 = pow(other.getY() - first.getY(), 2);
        double v3 = pow(other.getZ() - first.getZ(), 2);
        return sqrt(v1 + v2 + v3);
    }

    /**
     * Gets the unit vector between two vectors.
     *
     * @param from the from
     * @param to   the to
     * @return vector 3 d interface
     */
    public static Vector3dInterface unitVectorDistance(Vector3dInterface from, Vector3dInterface to) {
        return normalize(new Vector3D(
                from.getX() - to.getX(),
                from.getY() - to.getY(),
                from.getZ() - to.getZ()));
    }

    /**
     * Returns the dot product of two vectors.
     *
     * @param v1 the v 1
     * @param v2 the v 2
     * @return double double
     */
    public static double dot(Vector3dInterface v1, Vector3dInterface v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ() * v2.getZ();
    }

    /**
     * Returns the cross of two vectors.
     *
     * @param v1 the v 1
     * @param v2 the v 2
     * @return vector 3 d interface
     */
    public static Vector3dInterface cross(Vector3dInterface v1, Vector3dInterface v2) {
        return new Vector3D(
                v1.getY() * v2.getZ() - v1.getZ() * v2.getY(),
                v1.getZ() * v2.getX() - v1.getX() * v2.getZ(),
                v1.getX() * v2.getY() - v1.getY() * v2.getX());
    }

    @Override
    public void set(double x, int i) {
        if (i > this.val.length - 1) throw new IllegalArgumentException("Out of bounds");
        this.val[i] = x;
    }

    /**
     * Adds another vector onto this vector.
     *
     * @return a new Vector3d
     */
    @Override
    public Vector3dInterface add(Vector3dInterface other) {
        Vector3dInterface sum = new Vector3D(new double[other.getDim()]);
        for (int i = 0; i < other.getVal().length; i++) {
            sum.getVal()[i] = this.getVal()[i] + other.getVal()[i];
        }
        return sum;
    }

    /**
     * Subtracts this vector from another vector.
     *
     * @return a new Vector3d
     */
    @Override
    public Vector3dInterface sub(Vector3dInterface other) {
        Vector3dInterface v = new Vector3D(new double[other.getDim()]);
        for (int i = 0; i < other.getVal().length; i++) {
            v.getVal()[i] = this.getVal()[i] - other.getVal()[i];
        }
        return v;
    }

    /**
     * @return the result of |this-other|
     */
    public Vector3dInterface absSub(Vector3dInterface other) {
        Vector3dInterface v = new Vector3D(new double[other.getDim()]);
        for (int i = 0; i < other.getVal().length; i++) {
            v.getVal()[i] = abs(this.getVal()[i] - other.getVal()[i]);
        }
        return v;
    }

    /**
     * Multiplies this vector by a scalar.
     *
     * @return a new Vector3d
     */
    @Override
    public Vector3dInterface mul(double scalar) {
        Vector3dInterface v = new Vector3D(new double[this.getDim()]);
        for (int i = 0; i < this.getVal().length; i++) {
            v.getVal()[i] = this.getVal()[i] * scalar;
        }
        return v;
    }

    @Override
    public Vector3dInterface div(Vector3dInterface other) {
        Vector3dInterface v = new Vector3D(new double[other.getDim()]);
        for (int i = 0; i < other.getVal().length; i++) {
            v.getVal()[i] = this.getVal()[i] / other.getVal()[i];
        }
        return v;
    }

    @Override
    public Vector3dInterface multiply(Vector3dInterface other) {
        Vector3dInterface v = new Vector3D(new double[other.getDim()]);
        for (int i = 0; i < other.getVal().length; i++) {
            v.getVal()[i] = this.getVal()[i] * other.getVal()[i];
        }
        return v;
    }

    /**
     * Divides this vector by a scalar.
     *
     * @return a new Vector3d
     */
    @Override
    public Vector3dInterface div(double scalar) {
        Vector3dInterface v = new Vector3D(new double[getDim()]);
        for (int i = 0; i < getVal().length; i++) {
            v.getVal()[i] = this.getVal()[i] / scalar;
        }
        return v;
    }

    /**
     * Adds another vector onto this one after it is multiplied by a scalar.
     *
     * @return a new Vector3d
     */
    @Override
    public Vector3dInterface addMul(double scalar, Vector3dInterface other) {
        Vector3dInterface newV = other.mul(scalar);
        return this.add(newV);
    }

    /**
     * Gets the norm of a vector.
     */
    @Override
    public double norm() {
        double sum = 0;
        for (double x : this.val) {
            sum += pow(x, 2);
        }
        return sqrt(sum);
    }

    /**
     * Gets the distance between this and another vector.
     */
    @Override
    public double dist(Vector3dInterface other) {
        double sum = 0;
        for (int i = 0; i < val.length; i++) {
            sum += pow(other.getVal()[i] - val[i], 2);
        }
        return sqrt(sum);
    }

    /**
     * Converts a vector to a string.
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("(");
        for (int i = 0; i < this.val.length; i++) {
            s.append(this.val[i]);
            if (i != this.val.length - 1)
                s.append(",");
        }
        return s + ")";
    }



    /**
     * Clones a Vector3dInterface.
     */
    @Override
    public Vector3dInterface clone() {
        return new Vector3D(Arrays.copyOf(val, val.length));
    }


    /**
     * Radian mode
     *
     * @return Angle of rotation for 2D vectors
     */
    @Override
    public double heading() {
        return Math.atan2(this.getY(), this.getX()) * (180 / PI);
    }

    @Override
    public double getX() {
        return this.val[0];
    }

    @Override
    public void setX(double x) {
        this.val[0] = x;
    }

    @Override
    public double getY() {
        return this.val[1];
    }

    @Override
    public void setY(double y) {
        this.val[1] = y;
    }

    @Override
    public double getZ() {
        return this.val[2];
    }

    @Override
    public void setZ(double z) {
        this.val[2] = z;
    }

    @Override
    public boolean isZero() {
        for (double x : this.val)
            if (x != 0)
                return false;
        return true;
    }


    /**
     * Implementation of the equals and HashCode methods for the scheduling purpose.
     * Vector decisions are stored in an hashmap linked to a Clock as a key or a StateInterface,
     * we therefore want the key to be mapped and retrieved in an approximate situation, to avoid
     * such an uniqueness in the hashcode of the Vectors there we therefore
     * decided to hash the first 8 significant figures and to compare vectors by an accuracy of 10^-8
     */
    @Override
    public int hashCode() {
        int hash = 11;
        hash = 31 * hash + Double.hashCode(this.val[0]);
        hash = 31 * hash + Double.hashCode(this.val[1]);
        hash = 31 * hash + Double.hashCode(this.val[2]);
        return hash;
    }

    /**
     * Actual accuracy is 10^-8 here too
     *
     * @param o the object to be compared to
     * @return true if the vector is equal up to a certain accuracy
     */

    @Override
    public boolean equals(Object o) {
        if (o instanceof Vector3dInterface) {
            Vector3dInterface v = (Vector3dInterface) o;
            return v.getX() == this.val[0] &&
                    v.getY() == this.val[1] &&
                    v.getZ() == this.val[2];
        }
        return false;
    }
}
