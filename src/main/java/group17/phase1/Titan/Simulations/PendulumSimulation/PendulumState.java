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
        double v1 = (simulation.system().getCelestialBodies().get(0).getVectorVelocity().getX());
        double v2 = (simulation.system().getCelestialBodies().get(1).getVectorVelocity().getX());
        this.angles.add(new Vector3D(v1, x1, 0));
        this.angles.add(new Vector3D(v2, x2, 0));
        this.rate.getVelocities().add(new Vector3D(0, 0, 0));
        this.rate.getVelocities().add(new Vector3D(0, 0, 0));
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


        // velocity += acceleration
        // angle += velocity


        //state has velocity, angle
        //rate has acceleration,velocity,0


        for (int i = 0; i < this.getPositions().size(); i++) {
            this.getPositions().set(i, this.getPositions().get(i).add(rate.getVelocities().get(i)));
            newState.getPositions().add(this.getPositions().get(i));
            newState.getRateOfChange().getVelocities().add(new Vector3D());
        }
        return newState;
    }
}
