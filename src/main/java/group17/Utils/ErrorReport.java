package group17.Utils;

import group17.Interfaces.Vector3dInterface;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import static group17.Main.simulation;
import static group17.Main.userDialog;
import static group17.Utils.Config.DEBUG;
import static group17.Utils.Config.ORIGINAL_DATA;

/**
 * The type Error report.
 */
public class ErrorReport implements Runnable {

    /**
     * The constant monthIndex.
     */
    public static int monthIndex = -1;
    private final ErrorData state;
    private List<Vector3dInterface> absPosition, absVelocity;
    private List<Vector3dInterface> relPosition, relVelocity;

    /**
     * Instantiates a new Error report.
     *
     * @param state the state
     */
    @Contract(pure = true)
    public ErrorReport(final ErrorData state) {
        //make a copy of the references
        this.state = state;
    }

    /**
     * Start.
     */
    public void start() {
        if (monthIndex < 13) new Thread(this, "Error Log").start();
    }

    @Override
    public void run() {
        if (userDialog != null) userDialog.disable(6);
        //ASSUMING [ NO ] ROCKET HERE
        absPosition = new ArrayList<>(11);
        absVelocity = new ArrayList<>(11);
        relPosition = new ArrayList<>(11);
        relVelocity = new ArrayList<>(11);

        for (int i = 0; i < state.getPositions().size(); i++) {
            absPosition.add(ORIGINAL_DATA[monthIndex].getPositions().get(i)
                    .absSub(state.getPositions().get(i)));
            absVelocity.add(ORIGINAL_DATA[monthIndex].getVelocities().get(i)
                    .absSub(state.getVelocities().get(i)));
            relPosition.add(absPosition.get(i)
                    .div(ORIGINAL_DATA[monthIndex].getPositions().get(i)));
            relVelocity.add(absVelocity.get(i)
                    .div(ORIGINAL_DATA[monthIndex].getVelocities().get(i)));
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

        if (userDialog != null) {
            userDialog.getErrorWindow().updateLabels(new ErrorData(absPosition, absVelocity));
            userDialog.enable(6);
        }
    }


}
