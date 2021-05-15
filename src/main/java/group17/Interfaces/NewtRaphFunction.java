package group17.Interfaces;

public interface NewtRaphFunction {


    /**
     * Vector-valued function of the model to be approximated
     *
     * @param vector input vector (e.g. velocity)
     * @return result of the function
     */
    Vector3dInterface modelFx(Vector3dInterface vector);


    /**
     * Method simulates trajectory of a spacecraft in Solar System
     *
     * @param initPos      starting position
     * @param initVelocity initial velocity (CI)
     * @param time         end time
     * @return position of the spacecraft at the end time
     */

    Vector3dInterface stateFX(Vector3dInterface initPos, Vector3dInterface initVelocity, double time);


}
