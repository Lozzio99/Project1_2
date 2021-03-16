package group17.phase1.Titan.Graphics.Geometry;

public class Point3D
{
    public final static Point3D origin = new Point3D(0, 0, 0);

    public double x, y, z;
    public double xOffset, yOffset, zOffset;

    public Point3D(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.xOffset = 0;
        this.yOffset = 0;
        this.zOffset = 0;
    }

    public double getXCoordinate() {
        return this.x+this.xOffset;
    }

    public double getYCoordinate() {
        return this.y + this.yOffset;
    }

    public double getZCoordinate() {
        return this.z + this.zOffset;
    }

    public static double euclidDist(Point3D p1, Point3D p2) {
        double x2 = Math.pow(p1.x - p2.x, 2);
        double y2 = Math.pow(p1.y - p2.y, 2);
        double z2 = Math.pow(p1.z - p2.z, 2);
        return Math.sqrt(x2 + y2 + z2);
    }

    public double euclidDist(Point3D p2) {
        double x2 = Math.pow(this.x - p2.x, 2);
        double y2 = Math.pow(this.y - p2.y, 2);
        double z2 = Math.pow(this.z - p2.z, 2);
        return  Math.sqrt(x2 + y2 + z2);
    }
}
