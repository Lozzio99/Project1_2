package group17.phase1.Titan.Graphics.Scenes;


import group17.phase1.Titan.Physics.Math.Point3D;
import group17.phase1.Titan.Physics.Math.Point3DConverter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static group17.phase1.Titan.Config.SIMULATION_LEVEL;
import static group17.phase1.Titan.Config.SOLAR_SYSTEM_SIMULATION;
import static group17.phase1.Titan.Interfaces.GraphicsInterface.screen;

public abstract class Scene extends JPanel {
    private final static int UNIT_SIZE = 1410;//*(int) scale;
    public static MouseInput mouse;
    protected final static double mouseSensitivity = 16;
    protected static int initialX, initialY, x, y, xDif, yDif, deltaX, deltaY, totalXDif, totalYDif;
    protected static double scale = 3e8;
    protected double radiusMag = 1e2;
    private BufferedImage image;
    private Graphics2D g;


    protected Point3D left = new Point3D(-UNIT_SIZE, 0, 0),
            right = new Point3D(UNIT_SIZE, 0, 0),
            top = new Point3D(0, UNIT_SIZE, 0),
            bottom = new Point3D(0, -UNIT_SIZE, 0),
            front = new Point3D(0, 0, UNIT_SIZE),
            rear = new Point3D(0, 0, -UNIT_SIZE);

    public void paintComponent(Graphics graphics) {

        //super.paintComponent(graphics);

        g = (Graphics2D) graphics;
        if (SIMULATION_LEVEL == SOLAR_SYSTEM_SIMULATION) {
            if (image == null) {
                create();
                setHints(g);
            } else {
                g.drawImage(image, 0, 0, screen.width, screen.height, 0, 0, image.getWidth(), image.getHeight(), new Color(0, 0, 0, 111), null);
            }
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, screen.width, screen.height);
        }

    }

    public void setHints(Graphics2D g)
    {
        this.doLayout();
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }

    void create()
    {
        try {
            File f = new File(Objects.requireNonNull(Scene.class.getClassLoader().getResource("milky-way4k.jpg")).getFile());
            image = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void init();

    public void update() {
        x = mouse.getX();
        y = mouse.getY();
        deltaX = deltaY = 0;

        if (mouse.getButton() == MouseInput.ClickType.LeftClick) {
            deltaX = xDif = x - initialX;
            this.rotateOnAxisY(false, xDif / mouseSensitivity);
            totalXDif += xDif;
        } else if (mouse.getButton() == MouseInput.ClickType.RightClick) {
            deltaY = yDif = y - initialY;
            this.rotateOnAxisX(false, yDif / mouseSensitivity);
            totalYDif += yDif;
        }


        if (mouse.isScrollingUp()) {
            Point3DConverter.zoomIn();
        } else if (mouse.isScrollingDown()) {
            Point3DConverter.zoomOut();
        }

        //call this from the subclass
        //this.resetMouse();

    }

    public void addMouseControl(MouseInput mouse){
        Scene.mouse = mouse;
    }

    public void resetMouse(){
        mouse.resetScroll();
        initialX = x;
        initialY = y;
    }

    public void rotateOnAxisY(boolean cw, double y){
        Point3DConverter.rotateAxisY(top,cw,y);
        Point3DConverter.rotateAxisY(bottom,cw,y);
        Point3DConverter.rotateAxisY(left,cw,y);
        Point3DConverter.rotateAxisY(right,cw,y);
        Point3DConverter.rotateAxisY(rear,cw,y);
        Point3DConverter.rotateAxisY(front,cw,y);
    }
    void rotateOnAxisX(boolean cw, double x)
    {
        Point3DConverter.rotateAxisX(top,cw,x);
        Point3DConverter.rotateAxisX(bottom,cw,x);
        Point3DConverter.rotateAxisX(left,cw,x);
        Point3DConverter.rotateAxisX(right,cw,x);
        Point3DConverter.rotateAxisX(rear,cw,x);
        Point3DConverter.rotateAxisX(front,cw,x);
    }

    void rotateOnAxisZ(boolean cw, double z){
        Point3DConverter.rotateAxisZ(top,cw,z);
        Point3DConverter.rotateAxisZ(bottom,cw,z);
        Point3DConverter.rotateAxisZ(left,cw,z);
        Point3DConverter.rotateAxisZ(right,cw,z);
        Point3DConverter.rotateAxisZ(rear,cw,z);
        Point3DConverter.rotateAxisZ(front,cw,z);
    }


    public enum SceneType{
        STARTING_SCENE,
        SIMULATION_SCENE,
        TRAJECTORIES
    }

}
