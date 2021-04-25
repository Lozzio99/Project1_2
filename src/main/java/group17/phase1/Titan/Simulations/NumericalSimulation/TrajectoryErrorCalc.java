package group17.phase1.Titan.Simulations.NumericalSimulation;

import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.Physics.Math.Vector3D;
import group17.phase1.Titan.System.SystemState;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static group17.phase1.Titan.Main.simulation;

/**
 * This class calculates the error between the running simulation and the actual positions of the planets in our solar system.
 * It reads the data from a given file and creates an output file, which can be reviewed later.
 */
public class TrajectoryErrorCalc {

    // Variables
    File inputData;

    private final String ORIGINAL_DATA_PATH = "trajectoryData/originalData.txt";
    private final String SIMULATION_DATA_PATH = "trajectoryData/simulationData.txt";
    private final String ERROR_DATA_PATH = "trajectoryData/errorData.txt";
    private List<StateInterface> months;
    Vector3dInterface[][][] originalData;
    // 10 bodies in the following order: 0: sun, 1: mercury, 2: venus, 3: earth, 4: mars, 5: jupiter, 6: saturn, 7: titan, 8: uranus, 9: neptune
    // Stores the exact values of the celestial bodies in 3 dimensions:
    // 1. planet index
    // 2. month (starting from 04.2020, ending at 04.2021)
    // 3. position vector at index 0 and velocity vector at index 1
    Vector3dInterface[][][] simulationData;
    double[][][] errorData;

    /**
     * Constructor for a new instance of a trajectory error calculation
     */
    public TrajectoryErrorCalc() {
        //TODO : insert MOON
        this.originalData = new Vector3dInterface[simulation.system().getCelestialBodies().size() - 1][13][2];
        this.simulationData = new Vector3dInterface[simulation.system().getCelestialBodies().size() - 1][13][2];
        this.errorData = new double[simulation.system().getCelestialBodies().size() - 1][13][2];
        inputData = new File("src/main/resources/SS_coords.txt");
        Scanner in = null;
        try {
            in = new Scanner(inputData);
            double posX, posY, posZ;
            double velX, velY, velZ;
            // Fill originalData with values from file:
            for (int i = 0; i < 13; i++) { // for each month
                in.nextLine(); // skip month name
                //TODO : insert MOON
                for (int j = 0; j < simulation.system().getCelestialBodies().size() - 1; j++) { // for each body
                    posX = in.nextDouble();
                    posY = in.nextDouble();
                    posZ = in.nextDouble();
                    velX = in.nextDouble();
                    velY = in.nextDouble();
                    velZ = in.nextDouble();

                    originalData[j][i][0] = new Vector3D(posX, posY, posZ); // position vector
                    originalData[j][i][1] = new Vector3D(velX, velY, velZ); // velocity vector

                    if (in.hasNextLine()) {
                        in.nextLine();
                    }
                }
            }

        } catch (FileNotFoundException | NullPointerException e) {
            System.err.println(e.getMessage());
        }
    }


    public List<StateInterface> revert() {
        // Don't wanna change the way it's imported
        this.months = new ArrayList<>();
        for (int i = 0; i < originalData[0].length; i++) //for each month
        {
            StateInterface allPositionsOverOneMonth = new SystemState(); //all planets for each month
            for (int k = 0; k < originalData.length; k++) //for each planet
            {
                allPositionsOverOneMonth.getPositions().add(originalData[k][i][0]);
            }
            months.add(allPositionsOverOneMonth);
        }
        return months;
    }

    /**
     * Fills the simulation-array at a certain index with either a position or a velocity vector
     * @param planetIndex
     * @param month
     * @param type          0 for position, 1 for velocity
     */
    public void fillSimulation(int planetIndex, int month, int type, Vector3dInterface vector) {
        this.simulationData[planetIndex][month][type] = vector;
    }

