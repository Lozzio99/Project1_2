package group17.System;

public class ErrorReport implements Runnable {

    private final Data prevState, state;

    public ErrorReport(final Data prevState, final Data state) {
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
