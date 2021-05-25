package group17.System;

import group17.Interfaces.ODESolverInterface;
import group17.Interfaces.StateInterface;
import group17.Interfaces.Vector3dInterface;
import group17.Math.Solvers.RungeKutta4thSolver;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import static group17.Config.*;
import static group17.Config.STEP_SIZE;
import static group17.Main.simulation;
import static group17.Main.userDialog;

public class ErrorReport implements Runnable {

    public static int monthIndex = -1;
    private final ErrorData state;
    private List<Vector3dInterface> absPosition, absVelocity;
    private List<Vector3dInterface> relPosition, relVelocity;

    @Contract(pure = true)
    public ErrorReport(final ErrorData state) {
        //make a copy of the references
        this.state = state;
    }

    public void start() {
        if (monthIndex < 13) new Thread(this, "Error Log").start();
        else System.exit(0);
    }

    @Override
    public void run() {
        userDialog.disable(6);
        //ASSUMING [ NO ] ROCKET HERE
        absPosition = new ArrayList<>(11);
        absVelocity = new ArrayList<>(11);
        relPosition = new ArrayList<>(11);
        relVelocity = new ArrayList<>(11);

        double posMeanAbsoluteError = 0;
        double velMeanAbsoluteError = 0;
        double posMeanRelativeError = 0;
        double velMeanRelativeError = 0;



        for (int i = 0; i < state.getPositions().size(); i++) {
            absPosition.add(ORIGINAL_DATA[monthIndex].getPositions().get(i)
                    .absSub(state.getPositions().get(i)));

            absVelocity.add(ORIGINAL_DATA[monthIndex].getVelocities().get(i)
                    .absSub(state.getVelocities().get(i)));
            relPosition.add(absPosition.get(i)
                    .div(ORIGINAL_DATA[monthIndex].getPositions().get(i)));
            relVelocity.add(absVelocity.get(i)
                    .div(ORIGINAL_DATA[monthIndex].getVelocities().get(i)));


            posMeanAbsoluteError += absPosition.get(i).norm();
            velMeanAbsoluteError += absVelocity.get(i).norm();
            posMeanRelativeError += relPosition.get(i).norm();
            velMeanRelativeError += relVelocity.get(i).norm();
            if (DEBUG) {
                System.out.println("MONTH " + monthIndex);
                System.out.println("PLANET " + simulation.getSystem().getCelestialBodies().get(i).toString() + "~~~~~~~~");
                //System.out.println("ORIGINAL PV : " + ORIGINAL_DATA[monthIndex].getPositions().get(i));
                //System.out.println("SIMULATED PV: " + state.getPositions().get(i));
                System.out.println("ABS ERROR POSIT : " + absPosition.get(i));
                System.out.println("ABS ERROR VEL   : " + absVelocity.get(i));
                System.out.println();
                //System.out.println("ORIGINAL VV : " + ORIGINAL_DATA[monthIndex].getVelocities().get(i));
                //System.out.println("SIMULATED VV:" + state.getVelocities().get(i));
                System.out.println("REL ERROR POS : " + relPosition.get(i));
                System.out.println("REL ERROR VEL : " + relVelocity.get(i));
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }

        }

        posMeanAbsoluteError = posMeanAbsoluteError/state.getPositions().size();
        velMeanAbsoluteError = velMeanAbsoluteError/state.getVelocities().size();
        posMeanRelativeError = posMeanRelativeError/state.getPositions().size();
        velMeanRelativeError = velMeanRelativeError/state.getPositions().size();


            System.out.println("MEAN POSITION ABSOLUTE ERROR: " + posMeanAbsoluteError);
            System.out.println("MEAN VELOCITY ABSOLUTE ERROR: " + velMeanAbsoluteError);
            System.out.println("MEAN POSTION RELATIVE ERROR: " + posMeanRelativeError * 100 + " %");
            System.out.println("MEAN VELOCITY RELATIVE ERROR:" + velMeanRelativeError * 100 + " %");


        userDialog.getErrorWindow().updateLabels(new ErrorData(absPosition, absVelocity));
        userDialog.enable(6);

    }


}
