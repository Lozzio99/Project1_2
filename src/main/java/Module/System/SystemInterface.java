package Module.System;

import Module.System.Bodies.CelestialBody;
import Module.System.State.StateInterface;

import java.util.List;

public interface SystemInterface extends Runnable {
    StateInterface getState();

    List<CelestialBody> getCelestialBodies();

    void init();

    void start();

    void updateState(StateInterface step);
}
