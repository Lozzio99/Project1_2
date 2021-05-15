package group17.Math;

import group17.Graphics.GraphicsManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static group17.Main.simulation;

public class Point2DConverter {
    private static final double zoomFactor = 1.05;
    private static final int WIDTH = GraphicsManager.screen.width, HEIGHT = GraphicsManager.screen.height;
    private static Point ORIGIN = new Point(WIDTH / 2, HEIGHT / 2);
    private static double scale = 1.5d;
    private static double xMoved, yMoved;

    public static void zoomIn() {
        scale *= zoomFactor;
    }

    public static void zoomOut() {
        scale /= zoomFactor;
    }

    @Contract(pure = true)
    public static double getScale() {
        return scale;
    }

    @Contract("_ -> new")
    public static @NotNull Point convertPoint(@NotNull Point3D point3D) {
        return new Point((int) (ORIGIN.getX() + point3D.getXCoordinate()), (int) (ORIGIN.getY() - point3D.getYCoordinate()));
    }

    public static void translate(double xFactor, double yFactor) {
        xMoved = xFactor;
        yMoved = yFactor;
        ORIGIN.move((int) (ORIGIN.getX() + xFactor), (int) (ORIGIN.getY() - yFactor));
    }

    public static void resize() {
        ORIGIN = new Point(simulation.getGraphics().getFrame().getWidth() / 2, simulation.getGraphics().getFrame().getHeight() / 2);
        translate(xMoved, yMoved);
    }
}
