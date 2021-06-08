package Module.System.State;

import Module.Math.Vector3dInterface;

public class RateOfChange implements RateInterface<Vector3dInterface> {
    Vector3dInterface vel;

    public RateOfChange(Vector3dInterface v) {
        this.vel = v;
    }

    @Override
    public Vector3dInterface get() {
        return this.vel;
    }

    @Override
    public void set(Vector3dInterface v) {
        this.vel = v;
    }
}
