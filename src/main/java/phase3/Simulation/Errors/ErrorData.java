package phase3.Simulation.Errors;

import phase3.Math.ADT.Vector3dInterface;
import phase3.System.State.StateInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents Data used in calculating errors of simulation
 */
public class ErrorData {
    private List<Vector3dInterface> positions, velocities;

    /**
     * Instantiates a new Error data.
     */
    public ErrorData() {
    }

    /**
     * Instantiates a new Error data.
     *
     * @param state the state
     */
    public ErrorData(StateInterface<Vector3dInterface> state) {
        StateInterface<Vector3dInterface> copy = state.copy();
        this.positions = new ArrayList<>();
        this.velocities = new ArrayList<>();
        int s = state.get().length / 2;
        for (int i = 0; i < s; i++) {
            this.positions.add(state.get()[i]);
            this.velocities.add(state.get()[i + s]);
        }
    }

    /**
     * Instantiates a new Error data.
     *
     * @param positions  the positions
     * @param velocities the velocities
     */
    public ErrorData(List<Vector3dInterface> positions, List<Vector3dInterface> velocities) {
        this.positions = List.copyOf(positions);
        this.velocities = List.copyOf(velocities);
    }

    /**
     * Sets data.
     *
     * @param positions  the positions
     * @param velocities the velocities
     * @return the data
     */
    public ErrorData setData(List<Vector3dInterface> positions, List<Vector3dInterface> velocities) {
        this.positions = positions;
        this.velocities = velocities;
        if (this.positions.size() >= 12) {
            this.positions.remove(11);
            this.velocities.remove(11);//removing rocket , no data to compare for it
        }
        return this;
    }


    /**
     * Gets positions.
     *
     * @return the positions
     */
    public List<Vector3dInterface> getPositions() {
        return positions;
    }

    /**
     * Gets velocities.
     *
     * @return the velocities
     */
    public List<Vector3dInterface> getVelocities() {
        return velocities;
    }

    @Override
    public String toString() {
        return "ErrorData {\n" +
                "P = " + positions +
                ",\nV = " + velocities +
                "\n}\n";
    }

    public enum ErrorPrint {
        CONSOLE,
        TXT,
        CSV
    }
}
