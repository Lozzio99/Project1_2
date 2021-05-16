package group17.System;

import group17.Interfaces.Vector3dInterface;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import static group17.Config.ORIGINAL_DATA;
import static group17.Main.simulation;
import static group17.Main.userDialog;

public class ErrorReport implements Runnable {

    public static int monthIndex = -1;
    private final ErrorData state;
    private List<Vector3dInterface> diff_from_original_position, diff_from_original_velocities;

    @Contract(pure = true)
    public ErrorReport(final ErrorData state) {
        //make a copy of the references
        this.state = state;
    }

    public void start() {
        new Thread(this, "Error Log").start();
    }

    @Override
    public void run() {
        //ASSUMING [ NO ] ROCKET HERE
        diff_from_original_position = new ArrayList<>(11);
        diff_from_original_velocities = new ArrayList<>(11);

        for (int i = 0; i < state.getPositions().size(); i++) {

            diff_from_original_position.add(
                    ORIGINAL_DATA[monthIndex]
                            .getPositions().get(i)
                            .absSub(state.getPositions().get(i)));
            diff_from_original_velocities.add(
                    ORIGINAL_DATA[monthIndex]
                            .getVelocities().get(i)
                            .absSub(state.getVelocities().get(i)));
            if (true) {
                System.out.println("PLANET " + simulation.getSystem().getCelestialBodies().get(i).toString() + "~~~~~~~~");
                System.out.println("ORIGINAL PV : " + ORIGINAL_DATA[monthIndex].getPositions().get(i));
                System.out.println("SIMULATED PV: " + state.getPositions().get(i));
                System.out.println("ERROR POSIT : " + diff_from_original_position.get(i));
                System.out.println();
                System.out.println("ORIGINAL VV : " + ORIGINAL_DATA[monthIndex].getVelocities().get(i));
                System.out.println("SIMULATED VV:" + state.getVelocities().get(i));
                System.out.println("ERROR VELOC : " + diff_from_original_velocities.get(i));
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
        }

        userDialog.getErrorWindow().updateLabels(new ErrorData(diff_from_original_position, diff_from_original_velocities));
    }


}
