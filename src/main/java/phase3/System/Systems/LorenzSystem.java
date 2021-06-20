package phase3.System.Systems;

import phase3.Math.ADT.Vector3dInterface;
import phase3.System.Bodies.CelestialBody;
import phase3.System.Bodies.Particle;
import phase3.System.State.StateInterface;
import phase3.System.State.SystemState;

import java.util.ArrayList;
import java.util.List;

public class LorenzSystem implements SystemInterface {

    private final List<CelestialBody> bodies = new ArrayList<>();
    private StateInterface<Vector3dInterface> state;

    @Override
    public StateInterface<Vector3dInterface> getState() {
        return this.state;
    }

    @Override
    public List<CelestialBody> getCelestialBodies() {
        return this.bodies;
    }

    @Override
    public void init() {
        this.bodies.clear();
        Vector3dInterface[] pos = new Vector3dInterface[200];
        for (int i = 0; i < pos.length; i++) {
            this.bodies.add(new Particle(i + 1));
            this.bodies.get(i).initProperties();
            pos[i] = this.bodies.get(i).getVectorLocation();
        }
        this.state = new SystemState<>(pos);

    }

    @Override
    public void updateState(StateInterface<Vector3dInterface> step) {
        this.state = step;
    }
}
