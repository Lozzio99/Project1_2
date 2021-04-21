package group17.phase1.Titan.Physics.Solvers;

import group17.phase1.Titan.Interfaces.ODEFunctionInterface;
import group17.phase1.Titan.Interfaces.RateInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.Physics.Math.Vector3D;
import group17.phase1.Titan.System.RateOfChange;

import java.util.ArrayList;

/**
 * Implements differential equation: yd = sin(t) + y - y^3
 * With y(0) = 2
 * And exact value of y(6) = -0:6599693029 (10 dp).
 */
public class ODESolvable implements ODEFunctionInterface {

    private RateOfChange next_rate;
    private ArrayList<Vector3dInterface> stateList;

    @Override
    public RateInterface call(double t, StateInterface y) {
        Vector3D next_vector = new Vector3D();
        next_vector.setX(y.getPositions().get(0).getX());
        next_vector.setY(y.getPositions().get(0).getY());
        next_vector.setZ(Math.sin(t) + y.getPositions().get(0).getZ() - Math.pow(y.getPositions().get(0).getZ(), 3));

        next_rate = new RateOfChange();
        stateList = new ArrayList<>();
        stateList.add(next_vector);
        next_rate.setVel(stateList);

        return next_rate;
    }

    /**
     * For Higher cpu usages, shuts down all waiting or busy threads
     */
    @Override
    public void shutDown() {

    }
}
