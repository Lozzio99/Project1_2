package phase3;

import phase3.Math.Noise.Noise;
import phase3.Simulation.Errors.ErrorData;
import phase3.System.Clock;

public class Config {
    /**
     * SIMULATION TYPE
     */
    public static final int FLIGHT_TO_TITAN = 0;
    public static final int LANDING_ON_TITAN = 1;
    public static final int FULL_SIMULATION = 2;
    /**
     * TIME SETTINGS
     */
    public static final double MODULE_STEP_SIZE = 0.01;
    public static final double SS_STEP_SIZE = Clock.HOUR;
    public static double NEWTON_STEP_SIZE = 360.0;
    public static final Clock SIMULATION_CLOCK = new Clock();
    public static double CURRENT_TIME = 0;
    /**
     * SOLVERS CONFIGURATIONS
     */
    public static final int EULER = 0;
    /**
     * MODULE CONTROLLERS CONFIGURATIONS
     */
    public static final int RK4 = 1;
    public static final int VERLET_STD = 2;
    public static final int VERLET_VEL = 3;
    public static final int MIDPOINT = 4;

    public static final boolean DEBUG = false;
    public static final ErrorData.ErrorPrint OUT_ERROR = ErrorData.ErrorPrint.CONSOLE;
    public static final int OPEN = 0;
    public static final int CLOSED = 2;
    public static int CONTROLLER = 0;
    public static final ErrorData[] ORIGINAL_DATA = new ErrorData[13];
    /**
     * GRAPHICS SETTINGS
     */
    public static final boolean GRAPHICS = false;
    public static final boolean NAMES = true;
    public static final boolean DRAW_TRAJECTORIES = true;
    public static final int TRAJECTORY_LENGTH = 100;
    public static Noise.NoiseDim NOISE_DIMENSIONS = Noise.NoiseDim.TRI_DIMENSIONAL;
    public static int SIMULATION = LANDING_ON_TITAN;
    public static int SOLVER = VERLET_VEL;
    /**
     * SIMULATION SETTINGS
     */
    public static boolean WIND = false;
    public static boolean ERROR_EVALUATION = false;
    public static int ERROR_MONTH_INDEX = -1; // clock will check first and increment it to 0 -> first month evaluation


}
