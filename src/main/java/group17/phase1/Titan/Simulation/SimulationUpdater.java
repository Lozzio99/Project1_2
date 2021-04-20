package group17.phase1.Titan.Simulation;

import static group17.phase1.Titan.Config.*;
import static group17.phase1.Titan.Main.simulation;

public class SimulationUpdater extends Thread {
    private volatile boolean isKilled;

    //TODO : set the this thread to be independent -> I.O. or ExceptionHandler
    //TODO : if no Dialog Assist simulation doesn't run/start

    public SimulationUpdater() {
        super("Updater");
    }

    @Override
    public void run() {
        isKilled = false;
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / FPS;
        double delta = 0;

        if (ENABLE_ASSIST)
            simulation.assist().acquireData();

        while (simulation.graphics().get().running()) {

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (isKilled) {
                onSpinWait();
            }

            while (delta >= 1) {

                simulation.system().step();
                simulation.system().getClock().step(STEP_SIZE);

                if (System.currentTimeMillis() - timer > 1000) {
                    if (ENABLE_ASSIST) {
                        simulation.assist().setOutput(simulation.toString());
                        simulation.assist().setDate();
                    } else {
                        //TODO : here goes IO stream
                        //System.out.println(simulation.toString());
                    }
                    timer += 1000;
                }
                delta--;
            }

        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public State getState() {
        return super.getState();
    }

    public void tryStop() {
        this.isKilled = true;
    }

    public void launch() {
        System.out.println("Launching main graphics...");
        simulation.graphics().get().setRunning();
        if (this.isKilled) {
            this.isKilled = false;
            return;
        }
        this.start();
    }
}
