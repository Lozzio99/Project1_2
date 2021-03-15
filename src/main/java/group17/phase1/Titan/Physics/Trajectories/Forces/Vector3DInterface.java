/*
 * @author Pieter Collins, Christof Seiler, Katerina Stankova, Nico Roos, Katharina Schueller
 * @version 0.99.0
 * 
 * This interface serves as the API for students in phase 1.
 */

package group17.phase1.Titan.Physics.Trajectories.Forces;

public interface Vector3DInterface {

    double getX();
    double getY();
    double getZ();

    void setX(double x);
    void setY(double y);
    void setZ(double z);

    Vector3DInterface add(Vector3DInterface other);
    Vector3DInterface sub(Vector3DInterface other);
    Vector3DInterface mul(double scalar);
    Vector3DInterface div(double scalar);


    /**
     * Scalar x vector multiplication, followed by an addition
     * 
    * @param scalar the double used in the multiplication step
    * @param other  the vector used in the multiplication step
    * @return the result of the multiplication step added to this vector,
    * for example:
    * 
    *       Vector3d a = Vector();
    *       double h = 2;
    *       Vector3d b = Vector();
    *       ahb = a.addMul(h, b);
    *       
    * ahb should now contain the result of this mathematical operation:
    *       a+h*b
    */
    Vector3DInterface addMul(double scalar, Vector3DInterface other);
    
    /**
    * @return the Euclidean norm of a vector
    */
    double norm();
    
    /**
     * @return the Euclidean distance between two vectors
     */
    double dist(Vector3DInterface other);
    
    /**
    * @return A string in this format: 
    * Vector3d(-1.0, 2, -3.0) should print out (-1.0,2.0,-3.0)
    */
    String toString();
    
}
