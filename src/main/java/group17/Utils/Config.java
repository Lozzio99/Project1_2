package group17.Utils;

import group17.Simulation.System.Clock;

/**
 * The type Config.
 */
public class Config {

    //             FINAL CONFIGURATIONS

    /**
     * Global enum for the accuracy output,
     * NO_OUTPUT for suppressing file generation
     * or TXT, CSV
     *
     * @see Format
     * @see ErrorReport
     */
    public static final Format FORMAT = Format.NO_OUTPUT;


    /**
     * The constant ROCKET_SIMULATION.
     */
    public static final int ROCKET_SIMULATION = 0;
    /**
     * The constant PENDULUM_SIMULATION.
     */
    public static final int PENDULUM_SIMULATION = 1;

    /**
     * The constant PARTICLES_SIMULATION.
     */
    public static final int PARTICLES_SIMULATION = 2;
    /**
     * The constant EULER_SOLVER.
     */
    public static final int EULER_SOLVER = 1;
    /**
     * The constant RUNGE_KUTTA_SOLVER.
     */
    public static final int RUNGE_KUTTA_SOLVER = 2;
    /**
     * The constant VERLET_VEL_SOLVER.
     */
    public static final int VERLET_VEL_SOLVER = 3;
    /**
     * The constant VERLET_STD_SOLVER.
     */
    public static final int VERLET_STD_SOLVER = 4;
    /**
     * The constant MIDPOINT_SOLVER.
     */
    public static final int MIDPOINT_SOLVER = 5;
    /**
     * TESTING SOLVERS
     */
    public static final int GREEDY_RUNGE_KUTTA = 6;
    /**
     * The Lazy runge.
     */
    public static final int TESTS_RUNGE_KUTTA = 7;
    /**
     * The constant MIN_CPU.
     */
    public static final int MIN_CPU = 1;
    /**
     * The constant MAX_CPU.
     */
    public static final int MAX_CPU = 5;
    /**
     * The constant ORIGINAL_DATA.
     */
    public static final ErrorData[] ORIGINAL_DATA = new ErrorData[13];
    //default global array for imported original data values (loaded when launching the simulation)

    /**
     * The constant LAUNCH_DATE.
     */
    public static final Clock LAUNCH_DATE = new Clock();  //set to launch day in runtime


    /**
     * BOOLEAN CONFIGURATIONS
     * available in assist window
     */
    //                 EDITABLE  (in runtime too)
    public static Boolean LAUNCH_ASSIST = true;
    /**
     * The constant ENABLE_GRAPHICS.
     */
    public static Boolean ENABLE_GRAPHICS = true;
    /**
     * The constant REPORT.
     */
    public static Boolean REPORT = true;
    /**
     * The constant DEBUG.
     */
    public static Boolean DEBUG = false;
    /**
     * The constant INSERT_ROCKET.
     */
    public static Boolean INSERT_ROCKET = true;
    /**
     * The constant ROCKET_INFO.
     */
    public static Boolean ROCKET_INFO = true;
    /**
     * The constant PERFORMANCE.
     */
    public static Boolean PERFORMANCE = true;
    /**
     * The constant ERROR_EVALUATION.
     */
    public static Boolean ERROR_EVALUATION = true;
    /**
     * The constant CHECK_COLLISIONS.
     */
    public static Boolean CHECK_COLLISIONS = false;
    /**
     * The constant PLOT.
     */
    public static Boolean PLOT = false;
    /**
     * The constant DRAW_TRAJECTORIES.
     */
    public static Boolean DRAW_TRAJECTORIES = true;
    /**
     * The constant NAMES.
     */
    public static Boolean NAMES = true;
    /**
     * The constant DEFAULT_SOLVER.
     */
    public static int DEFAULT_SOLVER = GREEDY_RUNGE_KUTTA;  //editable in main menu


    /**
     * NUMERICAL CONFIGURATIONS
     */
    //                      EDITABLE (not in runtime)
    public static double FPS = 0;
    /**
     * The constant SIMULATION_LEVEL.
     */
    public static int SIMULATION_LEVEL = 0;//which simulation is going to run
    /**
     * The constant STEP_SIZE.
     */
    public static double STEP_SIZE = 7200;
    /**
     * The constant CURRENT_TIME.
     */
    public static double CURRENT_TIME = 0;
    /**
     * The constant TRAJECTORY_LENGTH.
     */
    public static int TRAJECTORY_LENGTH = 2000;

    /**
     * The constant CPU_LEVEL.
     */
    public static int CPU_LEVEL = 1;
    /**
     * The constant G.
     */
    public static double G = 6.67408e-11;


}
