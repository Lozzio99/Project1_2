package group17.Interfaces;

import java.util.List;

/**
 * The interface Reporter interface.
 */
public interface ReporterInterface extends Runnable {

    /**
     * Init.
     */
    void init();

    /**
     * Start.
     */
    void start();

    /**
     * Report.
     */
    void report();

    void reportToAssist();

    /**
     * Report.
     *
     * @param string the string
     */
    void report(String string);

    /**
     * Report.
     *
     * @param strings the strings
     */
    void report(List<String> strings);

    /**
     * Report.
     *
     * @param e the e
     */
    void report(Throwable e);

    /**
     * Report.
     *
     * @param t the t
     * @param e the e
     */
    void report(Thread t, Throwable e);

    /**
     * Parse exception string.
     *
     * @param message the message
     * @return the string
     */
    String parseException(String message);
}
