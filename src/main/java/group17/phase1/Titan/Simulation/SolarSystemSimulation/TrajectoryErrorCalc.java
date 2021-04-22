package group17.phase1.Titan.Simulation.SolarSystemSimulation;

import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.Physics.Math.Vector3D;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class calculates the error between the running simulation and the actual positions of the planets in our solar system.
 * It reads the data from a given file and creates an output file, which can be reviewed later.
 */
public class TrajectoryErrorCalc {

    // Variables
    File inputData;

    Vector3dInterface[][][] originalData = new Vector3D[10][13][2];
                                // 10 bodies in the following order: 0: sun, 1: mercury, 2: venus, 3: earth, 4: mars, 5: jupiter, 6: saturn, 7: titan, 8: uranus, 9: neptune
                                // Stores the exact values of the celestial bodies in 3 dimensions:
                                // 1. planet index
                                // 2. month (starting from 04.2020, ending at 04.2021)
                                // 3. position vector at index 0 and velocity vector at index 1
    Vector3dInterface[][][] simulationData = new Vector3D[10][13][2];

    /**
     * Constructor. Initializes the original data and stores the values in the array originalData.
     * @throws FileNotFoundException
     */
    public TrajectoryErrorCalc() throws FileNotFoundException {
        inputData = new File("src/main/resources/SS_coords.txt");
        Scanner in = new Scanner(inputData);

        double posX, posY, posZ;
        double velX, velY, velZ;
        // fill originalData with values from file:
        for (int i = 0; i < 13; i++) { // for each month
            in.nextLine(); // skip month name
            for (int j = 0; j < 10; j++) { // for each body
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

        System.out.println(originalData[0][0][0].getX());
    }

    /**
     * Prints the original values.
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

    public void printSimulationData() {
        this.printData(simulationData);
    }

    public void printOriginalData() {
        this.printData(originalData);
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

    public void exportFile() {

    }

    // Only for testing purposes
    public static void main(String[] args) throws FileNotFoundException {
        TrajectoryErrorCalc test = new TrajectoryErrorCalc();
        test.printData(test.simulationData);
    }
}
