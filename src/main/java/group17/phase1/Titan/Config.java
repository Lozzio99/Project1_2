package group17.phase1.Titan;

import group17.phase1.Titan.System.Clock;

public class Config {

    public static final int PENDULUM_SIMULATION = 3;
    public static final int NUMERICAL_SIMULATION = 2;
    public static final int PARTICLES_SIMULATION = 1;
    public static final int SOLAR_SYSTEM_SIMULATION = 0;


    public static final boolean ENABLE_GRAPHICS = true;
    public static final double FPS = 80;
    public static final int BETA_SIMULATION = 2;


    public static final int EULER_SOLVER = 1;
    public static final int RUNGE_KUTTA_SOLVER = 2;
    public static final int VERLET_VEL_SOLVER = 3;
    public static final int VERLET_STD_SOLVER = 4;
    public static int SOLVER = 1;


    public static int CPU_LEVEL = 1;
    public static final int MIN_CPU = 1;  //2 threads
    public static final int CPU_LEVEL2 = 2;
    public static final int CPU_LEVEL3 = 3;
    public static final int CPU_LEVEL4 = 4;

    public static final int MAX_CPU = 5;


    public static final boolean REPORT = true;
    public static final boolean DEBUG = false;
    //which simulation is going to run
    public static int SIMULATION_LEVEL = 0;
    public static final boolean ENABLE_ASSIST = true;


    public static final double G = 6.67408e-11;
    public static boolean INSERT_PROBE = false;

    public static double STEP_SIZE = 8000;


    //TODO : set this to be updated for particles simulation
    public static boolean DRAW_TRAJECTORIES = true;
    //TODO : set this to be updated for particles simulation
    public static boolean NAMES = true;


    public static int TRAJECTORY_LENGTH = SIMULATION_LEVEL == SOLAR_SYSTEM_SIMULATION ? 1000 : 50;
    public static int PARTICLES = 700;

    public static Clock LAUNCH_DATE = new Clock().setLaunchDay();


}
