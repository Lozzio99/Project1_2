package Module.Math;


/**
 * The type Point 3 d.
 */
public class Point3D {
    /**
     * The X.
     */
    public double x, /**
     * The Y.
     */
    y, /**
     * The Z.
     */
    z;
    /**
     * The X offset.
     */
    public double xOffset, /**
     * The Y offset.
     */
    yOffset, /**
     * The Z offset.
     */
    zOffset;

    /**
     * Instantiates a new Point 3 d.
     *
     * @param x the x
     * @param y the y
     * @param z the z
     */
    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.xOffset = 0;
        this.yOffset = 0;
        this.zOffset = 0;
    }

    /**
     * Euclid dist double.
     *
     * @param p1 the p 1
     * @param p2 the p 2
     * @return the double
     */
    public static double euclidDist(Point3D p1, Point3D p2) {
        double x2 = Math.pow(p1.x - p2.x, 2);
        double y2 = Math.pow(p1.y - p2.y, 2);
        double z2 = Math.pow(p1.z - p2.z, 2);
        return Math.sqrt(x2 + y2 + z2);
    }

    /**
     * Gets x coordinate.
     *
     * @return the x coordinate
     */
    public double getXCoordinate() {
        return this.x + this.xOffset;
    }

    /**
     * Gets y coordinate.
     *
     * @return the y coordinate
     */
    public double getYCoordinate() {
        return this.y + this.yOffset;
    }

    /**
     * Gets z coordinate.
     *
     * @return the z coordinate
     */
    public double getZCoordinate() {
        return this.z + this.zOffset;
    }

    /**
     * Euclid dist double.
     *
     * @param p2 the p 2
     * @return the double
     */
    public double euclidDist(Point3D p2) {
        double x2 = Math.pow(this.x - p2.x, 2);
        double y2 = Math.pow(this.y - p2.y, 2);
        double z2 = Math.pow(this.z - p2.z, 2);
        return Math.sqrt(x2 + y2 + z2);
    }

    /**
     * Scale.
     *
     * @param scale the scale
     */
    public void scale(double scale) {
        this.x /= scale;
        this.y /= scale;
        this.z /= scale;
    }

    @Override
    public String toString() {
        return "( " + this.getXCoordinate() + " , " + this.getYCoordinate() + " , " + this.getZCoordinate() + " )";
    }

    /**
     * Translate point 3 d.
     *
     * @param x the x
     * @param y the y
     * @param z the z
     * @return the point 3 d
     */
    public Point3D translate(double x, double y, double z) {
        this.xOffset += x;
        this.yOffset += y;
        this.zOffset += z;
        return this;
    }

    /**
     * Heading double.
     *
     * @return the double
     */
    public double heading() {
        return Math.atan2(this.getYCoordinate(), this.getZCoordinate());
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof Point3D) {
            Point3D p = (Point3D) o;
            return this.x == p.x && this.y == p.y && this.z == p.z &&
                    this.xOffset == p.xOffset && this.yOffset == p.yOffset && this.zOffset == p.zOffset;
        }
        return false;
    }
}
