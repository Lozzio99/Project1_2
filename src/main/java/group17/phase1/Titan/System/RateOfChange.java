package group17.phase1.Titan.System;

import group17.phase1.Titan.Interfaces.RateInterface;
import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.Main;

import java.util.ArrayList;
import java.util.List;

public class RateOfChange implements RateInterface {
    List<Vector3dInterface> vel;

    public RateOfChange() {
        this.vel = new ArrayList<>();
    }

    public RateOfChange state0() {
        for (int i = 0; i < Main.simulation.system().systemState().getPositions().size(); i++)
            this.vel.add(Main.simulation.system().getCelestialBodies().get(i).getVectorVelocity());
        return this;
    }

    public void setVel(List<Vector3dInterface> vel) {
        this.vel = vel;
    }

    @Override
    public List<Vector3dInterface> getRateOfChange() {
        return this.vel;
    }
}
