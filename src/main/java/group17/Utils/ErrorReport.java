package group17.Utils;

import group17.Interfaces.Vector3dInterface;
import group17.Math.Lib.Vector3D;
import org.jetbrains.annotations.Contract;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
    public static volatile int monthIndex = -1;
    private final ErrorData state;
    private static volatile AtomicReference<FileWriter> fileWriter;
    private List<Vector3dInterface> absPosition, absVelocity;
    private List<Vector3dInterface> relPosition, relVelocity;
    private final List<Vector3dInterface> avgPosErrorMonth;
    private final List<Vector3dInterface> avgVelErrorMonth;

    /**
     * Instantiates a new Error report.
     *
     * @param state the state
     */
    @Contract(pure = true)
    public ErrorReport(final ErrorData state) {
        //make a copy of the references
        this.state = state;
        this.avgPosErrorMonth = new ArrayList<>(13);
        this.avgVelErrorMonth = new ArrayList<>(13);
        fileWriter = null;
    }

    /**
     * Instantiates a new Error report.
     *
     * @param fw        the fw
     * @param errorData the error data
     */
    @Contract(pure = true)
    public ErrorReport(AtomicReference<FileWriter> fw, ErrorData errorData) {
        this(errorData);
        fileWriter = fw;
    }

    /**
     * Start.
     */
    public void start() {
        if (monthIndex < 13) new Thread(this, "Error Log").start();
    }

    @Override
    public void run() {
        System.out.println("running error report");
        if (userDialog != null) userDialog.disable(6);
        //ASSUMING [ NO ] ROCKET HERE
        absPosition = new ArrayList<>(11);
        absVelocity = new ArrayList<>(11);
        relPosition = new ArrayList<>(11);
        relVelocity = new ArrayList<>(11);
        Vector3dInterface averageErrorPosition = new Vector3D();
        Vector3dInterface averageErrorVelocity = new Vector3D();
        if (DEBUG) {
            System.out.println("********************        SOE     **************************");
            System.out.println("MONTH " + monthIndex);
        }

        for (int i = 0; i < state.getPositions().size(); i++) {
            absPosition.add(ORIGINAL_DATA[monthIndex].getPositions().get(i)
                    .absSub(state.getPositions().get(i)));
            absVelocity.add(ORIGINAL_DATA[monthIndex].getVelocities().get(i)
                    .absSub(state.getVelocities().get(i)));
            relPosition.add(absPosition.get(i)
                    .div(ORIGINAL_DATA[monthIndex].getPositions().get(i)));
            relVelocity.add(absVelocity.get(i)
                    .div(ORIGINAL_DATA[monthIndex].getVelocities().get(i)));
            averageErrorPosition = averageErrorPosition.add(absPosition.get(i));
            averageErrorVelocity = averageErrorVelocity.add(absVelocity.get(i));
            if (DEBUG) {
                System.out.println("PLANET " + simulation.getSystem().getCelestialBodies().get(i).toString() + "~~~~~~~~");
                //System.out.println("ORIGINAL PV : " + ORIGINAL_DATA[monthIndex].getPositions().get(i));
                //System.out.println("SIMULATED PV: " + state.getPositions().get(i));
                System.out.println("ABS ERROR POSIT : " + absPosition.get(i));
                System.out.println("ABS ERROR VEL   : " + absVelocity.get(i));
                //System.out.println("ORIGINAL VV : " + ORIGINAL_DATA[monthIndex].getVelocities().get(i));
                //System.out.println("SIMULATED VV:" + state.getVelocities().get(i));
                System.out.println("REL ERROR POS : " + relPosition.get(i));
                System.out.println("REL ERROR VEL : " + relVelocity.get(i));
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
        }
        averageErrorPosition = averageErrorPosition.div(state.getPositions().size());
        averageErrorVelocity = averageErrorVelocity.div(state.getVelocities().size());
        avgPosErrorMonth.add(averageErrorPosition);
        avgVelErrorMonth.add(averageErrorVelocity);

        if (DEBUG) {
            System.out.println("AVERAGE ERROR POSIT : " + averageErrorPosition);
            System.out.println("AVERAGE ERROR VEL   : " + averageErrorVelocity);
            System.out.println("********************        EOE     **************************");
        }


        if (userDialog != null) {
            userDialog.getErrorWindow().updateLabels(new ErrorData(absPosition, absVelocity));
            userDialog.enable(6);
        }

        if (fileWriter != null) {
            System.out.println("writing file...");
            try {
                fileWriter.get().write("********************************        SOE     **************************************\n");
                fileWriter.get().write("MONTH " + monthIndex + "\n");
                for (int i = 0; i < state.getPositions().size(); i++) {
                    fileWriter.get().write("PLANET " + simulation.getSystem().getCelestialBodies().get(i).toString() + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                    //fileWriter.get().write("ABS ERROR POSIT : " + absPosition.get(i) + "\n");
                    //fileWriter.get().write("ABS ERROR VEL   : " + absVelocity.get(i)+ "\n");
                    fileWriter.get().write("REL ERROR POS : " + relPosition.get(i) + "\n");
                    fileWriter.get().write("REL ERROR VEL : " + relVelocity.get(i) + "\n");
                }
                fileWriter.get().write("\n" + "AVERAGE ERROR POSIT : " + averageErrorPosition + "\n");
                fileWriter.get().write("AVERAGE ERROR VEL   : " + averageErrorVelocity + "\n");
                fileWriter.get().write("********************************        EOE     **************************************\n");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.println("month evaluation logged successfully");
            }
        }
    }

    /**
     * Gets average errors.
     *
     * @return the average errors
     */
    public ErrorData getAverageErrors() {
        return new ErrorData(avgPosErrorMonth, avgVelErrorMonth);
    }
}
