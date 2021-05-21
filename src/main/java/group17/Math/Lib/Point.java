package group17.Math.Lib;

import org.jetbrains.annotations.Contract;

import java.awt.*;


/**
 * The type Point.
 */
public class Point {
    /**
     * The constant scale.
     */
    public static double scale = 1.05;
    private static Dimension screen;
    private static Point ORIGIN;
    /**
     * The X.
     */
    public double x, /**
     * The Y.
     */
    y;
    /**
     * The X off.
     */
    public double xOff, /**
     * The Y off.
     */
    yOff;


    /**
     * Instantiates a new Point.
     *
     * @param x the x
     * @param y the y
     */
    public Point(double x, double y) {
        this.x = ORIGIN.getX() + (x * scale);
        this.y = ORIGIN.getY() - (y * scale);
    }

    /**
     * Instantiates a new Point.
     */
    public Point() {
        this.x = screen.width / 2. + xOff;
        this.y = screen.height / 2. - yOff;
    }

    /**
     * Sets screen.
     *
     * @param s the s
     */
    public static void setScreen(Dimension s) {
        screen = s;
    }

    /**
     * Sets origin.
     *
     * @param point the point
     */
    public static void setOrigin(Point point) {
        ORIGIN = point;
    }

    /**
     * Gets origin.
     *
     * @return the origin
     */
    @Contract(pure = true)
    public static Point getORIGIN() {
        return ORIGIN;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public double getX() {
        double x = (this.x + (xOff));
        return ((x > screen.width ? screen.width : x) < 0 ? 0 : x);
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public double getY() {
        double y = (this.y + (yOff));
        return ((y > screen.width ? screen.width : y) < 0 ? 0 : y);
    }

    @Override
    public String toString() {
        return "{" +
                "drawX=" + x +
                ",drawY=" + y +
                '}';
    }
}
