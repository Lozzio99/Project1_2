package group17.System;

import group17.Interfaces.RateInterface;
import group17.Interfaces.SimulationInterface;
import group17.Interfaces.StateInterface;
import group17.Interfaces.Vector3dInterface;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import static group17.Main.simulationInstance;


public class SystemState implements StateInterface {

    List<Vector3dInterface> positions;
    RateInterface rateOfChange;

    public SystemState() {
        this.positions = new ArrayList<>();
        this.rateOfChange = new RateOfChange();
    }

    @Contract(pure = true)
    public SystemState(List<Vector3dInterface> positions) {
        this.positions = positions;
    }

    public SystemState(Vector3dInterface positionsVector) {
        this.positions = new ArrayList<>();
        positions.add(positionsVector);
    }


    @Override
    public List<Vector3dInterface> getPositions() {
        return this.positions;
    }

    @Override
    public void setPositions(List<Vector3dInterface> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        SimulationInterface simulation = simulationInstance;
        StringBuilder s = new StringBuilder();
        s.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        if (simulation == null) {
            for (Vector3dInterface position : this.positions) {
                s.append("PV:\t");
                s.append(position.toString()).append("\n");
            }
        } else {
            for (int i = 0; i < this.positions.size(); i++) {
                s.append(simulationInstance.getSystem().getCelestialBodies().get(i).toString());
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
