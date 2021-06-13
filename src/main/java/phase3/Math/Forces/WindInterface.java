package phase3.Math.Forces;

import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Functions.ODEFunctionInterface;
import phase3.Math.Noise.Noise;

public interface WindInterface {

    void init();

    ODEFunctionInterface<Vector3dInterface> getWindFunction();

    Noise getNoise();


}