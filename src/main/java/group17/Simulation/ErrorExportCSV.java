package group17.Simulation;

import group17.Interfaces.Vector3dInterface;
import static group17.Config.DEBUG;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * This class takes care of exporting the calculated errors to a .csv file.
 * The folder must be present in the project.
 */
public class ErrorExportCSV {

    // Singleton instance
    private static ErrorExportCSV singleton;

    // Variables
    File file;
    String filePath = "out/errorData/errorLog.csv";
    PrintWriter pw;
    StringBuilder sb;

    /**
     * Accessor for the singleton instance.
     * @return
     */
    public static ErrorExportCSV getErrorExportCSV() {
        if (singleton == null)
            singleton = new ErrorExportCSV();
        return singleton;
    }

    /**
     * Private constructor of a singleton.
     */
    private ErrorExportCSV() {
        // Remove old file
        file = new File(filePath);
        file.delete();
        if (DEBUG)
            System.out.println("Old .csv file deleted!");

        // Create instances to write into file
        try {
            pw = new PrintWriter(filePath);
            sb = new StringBuilder();

            pw.write("MONTH, PLANET, RELATIVE_POSITION ERROR, RELATIVE_VELOCITY ERROR, RELATIVE_POSITION_ERROR_NORM, RELATIVE_VELOCITY_ERROR_NORM");
            pw.write("\n");

            pw.write(sb.toString());
            pw.flush();
        }
        catch (Exception e) {
        }
    }

    /**
     * Add a set of error data to the CSV file.
     * @param month
     * @param plantet
     * @param relErrorPos
     * @param relErrorVel
     */
    public void addErrorData(int month, String plantet, Vector3dInterface relErrorPos, Vector3dInterface relErrorVel) {
        if (DEBUG)
            System.out.println("Log data to CSV: " + month + " " + plantet);
        pw.write(month + ", " + plantet + ", " + relErrorPos + ", " + relErrorVel + ", " + relErrorPos.norm() + ", " + relErrorVel.norm());
        pw.write("\n");
        pw.flush();
        if (month == 12)
            closeFile();
    }

    /**
     * Closes the file.
     */
    public void closeFile() {
        pw.close();
        if (DEBUG)
            System.out.println("Close file");
    }
}