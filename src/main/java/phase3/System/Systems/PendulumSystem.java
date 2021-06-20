package phase3.System.Systems;

import phase3.Math.ADT.Vector3D;
import phase3.Math.ADT.Vector3dInterface;
import phase3.System.Bodies.CelestialBody;
import phase3.System.State.StateInterface;
import phase3.System.State.SystemState;

import java.util.List;

import static java.lang.Math.PI;

public class PendulumSystem implements SystemInterface {
    private StateInterface<Vector3dInterface> state;

    @Override
    public StateInterface<Vector3dInterface> getState() {
        return state;
    }

    @Override
    public List<CelestialBody> getCelestialBodies() {
        return null;
    }

    @Override
    public void init() {
        Vector3dInterface[] state = new Vector3dInterface[4];
        state[0] = new Vector3D(20, 500, PI / 2); //mass,length,angle
        state[1] = new Vector3D(30, 400, -15);
        state[2] = new Vector3D(0, 0, 10); //dMass, dLength, dAngle
        state[3] = new Vector3D(0, 0, 0.1);
        this.state = new SystemState<>(state);
    }

    @Override
    public void updateState(StateInterface<Vector3dInterface> step) {
        this.state = step;
    }
}
