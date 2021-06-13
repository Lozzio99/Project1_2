package phase3.System;

import phase3.Math.ADT.Vector3dInterface;
import phase3.System.Bodies.CelestialBody;
import phase3.System.State.StateInterface;

import java.util.List;

public interface SystemInterface {
    StateInterface<Vector3dInterface> getState();

    List<CelestialBody> getCelestialBodies();

    void init();

    void updateState(StateInterface<Vector3dInterface> step);
}
