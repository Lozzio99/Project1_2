package Module.System;

import Module.Math.Vector3D;
import Module.Math.Vector3dInterface;
import Module.System.Bodies.CelestialBody;
import Module.System.Module.ModuleSimulator;
import Module.System.State.StateInterface;
import Module.System.State.SystemState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TitanSystem implements SystemInterface {
    private final AtomicReference<Thread> systemThread = new AtomicReference<>();
    private volatile StateInterface<Vector3dInterface> state;
    private List<CelestialBody> bodies;

    @Override
    public StateInterface<Vector3dInterface> getState() {
        return state;
    }

    @Override
    public List<CelestialBody> getCelestialBodies() {
        return bodies;
    }

    @Override
    public void init() {
        this.state = new SystemState<>(new Vector3D());
        this.bodies = new ArrayList<>();
        this.bodies.add(new ModuleSimulator());
        this.bodies.get(0).initProperties();
        this.state = new SystemState<>(this.bodies.get(0).getVectorLocation(), this.bodies.get(0).getVectorVelocity());
    }

    @Override
    public synchronized void start() {
        systemThread.set(new Thread(this, "SystemUpdater"));
        systemThread.get().start();
    }

    @Override
    public synchronized void updateState(StateInterface<Vector3dInterface> step) {
        this.state.set(step.get().clone());
        this.state.getRateOfChange().set(step.getRateOfChange().get().clone());
    }

    @Override
    public void run() {

    }
}
