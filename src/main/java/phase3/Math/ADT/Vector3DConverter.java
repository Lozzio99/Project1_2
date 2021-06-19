package phase3.Math.ADT;

import static phase3.Graphics.GraphicsInterface.screen;

/**
 * The type Point 3 d converter.
 */
public class Vector3DConverter extends Vector2DConverter {
    private static final int SCREEN_WIDTH = screen.width, SCREEN_HEIGHT = screen.height;

    /**
     * Converts a point with respect to the scale.
     *
     * @param point3D the point 3 d
     * @return java . awt . point
     */
    public static java.awt.Point convertPoint(Vector3dInterface point3D) {
        double x3d = point3D.getX() * scale;
        double y3d = point3D.getY() * scale;
        double depth = point3D.getZ() * scale;
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
        double radius = Math.sqrt(p.getY() * p.getY() + p.getZ() * p.getZ());
        double theta = Math.atan2(p.getZ(), p.getY());
        theta += 2 * Math.PI / 360 * degrees * (CW ? -1 : 1);
        p.setY(radius * Math.cos(theta));
        p.setZ(radius * Math.sin(theta));
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
        double radius = Math.sqrt(p.getZ() * p.getZ() + p.getX() * p.getX());
        double theta = Math.atan2(p.getZ(), p.getX());
        theta += 2 * Math.PI / 360 * degrees * (CW ? -1 : 1);
        p.setZ(radius * Math.sin(theta));
        p.setX(radius * Math.cos(theta));
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
        double radius = Math.sqrt(p.getY() * p.getY() + p.getX() * p.getX());
        double theta = Math.atan2(p.getY(), p.getX());
        theta += 2 * Math.PI / 360 * degrees * (CW ? -1 : 1);
        p.setY(radius * Math.sin(theta));
        p.setX(radius * Math.cos(theta));
    }

}
