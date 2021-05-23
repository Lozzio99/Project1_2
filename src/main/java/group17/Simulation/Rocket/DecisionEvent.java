package group17.Simulation.Rocket;

import group17.Interfaces.Vector3dInterface;
import group17.Math.Lib.Vector3D;
import org.jetbrains.annotations.Contract;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.round;

/**
 * The type Decision event.
 */
public class DecisionEvent {


    public DecisionEvent(Vector3dInterface rocketP, Vector3dInterface titanPosition) {
        new ApproxV3D(rocketP);
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Vector3dInterface v1, av1;
        System.out.println((v1 = new Vector3D(9e-10, 9e-10, 9e-10)).hashCode());
        System.out.println((av1 = new ApproxV3D(9e-10, 9e-10, 9e-10)).hashCode());

        System.out.println(v1);
        System.out.println(av1);
        System.out.println(v1.equals(av1));
        System.out.println(av1.equals(new Vector3D(0, 0, 0)));   // this is the cool thing here
    }

    /**
     * The type Approx v 3 d.
     */
    public static class ApproxV3D extends Vector3D {
        /**
         * The constant epsilon.
         */
        public static double epsilon;

        static {
            ApproxV3D.epsilon = 1e-8;  //override var
        }

        /**
         * Instantiates a new Approx v 3 d.
         *
         * @param x the x
         * @param y the y
         * @param z the z
         */
        public ApproxV3D(double x, double y, double z) {
            super(x, y, z);
        }

        public ApproxV3D(Vector3dInterface v) {
            super(v.getX(), v.getY(), v.getZ());
        }

        @Override
        public int hashCode() {
            int hash = 11;
            hash = 31 * hash + Long.hashCode(round(this.x / ApproxV3D.epsilon));
            hash = 31 * hash + Long.hashCode(round(this.y / ApproxV3D.epsilon));
            hash = 31 * hash + Long.hashCode(round(this.z / ApproxV3D.epsilon));
            return hash;
        }

        @Contract(value = "null -> false", pure = true)
        @Override
        public boolean equals(Object o) {
            if (o instanceof Vector3dInterface) {
                Vector3dInterface v = (Vector3dInterface) o;
                return (abs(v.getX() - x) <= ApproxV3D.epsilon) &&
                        (abs(v.getY() - y) <= ApproxV3D.epsilon) &&
                        (abs(v.getZ() - z) <= ApproxV3D.epsilon);
            }
            return false;
        }
    }
}
