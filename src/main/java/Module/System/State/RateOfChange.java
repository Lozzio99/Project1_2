package Module.System.State;

public class RateOfChange<E> implements RateInterface<E> {
    E vel;

    public RateOfChange(E v) {
        this.vel = v;
    }

    @Override
    public E get() {
        return this.vel;
    }

    @Override
    public void set(E v) {
        this.vel = v;
    }

    @Override
    public String toString() {
        return vel.toString();
    }

}
