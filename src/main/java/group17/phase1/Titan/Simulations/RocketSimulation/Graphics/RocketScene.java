package group17.phase1.Titan.Simulations.RocketSimulation.Graphics;

import group17.phase1.Titan.Graphics.Scenes.Scene;
import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.Physics.Bodies.CelestialBody;
import group17.phase1.Titan.Physics.Math.Point3D;
import group17.phase1.Titan.Physics.Math.Point3DConverter;
import group17.phase1.Titan.Physics.Math.Vector3D;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import static group17.phase1.Titan.Main.simulation;

public class RocketScene extends Scene {

    Point3D[] a_p;
    Line2D.Double[] axis;

    private final List<ShapeInterface> entities;

    private final Vector3dInterface lightVector = Vector3D.normalize(new Vector3D(0, 0, -1));

    RocketScene() {
        this.entities = new ArrayList<>();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(new Color(77, 39, 156));
        g.fill3DRect(0, 0, simulation.graphics().get().getFrame().getWidth(), simulation.graphics().get().getFrame().getHeight(), true);
        g.setColor(simulation.system().getCelestialBodies().get(0).getColour());
        for (Line2D l : axis)
            g.draw(l);
        for (ShapeInterface s : this.entities)
            s.render(g);
    }

    /*
    private Ellipse2D.Double shape(int i) {
        Point p = Point3DConverter.convertPoint(this.a_p[i]);
        double r = simulation.system().getCelestialBodies().get(i).getRADIUS();//missing rocket
        return new Ellipse2D.Double(p.getX() - r, p.getY() - r, r * 2, r * 2);
    }
     */

    @Override
    public void init() {
        this.a_p = new Point3D[6]; //no rocket
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
        CelestialBody p = simulation.system().getCelestialBodies().get(1);
        this.entities.add(Shape3D.createRocket(200));
        this.entities.add(Shape3D.createCube(p.getRADIUS(), p.getVectorLocation().getX(), p.getVectorLocation().getY(), p.getVectorLocation().getZ()));
        this.setLighting();
    }

    @Override
    public void update() {
        super.update();
        this.updateBodies();
        super.resetMouse();
    }

    public void updateBodies() {
        for (Point3D p : this.a_p) {
            Point3DConverter.rotateAxisY(p, true, deltaX);
            Point3DConverter.rotateAxisX(p, true, deltaY);
        }
        this.axis = new Line2D.Double[]{
                new Line2D.Double(Point3DConverter.convertPoint(this.a_p[0]), Point3DConverter.convertPoint(this.a_p[1])),
                new Line2D.Double(Point3DConverter.convertPoint(this.a_p[2]), Point3DConverter.convertPoint(this.a_p[3])),
                new Line2D.Double(Point3DConverter.convertPoint(this.a_p[4]), Point3DConverter.convertPoint(this.a_p[5])),
        };
        for (int i = 0; i < this.entities.size(); i++) {
            this.entities.get(i).translate(simulation.system().systemState().getPositions().get(i).fromVector());
        }
        //this.entities.get(0).seek(simulation.system().systemState().getPositions().get(1));
        this.rotate(true, deltaY, deltaX, 0);
    }

    private void setLighting() {
        for (ShapeInterface entity : this.entities) {
            entity.setLighting(this.lightVector);
        }
    }

    private void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees) {
        for (ShapeInterface entity : this.entities) {
            entity.rotate(CW, xDegrees, yDegrees, zDegrees, this.lightVector);
        }
    }

}
