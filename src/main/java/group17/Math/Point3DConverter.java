package group17.Math;

import group17.Graphics.GraphicsManager;
import org.jetbrains.annotations.Contract;

import java.awt.*;

public class Point3DConverter {
    private static final double ZoomFactor = 1.05;
    private static final int SCREEN_WIDTH = GraphicsManager.screen.width, SCREEN_HEIGHT = GraphicsManager.screen.height;
    private static double scale = 1.5d;

    /**
     * Zooms in by a factor.
     */
    public static void zoomIn() {
        scale *= ZoomFactor;
    }

    /**
     * Zooms out by a factor.
     */
    public static void zoomOut() {
        scale /= ZoomFactor;
    }

    /**
     * Returns the scale.
     *
     * @return
     */
    @Contract(pure = true)
    public static double getScale() {
        return scale;
    }

    /**
     * Converts a point with respect to the scale.
     *
     * @param point3D
     * @return
     */
    public static Point convertPoint(Point3D point3D) {
        double x3d = point3D.getXCoordinate() * scale;
        double y3d = point3D.getYCoordinate() * scale;
        double depth = point3D.getZCoordinate() * scale;
        double[] newVal = scale(x3d, y3d, depth);
        int x2d = (int) (SCREEN_WIDTH / 2 + newVal[0]);
        int y2d = (int) (SCREEN_HEIGHT / 2 - newVal[1]);
        Point point2D = new Point(x2d, y2d);
        return point2D;
    }

    /**
     * Returns an array of double variables which represent a scale.
     *
     * @param x3d
     * @param y3d
     * @param depth
     * @return
     */
    private static double[] scale(double x3d, double y3d, double depth) {
        double dist = Math.sqrt(x3d * x3d + y3d * y3d);
        double theta = Math.atan2(y3d, x3d);
        double depth2 = 15 - depth;
        double localScale = Math.abs(1400 / (depth2 + 1400));
        dist *= localScale;
        double[] newVal = new double[2];
        newVal[0] = dist * Math.cos(theta);
        newVal[1] = dist * Math.sin(theta);
        return newVal;
    }

    /**
     * Rotates a point around the X axis.
     *
     * @param p
     * @param CW
     * @param degrees
     */
    public static void rotateAxisX(Point3D p, boolean CW, double degrees) {
        if (degrees == 0) return;
        double radius = Math.sqrt(p.y * p.y + p.z * p.z);
        double theta = Math.atan2(p.z, p.y);
        theta += 2 * Math.PI / 360 * degrees * (CW ? -1 : 1);
        p.y = (radius * Math.cos(theta));
        p.z = (radius * Math.sin(theta));
    }

    /**
     * Rotates a point around the Y axis.
     *
     * @param p
     * @param CW
     * @param degrees
     */
    public static void rotateAxisY(Point3D p, boolean CW, double degrees) {
        if (degrees == 0) return;
        double radius = Math.sqrt(p.z * p.z + p.x * p.x);
        double theta = Math.atan2(p.z, p.x);
        theta += 2 * Math.PI / 360 * degrees * (CW ? -1 : 1);
        p.z = (radius * Math.sin(theta));
        p.x = (radius * Math.cos(theta));
    }

    /**
     * Rotates a point around the Z axis.
     *
     * @param p
     * @param CW
     * @param degrees
     */
    public static void rotateAxisZ(Point3D p, boolean CW, double degrees) {
        if (degrees == 0) return;
        double radius = Math.sqrt(p.y * p.y + p.x * p.x);
        double theta = Math.atan2(p.y, p.x);
        theta += 2 * Math.PI / 360 * degrees * (CW ? -1 : 1);
        p.y = (radius * Math.sin(theta));
        p.x = (radius * Math.cos(theta));
    }

}
