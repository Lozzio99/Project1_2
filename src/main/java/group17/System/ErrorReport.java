package group17.System;

import org.jetbrains.annotations.Contract;

public class ErrorReport implements Runnable {

    public static int monthIndex = 0;
    private final ErrorData state;

    @Contract(pure = true)
    public ErrorReport(final ErrorData state) {
        //make a copy of the references
        this.state = state;
        this.start();
    }

    public void start() {
        new Thread(this, "Error Log").start();
    }

    @Override
    public void run() {
        monthIndex++;

    }


}
