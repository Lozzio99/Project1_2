package group17.phase1.Titan.Graphics.Renderer.geometry;

import group17.phase1.Titan.Graphics.Renderer.Point3DConverter;
import group17.phase1.Titan.Physics.Trajectories.Forces.Vector3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Polygon3D {

    private static final double AmbientLight = 0.13;

    private Color baseColor, lightingColor;
    private final Point3D[] points;
    private boolean visible = true;

    public Polygon3D(Color color, Point3D... points) {
        this.baseColor = this.lightingColor = color;
        this.points = new Point3D[points.length];
        for (int i = 0; i < points.length; i++) {
            Point3D p = points[i];
            this.points[i] = new Point3D(p.x, p.y, p.z);
        }
        this.updateVisibility();

    }

    public Polygon3D(Point3D... points) {
        this.baseColor = this.lightingColor = Color.WHITE;
        this.points = new Point3D[points.length];
        for (int i = 0; i < points.length; i++) {
            Point3D p = points[i];
            this.points[i] = new Point3D(p.x, p.y, p.z);
        }
        this.updateVisibility();

    }

    public static Polygon3D[] sortPolygons(Polygon3D[] Polygon3DS) {
        List<Polygon3D> polygonsList = new ArrayList<Polygon3D>();

        for (Polygon3D poly : Polygon3DS) {
            polygonsList.add(poly);
        }

        Collections.sort(polygonsList, (p1, p2) -> {

            Point3D p1Average = p1.getAveragePoint();
            Point3D p2Average = p2.getAveragePoint();
            double p1Dist = Point3D.euclidDist(p1Average, Point3D.origin);
            double p2Dist = Point3D.euclidDist(p2Average, Point3D.origin);
            double diff = p1Dist - p2Dist;
            if (diff == 0) {
                return 0;
            }

            return diff < 0 ? 1 : -1;
        });

        for (int i = 0; i < Polygon3DS.length; i++) {
            Polygon3DS[i] = polygonsList.get(i);
        }

        return Polygon3DS;
    }

    public void translate(double x, double y, double z) {
        for(Point3D p : points) {
            p.xOffset += x;
            p.yOffset += y;
            p.zOffset += z;
        }
        this.updateVisibility();
    }

    public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees, Vector3D lightVector3D) {
        for(Point3D p : points) {
            Point3DConverter.rotateAxisX(p, CW, xDegrees);
            Point3DConverter.rotateAxisY(p, CW, yDegrees);
            Point3DConverter.rotateAxisZ(p, CW, zDegrees);
        }
        this.updateVisibility();
        this.setLighting(lightVector3D);
    }

    public double getAverageX() {
        double sum = 0;
        for(Point3D p : this.points) {
            sum += p.x + p.xOffset;
        }

        return sum / this.points.length;
    }

    public void setColor(Color color) {
        this.baseColor = color;
    }

    public void render(Graphics g) {
        if (!this.visible) return;

        Polygon poly = new Polygon();
        for (int i = 0; i < this.points.length; i++) {
            Point p = Point3DConverter.convertPoint(this.points[i]);
            poly.addPoint(p.x, p.y);
        }

        g.setColor(this.lightingColor);
        g.fillPolygon(poly);
    }

    public void setLighting(Vector3D lightVector3D) {
        if(this.points.length < 3) {
            return;
        }

        Vector3D v1 = new Vector3D(this.points[0], this.points[1]);
        Vector3D v2 = new Vector3D(this.points[1], this.points[2]);
        Vector3D normal = Vector3D.normalize(Vector3D.cross( v2, v1));
        double dot = Vector3D.dot(normal, lightVector3D);
        double sign = dot < 0 ? -1 : 1;
        dot = sign * dot * dot;
        dot = (dot + 1) / 2 * (1 - AmbientLight);

        double lightRatio = Math.min(1, Math.max(0, AmbientLight + dot));
        this.updateLightingColor(lightRatio);
    }

    public boolean isVisible() {
        return this.visible;
    }

    private void updateVisibility() {
        //this.visible = true;
        this.visible = this.getAverageX() < 400;
    }

    private void updateLightingColor(double lightRatio) {
        int red = (int) (this.baseColor.getRed() * lightRatio);
        int green = (int) (this.baseColor.getGreen() * lightRatio);
        int blue = (int) (this.baseColor.getBlue() * lightRatio);
        this.lightingColor = new Color(red, green, blue);
    }

    private Point3D getAveragePoint() {
        float x = 0;
        float y = 0;
        float z = 0;
        for(Point3D p : this.points) {
            x += p.x + p.xOffset;
            y += p.y + p.yOffset;
            z += p.z + p.zOffset;
        }

        x /= this.points.length;
        y /= this.points.length;
        z /= this.points.length;

        return new Point3D(x, y, z);
    }

}
