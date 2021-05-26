package group17.Graphics.Scenes;

import group17.Interfaces.GraphicsInterface;
import group17.Math.Lib.Point3D;
import group17.Math.Lib.Point3DConverter;

import java.awt.*;
import java.awt.geom.Line2D;

public class SceneTest extends Scene {

    private static final Point3D TOP;
    private static final Point3D BOTTOM;
    private static final Point3D LEFT;
    private static final Point3D RIGHT;
    private static final Point3D REAR;
    private static final Point3D FRONT;
    static int sceneCount = 0;

    static {
        double UNIT_SIZE = 400;
        LEFT = new Point3D(-UNIT_SIZE, 0, 0);
        RIGHT = new Point3D(UNIT_SIZE, 0, 0);
        TOP = new Point3D(0, UNIT_SIZE, 0);
        BOTTOM = new Point3D(0, -UNIT_SIZE, 0);
        FRONT = new Point3D(0, 0, UNIT_SIZE);
        REAR = new Point3D(0, 0, -UNIT_SIZE);
    }

    private final int sceneId;

    public SceneTest() {
        this.sceneId = sceneCount;
        sceneCount++;
        sceneCount = sceneCount > 8 ? 0 : sceneCount;
    }


    @Override
    public void paintComponent(Graphics graphics) {
        Color c;
        switch (this.sceneId) {
            case 0 -> c = new Color(255, 0, 0, 255);
            case 1 -> c = new Color(255, 118, 26, 244);
            case 2 -> c = new Color(244, 255, 54, 255);
            case 3 -> c = new Color(27, 255, 40, 211);
            case 4 -> c = new Color(0, 242, 255, 251);
            case 5 -> c = new Color(64, 52, 252, 188);
            case 6 -> c = new Color(160, 27, 255, 211);
            case 7 -> c = new Color(255, 36, 181, 244);
            case 8 -> c = new Color(255, 107, 107);
            default -> c = new Color(1, 1, 1);
        }
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(c);
        g.fill3DRect(0, 0, GraphicsInterface.screen.width, GraphicsInterface.screen.height, false);
        g.setColor(Color.WHITE.brighter());
        g.drawString("SCENE " + this.sceneId, 20, 20);
        g.draw(new Line2D.Double(Point3DConverter.convertPoint(right), Point3DConverter.convertPoint(left)));
        g.draw(new Line2D.Double(Point3DConverter.convertPoint(top), Point3DConverter.convertPoint(bottom)));
        g.draw(new Line2D.Double(Point3DConverter.convertPoint(front), Point3DConverter.convertPoint(rear)));
    }

    @Override
    public void init() {
        this.top = TOP;
        this.bottom = BOTTOM;
        this.left = LEFT;
        this.right = RIGHT;
        this.rear = REAR;
        this.front = FRONT;
    }

    @Override
    public void update() {
        super.update();
        super.resetMouse();
    }

}
