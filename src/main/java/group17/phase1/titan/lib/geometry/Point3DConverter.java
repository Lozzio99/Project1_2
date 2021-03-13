package group17.phase1.titan.lib.geometry;

import java.awt.*;

public class Point3DConverter {

    private static double scale = 1d;
    private static final double ZoomFactor = 1.2;
    private static final int SCREEN_WIDTH = 800, SCREEN_HEIGHT = 800;

    public static void zoomIn() {
        scale *= ZoomFactor;
    }

    public static void zoomOut() {
        scale /= ZoomFactor;
    }

    public static Point convertPoint(Point3D point3D) {
        double x3d = point3D.getX() * scale;
        double y3d = point3D.getY() * scale;
        double depth = point3D.getZ() * scale;
        double[] newVal = scale(x3d, y3d, depth);
        int x2d = (int) (SCREEN_WIDTH / 2 + newVal[0]);
        int y2d = (int) (SCREEN_HEIGHT / 2 - newVal[1]);
        Point point2D = new Point(x2d, y2d);
        return point2D;
    }

    private static double[] scale(double x3d, double y3d, double depth) {
        double dist = Math.sqrt(x3d*x3d + y3d*y3d);
        double theta = Math.atan2(y3d, x3d);
        double depth2 = 15 - depth;
        double localScale = Math.abs(1400/(depth2+1400));
        dist *= localScale;
        double[] newVal = new double[2];
        newVal[0] = dist * Math.cos(theta);
        newVal[1] = dist * Math.sin(theta);
        return newVal;
    }

    public static void rotateAxisX(Point3D p, boolean CW, double degrees) {
        double radius = Math.sqrt(p.y*p.y + p.z*p.z);
        double theta = Math.atan2(p.z, p.y);
        theta += 2*Math.PI/360*degrees*(CW?-1:1);
        p.y = (float) (radius * Math.cos(theta));
        p.z = (float)(radius * Math.sin(theta));
    }

    public static void rotateAxisY(Point3D p, boolean CW, double degrees) {
        double radius = Math.sqrt(p.x*p.x + p.z*p.z);
        double theta = Math.atan2(p.x, p.z);
        theta += 2*Math.PI/360*degrees*(CW?-1:1);
        p.x = (float) (radius * Math.sin(theta));
        p.z = (float) (radius * Math.cos(theta));
    }

    public static void rotateAxisZ(Point3D p, boolean CW, double degrees) {
        double radius = Math.sqrt(p.y*p.y + p.x*p.x);
        double theta = Math.atan2(p.y, p.x);
        theta += 2*Math.PI/360*degrees*(CW?-1:1);
        p.y =(float) (radius * Math.sin(theta));
        p.x =(float) (radius * Math.cos(theta));
    }

}

