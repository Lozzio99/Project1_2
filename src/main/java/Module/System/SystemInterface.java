package Module.System;

import Module.Math.Vector3dInterface;
import Module.System.Bodies.CelestialBody;
import Module.System.State.StateInterface;

import java.util.List;

public interface SystemInterface extends Runnable {
    StateInterface<Vector3dInterface> getState();

    List<CelestialBody> getCelestialBodies();

    void init();

    void start();

    void updateState(StateInterface<Vector3dInterface> step);
}
