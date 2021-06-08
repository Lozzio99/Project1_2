package Module.System.State;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

public class State<E> {
    List<E> type;

    @Contract(pure = true)
    public State() {
        this.type = new ArrayList<>();
    }

    public List<E> getState() {
        return type;
    }
}
