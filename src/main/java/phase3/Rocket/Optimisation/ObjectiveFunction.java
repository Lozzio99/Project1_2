package phase3.Rocket.Optimisation;

import phase3.Math.ADT.Vector3dInterface;

/**
 * The interface Newt raph function.
 */
public interface ObjectiveFunction {

    Vector3dInterface modelFx(Vector3dInterface vector);

}
