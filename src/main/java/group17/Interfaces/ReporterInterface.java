package group17.Interfaces;

import java.util.List;

public interface ReporterInterface extends Runnable {

    void init();

    void start();

    void report();

    void report(String string);

    void report(List<String> strings);

    void report(Throwable e);

    String parseException(String message);
}
