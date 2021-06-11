package Module.System.State;

import Module.Math.ADT.Vector3D;
import Module.Math.ADT.Vector3dInterface;

public class SystemState<E> implements StateInterface<E> {
    E position;
    RateInterface<E> vel;

    @SuppressWarnings("{unchecked,unsafe}")
    public SystemState(E pos) {
        this.position = pos;
        if (pos instanceof Vector3dInterface) {
            RateInterface<Vector3dInterface> r = new RateOfChange<>(new Vector3D(0, 0, 0));
            this.vel = (RateOfChange<E>) r;
        } else if (pos instanceof Double) {
            RateInterface<Double> r = new RateOfChange<>(0.);
            this.vel = (RateOfChange<E>) r;
        } else throw new UnsupportedOperationException();
    }

    public SystemState(E pos, E vel) {
        this.position = pos;
        this.vel = new RateOfChange<>(vel);
    }

    @Override
    //@SuppressWarnings("{unchecked,unsafe}")
    public StateInterface<E> addMul(double step, RateInterface<E> rate) {
        if (this.position instanceof Vector3dInterface) {
            Vector3dInterface vel = ((Vector3dInterface) this.vel.get()).addMul(step, ((Vector3dInterface) rate.get()));
            StateInterface<Vector3dInterface> s = new SystemState<>(((Vector3dInterface) this.position).addMul(step, vel), vel);
            return (SystemState<E>) s;
        } else if (this.position instanceof Double) {
            double v = ((Double) this.vel.get()) + (((Double) rate.get()) * step);
            StateInterface<Double> s = new SystemState<>(((Double) this.position) + (step * v), v);
            return (SystemState<E>) s;
        } else
            throw new UnsupportedOperationException();
    }


    @Override
    public E get() {
        return position;
    }

    @Override
    public void set(E v) {
        this.position = v;
    }

    @Override
    public RateInterface<E> getRateOfChange() {
        return vel;
    }


    @Override
    public String toString() {
        return "[ " + this.position.toString() + " ; " + this.getRateOfChange().toString() + " ]";
    }
}
