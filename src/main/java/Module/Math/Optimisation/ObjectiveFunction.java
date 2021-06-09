package Module.Math.Optimisation;

import Module.Math.Vector3dInterface;

/**
 * The interface Newt raph function.
 */
public interface ObjectiveFunction {

    Vector3dInterface modelFx(Vector3dInterface vector);

}
