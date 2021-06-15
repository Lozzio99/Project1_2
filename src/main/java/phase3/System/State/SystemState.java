package phase3.System.State;

import phase3.Math.ADT.Vector3D;
import phase3.Math.ADT.Vector3dInterface;

import java.util.Arrays;

public class SystemState<E> implements StateInterface<E> {
    final E[] y;

    @SafeVarargs
    public SystemState(E... y) {
        this.y = y;
    }

    /**
     * Note : we are not doing any safety check here, but it is assumed that the state object and the rate object
     * have the same dimensions.
     * Example,
     * State and rate for 2nd order differentiation
     * State = [ y   ,  dy/dx   ]
     * Rate = [dy/dx , d2y/dx2 ]
     * <p>
     * So that the involved operation will be to return a new state which is the result of
     * this.y + (rate.y * step) applied accordingly to the integration order
     */
    @Override
    public StateInterface<E> addMul(double step, RateInterface<E> rate) {
        StateInterface<E> y1;
        if (y instanceof Vector3dInterface[]) {
            StateInterface<Vector3dInterface> n = new SystemState<>(new Vector3D[y.length]);
            for (int i = 0; i < y.length; i++)
                n.get()[i] = ((Vector3dInterface) this.get()[i]).addMul(step, ((Vector3dInterface) rate.get()[i]));
            y1 = (SystemState<E>) n;
        } else if (y instanceof Double[]) {
            StateInterface<Double> n = new SystemState<>(new Double[y.length]);
            for (int i = 0; i < y.length; i++)
                n.get()[i] = ((Double) this.get()[i]) + (step * ((Double) rate.get()[i]));
            y1 = (SystemState<E>) n;
        } else throw new UnsupportedOperationException();
        return y1;
    }

    @Override
    public E[] get() {
        return y;
    }

    @Override
    public StateInterface<E> copy() {
        return new SystemState<>(y[0], y[1]);
    }

    @Override
    public String toString() {
        return "[ " + Arrays.toString(this.y) + " ]";
    }
}
