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
    Vector3dInterface[][][] originalData = new Vector3D[11][13][2];
                                // 11 bodies in the following order: 0: sun, 1: mercury, 2: venus, 3: earth, 4: luna, 5: mars, 6: jupiter, 7: saturn, 8: titan, 9: uranus, 10: neptune
                                // Stores the exact values of the celestial bodies in 3 dimensions:
                                // 1. planet index
                                // 2. month (starting from 04.2020, ending at 04.2021)
                                // 3. position vector at index 0 and velocity vector at index 1

    public TrajectoryErrorCalc() throws FileNotFoundException {
        inputData = new File("src/main/resources/SS_coords.txt");
        Scanner in = new Scanner(inputData);

        double posX, posY, posZ;
        double velX, velY, velZ;
        // fill originalData with values from file:
        for (int i = 0; i < 13; i++) { // for each month
            in.nextLine(); // Skip string of month
            for (int j = 0; j < 11; j++) { // for each body
                posX = in.nextDouble();
                posY = in.nextDouble();
                posZ = in.nextDouble();
                velX = in.nextDouble();
                velY = in.nextDouble();
                velZ = in.nextDouble();

                originalData[j][i][0] = new Vector3D(); // position vector
                originalData[j][i][1] = new Vector3D(); // velocity vector
            }
        }

    }

    public void printOriginal() {
        for (int i = 0; i < originalData.length; i++) {
            for (int j = 0; j < originalData[i].length; j++) {
                for (int k = 0; k < originalData[i][j].length; k++) {
                    System.out.print(originalData[i][j][k].toString() + "\t");
                }
                System.out.println();
            }
        }
    }

    // Only for testing purposes
    public static void main(String[] args) throws FileNotFoundException {
        TrajectoryErrorCalc test = new TrajectoryErrorCalc();
    }
}
