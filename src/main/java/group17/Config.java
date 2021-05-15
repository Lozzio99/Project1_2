package group17;

import group17.System.Clock;

public class Config {


    public static final int ROCKET_SIMULATION = 4;
    public static final int PENDULUM_SIMULATION = 3;
    public static final int NUMERICAL_SIMULATION = 2;
    public static final int PARTICLES_SIMULATION = 1;
    public static final int SOLAR_SYSTEM_SIMULATION = 0;
    public static double FPS = 80;
    public static final int EULER_SOLVER = 1;
    public static final int RUNGE_KUTTA_SOLVER = 2;
    public static final int VERLET_VEL_SOLVER = 3;
    public static final int VERLET_STD_SOLVER = 4;
    public static final int MIN_CPU = 1;  //2 threads
    public static final int CPU_LEVEL2 = 2;
    public static final int CPU_LEVEL3 = 3;
    public static final int CPU_LEVEL4 = 4;
    public static final int MAX_CPU = 5;
    public static final double G = 6.67408e-11;
    public static Boolean LAUNCH_ASSIST = true;
    public static Boolean ENABLE_GRAPHICS = true;
    public static int SOLVER = EULER_SOLVER;
    public static int CPU_LEVEL = 1;

    public static Boolean REPORT = true;
    public static Boolean DEBUG = false;
    //which simulationInstance is going to run
    public static int SIMULATION_LEVEL = 0;
    public static Boolean INSERT_ROCKET = true;
    public static Boolean ROCKET_INFO = true;
    public static Boolean PERFORMANCE = true;
    public static Boolean ERROR_EVALUATION = true;
    public static Boolean CHECK_COLLISIONS = false;
    public static Boolean PLOT = false;
    public static double STEP_SIZE = 360;


    //TODO : set this to be updated for particles simulationInstance
    public static Boolean DRAW_TRAJECTORIES = true;
    //TODO : set this to be updated for particles simulationInstance
    public static Boolean NAMES = true;


    public static int TRAJECTORY_LENGTH = SIMULATION_LEVEL == SOLAR_SYSTEM_SIMULATION ? 500 : 50;
    public static int PARTICLES = 700;

    public static Clock LAUNCH_DATE = new Clock().setLaunchDay();


}
