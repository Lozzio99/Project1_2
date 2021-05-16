package group17.Interfaces;

import group17.Math.Utils.Vector3D;
import group17.System.RateOfChange;

import java.util.ArrayList;
import java.util.List;

import static group17.Main.simulation;

public interface RateInterface {

    static RateInterface clone(RateInterface tobeCloned) {
        RateInterface s = new RateOfChange();
        for (int i = 0; i < tobeCloned.getVelocities().size(); i++) {
            s.getVelocities().add(tobeCloned.getVelocities().get(i).clone());
        }
        return s;
    }

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

        for (int i = 0; i < this.getVelocities().size(); i++) {
            this.getVelocities().set(i, this.getVelocities().get(i).sub(new Vector3D(scalar, scalar, scalar)));
        }
        return this;
    }

    default RateInterface copy(RateInterface tobeCloned) {
        RateInterface s = new RateOfChange();
        for (Vector3dInterface v : tobeCloned.getVelocities()) {
            s.getVelocities().add(v.clone());
        }
        return s;
    }

    default RateInterface multiply(double scalar) {
        if (this.getVelocities().size() == 0)
            throw new RuntimeException(" Nothing to multiply ");

        for (int i = 0; i < this.getVelocities().size(); i++) {
            this.getVelocities().set(i, this.getVelocities().get(i).mul(scalar));
        }
        return this;
    }

    default RateInterface sub(double scalar) {
        if (this.getVelocities().size() == 0)
            throw new RuntimeException(" Nothing to multiply ");
        for (int i = 0; i < this.getVelocities().size(); i++) {
            this.getVelocities().set(i, this.getVelocities().get(i).add(new Vector3D(-scalar, -scalar, -scalar)));
        }
        return this;
    }


    default RateInterface add(RateInterface tobeAdded) {
        if (this.getVelocities().size() == 0)
            throw new RuntimeException(" Nothing to add ");

        for (int i = 0; i < this.getVelocities().size(); i++) {
            this.getVelocities().set(i, this.getVelocities().get(i).add(tobeAdded.getVelocities().get(i)));
        }
        return this;
    }

    default RateInterface sumOf(RateInterface... states) {
        for (int i = 0; i < states[0].getVelocities().size(); i++) {
            Vector3dInterface velsum = this.getVelocities().get(i);
            for (RateInterface r : states) {
                velsum = velsum.add(r.getVelocities().get(i));
            }
            this.getVelocities().set(i, velsum);
        }
        return this;
    }

    default RateInterface div(int scalar) {
        if (this.getVelocities().size() == 0)
            throw new RuntimeException(" Nothing to multiply ");

        for (int i = 0; i < this.getVelocities().size(); i++) {
            this.getVelocities().set(i, this.getVelocities().get(i).div(scalar));
        }
        return this;
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
