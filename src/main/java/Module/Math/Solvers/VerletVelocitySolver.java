package Module.Math.Solvers;


import Module.Math.ADT.Vector3D;
import Module.Math.ADT.Vector3dInterface;
import Module.Math.Functions.ODEFunctionInterface;
import Module.System.State.StateInterface;
import Module.System.State.SystemState;

/**
 * Verlet Velocity Solver (both positions and velocity, less round-off errors)
 * Second order Solver
 */
public class VerletVelocitySolver<E> implements ODESolverInterface<E> {

    private final ODEFunctionInterface<E> f;
    private Vector3dInterface acceleration;
    private final boolean first = true;

    /**
     * Instantiates a new Verlet velocity solver.
     *
     * @param f the f
     */
    public VerletVelocitySolver(final ODEFunctionInterface<E> f) {
        this.f = f;
    }


    /**
     * Step of a Verlet Velocity Algorithm:
     * Consists of two equations:
     * x_n+1 = x_n + (v_n)*delta_t+1/2*(a_n)*(delta_t^2)
     * v_n+1 = v_n + 1/2*((a_n+1)+(a_n))*(delta_t)
     * x - position, v - velocity, delta_t - step size
     * Source: http://www.physics.udel.edu/~bnikolic/teaching/phys660/numerical_ode/node5.html
     *
     * @param f ordinary differential equations - function of acceleration
     * @param t current time
     * @param y current state;
     * @param h step size
     * @return new state
     */
    @Override
    public StateInterface<E> step(ODEFunctionInterface<E> f, double t, StateInterface<E> y, double h) {
        StateInterface<E> next;
        Vector3dInterface position;
        if ((y.get()) instanceof Vector3dInterface) {
            position = (Vector3dInterface) y.get();
            Vector3dInterface velocity = (Vector3dInterface) y.getRateOfChange().get();
            /*
            if (first) {

                RungeKuttaSolver<Vector3dInterface> solver = new RungeKuttaSolver<>(new ModuleFunction().evaluateCurrentAccelerationFunction());
                next = (StateInterface<E>) solver.step(solver.getFunction(), t, (SystemState<Vector3dInterface>) y, h);
                acceleration = solver.getAcceleration();
               acceleration = (Vector3dInterface) f.call(t,y).get(); // acceleration at time t (current time i.e. a_n)
              //  next_acceleration = (Vector3dInterface) f.call(t+h,y).get(); // acceleration a_n+1

                first = false;

            }

             */
            if(first) {
                //x_n+1 = x_n + (v_n)*h+1/2*(a_n)*(h^2)
                //v_n+1 = v_n + 1/2*((a_n+1)+(a_n))*(h)
                Vector3dInterface acceleration =(Vector3dInterface) f.call(t,y).get();
                Vector3dInterface next_position = position.add(velocity.mul(h).addMul(h*h*0.5,acceleration));

                Vector3dInterface next_acceleration = (Vector3dInterface) f.call(t, (SystemState<E>) new SystemState<>(next_position, new Vector3D())).get();
                Vector3dInterface next_velocity = velocity.addMul(h * 0.5, next_acceleration.add(acceleration));
                next = (SystemState<E>) new SystemState<>(next_position, next_velocity);

                return next;
            }
        } else {
            throw new UnsupportedOperationException();
        }

        return null;
    }
/*
    @Override
    public StateInterface<E> step(ODEFunctionInterface<E> f, double t, StateInterface<E> y, double h) {
        if (y.get() instanceof Vector3dInterface) {
            // next position
            RateInterface<Vector3dInterface> velocity = (RateOfChange<Vector3dInterface>) y.getRateOfChange();
            // next position
            StateInterface<Vector3dInterface> part2 = (SystemState<Vector3dInterface>) y.addMul(0.5 * h * h, f.call(1, y));
            part2.set(part2.get().add(((Vector3dInterface) y.get())));
            SystemState<Vector3dInterface> part3 = (SystemState<Vector3dInterface>) y.addMul(h, (RateOfChange<E>) velocity);
            StateInterface<Vector3dInterface> part4 = new SystemState<>(part2.get().add(part3.get()));
            RateInterface<Vector3dInterface> part5 = (RateOfChange<Vector3dInterface>) f.call(1, (SystemState<E>) part4);
            part5.set(part5.get().add((Vector3dInterface) f.call(1, y).get()));
            RateOfChange<Vector3dInterface> part6 = new RateOfChange<>(part5.get().mul(0.5 * h));
            RateOfChange<Vector3dInterface> part7 = new RateOfChange<>(velocity.get().add(part6.get()));
            part4.getRateOfChange().set(part7.get());
            return (SystemState<E>) part4;
        } else {
            throw new UnsupportedOperationException();
        }

    }

 */

    @Override
    public ODEFunctionInterface<E> getFunction() {
        return f;
    }

    @Override
    public String toString() {
        return "VerletVelocitySolver";
    }
}