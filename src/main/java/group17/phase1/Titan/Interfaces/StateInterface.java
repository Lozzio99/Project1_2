package group17.phase1.Titan.Interfaces;
/*
 * @author Pieter Collins, Christof Seiler, Katerina Stankova, Nico Roos, Katharina Schueller
 * @version 0.99.0
 *
 * This interface serves as the API for students in phase 1.
 */


import group17.phase1.Titan.Physics.Bodies.CelestialBody;
import group17.phase1.Titan.Physics.Math.Vector3D;
import group17.phase1.Titan.System.SystemState;

import java.util.List;

/**
 * An interface representing the state of a system described by a differential equation.
 */

public interface StateInterface {

    /**
     * Initialises the system state to be the initial value at the launch day
     *
     * @return the StateInterface y(0)
     */
    StateInterface state0(List<CelestialBody> bodies);

    /**
     * The state of the bodies at a given point in time
     *
     * @return the positions of all bodies in the state
     */
    List<Vector3dInterface> getPositions();


    void setPositions(List<Vector3dInterface> v);

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
            this.getPositions().set(i, this.getPositions().get(i).addMul(step, rate.getVelocities().get(i)));
            newState.getPositions().add(this.getPositions().get(i).clone());
            newState.getRateOfChange().getVelocities().add(rate.getVelocities().get(i));
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
    String toString();


    static StateInterface clone(StateInterface tobeCloned) {
        StateInterface s = new SystemState();

        for (int i = 0; i < tobeCloned.getPositions().size(); i++) {
            s.getPositions().add(tobeCloned.getPositions().get(i).clone());
            s.getRateOfChange().getVelocities().add(tobeCloned.getRateOfChange().getVelocities().get(i).clone());
        }
        return s;
    }

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

        for (int i = 0; i < this.getPositions().size(); i++) {
            this.getPositions().set(i, this.getPositions().get(i).mul(scalar));
            this.getRateOfChange().getVelocities().set(i, this.getRateOfChange().getVelocities().get(i).mul(scalar));
        }
        return this;
    }

    default StateInterface div(double scalar) {
        if (this.getPositions().size() == 0)
            throw new RuntimeException(" Nothing to divide ");

        for (int i = 0; i < this.getPositions().size(); i++) {
            this.getPositions().set(i, this.getPositions().get(i).div(scalar));
            this.getRateOfChange().getVelocities().set(i, this.getRateOfChange().getVelocities().get(i).div(scalar));
        }
        return this;
    }


    default StateInterface add(StateInterface tobeAdded) {
        if (this.getPositions().size() == 0)
            throw new RuntimeException(" Nothing to add ");

        for (int i = 0; i < this.getPositions().size(); i++) {
            this.getPositions().set(i, this.getPositions().get(i).add(tobeAdded.getPositions().get(i)));
            this.getRateOfChange().getVelocities().set(i, this.getRateOfChange().getVelocities().get(i).add(tobeAdded.getRateOfChange().getVelocities().get(i)));
        }
        return this;
    }

    RateInterface getRateOfChange();

    void initialVelocity();

    default StateInterface sumOf(StateInterface... rates) {

        for (int i = 0; i < rates[0].getPositions().size(); i++) {
            Vector3dInterface sum = this.getPositions().get(i);
            Vector3dInterface velsum = this.getRateOfChange().getVelocities().get(i);
            for (StateInterface r : rates) {
                sum = sum.add(r.getPositions().get(i));
                velsum = velsum.add(r.getRateOfChange().getVelocities().get(i));
            }
            this.getPositions().set(i, sum);
            this.getRateOfChange().getVelocities().set(i, velsum);
        }
        return this;

    }

}

