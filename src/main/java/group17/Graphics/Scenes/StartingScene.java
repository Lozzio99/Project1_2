package group17.Graphics.Scenes;

import group17.Math.Point3DConverter;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * The type Starting scene.
 */
public class StartingScene extends Scene {

    /**
     * The X axis.
     */
    Line2D.Double xAxis, /**
     * The Y axis.
     */
    yAxis, /**
     * The Z axis.
     */
    zAxis;

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setFont(new Font("Monospaced", Font.PLAIN, 15));
        g.setColor(new Color(255, 255, 255, 255));
        g.drawString("x Axis", 20, 20);
        g.draw(xAxis);
        g.drawString("- Left click drag to rotate along the Y axis ", 1000, 20);
        g.drawString("- Right click drag to rotate along the X axis ", 1000, 35);
        g.drawString("- Pinch in-out or wheel scroll to zoom in-out ", 1000, 50);
        g.setColor(new Color(255, 42, 42, 255));
        g.drawString("y Axis", 20, 30);
        g.draw(yAxis);
        g.setColor(new Color(120, 255, 108, 255));
        g.drawString("z Axis", 20, 40);
        g.draw(zAxis);
    }

    @Override
    public void init() {
        xAxis = new Line2D.Double(Point3DConverter.convertPoint(left), Point3DConverter.convertPoint(right));
        yAxis = new Line2D.Double(Point3DConverter.convertPoint(top), Point3DConverter.convertPoint(bottom));
        zAxis = new Line2D.Double(Point3DConverter.convertPoint(rear), Point3DConverter.convertPoint(front));
    }

    @Override
    public void update() {
        super.update();
        xAxis.setLine(Point3DConverter.convertPoint(left), Point3DConverter.convertPoint(right));
        yAxis.setLine(Point3DConverter.convertPoint(top), Point3DConverter.convertPoint(bottom));
        zAxis.setLine(Point3DConverter.convertPoint(rear), Point3DConverter.convertPoint(front));
        super.resetMouse();
    }

    @Override
    public String toString() {
        return "StartingScene{}";
    }
}
