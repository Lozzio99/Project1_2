package group17.phase1.Titan.Physics.Math;

import group17.phase1.Titan.Graphics.GraphicsManager;

import java.awt.*;

public class Point2DConverter {
    private static final double zoomFactor = 1.05;
    private static final int WIDTH = GraphicsManager.screen.width, HEIGHT = GraphicsManager.screen.height;
    private static final Point ORIGIN = new Point(WIDTH / 2, HEIGHT / 2);
    private static double scale = 1.5d;

    public static void zoomIn() {
        scale *= zoomFactor;
    }

    public static void zoomOut() {
        scale /= zoomFactor;
    }

    public static double getScale() {
        return scale;
    }

    public static Point convertPoint(Point3D point3D) {
        return new Point((int) (ORIGIN.getX() + point3D.getXCoordinate()), (int) (ORIGIN.getY() - point3D.getYCoordinate()));
    }

    public static void translate(double xFactor, double yFactor) {
        ORIGIN.move((int) (ORIGIN.getX() + xFactor), (int) (ORIGIN.getY() - yFactor));
    }
}
