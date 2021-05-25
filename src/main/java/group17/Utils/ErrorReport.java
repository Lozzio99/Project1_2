package group17.Utils;

import group17.Interfaces.Vector3dInterface;
import group17.Math.Lib.Vector3D;
import org.jetbrains.annotations.Contract;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static group17.Main.simulation;
import static group17.Main.userDialog;
import static group17.Utils.Config.*;

/**
 * Represents Observer class for the Errors
 *   of Simulation
 */
public class ErrorReport implements Runnable {

    public final static String[] solvers = new String[]{"", "E", "Rk+", "Vve", "Vst", "M", "Rk-", "RkL"};
    /**
     * The constant monthIndex.
     */
    private static final AtomicInteger monthIndex = new AtomicInteger(-1);
    public static boolean testingAccuracy = false;
    private final List<Vector3dInterface> avgPosErrorMonth;
    private final ErrorData state;
    private final List<Vector3dInterface> avgVelErrorMonth;
    private Writer fileWriter;
    private List<Vector3dInterface> absPosition, absVelocity;
    private List<Vector3dInterface> relPosition, relVelocity;

    /**
     * Instantiates a new Error report.
     *
     * @param state the state
     */
    @Contract(pure = true)
    private ErrorReport(final ErrorData state) {
        //make a copy of the references
        this.state = state;
        this.avgPosErrorMonth = new ArrayList<>(13);
        this.avgVelErrorMonth = new ArrayList<>(13);
    }

    /**
     * Instantiates a new Error report.
     *
     * @param fw        the fw
     * @param errorData the error data
     */
    @Contract(pure = true)
    public ErrorReport(final AtomicReference<? extends Writer> fw, final ErrorData errorData) {
        this(errorData);
        fileWriter = fw.get();
    }

    public static synchronized int monthIndex() {
        return monthIndex.get();
    }

    public static void setMonthIndex(int value) {
        while (true) {
            int exist = monthIndex();
            if (monthIndex.compareAndSet(exist, value)) return;
        }
    }

    public static void incrementMonth() {
        while (true) {
            int exist = monthIndex();
            int newValue = exist + 1;
            if (monthIndex.compareAndSet(exist, newValue)) return;
        }
    }

    public static String makeMonthDirectories(Class<?> klass) {
        return extractDir() + "\\MONTH" + monthIndex + ".";
    }

    private static String extractDir() {
        String path = "src\\main\\resources\\ErrorData";
        path += "\\" + solvers[DEFAULT_SOLVER] + "\\" + ((int) STEP_SIZE);
        File dir = new File(path);
        dir.mkdirs();
        return path;
    }

    /**
     * Start.
     */
    public void start() {
        if (monthIndex() < 13 && monthIndex() >= 0) new Thread(this, "Error Log").start();
    }

    @Override
    public void run() {
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
            absPosition.add(state.getPositions().get(i)
                    .absSub(ORIGINAL_DATA[monthIndex()].getPositions().get(i)));
            absVelocity.add(state.getVelocities().get(i)
                    .absSub(ORIGINAL_DATA[monthIndex()].getVelocities().get(i)));
            relPosition.add(absPosition.get(i)
                    .div(ORIGINAL_DATA[monthIndex()].getPositions().get(i)));
            relVelocity.add(absVelocity.get(i)
                    .div(ORIGINAL_DATA[monthIndex()].getVelocities().get(i)));
            Vector3dInterface subPos = state.getPositions().get(i).sub(ORIGINAL_DATA[monthIndex()].getPositions().get(i));
            Vector3dInterface subVel = state.getVelocities().get(i).sub(ORIGINAL_DATA[monthIndex()].getVelocities().get(i));


            averageErrorPosition = averageErrorPosition.add(subPos.multiply(subPos));
            averageErrorVelocity = averageErrorVelocity.add(subVel.multiply(subVel));

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
            if (fileWriter instanceof FileWriter) writeFileTxt();
            else if (fileWriter instanceof ErrorExportCSV) writeFileCsv();
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

    public void writeFileTxt() {
        try {
            if (DEBUG) System.out.println("Writing on txt file");
            fileWriter.write("********************************        SOE     **************************************\n");
            fileWriter.write("MONTH " + monthIndex + "\n");
            for (int i = 0; i < state.getPositions().size(); i++) {
                fileWriter.write("PLANET " + simulation.getSystem().getCelestialBodies().get(i).toString() + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                //fileWriter.get().write("ABS ERROR POSIT : " + absPosition.get(i) + "\n");
                //fileWriter.get().write("ABS ERROR VEL   : " + absVelocity.get(i)+ "\n");
                fileWriter.write("REL ERROR POS : " + relPosition.get(i) + "\n");
                fileWriter.write("REL ERROR VEL : " + relVelocity.get(i) + "\n");
            }
            //fileWriter.get().write("\n" + "AVERAGE ERROR POSIT : " + averageErrorPosition + "\n");
            //fileWriter.get().write("AVERAGE ERROR VEL   : " + averageErrorVelocity + "\n");
            fileWriter.write("********************************        EOE     **************************************\n");
            if (!testingAccuracy) fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFileCsv() {
        try {
            if (DEBUG) System.out.println("Writing on csv file");
            for (int i = 0; i < state.getPositions().size(); i++) {
                ((ErrorExportCSV) fileWriter).addErrorData(
                        solvers[DEFAULT_SOLVER],
                        monthIndex(),
                        simulation.getSystem().getCelestialBodies().get(i).toString(),
                        relPosition.get(i),
                        relVelocity.get(i)
                );
            }
            if (!testingAccuracy) fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
