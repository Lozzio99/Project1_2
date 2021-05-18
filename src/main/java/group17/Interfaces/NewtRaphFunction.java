package group17.Interfaces;

public interface NewtRaphFunction {


    /**
     * Vector-valued function of the model to be approximated
     * @param vector input vector (e.g. velocity)
     * @return result of the function
     */
    Vector3dInterface modelFx(Vector3dInterface vector);


}
