package group17.phase1.Titan.Physics.Trajectories;

import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.Physics.Math.Vector3D;
import group17.phase1.Titan.System.SystemState;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class calculates the error between the running simulation and the actual positions of the planets in our solar system.
 * It reads the data from a given file and creates an output file, which can be reviewed later.
 */

public class TrajectoryErrorCalc {

    // Variables
    File inputData;
    Vector3dInterface[][][] originalData = new Vector3D[10][13][2];
    private ArrayList<StateInterface> months;
    // 10 bodies in the following order: 0: sun, 1: mercury, 2: venus, 3: earth, 4: mars, 5: jupiter, 6: saturn, 7: titan, 8: uranus, 9: neptune
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

        //System.out.println(originalData[0][0][0].getX());
    }

    // Only for testing purposes
    public static void main(String[] args) throws FileNotFoundException {
        TrajectoryErrorCalc test = new TrajectoryErrorCalc();
        test.printOriginal();
        test.printReverse(test.revert());
    }

    public List<StateInterface> revert() {
        //don't wanna change the way it's imported
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

    public void printOriginal() {
        for (int i = 0; i < originalData.length; i++) {
            System.out.println(" --- Planet " + i + " --- ");
            for (int j = 0; j < originalData[i].length; j++) {
                System.out.println("Month " + j);
                for (int k = 0; k < originalData[i][j].length; k++) {
                    System.out.print(originalData[i][j][k].toString() + "\t");
                }
                System.out.println();
            }
        }
    }

    public ArrayList<StateInterface> getMonths() {
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