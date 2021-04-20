package group17.phase1.Titan;

import group17.phase1.Titan.System.Clock;

public class Config {
    public static final int PARTICLES_SIMULATION = 1;

    public static final int SOLAR_SYSTEM_SIMULATION = 0;
    public static final double FPS = 60;
    public static final int BETA_SIMULATION = 2;

    public static int CPU_LEVEL = 1;
    public static final int MIN_CPU = 1;  //2 threads
    public static final int CPU_LEVEL2 = 2;
    public static final int CPU_LEVEL3 = 3;
    public static final int CPU_LEVEL4 = 4;
    //which simulation is going to run
    public static int SIMULATION_LEVEL = 0;
    public static final int MAX_CPU = 5;


    public static final boolean REPORT = false;
    public static final boolean DEBUG = false;


    public static final boolean ENABLE_GRAPHICS = true;

    //TODO: wtf this will literally never work
    public static final boolean ENABLE_ASSIST = true;


    public static final double G = 6.67408e-11;
    public static boolean INSERT_PROBE = false;

    public static double STEP_SIZE = SIMULATION_LEVEL == SOLAR_SYSTEM_SIMULATION ? 8e3 : 8;


    //TODO : set this to be updated for particles simulation
    public static boolean TRAJECTORIES = true;
    //TODO : set this to be updated for particles simulation
    public static boolean NAMES = true;


    public static int TRAJECTORY_LENGTH = SIMULATION_LEVEL == SOLAR_SYSTEM_SIMULATION ? 1000 : 50;


    public static int PARTICLES = 2000;
    public static Clock LAUNCH_DATE = new Clock().getLaunchDay();

}
