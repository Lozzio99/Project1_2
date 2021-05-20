package group17.Interfaces;

/**
 * The interface Function.
 *
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

