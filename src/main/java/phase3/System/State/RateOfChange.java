package phase3.System.State;

import java.util.Arrays;

public class RateOfChange<E> implements RateInterface<E> {
    final E[] dy;

    @SafeVarargs
    public RateOfChange(E... y) {
        this.dy = y;
    }

    @Override
    public E[] get() {
        return this.dy;
    }

    @Override
    public String toString() {
        return Arrays.toString(dy);
    }
}
