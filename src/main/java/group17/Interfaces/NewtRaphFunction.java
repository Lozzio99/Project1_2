package group17.Interfaces;

public interface NewtRaphFunction {


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
