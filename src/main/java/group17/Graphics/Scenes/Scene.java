package group17.Graphics.Scenes;


import group17.Math.Point3D;
import group17.Math.Point3DConverter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static group17.Config.PARTICLES_SIMULATION;
import static group17.Config.SIMULATION_LEVEL;
import static group17.Main.simulationInstance;

/**
 * Abstract class to Render a scene
 * to implement a Scene inherit from this class like this :
 * class MyScene extends Scene{
 * }
 * <p>
 * it is also possible to delegate the background painting
 * to the superclass with a call to the super.paintComponent(graphics)
 * {@link #paintComponent(Graphics)}
 * or to update the bodies positions and the mouse updates by a call
 * to the {@link #update()} and a consequent call to  {@link #resetMouse()}
 */
public abstract class Scene extends JPanel {
    protected final static double mouseSensitivity = 16;
    private final static int UNIT_SIZE = 1410;//*(int) scale;
    public static MouseInput mouse;
    protected static int initialX, initialY, x, y, xDif, yDif, deltaX, deltaY, totalXDif, totalYDif;
    protected static double scale = 3e8;
    protected double radiusMag = 4e2;
    protected Point3D left = new Point3D(-UNIT_SIZE, 0, 0),
            right = new Point3D(UNIT_SIZE, 0, 0),
            top = new Point3D(0, UNIT_SIZE, 0),
            bottom = new Point3D(0, -UNIT_SIZE, 0),
            front = new Point3D(0, 0, UNIT_SIZE),
            rear = new Point3D(0, 0, -UNIT_SIZE);
    private BufferedImage image;
    private Graphics2D g;
    private boolean IMAGE_FAILED = false;

    /**
     * Load and renders the background for the children scenes
     *
     * @param graphics the graphics object on which the background has to be painted
     */
    @Override
    public void paintComponent(Graphics graphics) {

        //super.paintComponent(graphics);

        g = (Graphics2D) graphics;

        if (!IMAGE_FAILED && SIMULATION_LEVEL != PARTICLES_SIMULATION) {
            if (image == null) {
                create();
                setHints(g);
            } else {
                g.drawImage(image, 0, 0, simulationInstance.getGraphics().getFrame().getWidth(),
                        simulationInstance.getGraphics().getFrame().getHeight(), 0, 0, image.getWidth(), image.getHeight(), new Color(0, 0, 0, 111), null);
            }
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, simulationInstance.getGraphics().getFrame().getWidth(),
                    simulationInstance.getGraphics().getFrame().getHeight());
        }

    }


    /**
     * Improves the rendering performance as well as the scaling and coloring
     * interpolations
     *
     * @param g the graphics object to configure
     */
    public void setHints(Graphics2D g) {
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

    /**
     * Loads the background image from resources
     * todo set throw exception
     */
    void create() {
        try {
            File f = new File(Scene.class.getClassLoader().getResource("milky-way4k.jpg").getFile());
            image = ImageIO.read(f);
        } catch (NullPointerException | IOException e) {
            IMAGE_FAILED = true;
            image = null;
            System.err.println(e.getMessage() + "\nPossible fix : make sure 'milky-way4k.jpg' is in resources");
        }
    }


    public abstract void init();


    /**
     * updates the mouse tracking variables to update the bodies if
     * any mouse event was generated
     *
     * @see SimulationScene#updateBodies()
     */
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

    /**
     * add the Mouse listener object to the scene
     *
     * @param mouse
     */
    public void addMouseControl(MouseInput mouse) {
        Scene.mouse = mouse;
    }

    /**
     * Reset the mouse events generated, after the update call,
     * in order to let them awake for the next frame,
     * avoiding such a call will lead to interminable mouse events such as
     * continuous spinning or zooming
     */
    public void resetMouse() {
        mouse.resetScroll();
        initialX = x;
        initialY = y;
    }

    /**
     * Rotates over the Y axis
     *
     * @param cw true if clockwise , false otherwise
     * @param y  the degree of rotation
     */
    public void rotateOnAxisY(boolean cw, double y) {
        Point3DConverter.rotateAxisY(top, cw, y);
        Point3DConverter.rotateAxisY(bottom, cw, y);
        Point3DConverter.rotateAxisY(left, cw, y);
        Point3DConverter.rotateAxisY(right, cw, y);
        Point3DConverter.rotateAxisY(rear, cw, y);
        Point3DConverter.rotateAxisY(front, cw, y);
    }

    /**
     * Rotates over the X axis
     *
     * @param cw true if clockwise , false otherwise
     * @param x  the degree of rotation
     */
    void rotateOnAxisX(boolean cw, double x) {
        Point3DConverter.rotateAxisX(top, cw, x);
        Point3DConverter.rotateAxisX(bottom, cw, x);
        Point3DConverter.rotateAxisX(left, cw, x);
        Point3DConverter.rotateAxisX(right, cw, x);
        Point3DConverter.rotateAxisX(rear, cw, x);
        Point3DConverter.rotateAxisX(front, cw, x);
    }

    /**
     * Rotates over the Z axis
     *
     * @param cw true if clockwise , false otherwise
     * @param z  the degree of rotation
     */
    void rotateOnAxisZ(boolean cw, double z) {
        Point3DConverter.rotateAxisZ(top, cw, z);
        Point3DConverter.rotateAxisZ(bottom, cw, z);
        Point3DConverter.rotateAxisZ(left, cw, z);
        Point3DConverter.rotateAxisZ(right, cw, z);
        Point3DConverter.rotateAxisZ(rear, cw, z);
        Point3DConverter.rotateAxisZ(front, cw, z);
    }


    /**
     * enum for the scenes implementations
     */
    public enum SceneType {
        /**
         * Only 3-dimensional axis and user suggestions
         */
        STARTING_SCENE,
        /**
         * Renders the system state bodies
         */
        SIMULATION_SCENE,
        /**
         * TODO: 19/04/2021 implement this back
         */
        TRAJECTORIES
    }

}
