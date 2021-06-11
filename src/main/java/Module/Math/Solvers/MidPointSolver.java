package Module.Math.Solvers;

import Module.Math.ADT.Vector3dInterface;
import Module.Math.Functions.ODEFunctionInterface;
import Module.System.State.StateInterface;
import Module.System.State.SystemState;

/**
 * The type Mid point solver.
 * Second order Solver
 */
public class MidPointSolver<E> implements ODESolverInterface<E> {
    private final ODEFunctionInterface<E> f;

    /**
     * Instantiates a new Mid point solver.
     *
     * @param f the f
     */
    public MidPointSolver(final ODEFunctionInterface<E> f) {
        this.f = f;
    }

    /**
     * @param f the function representing Newton's Gravitational law
     * @param y the instance  of the Simulation
     * @param h stepSize
     * @return the next state of the simulation based on A Midpoint Step
     */
    @Override
    public StateInterface<E> step(ODEFunctionInterface<E> f, double t, StateInterface<E> y, double h) {
        Vector3dInterface acceleration =(Vector3dInterface)  f.call(t, y).get();
        Vector3dInterface velocity = (Vector3dInterface) y.getRateOfChange().get();
        Vector3dInterface position = (Vector3dInterface) y.get();

        Vector3dInterface mid_velocity = velocity.add(acceleration.mul(h*0.5));
        Vector3dInterface mid_position = position.add(mid_velocity.mul(h));

        Vector3dInterface next_acceleration = (Vector3dInterface) f.call(0.5*h,new SystemState(mid_position,mid_velocity)).get();
        Vector3dInterface next_velocity =velocity.add(next_acceleration.mul(h));
        Vector3dInterface next_position = position.add(next_velocity.mul(h));

        return new SystemState(next_position,next_velocity);

        /*
        Vector3dInterface position = (Vector3dInterface) y.get();

        Vector3dInterface mid_velocity =(Vector3dInterface)  f.call( t+h*0.5, y).get();
        Vector3dInterface mid_position = position.addMul(h*0.5,mid_velocity);

        Vector3dInterface next_velocity =(Vector3dInterface)f.call(t+0.5*h,new SystemState(mid_position,mid_velocity)).get();
        Vector3dInterface next_position = position.addMul(h,next_velocity);

         */


        /*
        StateInterface<E> mid = y.addMul(h / 2, f.call(t, y));
        StateInterface<E> next = y.addMul(h, f.call(t + (h / 2), mid));
        next.getRateOfChange().set(mid.getRateOfChange().get());
        return next;

         */
    }

    @Override
    public ODEFunctionInterface<E> getFunction() {
        return this.f;
    }

}