    /**
     * Creates 3 files in /out/production/trajectoryData and outputs the positions and velocities
     * over one year at the beginning of each month.
     * The error data is the euclidean distance between the simulation- and the original data.
     */
    public void exportFile() {
        // Iterate through both arrays to check, that no entry is 0.
        if (arrayIsFilled(originalData) && arrayIsFilled(simulationData)) {
            computeErrors();
            printErrors();


            ClassLoader c = TrajectoryErrorCalc.class.getClassLoader();

            // Write data in files
            try {
                FileWriter fwOriginal = new FileWriter(Objects.requireNonNull(c.getResource(ORIGINAL_DATA_PATH)).getPath(), false);
                BufferedWriter bwOriginal = new BufferedWriter(fwOriginal);
                PrintWriter pwOriginal = new PrintWriter(bwOriginal);

                FileWriter fwSimulation = new FileWriter(Objects.requireNonNull(c.getResource(SIMULATION_DATA_PATH)).getPath(), false);
                BufferedWriter bwSimulation = new BufferedWriter(fwSimulation);
                PrintWriter pwSimulation = new PrintWriter(bwSimulation);

                FileWriter fwError = new FileWriter(Objects.requireNonNull(c.getResource(ERROR_DATA_PATH)).getPath(), false);
                BufferedWriter bwError = new BufferedWriter(fwError);
                PrintWriter pwError = new PrintWriter(bwError);

                for (int i = 0; i < originalData.length; i++) {
                    pwOriginal.println(" --- Body " + i + " --- ");
                    pwSimulation.println(" --- Body " + i + " --- ");
                    pwError.println(" --- Body " + i + " --- ");
                    for (int j = 0; j < originalData[i].length; j++) {
                        pwOriginal.println("Month " + j);
                        pwSimulation.println("Month " + j);
                        pwError.println("Month " + j);
                        for (int k = 0; k < originalData[i][j].length; k++) {
                            pwOriginal.print(originalData[i][j][k].toString() + "\t");
                            pwSimulation.print(simulationData[i][j][k].toString() + "\t");
                            pwError.print(errorData[i][j][k] + "\t");
                        }
                        pwOriginal.println();
                        pwSimulation.println();
                        pwError.println();
                    }
                }
                pwOriginal.flush();
                pwOriginal.close();

                pwSimulation.flush();
                pwSimulation.close();

                pwError.flush();
                pwError.close();
            } catch (NullPointerException | IOException e) {
                System.out.println("ERROR! Failed to write output file");
            }
        } else {
            System.err.println("Missing values in arrays data");
        }
    }

    /**
     * Prints the values of a 3D-Matrix.
     */
    private void printData(Vector3dInterface[][][] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(" --- Body " + i + " --- ");
            for (int j = 0; j < array[i].length; j++) {
                System.out.println("Month " + j);
                for (int k = 0; k < array[i][j].length; k++) {
                    System.out.print(array[i][j][k].toString() + "\t");
                }
                System.out.println();
            }
        }
    }

    /**
     * Prints the simulation data.
     */
    public void printSimulationData() {
        this.printData(simulationData);
    }

    /**
     * Prints the origianl data.
     */
    public void printOriginalData() {
        this.printData(originalData);
    }

    public void printErrors() {

        System.out.println("ERROR BETWEEN SIMULATION- AND ORIGINAL DATA: \n ");
        for (int i = 0; i < errorData.length; i++) {
            System.out.println(" --- Body " + i + " --- ");
            for (int j = 0; j < errorData[i].length; j++) {
                System.out.println("Month " + j);
                for (int k = 0; k < errorData[i][j].length; k++) {
                    System.out.print(errorData[i][j][k] + "\t");
                }
                System.out.println();
            }
        }
    }

    /**
     * Calculates the error between the simulation- and the original data.
     * The results are stored in the array errorData[][][].
     */
    private void computeErrors() {
        for (int i = 0; i < errorData.length; i++) {
            for (int j = 0; j < errorData[i].length; j++) {
                for (int k = 0; k < errorData[i][j].length; k++) {
                    errorData[i][j][k] = (Vector3D.dist(originalData[i][j][k].mul(1E3),simulationData[i][j][k])); // Compare simulation and original data and save the error
                }
            }
        }
    }


    /**
     * Checks whether an array is completely filled or not.
     * @param array
     * @return
     */
    private boolean arrayIsFilled(Vector3dInterface[][][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                for (int k = 0; k < array[i][j].length; k++) {
                    if (array[i][j][k] == null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public List<StateInterface> getMonths() {
        return this.months;
    }

    private void printReverse(List<StateInterface> months) {
        int i = 0;
        for (StateInterface onemonth : months) {
            System.out.println("Month " + (i++));
            for (Vector3dInterface v : onemonth.getPositions())
                System.out.println(v.toString());
        }
    }
}
