package group17.phase1.Titan.Physics.Solvers;

import group17.phase1.Titan.Config;
import group17.phase1.Titan.Interfaces.ODEFunctionInterface;
import group17.phase1.Titan.Interfaces.RateInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.Main;
import group17.phase1.Titan.Physics.Math.Vector3D;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import static group17.phase1.Titan.Config.G;

public class MaxCPUSolver implements ODEFunctionInterface {

    static StateInterface y;
    ExecutorService service;

    public MaxCPUSolver() {
    }

    public static Vector3dInterface set(int i, Vector3dInterface v) {
        Main.simulation.system().systemRateOfChange().getRateOfChange()
                .set(i, Main.simulation.system().systemRateOfChange().getRateOfChange().get(i).add(v));
        return v;
    }

    public static Vector3dInterface evaluate(int i) {
        Vector3dInterface totalAcc = new Vector3D();
        for (int k = 0; k < y.getPositions().size(); k++) {
            if (i != k) {
                Vector3dInterface acc = y.getPositions().get(i).clone();
                double squareDist = Math.pow(y.getPositions().get(i).dist(y.getPositions().get(k)), 2);
                acc = y.getPositions().get(k).sub(acc); // Get the force vector
                double den = Math.sqrt(squareDist);
                    /*
                        ! Important !
                        if two bodies collapses into the same point
                        that would crash to NaN and consequently
                        the same in all the system
                     */
                acc = acc.mul(1 / (den == 0 ? 0.0000001 : den)); // Normalise to length 1
                acc = acc.mul((G * Main.simulation.system().getCelestialBodies().get(k).getMASS()) / (squareDist == 0 ? 0.0000001 : squareDist)); // Convert force to acceleration
                totalAcc = totalAcc.addMul(Config.STEP_SIZE, acc);
            }
        }
        return totalAcc;
    }

    public MaxCPUSolver setCPULevel(int level) {
        service = Executors.newWorkStealingPool(level);
        return this;
    }

    @Override
    public RateInterface call(double t, StateInterface yGiven) {
        Config.STEP_SIZE = t;
        y = yGiven;

        for (int i = 0; i < y.getPositions().size(); i++) {
            final var finalI = i;
            try {
                CompletableFuture.supplyAsync(() -> evaluate(finalI), service)
                        .thenApply(e -> set(finalI, e));
            } catch (RejectedExecutionException ignored) {

            }
        }
        return Main.simulation.system().systemRateOfChange();
    }

    public void shutDown() {
        this.service.shutdownNow();
        this.service.shutdown();
    }


}
