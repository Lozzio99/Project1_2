package group17.phase1.Titan.Simulations.PendulumSimulation.Graphics;

import group17.phase1.Titan.Graphics.Scenes.Scene;
import group17.phase1.Titan.Physics.Math.Point3D;
import group17.phase1.Titan.Physics.Math.Point3DConverter;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import static group17.phase1.Titan.Main.simulation;

public class PendulumScene extends Scene {
    Point3D doll1, doll2;
    Point3D top = new Point3D(0, 0, 0);
    double r1, r2;

    @Override
    public void init() {
        doll1 = simulation.system().systemState().getPositions().get(0).fromVector();
        doll2 = simulation.system().systemState().getPositions().get(1).fromVector();
        r1 = simulation.system().getCelestialBodies().get(0).getRADIUS();
        r2 = simulation.system().getCelestialBodies().get(1).getRADIUS();

    }

    @Override
    public void update() {

        doll1 = simulation.system().systemState().getPositions().get(0).fromVector();
        doll2 = simulation.system().systemState().getPositions().get(1).fromVector();
        r1 = simulation.system().getCelestialBodies().get(0).getRADIUS();
        r2 = simulation.system().getCelestialBodies().get(1).getRADIUS();

    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(new Color(125, 5, 229, 164));
        g.fill3DRect(0, 0, simulation.graphics().get().getFrame().getWidth(), simulation.graphics().get().getFrame().getWidth(), false);
        g.setColor(simulation.system().getCelestialBodies().get(0).getColour());
        Point p1 = Point3DConverter.convertPoint(doll1);
        g.draw(new Line2D.Double(Point3DConverter.convertPoint(top), p1));
        g.fill(shape(p1, r1));
        g.setColor(simulation.system().getCelestialBodies().get(1).getColour());
        Point p2 = Point3DConverter.convertPoint(doll2);
        g.draw(new Line2D.Double(p1, p2));
        g.fill(shape(p2, r2));

    }

    private Ellipse2D.Double shape(Point p, double r) {
        return new Ellipse2D.Double(p.getX() - r1, p.getY() - r2, r * 2, r * 2);
    }

}
