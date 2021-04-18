package group17.phase1.Titan.Interfaces;

import java.util.List;

public interface RateInterface {

    /**
     * List of velocity vectors for the next state up to be updated
     *
     * @return the velocity vectors of all bodies
     * @see group17.phase1.Titan.System.SystemState
     * @see StateInterface
     */
    List<Vector3dInterface> getRateOfChange();
}
