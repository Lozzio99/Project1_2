package group17.phase1.Titan.Simulations.PendulumSimulation;

import group17.phase1.Titan.Interfaces.RateInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.Physics.Math.Vector3D;
import group17.phase1.Titan.System.RateOfChange;
import group17.phase1.Titan.System.SystemState;

import java.util.ArrayList;
import java.util.List;

import static group17.phase1.Titan.Main.simulation;

public class PendulumState implements StateInterface {
    List<Vector3dInterface> angles;
    RateInterface rate;

    public PendulumState() {
        this.angles = new ArrayList<>();
        this.rate = new RateOfChange();
    }

    @Override
    public StateInterface state0() {
        double x1 = (((PendulumBody) simulation.system().getCelestialBodies().get(0)).getAngle());
        double x2 = (((PendulumBody) simulation.system().getCelestialBodies().get(1)).getAngle());
        this.angles.add(new Vector3D(x1, x1, 0));
        this.angles.add(new Vector3D(x2, x2, 0));
        this.initialVelocity();
        return this;
    }

    @Override
    public List<Vector3dInterface> getPositions() {
        return angles;
    }

    @Override
    public void setPositions(List<Vector3dInterface> v) {
        this.angles = v;
    }

    @Override
    public RateInterface getRateOfChange() {
        return rate;
    }

    @Override
    public void initialVelocity() {
        ((RateOfChange) this.rate).state0();
    }

    @Override
    public StateInterface addMul(double step, RateInterface rate) {
        PendulumBody[] p = {(PendulumBody) simulation.getBody("1"),
                (PendulumBody) simulation.getBody("2")};

        StateInterface newState = new SystemState();
        for (int i = 0; i < this.getPositions().size(); i++) {
            p[i].setAngle(p[i].getAngle() + rate.getVelocities().get(i).getX());

            this.getPositions().get(i).setX(p[i].getLength() * Math.sin(p[i].getAngle()));
            this.getPositions().get(i).setY(p[i].getLength() * Math.cos(p[i].getAngle()));
            newState.getPositions().add(this.getPositions().get(i).clone());
            newState.getRateOfChange().getVelocities().add(rate.getVelocities().get(i));
        }
        return newState;
    }
}
