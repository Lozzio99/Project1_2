package group17.Math.Lib;

import group17.Interfaces.Vector3dInterface;
import org.jetbrains.annotations.Contract;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.round;

/**
 * Approximated version of the vector 3d.
 * Intended to be used to link less accurate
 * scheduling decisions , in order to give a range
 * of choice which is in the bounds of the provided epsilon.
 */
public class ApproxV3D extends Vector3D {
    /**
     * The constant epsilon.
     */
    public static double epsilon;

    static {
        ApproxV3D.epsilon = 1e-8;  //override var
    }

    /**
     * Instantiates a new Approx v 3 d.
     *
     * @param x the x
     * @param y the y
     * @param z the z
     */
    public ApproxV3D(double x, double y, double z) {
        super(x, y, z);
    }

    public ApproxV3D(Vector3dInterface v) {
        super(v.getX(), v.getY(), v.getZ());
    }


    @Override
    public int hashCode() {
        int hash = 11;
        hash = 31 * hash + Long.hashCode(round(this.x / ApproxV3D.epsilon));
        hash = 31 * hash + Long.hashCode(round(this.y / ApproxV3D.epsilon));
        hash = 31 * hash + Long.hashCode(round(this.z / ApproxV3D.epsilon));
        return hash;
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object o) {
        if (o instanceof Vector3dInterface) {
            Vector3dInterface v = (Vector3dInterface) o;
            return (abs(v.getX() - x) <= ApproxV3D.epsilon) &&
                    (abs(v.getY() - y) <= ApproxV3D.epsilon) &&
                    (abs(v.getZ() - z) <= ApproxV3D.epsilon);
        }
        return false;
    }

}
