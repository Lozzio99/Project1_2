package group17.System;

public class ErrorReport implements Runnable {

    private final ErrorData prevState, state;

    public ErrorReport(final ErrorData prevState, final ErrorData state) {
        //make a copy of the references
        this.prevState = prevState;
        this.state = state;
        //this.start();
    }

    public void start() {
        new Thread(this, "Error Log").start();
    }

    @Override
    public void run() {

    }


}
