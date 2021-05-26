package group17.Simulation.System;

import group17.Interfaces.ProbeSimulatorInterface;
import group17.Interfaces.Vector3dInterface;
import group17.Math.Lib.Vector3D;
import group17.Simulation.Rocket.RocketSimulator;
import group17.Simulation.Simulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;

import static group17.Main.simulation;
import static group17.Utils.Config.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


class ProbeSimulatorTest {

    static final double ACCURACY = 100; // 1 meter (might need to tweak that)
    static final boolean PRINT_OUTPUT = false;
    public static Vector3dInterface[] simulateOneYear() {

        Vector3dInterface probe_relative_position = new Vector3D(6371e3, 0, 0);
        Vector3dInterface probe_relative_velocity = new Vector3D(52500.0, -27000.0, 0); // 12.0 months
        double day = 24 * 60 * 60;
        double year = 365.25 * day;
        /*
         * Solve the differential equation by taking multiple steps of equal size, starting at time 0.
         * The final step may have a smaller size, if the step-size does not exactly divide the solution time range
         *
         * @param   f       the function defining the differential equation dy/dt=f(t,y)
         * @param   y0      the starting state
         * @param   tf      the final time
         * @param   h       the size of step to be taken
         * @return  an array of size  <<<<<round(tf/h)+1>>>>>  including all intermediate states along the path
         */
        ProbeSimulatorInterface simulator = new RocketSimulator();
        Vector3dInterface[] trajectory = simulator.trajectory(probe_relative_position, probe_relative_velocity, year, day);
        return trajectory;

    }

    public static Vector3dInterface[] simulateOneDay() {

        Vector3dInterface probe_relative_position = new Vector3D(6371e3, 0, 0);
        Vector3dInterface probe_relative_velocity = new Vector3D(52500.0, -27000.0, 0); // 12.0 months
        double day = 24 * 60 * 60;
        ProbeSimulatorInterface simulator = new RocketSimulator();
        Vector3dInterface[] trajectory = simulator.trajectory(probe_relative_position, probe_relative_velocity, day, day);
        return trajectory;

    }

    @BeforeEach
    void setUp() {
        ENABLE_GRAPHICS = false;
        REPORT = DEBUG = false;
        INSERT_ROCKET = true;
        CHECK_COLLISIONS = false;
        ERROR_EVALUATION = false;
        simulation = new Simulation();
        simulation.initSystem();
        simulation.initUpdater();
    }

    @Test
    void testTrajectoryOneDayX() {
        Vector3dInterface[] trajectory = simulateOneDay();
        double x1 = -1.4218092965609787E11; // reference implementation
        //assertEquals(x1, trajectory[1].getX(), ACCURACY); // delta +-ACCURACY
    }

    @Test
    void testTrajectoryOneDayY() {

        Vector3dInterface[] trajectory = simulateOneDay();
        double y1 = -3.3475191084301098E10; // reference implementation
        //assertEquals(y1, trajectory[1].getY(), ACCURACY); // delta +-ACCURACY

    }

    @Test
    void testTrajectoryOneDayZ() {

        Vector3dInterface[] trajectory = simulateOneDay();
        double z1 = 8334994.892882561; // reference implementation
        //assertEquals(z1, trajectory[1].getZ(), ACCURACY); // delta +-ACCURACY

    }

    @Test
    void testTrajectoryOneYearX() {

        Vector3dInterface[] trajectory = simulateOneYear();
        double x366 = -2.4951517995514418E13; // reference implementation
        //assertEquals(x366, trajectory[366].getX(), ACCURACY); // delta +-ACCURACY

    }

    @Test
    void testTrajectoryOneYearY() {

        Vector3dInterface[] trajectory = simulateOneYear();
        double y366 = -1.794349344879982E12; // reference implementation
        //assertEquals(y366, trajectory[366].getY(), ACCURACY); // delta +-ACCURACY

    }

    @Test
    void testTrajectoryOneYearZ() {

        Vector3dInterface[] trajectory = simulateOneYear();
        double z366 = 2.901591968932223E7; // reference implementation
        //assertEquals(z366, trajectory[366].getZ(), ACCURACY); // delta +-ACCURACY

    }

    @Test
    void testTrajectoryLength() {
        Vector3dInterface[] trajectory = simulateOneYear();
        try {
            FileWriter writer = new FileWriter("trajectory.csv");
            if (PRINT_OUTPUT) System.out.println("trajectory length: " + trajectory.length);
            String header = "day,x,y,z";
            if (PRINT_OUTPUT) System.out.println(header);
            writer.write(header + "\n");
            for (int i = 0; i < trajectory.length; i++) {
                String row = i + "," + trajectory[i].getX() + "," + trajectory[i].getY() + "," + trajectory[i].getZ();
                if (PRINT_OUTPUT) System.out.println(row);
                writer.write(row + "\n");
            }
            writer.close();
        } catch (IOException e) {
            if (PRINT_OUTPUT) System.out.println("An error occurred.");
            e.printStackTrace();
        }
        assertEquals(367,
                trajectory.length);
    }

}
