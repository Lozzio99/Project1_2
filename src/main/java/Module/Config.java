package Module;

import Module.Math.Vector3D;
import Module.Math.Vector3dInterface;

public class Config {
    public static final boolean DEBUG = true;
    public static final double STEP_SIZE = 1440;


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
