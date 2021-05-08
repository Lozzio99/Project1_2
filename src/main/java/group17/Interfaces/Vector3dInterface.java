/*
 * @author Pieter Collins, Christof Seiler, Katerina Stankova, Nico Roos, Katharina Schueller
 * @version 0.99.0
 *
 * This interface serves as the API for students in phase 1.
 */

package group17.Interfaces;


import group17.Math.Point3D;

public interface Vector3dInterface {

    /**
     * @return a Point3D from a given Vector
     * @see Point3D
     */
    Point3D fromVector();

    /**
     * @return the x value
     */
    double getX();

    /**
     * set the x value
     */
    void setX(double x);

    /**
     * @return the y value
     */
    double getY();

    /**
     * set the y value
     */
    void setY(double y);

    /**
     * @return the z value
     */
    double getZ();

    /**
     * set the z value
     */
    void setZ(double z);

    /**
     * Sum the other vector and the current one
     *
     * @param other the vector to be added
     * @return a new Vector3D
     */
    Vector3dInterface add(Vector3dInterface other);

    /**
     * Subtract the other vector from the current one
     *
     * @param other the vector to be subtracted
     * @return a new Vector3D
     */
    Vector3dInterface sub(Vector3dInterface other);

    /**
     * Multiply the current vector by a scalar
     *
     * @param scalar the scalar to be multiplied
     * @return a new Vector3D
     */
    Vector3dInterface mul(double scalar);

    /**
     * Divide the current vector by a scalar
     *
     * @param scalar the scalar to be divided
     * @return a new Vector3D
     */
    Vector3dInterface div(double scalar);


    /**
     * Scalar x vector multiplication, followed by an addition
     *
     * @param scalar the double used in the multiplication step
     * @param other  the vector used in the multiplication step
     * @return the result of the multiplication step added to this vector,
     * for example:
     * <p>
     * Vector3d a = Vector();
     * double h = 2;
     * Vector3d b = Vector();
     * ahb = a.addMul(h, b);
     * <p>
     * ahb should now contain the result of this mathematical operation:
     * a+h*b
     */
    Vector3dInterface addMul(double scalar, Vector3dInterface other);

    /**
     * @return the Euclidean norm of a vector
     */
    double norm();

    /**
     * @return the Euclidean distance between two vectors
     */
    double dist(Vector3dInterface other);

    /**
     * @return A string in this format:
     * Vector3d(-1.0, 2, -3.0) should print out (-1.0,2.0,-3.0)
     */
    String toString();

    /**
     * safe clone of the current vector
     *
     * @return a new Vector3D with given x,y,z positions
     */
    Vector3dInterface clone();


    double heading();

    void mark();

    boolean isMarked();

}
