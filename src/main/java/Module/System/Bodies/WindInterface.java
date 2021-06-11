package Module.System.Bodies;

import Module.Math.ADT.Vector3dInterface;
import Module.Math.Functions.ODEFunctionInterface;

public interface WindInterface {

    ODEFunctionInterface<Vector3dInterface> getWindFunction();
}