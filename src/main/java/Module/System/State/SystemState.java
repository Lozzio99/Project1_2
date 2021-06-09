package Module.System.State;

import Module.Math.Vector3D;
import Module.Math.Vector3dInterface;

public class SystemState implements StateInterface<Vector3dInterface> {
    Vector3dInterface position;
    RateInterface<Vector3dInterface> vel;

    public SystemState(Vector3dInterface pos) {
        this.position = pos;
        this.vel = new RateOfChange(new Vector3D());
    }

    public SystemState(Vector3dInterface pos, Vector3dInterface vel) {
        this.position = pos;
        this.vel = new RateOfChange(vel);
    }

    @Override
    public StateInterface<Vector3dInterface> addMul(double step, RateInterface<Vector3dInterface> rate) {
        return new SystemState(this.position.addMul(step, rate.get()), rate.get());
    }


    @Override
    public Vector3dInterface get() {
        return position;
    }

    @Override
    public void set(Vector3dInterface v) {
        this.position = v;
    }

    @Override
    public RateInterface<Vector3dInterface> getRateOfChange() {
        return vel;
    }
}
