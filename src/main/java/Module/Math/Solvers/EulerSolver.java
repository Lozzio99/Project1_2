package Module.Math.Solvers;


import Module.Math.ADT.Vector3dInterface;
import Module.Math.Functions.ODEFunctionInterface;
import Module.System.State.StateInterface;
import Module.System.State.SystemState;

/**
 * The type Euler solver.
 * First Order Solver
 */
public class EulerSolver<E> implements ODESolverInterface<E> {

    /**
     * The Single core f.
     */
    private final ODEFunctionInterface<E> f;

    /**
     * Instantiates a new Euler solver.
     *
     * @param f the f
     */
    public EulerSolver(final ODEFunctionInterface<E> f) {
        this.f = f;
    }

    /**
     * Formula
     * y1 = y0 + h*f(t,y0);
     *
     * @param f the function representing Newton's Gravitational law
     * @param y the instance  of the Simulation
     * @return the next state of the simulation based on a Euler Step
     */
    @Override
    public StateInterface<E> step(ODEFunctionInterface<E> f, double currentTime, StateInterface<E> y, double stepSize) {


        /*
        Vector3dInterface position = (Vector3dInterface) y.get();
        Vector3dInterface next_velocity = (Vector3dInterface) f.call(currentTime,y).get();
        Vector3dInterface next_position = position.addMul(stepSize,next_velocity);

        return new SystemState(next_position,next_velocity);


         */


        Vector3dInterface acceleration =(Vector3dInterface)  f.call(currentTime, y).get();
        Vector3dInterface velocity = (Vector3dInterface) y.getRateOfChange().get();
        Vector3dInterface position = (Vector3dInterface) y.get();

        Vector3dInterface nextVelocity = velocity.addMul(stepSize,acceleration);
        Vector3dInterface nextPosition = position.addMul(stepSize,nextVelocity);

        return new SystemState(nextPosition,nextVelocity);




     //  return y.addMul(stepSize, f.call(currentTime, y));


    }

    @Override
    public ODEFunctionInterface<E> getFunction() {
        return f;
    }


    @Override
    public String toString() {
        return "EulerSolver{" +
                "function used =" + f +
                '}';
    }


}
