package phase3.System;

import phase3.Math.ADT.Vector3dInterface;
import phase3.Rocket.ModuleSimulator;
import phase3.System.Bodies.CelestialBody;
import phase3.System.State.StateInterface;
import phase3.System.State.SystemState;

import java.util.ArrayList;
import java.util.List;

public class TitanSystem implements SystemInterface {
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
        this.bodies = new ArrayList<>();
        this.bodies.add(new ModuleSimulator());
        this.bodies.get(0).initProperties();
        this.state = new SystemState<>(this.bodies.get(0).getVectorLocation(), this.bodies.get(0).getVectorVelocity());
    }

    @Override
    public synchronized void updateState(StateInterface<Vector3dInterface> step) {
        this.state = step;
    }
}
