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

    @Override
    public void setPositions(List<Vector3dInterface> positions) {
        this.positions = positions;
    }

    @Override
    public List<Vector3dInterface> getPositions() {
        return this.positions;
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(simulation.system().getClock().toString()).append("\n");
        //TODO : insert MOON
        for (int i = 0; i < this.positions.size() - 1; i++) {
            s.append(simulation.system().getCelestialBodies().get(i).toString());
            s.append("\tPV : ");
            s.append(this.positions.get(i).toString());
            s.append("\n");
        }
        s.append("""
                §-----------------------------------------------------------------------§
                """);
        return s.toString().trim();
    }


    @Override
    public RateInterface getRateOfChange() {
        return this.rateOfChange;
    }

    @Override
    public void initialVelocity() {
        this.rateOfChange = new RateOfChange().state0();
    }




}
