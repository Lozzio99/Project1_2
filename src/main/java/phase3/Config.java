package phase3;

import phase3.Math.ADT.Vector3D;
import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Noise.Noise;

public class Config {
    public static final boolean DEBUG = false;
    public static final double STEP_SIZE = 0.001;
    public static double CURRENT_TIME = 0;

    public static final int EULER = 0;
    public static final int RK4 = 1;
    public static final int VERLET_STD = 2;
    public static final int VERLET_VEL = 3;
    public static final int MIDPOINT = 4;
    public static int SOLVER = VERLET_VEL;

    public static final int OPEN = 0;
    public static final int OPEN_OLD = 1;
    public static final int CLOSED = 2;
    public static int CONTROLLER = 0;

    public static boolean WIND = false;

    public static Noise.NoiseDim NOISE_DIMENSIONS = Noise.NoiseDim.TRI_DIMENSIONAL;

    public static void main(String[] args) {
        Vector3dInterface vRight = new Vector3D(100, 0, 0);
        Vector3dInterface vLeft = new Vector3D(-100, 0, 0);
        Vector3dInterface vDown = new Vector3D(0, -100, 0);
        Vector3dInterface vUp = new Vector3D(0, 100, 0);
        System.out.println(vRight.heading());

        System.out.println(vUp.heading());

        System.out.println(vLeft.heading());

        System.out.println(vDown.heading());

    }
}
