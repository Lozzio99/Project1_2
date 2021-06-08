package Module.System;

import Module.System.Bodies.CelestialBody;
import Module.System.Bodies.Satellite;
import Module.System.State.StateInterface;
import Module.System.State.SystemState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TitanSystem implements SystemInterface {
    private final AtomicReference<Thread> systemThread = new AtomicReference<>();
    private StateInterface state;
    private List<CelestialBody> bodies;

    @Override
    public StateInterface getState() {
        return state;
    }

    @Override
    public List<CelestialBody> getCelestialBodies() {
        return bodies;
    }

    @Override
    public void init() {
        this.state = new SystemState();
        this.bodies = new ArrayList<>();
        this.bodies.add(new Satellite(Satellite.SatellitesEnum.TITAN));
    }

    @Override
    public synchronized void start() {
        systemThread.set(new Thread(this, "SystemUpdater"));
        systemThread.get().start();
    }

    @Override
    public void updateState(StateInterface step) {
        this.state = step;
    }

    @Override
    public void run() {

    }
}
