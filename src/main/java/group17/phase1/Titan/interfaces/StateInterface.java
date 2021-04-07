package group17.phase1.Titan.interfaces;
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

public interface StateInterface
{


    List<Vector3dInterface> getPositions();


    void setVectorPosition(List<Vector3dInterface> vectorPosition);

    /**
     * Update a state to a new state computed by: this + step * rate
     *
     * @param step   The time-step of the update
     * @param rate   The average rate-of-change over the time-step. Has dimensions of [state]/[time].
     * @return The new state after the update. Required to have the same class as 'this'.
     */

    StateInterface addMul(double step, RateInterface rate);

    String toString();

}

