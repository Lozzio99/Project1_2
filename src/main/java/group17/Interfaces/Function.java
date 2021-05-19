package group17.Interfaces;

@FunctionalInterface
public interface Function<E> {
    E apply(E value);

    @FunctionalInterface
    interface FX<E, T> {
        T apply(E value);
    }
}

