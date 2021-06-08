package Module.Math;

import Module.Graphics.GraphicsManager;

/**
 * The type Point 3 d converter.
 */
public class Vector3DConverter {
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
     * @return scale scale
     */
    public static double getScale() {
        return scale;
    }

    /**
     * Converts a point with respect to the scale.
     *
     * @param point3D the point 3 d
     * @return java . awt . point
     */
    public static java.awt.Point convertVector(Vector3dInterface point3D) {
        double x3d = point3D.get(0) * scale;
        double y3d = point3D.get(1) * scale;
        double depth = point3D.get(2) * scale;
        double[] newVal = scale(x3d, y3d, depth);
        int x2d = (int) (SCREEN_WIDTH / 2 + newVal[0]);
        int y2d = (int) (SCREEN_HEIGHT / 2 - newVal[1]);
        return new java.awt.Point(x2d, y2d);
    }

    /**
     * Returns an array of double variables which represent a scale.
     *
     * @param x3d   the x value
     * @param y3d   the y value
     * @param depth the depth of the scene
     * @return an array containing the x,y point coordinates
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
     * @param p       the p
     * @param CW      the cw
     * @param degrees the degrees
     */
    public static void rotateAxisX(final Vector3dInterface p, boolean CW, double degrees) {
        if (degrees == 0) return;
        double radius = Math.sqrt(p.get(1) * p.get(1) + p.get(2) * p.get(2));
        double theta = Math.atan2(p.get(2), p.get(1));
        theta += 2 * Math.PI / 360 * degrees * (CW ? -1 : 1);
        p.set(radius * Math.cos(theta), 1);
        p.set(radius * Math.sin(theta), 2);
    }

    /**
     * Rotates a point around the Y axis.
     *
     * @param p       the p
     * @param CW      the cw
     * @param degrees the degrees
     */
    public static void rotateAxisY(Vector3dInterface p, boolean CW, double degrees) {
        if (degrees == 0) return;
        double radius = Math.sqrt(p.get(2) * p.get(2) + p.get(0) * p.get(0));
        double theta = Math.atan2(p.get(0), p.get(2));
        theta += 2 * Math.PI / 360 * degrees * (CW ? -1 : 1);
        p.getVal()[2] = (radius * Math.cos(theta));
        p.getVal()[0] = (radius * Math.sin(theta));
    }

    /**
     * Rotates a point around the Z axis.
     *
     * @param p       the p
     * @param CW      the cw
     * @param degrees the degrees
     */
    public static void rotateAxisZ(Vector3dInterface p, boolean CW, double degrees) {
        if (degrees == 0) return;
        double radius = Math.sqrt(p.get(1) * p.get(1) + p.get(0) * p.get(0));
        double theta = Math.atan2(p.get(1), p.get(0));
        theta += 2 * Math.PI / 360 * degrees * (CW ? -1 : 1);
        p.getVal()[1] = (radius * Math.cos(theta));
        p.getVal()[0] = (radius * Math.sin(theta));
    }

}
