package group17.Interfaces;

import group17.Math.Lib.Vector3D;
import group17.System.Bodies.CelestialBody;
import group17.System.State.SystemState;

import java.util.List;

public interface StateInterface {

    default StateInterface copy() {
        StateInterface s = new SystemState();
        for (int i = 0; i < this.getPositions().size(); i++) {
            s.getPositions().add(this.getPositions().get(i).clone());
            s.getRateOfChange().getVelocities().add(this.getRateOfChange().getVelocities().get(i).clone());
        }
        return s;
    }

    String toString();

    RateInterface getRateOfChange();

    /**
     * The state of the bodies at a given point in time
     *
     * @return the positions of all bodies in the state
     */
    List<Vector3dInterface> getPositions();

    void setPositions(List<Vector3dInterface> v);

    /**
     * Initialises the system state to be the initial value at the launch day
     *
     * @return the StateInterface y(0)
     */
    default StateInterface state0(List<CelestialBody> bodies) {
        StateInterface state = this;
        for (CelestialBody b : bodies) {
            b.initProperties();
            state.getPositions().add(b.getVectorLocation().clone());
            state.getRateOfChange().getVelocities().add(b.getVectorVelocity().clone());
        }
        return state;
    }

    /**
     * Update a state to a new state computed by: this + step * rate
     *
     * @param step The time-step of the update
     * @param rate The average rate-of-change over the time-step. Has dimensions of [state]/[time].
     * @return The new state after the update. Required to have the same class as 'this'.
     */
    default StateInterface addMul(double step, RateInterface rate) {
        StateInterface newState = new SystemState();
        for (int i = 0; i < this.getPositions().size(); i++) {
            newState.getPositions().add(this.getPositions().get(i).addMul(step, rate.getVelocities().get(i)));
            newState.getRateOfChange().getVelocities().add(rate.getVelocities().get(i).clone());
        }
        return newState;
    }

    default StateInterface rateMul(double step, RateInterface rate) { //!!
        StateInterface newState = new SystemState();
        for (int i = 0; i < this.getPositions().size(); i++) {
            newState.getPositions().add(rate.getVelocities().get(i).mul(step));
            newState.getRateOfChange().getVelocities().add(new Vector3D(0, 0, 0));
        }
        return newState;
    }

    /**
     * @return a String containing the state of the system
     */
    default StateInterface copy(StateInterface tobeCloned) {
        StateInterface s = new SystemState();
        for (Vector3dInterface v : tobeCloned.getPositions()) {
            s.getPositions().add(v.clone());
        }
        return s;
    }

    default StateInterface multiply(double scalar) {
        if (this.getPositions().size() == 0)
            throw new RuntimeException(" Nothing to multiply ");
        StateInterface state = new SystemState();
        for (int i = 0; i < this.getPositions().size(); i++) {
            state.getPositions().add(this.getPositions().get(i).mul(scalar));
            state.getRateOfChange().getVelocities().add(this.getRateOfChange().getVelocities().get(i).mul(scalar));
        }
        return state;
    }

    default StateInterface div(double scalar) {
        if (this.getPositions().size() == 0)
            throw new RuntimeException(" Nothing to divide ");

        StateInterface state = new SystemState();
        for (int i = 0; i < this.getPositions().size(); i++) {
            state.getPositions().add(this.getPositions().get(i).div(scalar));
            state.getRateOfChange().getVelocities().add(this.getRateOfChange().getVelocities().get(i).div(scalar));
        }
        return state;
    }

    default StateInterface add(StateInterface tobeAdded) {
        if (this.getPositions().size() == 0)
            throw new RuntimeException(" Nothing to add ");
        StateInterface state = new SystemState();

        for (int i = 0; i < this.getPositions().size(); i++) {
            state.getPositions().add(this.getPositions().get(i).add(tobeAdded.getPositions().get(i)));
            state.getRateOfChange().getVelocities().add(this.getRateOfChange().getVelocities().get(i).add(tobeAdded.getRateOfChange().getVelocities().get(i)));
        }
        return state;
    }

    default StateInterface sumOf(StateInterface... rates) {
        StateInterface state = new SystemState();
        for (int i = 0; i < rates[0].getPositions().size(); i++) {
            Vector3dInterface sum = this.getPositions().get(i);
            Vector3dInterface velsum = this.getRateOfChange().getVelocities().get(i);
            for (StateInterface r : rates) {
                sum = sum.add(r.getPositions().get(i));
                velsum = velsum.add(r.getRateOfChange().getVelocities().get(i));
            }
            state.getPositions().add(sum);
            state.getRateOfChange().getVelocities().add(velsum);
        }
        return state;
    }

    default void update(StateInterface step) {
        this.setPositions(step.getPositions());
        this.getRateOfChange().setVel(step.getRateOfChange().getVelocities());
    }

    @Override
    int hashCode();
}
