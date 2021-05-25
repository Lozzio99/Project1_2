package group17.System;

import group17.Interfaces.RateInterface;
import group17.Interfaces.SimulationInterface;
import group17.Interfaces.StateInterface;
import group17.Interfaces.Vector3dInterface;
import group17.Main;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;


public class SystemState implements StateInterface {

    List<Vector3dInterface> positions;
    RateInterface rateOfChange;

    public SystemState() {
        this.positions = new ArrayList<>();
        this.rateOfChange = new RateOfChange();
    }

    @Override
    public void setRateOfChange(RateInterface rateOfChange) {
        this.rateOfChange = rateOfChange;
    }

    public SystemState(StateInterface copy) {
        this.positions = new ArrayList<>();
        this.rateOfChange = new RateOfChange();
        for (int i = 0; i < copy.getPositions().size(); i++) {
            this.positions.add(copy.getPositions().get(i).clone());
            this.rateOfChange.getVelocities().add(copy.getRateOfChange().getVelocities().get(i).clone());
        }
    }

    public SystemState(StateInterface copy, RateInterface rate) {
        this.positions = copy.getPositions();
        this.rateOfChange = new RateOfChange();
        this.rateOfChange.setVel(rate.getVelocities());
    }

    @Contract(pure = true)
    public SystemState(List<Vector3dInterface> positions, List<Vector3dInterface> velocities) {
        this.positions = positions;
        this.rateOfChange = new RateOfChange();
        rateOfChange.setVel(velocities);
    }


    @Override
    public List<Vector3dInterface> getPositions() {
        return this.positions;
    }

    @Override
    public void setPositions(List<Vector3dInterface> pos) {
        this.positions = pos;
    }

    @Override
    public String toString() {
        SimulationInterface simulation = Main.simulation;
        StringBuilder s = new StringBuilder();
        s.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        if (simulation == null) {
            for (Vector3dInterface position : this.positions) {
                s.append("PV:\t");
                s.append(position.toString()).append("\n");
            }
        } else {
            for (int i = 0; i < this.positions.size(); i++) {
                s.append(Main.simulation.getSystem().getCelestialBodies().get(i).toString());
                s.append("\tPV:\t");
                s.append(this.positions.get(i).toString()).append("\n");
                s.append("\tVV:\t");
                s.append(this.rateOfChange.getVelocities().get(i).toString()).append("\n");
            }
        }
        s.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        return s.toString().trim();
    }

    @Override
    public RateInterface getRateOfChange() {
        return this.rateOfChange;
    }

    @Override
    public int hashCode() {
        int hash = 31;
        hash = 31 * hash + this.positions.hashCode();
        hash = 31 * hash + this.rateOfChange.getVelocities().hashCode();
        return hash;
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StateInterface) {
            StateInterface s = (StateInterface) obj;
            return this.hashCode() == s.hashCode();
        }
        return false;
    }
}
