package Module.Math.Gravity;

/**
 * An interface for the function f that represents the
 * differential equation dy/dt = f(t,y).
 * @param <E> the type parameter
 */
@FunctionalInterface
public interface Function<E> {
    /**
     * Apply e.
     *
     * @param value the value
     * @return the e
     */
    E apply(E value);

    /**
     * The interface Fx.
     *
     * @param <E> the type parameter
     * @param <T> the type parameter
     */
    @FunctionalInterface
    interface FX<E, T> {
        /**
         * Apply t.
         *
         * @param value the value
         * @return the t
         */
        T apply(E value);
    }
}

