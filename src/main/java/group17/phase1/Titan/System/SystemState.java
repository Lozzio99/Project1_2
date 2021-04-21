package group17.phase1.Titan.System;

import group17.phase1.Titan.Interfaces.RateInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.Physics.Bodies.CelestialBody;

import java.util.ArrayList;
import java.util.List;

import static group17.phase1.Titan.Main.simulation;

public class SystemState implements StateInterface {

    List<Vector3dInterface> positions;

    public SystemState() {
        this.positions = new ArrayList<>();
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
            this.positions.set(i, this.positions.get(i).addMul(step, rate.getRateOfChange().get(i)));
            newState.getPositions().add(this.positions.get(i).clone());
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
}
