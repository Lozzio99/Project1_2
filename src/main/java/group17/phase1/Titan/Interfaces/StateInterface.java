package group17.phase1.Titan.Interfaces;
/*
 * @author Pieter Collins, Christof Seiler, Katerina Stankova, Nico Roos, Katharina Schueller
 * @version 0.99.0
 *
 * This interface serves as the API for students in phase 1.
 */


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
    StateInterface state0();

    /**
     * The state of the bodies at a given point in time
     *
     * @return the positions of all bodies in the state
     */
    List<Vector3dInterface> getPositions();


    /**
     * Update a state to a new state computed by: this + step * rate
     *
     * @param step The time-step of the update
     * @param rate The average rate-of-change over the time-step. Has dimensions of [state]/[time].
     * @return The new state after the update. Required to have the same class as 'this'.
     */

    StateInterface addMul(double step, RateInterface rate);

    /**
     * @return a String containing the state of the system
     */
    String toString();

}

