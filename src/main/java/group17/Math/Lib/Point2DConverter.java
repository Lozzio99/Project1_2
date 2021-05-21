package group17.Math.Lib;

import group17.Graphics.GraphicsManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.Point;

import static group17.Main.simulation;

/**
 * The type Point 2 d converter.
 */
public class Point2DConverter {
    private static final double zoomFactor = 1.05;
    private static final int WIDTH = GraphicsManager.screen.width, HEIGHT = GraphicsManager.screen.height;
    private static java.awt.Point ORIGIN = new java.awt.Point(WIDTH / 2, HEIGHT / 2);
    private static double scale = 1.5d;
    private static double xMoved, yMoved;

    /**
     * Zoom in.
     */
    public static void zoomIn() {
        scale *= zoomFactor;
    }

    /**
     * Zoom out.
     */
    public static void zoomOut() {
        scale /= zoomFactor;
    }

    /**
     * Gets scale.
     *
     * @return the scale
     */
    @Contract(pure = true)
    public static double getScale() {
        return scale;
    }

    /**
     * Convert point point.
     *
     * @param point3D the point 3 d
     * @return the point
     */
    @Contract("_ -> new")
    public static @NotNull Point convertPoint(@NotNull Point3D point3D) {
        return new Point((int) (ORIGIN.getX() + point3D.getXCoordinate()), (int) (ORIGIN.getY() - point3D.getYCoordinate()));
    }

    /**
     * Translate.
     *
     * @param xFactor the x factor
     * @param yFactor the y factor
     */
    public static void translate(double xFactor, double yFactor) {
        xMoved = xFactor;
        yMoved = yFactor;
        ORIGIN.move((int) (ORIGIN.getX() + xFactor), (int) (ORIGIN.getY() - yFactor));
    }

    /**
     * Resize.
     */
    public static void resize() {
        ORIGIN = new java.awt.Point(simulation.getGraphics().getFrame().getWidth() / 2, simulation.getGraphics().getFrame().getHeight() / 2);
        translate(xMoved, yMoved);
    }
}
