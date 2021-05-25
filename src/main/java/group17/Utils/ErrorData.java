package group17.Utils;

import group17.Interfaces.StateInterface;
import group17.Interfaces.Vector3dInterface;
import org.jetbrains.annotations.Contract;

import java.util.List;

/**
 * Represents Data used in calculating errors of simulation
 */
public class ErrorData {
    private List<Vector3dInterface> positions, velocities;

    /**
     * Instantiates a new Error data.
     */
    @Contract(pure = true)
    public ErrorData() {
    }

    /**
     * Instantiates a new Error data.
     *
     * @param state the state
     */
    public ErrorData(StateInterface state) {
        StateInterface copy = state.copy();
        this.positions = copy.getPositions();
        if (this.positions.size() >= 12) this.positions.remove(11);
        this.velocities = copy.getRateOfChange().getVelocities();
        if (this.velocities.size() >= 12) this.velocities.remove(11);
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
}
