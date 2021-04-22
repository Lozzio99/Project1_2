package group17.phase1.Titan.Interfaces;

import group17.phase1.Titan.System.RateOfChange;
import group17.phase1.Titan.System.SystemState;

import java.util.List;

public interface RateInterface {

    /**
     * List of velocity vectors for the next state up to be updated
     *
     * @return the velocity vectors of all bodies
     * @see group17.phase1.Titan.System.SystemState
     * @see StateInterface
     */
    List<Vector3dInterface> getVelocities();

     void setVel(List<Vector3dInterface> vel); // !!

    RateInterface sub(int i);

    static RateInterface clone(RateInterface tobeCloned) {
        RateInterface s = new RateOfChange();

        for (int i = 0; i < tobeCloned.getVelocities().size(); i++) {
            s.getVelocities().add(tobeCloned.getVelocities().get(i).clone());
        }
        return s;
    }

    RateInterface copy(RateInterface tobeCloned);

    RateInterface multiply(double scalar);

    RateInterface sub(double scalar);


    RateInterface add(RateInterface tobeAdded);

    RateInterface sumOf(RateInterface... states);

    RateInterface div(int i);


}
