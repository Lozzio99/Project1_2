package Module.Graphics;

import Module.Math.Vector3DConverter;

import java.awt.*;
import java.awt.geom.Line2D;

public class ModuleScene extends Scene {

    /**
     * The X,Y,Z axis.
     */
    Line2D.Double xAxis, yAxis, zAxis;


    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setFont(new Font("Monospaced", Font.PLAIN, 15));
        g.setColor(new Color(255, 255, 255, 255));
        g.drawString("x Axis", 20, 20);
        g.draw(xAxis);
        g.setColor(new Color(255, 42, 42, 255));
        g.drawString("y Axis", 20, 30);
        g.draw(yAxis);
        g.setColor(new Color(120, 255, 108, 255));
        g.drawString("z Axis", 20, 40);
        g.draw(zAxis);
    }

    @Override
    public void init() {
        xAxis = new Line2D.Double(Vector3DConverter.convertVector(left), Vector3DConverter.convertVector(right));
        yAxis = new Line2D.Double(Vector3DConverter.convertVector(top), Vector3DConverter.convertVector(bottom));
        zAxis = new Line2D.Double(Vector3DConverter.convertVector(rear), Vector3DConverter.convertVector(front));
    }

    @Override
    public void update() {
        super.update();
        xAxis.setLine(Vector3DConverter.convertVector(left), Vector3DConverter.convertVector(right));
        yAxis.setLine(Vector3DConverter.convertVector(top), Vector3DConverter.convertVector(bottom));
        zAxis.setLine(Vector3DConverter.convertVector(rear), Vector3DConverter.convertVector(front));
        super.resetMouse();
    }

    @Override
    public String toString() {
        return "Module Scene";
    }
}
