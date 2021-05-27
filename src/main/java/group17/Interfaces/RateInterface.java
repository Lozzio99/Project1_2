package group17.Interfaces;

import group17.Math.Vector3D;
import group17.Simulation.System.State.RateOfChange;

import java.util.ArrayList;
import java.util.List;

import static group17.Main.simulation;

/**
 *  An interface representing the time-derivative (rate-of-change) of the state of a system.
 */
public interface RateInterface {


    /**
     * List of velocity vectors for the next state up to be updated
     *
     * @return the velocity vectors of all bodies
     * @see StateInterface
     */
    List<Vector3dInterface> getVelocities();

    /**
     * Sets velocities.
     *
     * @param vel the vel
     */
    void setVelocities(List<Vector3dInterface> vel);

    /**
     * Multiply rate interface.
     *
     * @param scalar the scalar
     * @return the rate interface
     */
    default RateInterface multiply(double scalar) {
        if (this.getVelocities().size() == 0)
            throw new RuntimeException(" Nothing to multiply ");

        RateInterface rate = new RateOfChange();
        for (int i = 0; i < this.getVelocities().size(); i++) {
            rate.getVelocities().add(this.getVelocities().get(i).mul(scalar));
        }
        return rate;
    }

    /**
     * Sub rate interface.
     *
     * @param scalar the scalar
     * @return the rate interface
     */
    default RateInterface sub(double scalar) {
        if (this.getVelocities().size() == 0)
            throw new RuntimeException(" Nothing to multiply ");
        RateInterface rate = new RateOfChange();
        for (int i = 0; i < this.getVelocities().size(); i++) {
            rate.getVelocities().add(this.getVelocities().get(i).add(new Vector3D(-scalar, -scalar, -scalar)));
        }
        return rate;
    }

    /**
     * Add rate interface.
     *
     * @param scalar the scalar
     * @return the rate interface
     */
    default RateInterface add(double scalar) {
        if (this.getVelocities().size() == 0)
            throw new RuntimeException(" Nothing to multiply ");
        RateInterface rate = new RateOfChange();
        for (int i = 0; i < this.getVelocities().size(); i++) {
            rate.getVelocities().add(this.getVelocities().get(i).add(new Vector3D(scalar, scalar, scalar)));
        }
        return rate;
    }

    /**
     * Div rate interface.
     *
     * @param scalar the scalar
     * @return the rate interface
     */
    default RateInterface div(double scalar) {
        if (this.getVelocities().size() == 0)
            throw new RuntimeException(" Nothing to multiply ");
        RateInterface rate = new RateOfChange();

        for (int i = 0; i < this.getVelocities().size(); i++) {
            rate.getVelocities().add(this.getVelocities().get(i).div(scalar));
        }
        return rate;
    }

    /**
     * Copy rate interface.
     *
     * @return the rate interface
     */
    default RateInterface copy() {
        RateInterface s = new RateOfChange();
        for (Vector3dInterface v : this.getVelocities()) {
            s.getVelocities().add(v.clone());
        }
        return s;
    }

    /**
     * Add rate interface.
     *
     * @param tobeAdded the tobe added
     * @return the rate interface
     */
    default RateInterface add(RateInterface tobeAdded) {
        if (this.getVelocities().size() == 0)
            throw new RuntimeException(" Nothing to add ");
        RateInterface rate = new RateOfChange();

        for (int i = 0; i < this.getVelocities().size(); i++) {
            rate.getVelocities().add(this.getVelocities().get(i).add(tobeAdded.getVelocities().get(i)));
        }
        return rate;
    }

    /**
     * Sum of rate interfaces.
     *
     * @param states the states
     * @return the rate interface
     */
    default RateInterface sumOf(RateInterface... states) {
        RateInterface rate = new RateOfChange();
        for (int i = 0; i < states[0].getVelocities().size(); i++) {
            Vector3dInterface velsum = this.getVelocities().get(i);
            for (RateInterface r : states) {
                velsum = velsum.add(r.getVelocities().get(i));
            }
            rate.getVelocities().add(velsum);
        }
        return rate;
    }

    /**
     * Initial rate rate interface.
     *
     * @return the rate interface
     */
    default RateInterface initialRate() {
        if (this.getVelocities().size() != 0)
            this.setVelocities(new ArrayList<>(11));
        for (int i = 0; i < simulation.getSystem().systemState().getPositions().size(); i++) {
            this.getVelocities().add(simulation.getSystem().getCelestialBodies().get(i).getVectorVelocity());
        }
        return this;
    }
}
