package group17.phase1.Titan.Graphics.Renderer.geometry;

import group17.phase1.Titan.Physics.Trajectories.CoordinateInterface;

public class Point3D implements CoordinateInterface
{
    public final static Point3D origin = new Point3D(0, 0, 0);

    public float x, y, z;
    public float xOffset, yOffset, zOffset;

    public Point3D(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.xOffset = 0;
        this.yOffset = 0;
        this.zOffset = 0;
    }


    @Override
    public double getXCoordinate() {
        return this.x+this.xOffset;
    }

    @Override
    public double getYCoordinate() {
        return this.y + this.yOffset;
    }

    @Override
    public double getZCoordinate() {
        return this.z + this.zOffset;
    }

    public static float euclidDist(Point3D p1, Point3D p2) {
        double x2 = Math.pow(p1.x - p2.x, 2);
        double y2 = Math.pow(p1.y - p2.y, 2);
        double z2 = Math.pow(p1.z - p2.z, 2);
        return (float) Math.sqrt(x2 + y2 + z2);
    }

    public float euclidDist(Point3D p2) {
        double x2 = Math.pow(this.x - p2.x, 2);
        double y2 = Math.pow(this.y - p2.y, 2);
        double z2 = Math.pow(this.z - p2.z, 2);
        return (float) Math.sqrt(x2 + y2 + z2);
    }
}
