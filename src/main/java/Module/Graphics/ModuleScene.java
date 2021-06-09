package Module.Graphics;

import Module.Math.Vector2DConverter;
import Module.Math.Vector3D;
import Module.Math.Vector3dInterface;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import static Module.Math.Vector2DConverter.getScale;

public class ModuleScene extends Scene {


    volatile static BufferedImage image;
    volatile Shape shape;

    @Override
    public synchronized void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.BLACK);
        g.draw(X);
        g.draw(Y);
        Point2D.Double p = Vector2DConverter.convertVector(state);

        image = ModuleShape.createImage(state.get(2)); //should rotate the image

        int x = (int) p.x, y = (int) p.y;
        g.drawImage(image, x, y, (int) (x + (50 * getScale())),
                //here can do some logic, to enlarge the scaling
                //image size to follow the angle rotation (when it?s 45° the diagonal is bigger than the side
                // so would need some fixed increment (pythagoras) )
                (int) (y + (50 * getScale())), 0, 0, image.getWidth(), image.getHeight(), new Color(0, 0, 0, 0), this);
    }

    @Override
    public void init() {
        image = ModuleShape.load();
        Vector2DConverter.translate(0, 200);
        X = new Line2D.Double(Vector2DConverter.convertVector(new Vector3D(1000, 0, 0)), Vector2DConverter.convertVector(new Vector3D(-1000, 0, 0)));
        Y = new Line2D.Double(Vector2DConverter.convertVector(new Vector3D(0, 1000, 0)), Vector2DConverter.convertVector(new Vector3D(0, -1000, 0)));
        this.shape = ModuleShape.createShape(new Point2D.Double());
    }

    @Override
    public synchronized void update(final Vector3dInterface v) {
        super.update(v);
        if (x != initialX || y != initialY) {
            X.setLine(Vector2DConverter.convertVector(new Vector3D(1000, 0, 0)), Vector2DConverter.convertVector(new Vector3D(-1000, 0, 0)));
            Y.setLine(Vector2DConverter.convertVector(new Vector3D(0, 1000, 0)), Vector2DConverter.convertVector(new Vector3D(0, -1000, 0)));
        }
        super.resetMouse();
    }

    @Override
    public String toString() {
        return "Module Scene";
    }
}
