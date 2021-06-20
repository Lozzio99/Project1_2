package phase3.Graphics.Scenes;

import phase3.Graphics.MouseInput;
import phase3.Math.ADT.Vector2DConverter;
import phase3.Math.ADT.Vector3D;
import phase3.Math.ADT.Vector3dInterface;
import phase3.Simulation.SimulationInterface;
import phase3.System.State.StateInterface;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static phase3.Config.TRAJECTORY_LENGTH;
import static phase3.Graphics.GraphicsInterface.screen;
import static phase3.Math.ADT.Vector2DConverter.convertVector;
import static phase3.Math.ADT.Vector2DConverter.getScale;
import static phase3.Math.Noise.WindNoiseCanvas.saveImage;

public class PendulumScene extends Scene {

    static int i = 0;
    static Vector3dInterface vx, vy, vx2, vy2;
    private static double r1, r2, a1, a2, l1, l2;
    private final Color[] colors = new Color[]{
            new Color(255, 255, 255, 149),
            new Color(255, 55, 55),
            new Color(113, 255, 115)
    };
    private final Vector3dInterface origin = new Vector3D(0, 0, 0), p1 = new Vector3D(-100, -200, 0), p2 = new Vector3D(-100, -400, 0);
    private final SimulationScene.Bag[] trajectories = new SimulationScene.Bag[2];

    public PendulumScene(SimulationInterface s) {
        super(s);
    }

    protected static void drawTrajectories(Graphics2D g, int i, SimulationScene.Bag[] trajectories) {
        for (int k = trajectories[i].insert; k < trajectories[i].getTrajectories().length - 1; k++) {
            if (trajectories[i].getTrajectories()[k + 1] == null)
                break;
            g.draw(new Line2D.Double(
                    Vector2DConverter.convertVector(trajectories[i].getTrajectories()[k]),
                    Vector2DConverter.convertVector(trajectories[i].getTrajectories()[k + 1])));
        }
        for (int k = 0; k < trajectories[i].insert - 1; k++) {
            if (trajectories[i].getTrajectories()[k + 1] == null)
                break;
            g.draw(new Line2D.Double(
                    Vector2DConverter.convertVector(trajectories[i].getTrajectories()[k]),
                    Vector2DConverter.convertVector(trajectories[i].getTrajectories()[k + 1])));
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {

        Graphics2D g = (Graphics2D) graphics;
        BufferedImage image = new BufferedImage(screen.width, screen.height, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        g.setColor(Color.black);
        g.fill3DRect(0, 0, screen.width, screen.height, false);
        g.setColor(colors[0]);
        g.setStroke(new BasicStroke(1f));
        g.draw(X);
        g.draw(Y);
        Point2D.Double po = convertVector(origin),
                p1 = convertVector(this.p1),
                p2 = convertVector(this.p2);
        g.draw(new Line2D.Double(po, p1));
        g.draw(new Line2D.Double(p1, p2));
        g.draw(planetShape(po, 10 * getScale()));
        g.setColor(colors[1]);
        g.fill(planetShape(p1, r1 * getScale()));
        drawTrajectories(g, 0, trajectories);
        g.setColor(colors[2]);
        g.fill(planetShape(p2, r2 * getScale()));
        drawTrajectories(g, 1, trajectories);
        saveImage("src/main/resources/Gifs/pendulum" + (i++) + ".png", image);
        if (i == 200) System.exit(0);
    }

    @Override
    public void init() {
        for (int i = 0; i < 25; i++) Vector2DConverter.zoomOut();
        mouseSensitivity = 2;
        TRAJECTORY_LENGTH = 2000;
        this.state = simulation.getSystem().getState();
        this.trajectories[0] = new SimulationScene.Bag();
        this.trajectories[1] = new SimulationScene.Bag();
        updateBodies();


        vx = new Vector3D(1000, 0, 0);
        vx2 = new Vector3D(-1000, 0, 0);
        vy = new Vector3D(0, 1000, 0);
        vy2 = new Vector3D(0, -1000, 0);
        X = new Line2D.Double(Vector2DConverter.convertVector(vx), Vector2DConverter.convertVector(vx2));
        Y = new Line2D.Double(Vector2DConverter.convertVector(vy), Vector2DConverter.convertVector(vy2));
    }


    @Override
    public synchronized void update(StateInterface<Vector3dInterface> v) {
        super.update(v);
        updateBodies();
        X.setLine(Vector2DConverter.convertVector(vx), Vector2DConverter.convertVector(vx2));
        Y.setLine(Vector2DConverter.convertVector(vy), Vector2DConverter.convertVector(vy2));
        super.resetMouse();
    }

    protected void updateBodies() {
        r1 = state.get()[0].getX();
        r2 = state.get()[1].getX();
        l1 = state.get()[0].getY();
        l2 = state.get()[1].getY();
        a1 = state.get()[0].getZ();
        a2 = state.get()[1].getZ();
        p1.setX(l1 * sin(a1));
        p1.setY(l1 * cos(a1));
        p2.setX(p1.getX() + l2 * sin(a2));
        p2.setY(p1.getY() + l2 * cos(a2));
        this.trajectories[0].add(p1.clone());
        this.trajectories[1].add(p2.clone());
    }

    @Override
    public void addMouseControl(MouseInput mouse) {
        this.mouse = mouse;
    }
}
