package group17.phase1.Titan.Simulations.PendulumSimulation;

import group17.phase1.Titan.Simulations.SolarSystemSimulation.SimulationUpdater;

import static group17.phase1.Titan.Config.FPS;
import static group17.phase1.Titan.Main.simulation;

public class PendulumUpdater extends SimulationUpdater {

    public PendulumUpdater(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "Pendulum Updater";
    }

    @Override
    public void run() {
        isKilled = false;
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / FPS;
        double delta = 0;

        while (simulation.running()) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                simulation.system().step();
                if (System.currentTimeMillis() - timer > 1000)
                    System.out.println(simulation.system().toString());
                timer += 1000;
                delta--;
            }

        }
    }


    @Override
    public void launch() {
        super.launch();
    }
}
