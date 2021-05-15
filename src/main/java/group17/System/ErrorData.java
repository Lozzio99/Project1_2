package group17.System;

import group17.Interfaces.StateInterface;
import group17.Interfaces.Vector3dInterface;
import org.jetbrains.annotations.Contract;

import java.util.List;

public class ErrorData {
    List<Vector3dInterface> positions, velocities;

    @Contract(pure = true)
    public ErrorData() {
    }

    public ErrorData setData(List<Vector3dInterface> positions, List<Vector3dInterface> velocities) {
        this.positions = positions;
        this.velocities = velocities;
        this.positions.remove(this.positions.size() - 1);
        this.velocities.remove(this.velocities.size() - 1);//removing rocket , no data to compare for it
        return this;
    }

    public ErrorData(List<Vector3dInterface> positions, List<Vector3dInterface> velocities) {
        this.positions = List.copyOf(positions);
        this.velocities = List.copyOf(velocities);
    }

    public ErrorData(StateInterface state) {
        this.positions = List.copyOf(state.getPositions());
        this.velocities = List.copyOf(state.getRateOfChange().getVelocities());
    }

}
