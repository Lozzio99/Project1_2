package group17.System;

import group17.Interfaces.StateInterface;
import group17.Interfaces.Vector3dInterface;
import org.jetbrains.annotations.Contract;

import java.util.List;

public class ErrorData {
    private List<Vector3dInterface> positions, velocities;

    @Contract(pure = true)
    public ErrorData() {
    }

    public ErrorData(StateInterface state) {
        StateInterface copy = state.copy();
        this.positions = copy.getPositions();
        if (this.positions.size() >= 12) this.positions.remove(11);
        this.velocities = copy.getRateOfChange().getVelocities();
        if (this.velocities.size() >= 12) this.velocities.remove(11);
    }

    public ErrorData(List<Vector3dInterface> positions, List<Vector3dInterface> velocities) {
        this.positions = List.copyOf(positions);
        this.velocities = List.copyOf(velocities);
    }

    public ErrorData setData(List<Vector3dInterface> positions, List<Vector3dInterface> velocities) {
        this.positions = positions;
        this.velocities = velocities;
        if (this.positions.size() >= 12) {
            this.positions.remove(11);
            this.velocities.remove(11);//removing rocket , no data to compare for it
        }
        return this;
    }

    public List<Vector3dInterface> getPositions() {
        return positions;
    }

    public List<Vector3dInterface> getVelocities() {
        return velocities;
    }
}
