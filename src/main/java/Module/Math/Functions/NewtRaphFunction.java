package Module.Math.Functions;

import Module.Math.Vector3dInterface;

/**
 * The interface Newt raph function.
 */
public interface NewtRaphFunction {


    /**
     * Vector-valued function of the model to be approximated
     *
     * @param vector input vector (e.g. velocity)
     * @return result of the function
     */
    Vector3dInterface modelFx(Vector3dInterface vector);


}
