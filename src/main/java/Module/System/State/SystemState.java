package Module.System.State;

import Module.Math.Vector3dInterface;

import java.util.List;

public class SystemState implements StateInterface {
    private RateInterface rate;
    private List<Vector3dInterface> positions;

    @Override
    public RateInterface getRateOfChange() {
        return this.rate;
    }

    @Override
    public void setRateOfChange(RateInterface newRate) {
        this.rate = newRate;
    }

    @Override
    public List<Vector3dInterface> getPositions() {
        return this.positions;
    }

    @Override
    public void setPositions(List<Vector3dInterface> v) {
        this.positions = v;
    }
}
