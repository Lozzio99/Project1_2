package group17.phase1.Titan.Simulation;

import static group17.phase1.Titan.Config.FPS;
import static group17.phase1.Titan.Config.STEP_SIZE;
import static group17.phase1.Titan.Main.simulation;

public class SimulationUpdater extends Thread {
    private boolean isKilled;

    //TODO : set the this thread to be independent -> I.O. or ExceptionHandler
    //TODO : if no Dialog Assist simulation doesn't run/start


    @Override
    public void run() {
        isKilled = false;
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / FPS;
        double delta = 0;

        simulation.assist().acquireData();

        while (simulation.graphics().get().running()) {

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                if (isKilled) {
                    try {
                        this.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                simulation.system().step();
                simulation.system().getClock().step(STEP_SIZE);

                if (System.currentTimeMillis() - timer > 1000) {
                    simulation.assist().setOutput(simulation.toString());
                    simulation.assist().setDate();
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
        System.out.println("launching");
        simulation.graphics().get().setRunning();
        this.start();
    }
}
