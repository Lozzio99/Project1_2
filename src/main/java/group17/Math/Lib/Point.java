package group17.Math.Lib;

import org.jetbrains.annotations.Contract;

import java.awt.*;


public class Point {
    public static double scale = 1.05;
    private static Dimension screen;
    private static Point ORIGIN;
    public double x, y;
    public double xOff, yOff;


    public Point(double x, double y) {
        this.x = ORIGIN.getX() + (x * scale);
        this.y = ORIGIN.getY() - (y * scale);
    }

    public Point() {
        this.x = screen.width / 2. + xOff;
        this.y = screen.height / 2. - yOff;
    }

    public static void setScreen(Dimension s) {
        screen = s;
    }

    public static void setOrigin(Point point) {
        ORIGIN = point;
    }

    @Contract(pure = true)
    public static Point getORIGIN() {
        return ORIGIN;
    }

    public double getX() {
        double x = (this.x + (xOff));
        return ((x > screen.width ? screen.width : x) < 0 ? 0 : x);
    }

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
