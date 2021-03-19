package group17.phase1.Titan.Graphics.Geometry;

/**
 * This class represents a single 3D-point on the x,y,z axis.
 * @author 	Dan Parii, Lorenzo Pompigna, Nikola Prianikov, Axel Rozental, Konstantin Sandfort, Abhinandan Vasudevan
 * @version 1.0
 * @since	19/02/2021
 */
public class Point3D
{
    // Variables
    public final static Point3D origin = new Point3D(0, 0, 0);
    public double x, y, z;
    public double xOffset, yOffset, zOffset;

    /**
     * Constructor
     * @param x
     * @param y
     * @param z
     */
    public Point3D(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.xOffset = 0;
        this.yOffset = 0;
        this.zOffset = 0;
    }

    /**
     * Returns the X-coordinate.
     * @return
     */
    public double getXCoordinate()
    {
        return this.x+this.xOffset;
    }

    /**
     * Returns the Y-coordinate.
     * @return
     */
    public double getYCoordinate()
    {
        return this.y + this.yOffset;
    }

    /**
     * Returns the Z-coordinate.
     * @return
     */
    public double getZCoordinate()
    {
        return this.z + this.zOffset;
    }

    /**
     * Translates a point by a given offset.
     * @param x
     * @param y
     * @param z
     */
    public void translate(double x, double y, double z)
    {
        this.xOffset += x;
        this.yOffset += y;
        this.zOffset += z;
    }

    /**
     * Scales a point by a given factor.
     * @param scale
     */
    public void scale(double scale){
        this.x /= scale;
        this.y /= scale;
        this.z /= scale;
    }

    /**
     * Calculates the euclidean distance of two points.
     * @param p1
     * @param p2
     * @return
     */
    public static double euclidDist(Point3D p1, Point3D p2) {
        double x2 = Math.pow(p1.x - p2.x, 2);
        double y2 = Math.pow(p1.y - p2.y, 2);
        double z2 = Math.pow(p1.z - p2.z, 2);
        return Math.sqrt(x2 + y2 + z2);
    }

    /**
     * Calculates the euclidean distance of this instance and another point.
     * @param p2
     * @return
     */
    public double euclidDist(Point3D p2) {
        double x2 = Math.pow(this.x - p2.x, 2);
        double y2 = Math.pow(this.y - p2.y, 2);
        double z2 = Math.pow(this.z - p2.z, 2);
        return  Math.sqrt(x2 + y2 + z2);
    }

    @Override
    /**
     * Outputs the position of a point.
     */
    public String toString(){
        return "("+this.x+","+this.y+ ","+this.z+")";
    }
}

