package Module.Graphics;


import Module.Math.Vector3D;
import Module.Math.Vector3DConverter;
import Module.Math.Vector3dInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static Module.Main.simulation;

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
    /**
     * The constant mouseSensitivity.
     */
    protected final static double mouseSensitivity = 16;
    /**
     * The unit size for the axis
     */
    private final static int UNIT_SIZE = 1410;//*(int) scale;
    /**
     * The constant mouse.
     */
    public static MouseInput mouse;
    /**
     * The constant initialX.
     */
    protected static int initialX,
    /**
     * The Initial y.
     */
    initialY,
    /**
     * The X.
     */
    x,
    /**
     * The Y.
     */
    y,
    /**
     * The X dif.
     */
    xDif,
    /**
     * The Y dif.
     */
    yDif,
    /**
     * The Delta x.
     */
    deltaX,
    /**
     * The Delta y.
     */
    deltaY,
    /**
     * The Total x dif.
     */
    totalXDif,
    /**
     * The Total y dif.
     */
    totalYDif;
    /**
     * The constant scale.
     */
    protected static double scale = 3e8;
    /**
     * The Radius mag.
     */
    protected double radiusMag = 4e2;
    /**
     * The Left.
     */
    Vector3dInterface left = new Vector3D(-UNIT_SIZE, 0, 0),
    /**
     * The Right.
     */
    right = new Vector3D(UNIT_SIZE, 0, 0),
    /**
     * The Top.
     */
    top = new Vector3D(0, UNIT_SIZE, 0),
    /**
     * The Bottom.
     */
    bottom = new Vector3D(0, -UNIT_SIZE, 0),
    /**
     * The Front.
     */
    front = new Vector3D(0, 0, UNIT_SIZE),
    /**
     * The Rear.
     */
    rear = new Vector3D(0, 0, -UNIT_SIZE);
    /**
     * The background image
     */
    private BufferedImage image;
    /**
     * The Graphics object
     */
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
        if (!IMAGE_FAILED) {
            if (image == null) {
                create();
                setHints(g);
            } else {
                g.drawImage(image, 0, 0, simulation.getGraphics().getFrame().getWidth(),
                        simulation.getGraphics().getFrame().getHeight(), 0, 0, image.getWidth(), image.getHeight(), new Color(0, 0, 0, 111), this);
            }
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, simulation.getGraphics().getFrame().getWidth(),
                    simulation.getGraphics().getFrame().getHeight());
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
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
        g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
    }

    /**
     * Loads the background image from resources
     */
    void create() {
        try {
            File f = new File(Objects.requireNonNull(Scene.class.getClassLoader().getResource("titan2.png")).getFile());
            image = ImageIO.read(f);
        } catch (NullPointerException | IOException e) {
            IMAGE_FAILED = true;
            image = null;
            System.err.println(e.getMessage() + "\nPossible fix : make sure 'milky-way4k.jpg' is in resources");
        }
    }


    /**
     * Init.
     */
    public abstract void init();


    /**
     * updates the mouse tracking variables to update the bodies if
     * any mouse event was generated
     *
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
            Vector3DConverter.zoomIn();
        } else if (mouse.isScrollingDown()) {
            Vector3DConverter.zoomOut();
        }

        //call this from the subclass
        //this.resetMouse();
    }

    /**
     * add the Mouse listener object to the scene
     *
     * @param mouse the mouse
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
        Vector3DConverter.rotateAxisY(top, cw, y);
        Vector3DConverter.rotateAxisY(bottom, cw, y);
        Vector3DConverter.rotateAxisY(left, cw, y);
        Vector3DConverter.rotateAxisY(right, cw, y);
        Vector3DConverter.rotateAxisY(rear, cw, y);
        Vector3DConverter.rotateAxisY(front, cw, y);
    }

    /**
     * Rotates over the X axis
     *
     * @param cw true if clockwise , false otherwise
     * @param x  the degree of rotation
     */
    void rotateOnAxisX(boolean cw, double x) {
        Vector3DConverter.rotateAxisX(top, cw, x);
        Vector3DConverter.rotateAxisX(bottom, cw, x);
        Vector3DConverter.rotateAxisX(left, cw, x);
        Vector3DConverter.rotateAxisX(right, cw, x);
        Vector3DConverter.rotateAxisX(rear, cw, x);
        Vector3DConverter.rotateAxisX(front, cw, x);
    }

    /**
     * Rotates over the Z axis
     *
     * @param cw true if clockwise , false otherwise
     * @param z  the degree of rotation
     */
    void rotateOnAxisZ(boolean cw, double z) {
        Vector3DConverter.rotateAxisZ(top, cw, z);
        Vector3DConverter.rotateAxisZ(bottom, cw, z);
        Vector3DConverter.rotateAxisZ(left, cw, z);
        Vector3DConverter.rotateAxisZ(right, cw, z);
        Vector3DConverter.rotateAxisZ(rear, cw, z);
        Vector3DConverter.rotateAxisZ(front, cw, z);
    }


    /**
     * enum for the scenes implementations
     */
    public enum SceneType {
        /**
         * Landing module
         */
        MODULE_SCENE
    }

}
