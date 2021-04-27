package group17.phase1.Titan.Simulations.RocketSimulation;

import group17.phase1.Titan.Physics.Math.Vector3D;
import group17.phase1.Titan.Simulations.SolarSystemSimulation.SimulationUpdater;

import java.util.Random;

import static group17.phase1.Titan.Config.FPS;
import static group17.phase1.Titan.Main.simulation;

public class RocketUpdater extends SimulationUpdater {
    public RocketUpdater(String name) {
        super(name);
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
                double r = new Random().nextDouble();
                double s = new Random().nextDouble();
                r *= new Random().nextInt(10);
                if (s > 0.5) {
                    r *= -1;
                }
                simulation.system().systemState().getPositions().set(1,
                        simulation.system().systemState().getPositions().get(1).add(new Vector3D(r, r, r)));
                if (System.currentTimeMillis() - timer > 1000) {

                    //System.out.println(simulation.system().systemState().getPositions().get(1));
                    timer += 1000;
                }
                delta--;
            }

        }
    }
}
