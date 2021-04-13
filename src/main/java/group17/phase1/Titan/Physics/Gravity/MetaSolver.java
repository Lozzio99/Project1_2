package group17.phase1.Titan.Physics.Gravity;

import group17.phase1.Titan.Main;
import group17.phase1.Titan.Physics.Vector3D;
import group17.phase1.Titan.interfaces.RateInterface;
import group17.phase1.Titan.interfaces.StateInterface;
import group17.phase1.Titan.interfaces.Vector3dInterface;

import java.util.concurrent.CompletableFuture;

public class MetaSolver extends Solver
{

    static StateInterface y;



    @Override
    public RateInterface call(double t, StateInterface yGiven)
    {

        y = yGiven;
        for (int i = 0; i< y.getPositions().size(); i++){
            final var finalI = i;
            CompletableFuture.supplyAsync(() -> evaluate(finalI))
                    .thenAccept(e -> set(finalI,e));
        }
        return Main.simulation.rateOfChange();
    }


    public static Vector3dInterface set(int i,Vector3dInterface v)
    {
        Main.simulation.rateOfChange().getRateOfChange().get(i).add(v);
        return v;
    }

    public static Vector3dInterface evaluate(int i)
    {
        Vector3dInterface totalAcc = new Vector3D();
        for (int k = 0;k< y.getPositions().size(); k++)
        {
            if (i!= k)
            {
                Vector3dInterface acc = y.getPositions().get(i).clone();
                double squareDist = Math.pow(y.getPositions().get(i).dist(y.getPositions().get(k)),2);
                acc.sub(y.getPositions().get(k)); // Get the force vector
                double den = Math.sqrt(squareDist);
                    /*
                        ! Important !
                        if two bodies collapses into the same point
                        that would crash to NaN and consequently
                        the same in all the system
                     */
                acc.mul(1 / (den == 0 ? 0.0000001 : den)); // Normalise to length 1
                acc.mul((G * Main.simulation.solarSystem().getCelestialBodies().get(k).getMASS()) / (squareDist == 0 ? 0.0000001 : squareDist) ); // Convert force to acceleration
                totalAcc.addMul(Main.simulation.getStepSize(), acc);
            }
        }
        return totalAcc;
    }
}
