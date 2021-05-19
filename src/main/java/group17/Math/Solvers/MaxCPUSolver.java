package group17.Math.Solvers;

import group17.Interfaces.ODEFunctionInterface;
import group17.Interfaces.RateInterface;
import group17.Interfaces.StateInterface;
import group17.Interfaces.Vector3dInterface;
import group17.Math.Utils.Vector3D;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;

import static group17.Config.*;
import static group17.Main.simulation;

public class MaxCPUSolver implements ODEFunctionInterface {

    ExecutorService service;

    @Contract(pure = true)
    public MaxCPUSolver() {

    }

    @Contract("_, _, _ -> param3")
    public static Vector3dInterface set(int i, StateInterface state, Vector3dInterface acc) {
        if (DEFAULT_SOLVER == EULER_SOLVER)
            state.getRateOfChange().getVelocities().set(i, state.getRateOfChange().getVelocities().get(i).add(acc));
        if (DEFAULT_SOLVER == RUNGE_KUTTA_SOLVER)
            state.getRateOfChange().getVelocities().set(i, acc.clone());
        return acc;
    }

    @Contract("_, _, _ -> param3")
    public final StateInterface evaluate(double t, int i, @NotNull StateInterface y) {
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
                acc = acc.mul((G * simulation.getSystem().getCelestialBodies().get(k).getMASS()) / (squareDist == 0 ? 0.0000001 : squareDist)); // Convert force to acceleration
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
