package group17.System;

import group17.Interfaces.StateInterface;
import group17.Interfaces.Vector3dInterface;

import java.util.List;

public class Data {
    final List<Vector3dInterface> positions, velocities;

    public Data(List<Vector3dInterface> positions, List<Vector3dInterface> velocities) {
        this.positions = List.copyOf(positions);
        this.velocities = List.copyOf(velocities);
    }

    public Data(StateInterface state) {
        this.positions = List.copyOf(state.getPositions());
        this.velocities = List.copyOf(state.getRateOfChange().getVelocities());
    }
}
