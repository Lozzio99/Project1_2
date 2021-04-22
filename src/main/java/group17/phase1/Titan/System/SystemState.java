package group17.phase1.Titan.System;

import group17.phase1.Titan.Interfaces.RateInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.Physics.Bodies.CelestialBody;
import group17.phase1.Titan.Physics.Math.Vector3D;

import java.util.ArrayList;
import java.util.List;

import static group17.phase1.Titan.Main.simulation;

public class SystemState implements StateInterface {

    List<Vector3dInterface> positions;
    RateInterface rateOfChange;

    public SystemState() {
        this.positions = new ArrayList<>();
        this.rateOfChange = new RateOfChange();
    }

    public SystemState(List<Vector3dInterface> positions) {
        this.positions = positions;
    }

    public SystemState(Vector3dInterface positionsVector) {
        this.positions = new ArrayList<>();
        positions.add(positionsVector);
    }

    @Override
    public StateInterface state0() {
        for (CelestialBody c : simulation.system().getCelestialBodies())
            this.positions.add(c.getVectorLocation().clone());
        return this;
    }

    public void setPositions(List<Vector3dInterface> positions) {
        this.positions = positions;
    }

    @Override
    public List<Vector3dInterface> getPositions() {
        return this.positions;
    }

    @Override
    public StateInterface addMul(double step, RateInterface rate) {
        StateInterface newState = new SystemState();
        for (int i = 0; i < this.positions.size(); i++) {
            this.positions.set(i, this.positions.get(i).addMul(step, rate.getVelocities().get(i)));
            newState.getPositions().add(this.positions.get(i).clone());
            newState.getRateOfChange().getVelocities().add(rate.getVelocities().get(i));
        }
        return newState;
    }
    @Override
    public StateInterface rateMul(double step, RateInterface rate){ //!!
        StateInterface newState = new SystemState();
        for (int i = 0; i < this.positions.size(); i++) {
                newState.getPositions().add(rate.getVelocities().get(i).mul(step));
                newState.getRateOfChange().getVelocities().add(new Vector3D(0,0,0));
        }
        return newState;


    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < this.positions.size(); i++) {
            s.append(simulation.system().getCelestialBodies().get(i).toString());
            s.append("\tPV : ");
            s.append(this.positions.get(i).toString());
            s.append("\n");
        }
        return s.toString().trim();
    }

    @Override
    public StateInterface copy(StateInterface tobeCloned) {
        StateInterface s = new SystemState();
        for (Vector3dInterface v : tobeCloned.getPositions()) {
            s.getPositions().add(v.clone());
        }
        return s;
    }

    @Override
    public StateInterface multiply(double scalar) {
        if (this.positions.size() == 0)
            throw new RuntimeException(" Nothing to multiply ");

        for (int i = 0; i < this.positions.size(); i++) {
            this.positions.set(i, this.positions.get(i).mul(scalar));
            this.rateOfChange.getVelocities().set(i, this.rateOfChange.getVelocities().get(i).mul(scalar));
        }
        return this;
    }

    @Override
    public StateInterface div(double scalar) {
        if (this.positions.size() == 0)
            throw new RuntimeException(" Nothing to divide ");

        for (int i = 0; i < this.positions.size(); i++) {
            this.positions.set(i, this.positions.get(i).div(scalar));
            this.rateOfChange.getVelocities().set(i, this.rateOfChange.getVelocities().get(i).div(scalar));
        }
        return this;
    }

    @Override
    public StateInterface add(StateInterface tobeAdded) {
        if (this.positions.size() == 0)
            throw new RuntimeException(" Nothing to add ");

        for (int i = 0; i < this.positions.size(); i++) {
            this.positions.set(i, this.positions.get(i).add(tobeAdded.getPositions().get(i)));
            this.rateOfChange.getVelocities().set(i, this.rateOfChange.getVelocities().get(i).add(tobeAdded.getRateOfChange().getVelocities().get(i)));
        }
        return this;
    }

    @Override
    public RateInterface getRateOfChange() {
        return this.rateOfChange;
    }

    @Override
    public void initialVelocity() {
        this.rateOfChange = new RateOfChange().state0();
    }

    @Override
    public StateInterface sumOf(StateInterface... rates) {

        for (int i = 0; i < rates[0].getPositions().size(); i++) {
            Vector3dInterface sum = this.getPositions().get(i);
            Vector3dInterface velsum = this.getRateOfChange().getVelocities().get(i);
            for (StateInterface r : rates) {
                sum = sum.add(r.getPositions().get(i));
                velsum = velsum.add(r.getRateOfChange().getVelocities().get(i));
            }
            this.getPositions().set(i, sum);
            this.getRateOfChange().getVelocities().set(i, velsum);
        }
        return this;

    }


}
