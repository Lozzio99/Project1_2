package Module.Math.ADT;

import java.awt.geom.Point2D;

import static Module.Graphics.GraphicsManager.screen;

/**
 * The type Point 3 d converter.
 */
public class Vector2DConverter {
    private static final double ZoomFactor = 1.05;
    private static final Point2D.Double ORIGIN = new Point2D.Double(screen.width / 2., screen.height / 2.);
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
    public static Point2D.Double convertVector(final Vector3dInterface point3D) {
        return new Point2D.Double(ORIGIN.x + (point3D.getX() * scale), ORIGIN.y - (point3D.getY() * scale));
    }


    public static void translate(final double dx, final double dy) {
        ORIGIN.x += dx;
        ORIGIN.y += dy;
    }


}
