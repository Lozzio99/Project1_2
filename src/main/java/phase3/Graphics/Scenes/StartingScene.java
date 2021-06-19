package phase3.Graphics.Scenes;

import phase3.Graphics.MouseInput;
import phase3.Math.ADT.Vector3dInterface;
import phase3.System.State.StateInterface;

import java.awt.*;
import java.awt.geom.Line2D;

import static phase3.Math.ADT.Vector3DConverter.convertPoint;

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
        super.paintImage(g, flightImage);
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
        xAxis = new Line2D.Double(convertPoint(left), convertPoint(right));
        yAxis = new Line2D.Double(convertPoint(top), convertPoint(bottom));
        zAxis = new Line2D.Double(convertPoint(rear), convertPoint(front));
    }

    @Override
    public synchronized void update(final StateInterface<Vector3dInterface> v) {
        super.update(v);
        xAxis.setLine(convertPoint(left), convertPoint(right));
        yAxis.setLine(convertPoint(top), convertPoint(bottom));
        zAxis.setLine(convertPoint(rear), convertPoint(front));
        super.resetMouse();
    }

    @Override
    public void addMouseControl(MouseInput mouse) {
        this.mouse = mouse;
    }

    @Override
    public String toString() {
        return "StartingScene{}";
    }
}
