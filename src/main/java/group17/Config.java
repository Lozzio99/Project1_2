package group17;

import group17.System.Clock;
import group17.System.ErrorData;

public class Config {
    /**
     * FINAL CONFIGURATIONS
     **/
    //NOT EDITABLE
    public static final int ROCKET_SIMULATION = 4;
    public static final int PENDULUM_SIMULATION = 3;
    public static final int NUMERICAL_SIMULATION = 2;
    public static final int PARTICLES_SIMULATION = 1;
    public static final int SOLAR_SYSTEM_SIMULATION = 0;
    public static final int EULER_SOLVER = 1;
    public static final int RUNGE_KUTTA_SOLVER = 2;
    public static final int VERLET_VEL_SOLVER = 3;
    public static final int VERLET_STD_SOLVER = 4;
    public static final int MIDPOINT_SOLVER = 5;
    public static final int MIN_CPU = 1;  //2 threads
    public static final int MAX_CPU = 5;


    /**
     * BOOLEAN CONFIGURATIONS
     **/
    // EDITABLE
    public static Boolean LAUNCH_ASSIST = true;
    public static Boolean ENABLE_GRAPHICS = true;
    public static Boolean REPORT = true;
    public static Boolean DEBUG = false;
    public static Boolean INSERT_ROCKET = false;
    public static Boolean ROCKET_INFO = true;
    public static Boolean PERFORMANCE = true;
    public static Boolean ERROR_EVALUATION = true;
    public static Boolean CHECK_COLLISIONS = false;
    public static Boolean PLOT = false;
    public static Boolean DRAW_TRAJECTORIES = true;
    public static Boolean NAMES = true;
    public static Clock LAUNCH_DATE;


    /**
     * NUMERICAL CONFIGURATIONS
     **/
    //EDITABLE
    public static double FPS = 0;
    public static int SIMULATION_LEVEL = 0;//which simulation is going to run
    public static double STEP_SIZE = 3600;
    public static double CURRENT_TIME = 0;
    public static int TRAJECTORY_LENGTH = SIMULATION_LEVEL == SOLAR_SYSTEM_SIMULATION ? 500 : 50;
    public static int PARTICLES = 700;
    public static int DEFAULT_SOLVER = EULER_SOLVER;
    public static int CPU_LEVEL = 1;

    public static double G = 6.67408e-11;

    public static final ErrorData[] ORIGINAL_DATA = new ErrorData[13];


}
