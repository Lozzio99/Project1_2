package group17.phase1.Titan.Simulations.RocketSimulation.Graphics;

import group17.phase1.Titan.Graphics.Scenes.Scene;
import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.Physics.Math.Point3D;
import group17.phase1.Titan.Physics.Math.Point3DConverter;
import group17.phase1.Titan.Physics.Math.Vector3D;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import static group17.phase1.Titan.Main.simulation;

public class RocketScene extends Scene {
    Point3D[] a_p;
    Point3D p1, p2, p3;
    Line2D.Double[] axis;

    Shape rocket;


    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(new Color(77, 39, 156));
        g.fill3DRect(0, 0, simulation.graphics().get().getFrame().getWidth(), simulation.graphics().get().getFrame().getHeight(), true);
        g.setColor(simulation.system().getCelestialBodies().get(0).getColour());
        for (Line2D l : axis)
            g.draw(l);
        for (int i = 6; i < this.a_p.length; i++) {
            g.draw(shape(i));
        }
        g.draw(rocket());
    }

    private Shape rocket() {
        Vector3dInterface p = simulation.system().systemState().getPositions().get(0), dir = simulation.system().systemState().getPositions().get(1).sub(p);
        dir = Vector3D.normalize(dir);

        p1 = p.add(dir.mul(50)).fromVector();
        p2 = p.add(dir.mul(20)).fromVector();
        p3 = p.add(dir.mul(20)).fromVector();

        Point3DConverter.rotateAxisY(p1, true, totalYDif);
        Point3DConverter.rotateAxisY(p2, true, totalYDif);
        Point3DConverter.rotateAxisY(p3, true, totalYDif);

        Point3DConverter.rotateAxisX(p3, true, totalXDif);
        Point3DConverter.rotateAxisX(p1, true, totalXDif);
        Point3DConverter.rotateAxisX(p2, true, totalXDif);

        Point3DConverter.rotateAxisZ(p3, true, 90);
        Point3DConverter.rotateAxisZ(p2, false, 90);


        Point[] drawingPoints = new Point[3];
        drawingPoints[0] = Point3DConverter.convertPoint(p1);
        drawingPoints[1] = Point3DConverter.convertPoint(p2);
        drawingPoints[2] = Point3DConverter.convertPoint(p3);
        return rocket = new Polygon(new int[]{drawingPoints[0].x, drawingPoints[1].x, drawingPoints[2].x}, new int[]{drawingPoints[0].y, drawingPoints[1].y, drawingPoints[2].y}, 3);
    }


    private Ellipse2D.Double shape(int i) {
        Point p = Point3DConverter.convertPoint(this.a_p[i]);
        double r = simulation.system().getCelestialBodies().get(i - 5).getRADIUS();//missing rocket
        return new Ellipse2D.Double(p.getX() - r, p.getY() - r, r * 2, r * 2);
    }

    @Override
    public void init() {
        this.a_p = new Point3D[6 + (simulation.system().getCelestialBodies().size() - 1)]; //no rocket
        this.a_p[0] = new Point3D(400, 0, 0);
        this.a_p[1] = new Point3D(-400, 0, 0);
        this.a_p[2] = new Point3D(0, 400, 0);
        this.a_p[3] = new Point3D(0, -400, 0);
        this.a_p[4] = new Point3D(0, 0, 400);
        this.a_p[5] = new Point3D(0, 0, -400);
        this.axis = new Line2D.Double[]{
                new Line2D.Double(Point3DConverter.convertPoint(this.a_p[0]), Point3DConverter.convertPoint(this.a_p[1])),
                new Line2D.Double(Point3DConverter.convertPoint(this.a_p[2]), Point3DConverter.convertPoint(this.a_p[3])),
                new Line2D.Double(Point3DConverter.convertPoint(this.a_p[4]), Point3DConverter.convertPoint(this.a_p[5])),
        };
        for (int i = 6; i < simulation.system().getCelestialBodies().size() + 5; i++) {
            this.a_p[i] = (simulation.system().getCelestialBodies().get(i - 5).getVectorLocation().fromVector());
        }
    }

    @Override
    public void update() {
        super.update();
        this.updateBodies();
        super.resetMouse();
    }

    public void updateBodies() {
        for (Point3D p : this.a_p) {
            Point3DConverter.rotateAxisY(p, true, totalYDif);
            Point3DConverter.rotateAxisX(p, true, totalXDif);
        }
        Point3DConverter.rotateAxisY(p3, true, totalYDif);
        Point3DConverter.rotateAxisY(p2, true, totalYDif);
        Point3DConverter.rotateAxisX(p3, true, totalXDif);
        Point3DConverter.rotateAxisX(p2, true, totalXDif);
        for (int i = 6; i < simulation.system().getCelestialBodies().size() + 5; i++) {
            this.a_p[i] = (simulation.system().systemState().getPositions().get(i - 5).fromVector());
            Point3DConverter.rotateAxisY(this.a_p[i], true, totalYDif);
            Point3DConverter.rotateAxisX(this.a_p[i], true, totalXDif);
        }
    }

}
