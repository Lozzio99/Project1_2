package group17.phase1.Titan.Physics.Solvers;

import group17.phase1.Titan.Interfaces.ODEFunctionInterface;
import group17.phase1.Titan.Interfaces.RateInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.Physics.Math.Vector3D;

import java.util.concurrent.*;

import static group17.phase1.Titan.Config.*;
import static group17.phase1.Titan.Main.simulation;

public class MaxCPUSolver implements ODEFunctionInterface {

    ExecutorService service;

    public MaxCPUSolver() {

    }

    public static Vector3dInterface set(int i, StateInterface state, Vector3dInterface acc) {
        if (SOLVER == EULER_SOLVER)
            state.getRateOfChange().getVelocities().set(i, state.getRateOfChange().getVelocities().get(i).add(acc));
        if (SOLVER == RUNGE_KUTTA_SOLVER)
            state.getRateOfChange().getVelocities().set(i, acc.clone());
        return acc;
    }

    public final StateInterface evaluate(double t, int i, StateInterface y) {
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
                acc = acc.mul((G * simulation.system().getCelestialBodies().get(k).getMASS()) / (squareDist == 0 ? 0.0000001 : squareDist)); // Convert force to acceleration
                totalAcc = totalAcc.addMul(t, acc);
            }
        }
        set(i, y, totalAcc);
        return y;
    }

    public MaxCPUSolver setCPULevel(int level) {
        //FIXME : why is this not waking up the asked number of threads?
        service = Executors.newWorkStealingPool(level);
        return this;
    }

    public ExecutorService getService() {
        return service;
    }

    @Override
    public RateInterface call(double t, StateInterface yGiven) {
        for (int i = 0; i < yGiven.getPositions().size(); i++) {
            final var finalI = i;
            try {
                CompletableFuture
                        .supplyAsync(() -> evaluate(t, finalI, yGiven), service).join();//start a new parallel worker thread (async from previous ones)
                //.thenApply(e -> set(finalI, yGiven, e)) //then once it's completed put it where it should be,
                //.join();  // make sure to accept the completed state only
            } catch (RejectedExecutionException | CancellationException ignored) {
            }

        }
        return yGiven.getRateOfChange();
    }

    public void shutDown() {
        this.service.shutdownNow();
        this.service.shutdown();
    }

    @Override
    public String toString() {
        return "MaxCPUSolver{" +
                "service=" + service.toString() +
                '}';
    }
}
