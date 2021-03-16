package group17.phase1.Titan.Graphics.Geometry;

import group17.phase1.Titan.Interfaces.Vector3dInterface;

import java.awt.*;

public class Polyhedron3D
{
    private final Polygon3D[] Polygon3DS;
    private Color color;

    public Polyhedron3D(Color color, boolean decayColor, Polygon3D... Polygon3DS) {
        this.color = color;
        this.Polygon3DS = Polygon3DS;
        if(decayColor) {
            this.setDecayingPolygonColor();
        }
        else {
            this.setPolygonColor();
        }
        this.sortPolygons();
    }

    public Polyhedron3D(Polygon3D... Polygon3DS) {
        this.color = Color.WHITE;
        this.Polygon3DS = Polygon3DS;
        this.sortPolygons();
    }

    public void render(Graphics g) {
        for(Polygon3D poly : this.Polygon3DS) {
            poly.render(g);
        }
    }

    public void translate(double x, double y, double z) {
        for(Polygon3D p : this.Polygon3DS) {
            p.translate(x, y, z);
        }
        this.sortPolygons();
    }

    public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees, Vector3dInterface lightVector3D) {
        for(Polygon3D p : this.Polygon3DS) {
            p.rotate(CW, xDegrees, yDegrees, zDegrees, lightVector3D);
        }
        this.sortPolygons();
    }

    public void setLighting(Vector3dInterface lightVector3D) {
        for(Polygon3D p : this.Polygon3DS) {
            p.setLighting(lightVector3D);
        }
    }

    public Polygon3D[] getPolygons() {
        return this.Polygon3DS;
    }

    private void sortPolygons() {
        Polygon3D.sortPolygons(this.Polygon3DS);
    }

    private void setPolygonColor() {
        for(Polygon3D poly : this.Polygon3DS) {
            poly.setColor(this.color);
        }
    }

    private void setDecayingPolygonColor() {
        double decayFactor = 0.97;
        for(Polygon3D poly : this.Polygon3DS) {
            poly.setColor(this.color);
            int r = (int) (this.color.getRed() * decayFactor);
            int g = (int) (this.color.getGreen() * decayFactor);
            int b = (int) (this.color.getBlue() * decayFactor);
            this.color = new Color(r, g, b);
        }
    }
}
