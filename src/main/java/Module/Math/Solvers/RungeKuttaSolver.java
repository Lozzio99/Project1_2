package Module.Math.Solvers;


import Module.Math.ADT.Vector3dInterface;
import Module.Math.Functions.ODEFunctionInterface;
import Module.System.State.RateInterface;
import Module.System.State.RateOfChange;
import Module.System.State.StateInterface;
import Module.System.State.SystemState;

/**
 * The type Runge kutta 4 th solver.
 * Fourth order solver for First Order ODE
 */

//OK around 10^-2/10^-3 error
public class RungeKuttaSolver<E> implements ODESolverInterface<E> {


    private final ODEFunctionInterface<E> f;
    private Vector3dInterface acceleration;

    /**
     * Instantiates a new Runge kutta 4 th solver.
     *
     * @param f the function
     */
    public RungeKuttaSolver(final ODEFunctionInterface<E> f) {
        this.f = f;
    }



    /**
     *
     * k1=f(tn,yn)
     * k2=f(tn+h2,yn+h/2*k1)
     * k3=f(tn+h2,yn+h/2*k2)
     * k4=hf(tn+h,yn+h*k3)
     * kk = (k1 + 2*k2 + 2*k3 + k4)/6
     *
     *
     * @param f the function representing Newton's Gravitational law
     * @param y the instance of the Simulation
     * @param h stepSize
     * @return the next state of the simulation based on Runge-Kutta 4 Step
     */
    public StateInterface<E> step(ODEFunctionInterface<E> f, double t, final StateInterface<E> y, double h) {
        /*
        RateInterface<E> k1, k2, k3, k4;
        k1 = f.call(t, y);
        k2 = f.call(t + (h / 2), y.addMul(0.5 * h, k1));
        k3 = f.call(t + (h / 2), y.addMul(0.5 * h, k2));
        k4 = f.call(t + h, y.addMul(h, k3));
        if (y.get() instanceof Vector3dInterface) {
            RateInterface<Vector3dInterface> k5 = new RateOfChange<>(
                    ((Vector3dInterface) k1.get())
                            .sumOf(((Vector3dInterface) k2.get()).mul(2),
                                    ((Vector3dInterface) k3.get()).mul(2),
                                    ((Vector3dInterface) k4.get())));
            k5.set(k5.get().div(6));
            return y.addMul(h, ((RateOfChange<E>) k5));
        } else if (y.get() instanceof Double) {
            RateInterface<Double> k5 = new RateOfChange<>(
                    ((Double) k1.get()) +
                            (Double) k2.get() * 2 +
                            ((Double) k3.get()) * 2 +
                            ((Double) k4.get()));
            k5.set(k5.get() / 6);
            return y.addMul(h, (RateOfChange<E>) k5);
        } else
            throw new UnsupportedOperationException();
         */


        Vector3dInterface position = (Vector3dInterface) y.get();
        Vector3dInterface velocity = (Vector3dInterface) y.getRateOfChange().get();


        Vector3dInterface k11,k12,k13,k14;  // position delta
        Vector3dInterface k21,k22,k23,k24;  // velocity delta



        k11 =  velocity.mul(h);
        k21 =((Vector3dInterface) f.call(t,y).get()).mul(h);


        k12 = velocity.add(k21.mul(0.5)).mul(h);
        k22 = ((Vector3dInterface) f.call(t+h*0.5,
                new SystemState(position.addMul(0.5,k11),velocity.addMul(0.5,k21))).get()).mul(h);


        k13 =velocity.add(k22.mul(0.5)).mul(h);
        k23 =((Vector3dInterface) f.call(t+h*0.5,new SystemState(position.addMul(0.5,k12),velocity.addMul(0.5,k22))).get()).mul(h);


        k14 = velocity.add(k23).mul(h);
        k24 =((Vector3dInterface) f.call(t+h,new SystemState(position.add(k13),velocity.add(k23))).get()).mul(h);



         Vector3dInterface next_position =position.add(k11.sumOf(k12.mul(2),k13.mul(2),k14).div(6));
         Vector3dInterface next_velocity =velocity.add(k21.sumOf(k22.mul(2),k23.mul(2),k24).div(6));

          acceleration =  next_velocity.sub(velocity).div(h);



        return new SystemState(next_position,next_velocity);
    }

    public Vector3dInterface getAcceleration() {
        return acceleration;
    }

    @Override
    public ODEFunctionInterface<E> getFunction() {
        return this.f;
    }

    @Override
    public String toString() {
        return "RungeKutta4thSolver{" +
                "function used =" + f +
                '}';
    }


}
