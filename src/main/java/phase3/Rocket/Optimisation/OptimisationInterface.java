package phase3.Rocket.Optimisation;

import phase3.Math.ADT.Vector3dInterface;

public interface OptimisationInterface {

    /**
     * Minimisation function
     *
     * @param initVector initial vector to be optimised
     * @param fX         function, which value is to be minimised (ideal fX(V)~0)
     * @return optimal vector
     */
    Vector3dInterface minimiseVector(Vector3dInterface initVector, ObjectiveFunction fX);

}
