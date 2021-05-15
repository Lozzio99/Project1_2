package group17.Math.Utils;

import org.jetbrains.annotations.Contract;

public class Point3D {
    public double x, y, z;
    public double xOffset, yOffset, zOffset;

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.xOffset = 0;
        this.yOffset = 0;
        this.zOffset = 0;
    }

    public static double euclidDist(Point3D p1, Point3D p2) {
        double x2 = Math.pow(p1.x - p2.x, 2);
        double y2 = Math.pow(p1.y - p2.y, 2);
        double z2 = Math.pow(p1.z - p2.z, 2);
        return Math.sqrt(x2 + y2 + z2);
    }

    public double getXCoordinate() {
        return this.x + this.xOffset;
    }

    public double getYCoordinate() {
        return this.y + this.yOffset;
    }

    public double getZCoordinate() {
        return this.z + this.zOffset;
    }

    public double euclidDist(Point3D p2) {
        double x2 = Math.pow(this.x - p2.x, 2);
        double y2 = Math.pow(this.y - p2.y, 2);
        double z2 = Math.pow(this.z - p2.z, 2);
        return Math.sqrt(x2 + y2 + z2);
    }

    public void scale(double scale) {
        this.x /= scale;
        this.y /= scale;
        this.z /= scale;
    }

    @Override
    public String toString() {
        return "( " + this.getXCoordinate() + " , " + this.getYCoordinate() + " , " + this.getZCoordinate() + " )";
    }

    public Point3D translate(double x, double y, double z) {
        this.xOffset += x;
        this.yOffset += y;
        this.zOffset += z;
        return this;
    }

    public double heading() {
        return Math.atan2(this.getYCoordinate(), this.getZCoordinate());
    }


    @Contract(value = "null -> false", pure = true)
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
