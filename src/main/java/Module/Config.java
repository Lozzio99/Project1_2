package Module;

import Module.Math.Vector3D;
import Module.Math.Vector3dInterface;

public class Config {
    public static final boolean DEBUG = true;
    public static final double STEP_SIZE = 0.001;
    public static double CURRENT_TIME = 0;

    public static final int EULER = 0;
    public static final int RK4 = 1;
    public static final int VERLET_STD = 2;
    public static final int VERLET_VEL = 3;
    public static final int MIDPOINT = 4;
    public static int SOLVER = 0;

    public static final int OPEN = 0;
    public static final int CLOSED = 1;
    public static int CONTROLLER = 1;

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
