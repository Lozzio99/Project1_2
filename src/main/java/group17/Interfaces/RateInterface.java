package group17.Interfaces;

import group17.Math.Lib.Vector3D;
import group17.System.State.RateOfChange;

import java.util.ArrayList;
import java.util.List;

import static group17.Main.simulation;

public interface RateInterface {


    /**
     * List of velocity vectors for the next state up to be updated
     *
     * @return the velocity vectors of all bodies
     * @see StateInterface
     */
    List<Vector3dInterface> getVelocities();

    void setVel(List<Vector3dInterface> vel);

    default RateInterface sub(int scalar) {
        if (this.getVelocities().size() == 0)
            throw new RuntimeException(" Nothing to multiply ");

        RateInterface rate = new RateOfChange();
        for (int i = 0; i < this.getVelocities().size(); i++) {
            rate.getVelocities().add(this.getVelocities().get(i).sub(new Vector3D(scalar, scalar, scalar)));
        }
        return rate;
    }

    default RateInterface copy() {
        RateInterface s = new RateOfChange();
        for (Vector3dInterface v : this.getVelocities()) {
            s.getVelocities().add(v.clone());
        }
        return s;
    }

    default RateInterface multiply(double scalar) {
        if (this.getVelocities().size() == 0)
            throw new RuntimeException(" Nothing to multiply ");

        RateInterface rate = new RateOfChange();
        for (int i = 0; i < this.getVelocities().size(); i++) {
            rate.getVelocities().add(this.getVelocities().get(i).mul(scalar));
        }
        return rate;
    }

    default RateInterface sub(double scalar) {
        if (this.getVelocities().size() == 0)
            throw new RuntimeException(" Nothing to multiply ");
        RateInterface rate = new RateOfChange();
        for (int i = 0; i < this.getVelocities().size(); i++) {
            rate.getVelocities().add(this.getVelocities().get(i).add(new Vector3D(-scalar, -scalar, -scalar)));
        }
        return rate;
    }


    default RateInterface add(RateInterface tobeAdded) {
        if (this.getVelocities().size() == 0)
            throw new RuntimeException(" Nothing to add ");
        RateInterface rate = new RateOfChange();

        for (int i = 0; i < this.getVelocities().size(); i++) {
            rate.getVelocities().add(this.getVelocities().get(i).add(tobeAdded.getVelocities().get(i)));
        }
        return rate;
    }

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

    default RateInterface div(double scalar) {
        if (this.getVelocities().size() == 0)
            throw new RuntimeException(" Nothing to multiply ");
        RateInterface rate = new RateOfChange();

        for (int i = 0; i < this.getVelocities().size(); i++) {
            rate.getVelocities().add(this.getVelocities().get(i).div(scalar));
        }
        return rate;
    }

    default RateInterface state0() {
        if (this.getVelocities().size() != 0)
            this.setVel(new ArrayList<>(11));
        for (int i = 0; i < simulation.getSystem().systemState().getPositions().size(); i++) {
            this.getVelocities().add(simulation.getSystem().getCelestialBodies().get(i).getVectorVelocity());
        }
        return this;
    }

}
