package group17.phase1.Titan;

import group17.phase1.Titan.System.Clock;

public class Config {
    //which simulation is going to run
    public static final int SIMULATION_LEVEL = 0;

    public static final int SOLAR_SYSTEM_SIMULATION = 0;
    public static final int META_SIMULATION = 1;
    public static final int BETA_SIMULATION = 2;
    public static final int MIN_CPU = 1;  //2 threads
    public static final int CPU_LEVEL2 = 2;
    public static final int CPU_LEVEL3 = 3;
    public static final int CPU_LEVEL4 = 4;
    public static final boolean INSERT_PROBE = false;
    public static final int MAX_CPU = 5;
    public static final boolean REPORT = false;


    public static final boolean DEBUG = false;
    public static final boolean ENABLE_GRAPHICS = true;
    public static final boolean ENABLE_ASSIST = true;
    public static final double G = 6.67408e-11;
    public static final double FPS = 120;
    public static int CPU_LEVEL = 1;
    public static double STEP_SIZE = 8e3;
    public static Clock LAUNCH_DATE = new Clock().getLaunchDay();

}
