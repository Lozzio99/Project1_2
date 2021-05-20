package group17.System.State;

import group17.Interfaces.RateInterface;
import group17.Interfaces.Vector3dInterface;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import static group17.Main.simulation;

/**
 * The type Rate of change.
 */
public class RateOfChange implements RateInterface {
    /**
     * The Vel.
     */
    List<Vector3dInterface> vel;

    /**
     * Instantiates a new Rate of change.
     */
    @Contract(pure = true)
    public RateOfChange() {
        this.vel = new ArrayList<>();
    }

    @Override
    public void setVelocities(List<Vector3dInterface> vel) {
        this.vel = vel;
    }

    @Override
    public List<Vector3dInterface> getVelocities() {
        return this.vel;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        if (simulation != null)
            for (int i = 0; i < vel.size(); i++) {
                s.append(simulation.getSystem().getCelestialBodies().get(i).toString());
                s.append(" : ");
                s.append(this.vel.get(i).toString()).append("\n");
            }
        else {
            s.append(this.vel.toString());
        }
        return s.toString().trim();
    }
}
