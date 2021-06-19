package phase3.Graphics.Scenes;


import phase3.Graphics.MouseInput;
import phase3.Math.ADT.Vector2DConverter;
import phase3.Math.ADT.Vector3D;
import phase3.Math.ADT.Vector3DConverter;
import phase3.Math.ADT.Vector3dInterface;
import phase3.System.State.StateInterface;
import phase3.System.State.SystemState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static phase3.Graphics.GraphicsInterface.screen;
import static phase3.Main.simulation;
import static phase3.Math.ADT.Vector3DConverter.*;

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
 * to the {@link #update(StateInterface v)} and a consequent call to  {@link #resetMouse()}
 */
public abstract class Scene extends JPanel {
    /**
     * The constant mouseSensitivity.
     */
    protected final static double mouseSensitivity = 8;
    private final static int UNIT_SIZE = 1410;//*(int) scale;
    public static Line2D.Double X, Y;
    /**
     * The constant initialX.
     */
    protected static int initialX, initialY, x, y, xDif, yDif, totalXDif, totalYDif;
    protected static int initialX2, initialY2, x2, y2, xDif2, yDif2, totalXDif2, totalYDif2;
    protected static double scale = 3e8;
    protected static double deltaX2;
    protected static double deltaY2;
    /**
     * The constant mouse.
     */
    public MouseInput mouse;
    protected double radiusMag = 4e2;
    protected Vector3dInterface left = new Vector3D(-UNIT_SIZE, 0, 0),
            right = new Vector3D(UNIT_SIZE, 0, 0),
            top = new Vector3D(0, UNIT_SIZE, 0),
            bottom = new Vector3D(0, -UNIT_SIZE, 0),
            front = new Vector3D(0, 0, UNIT_SIZE),
            rear = new Vector3D(0, 0, -UNIT_SIZE);
    protected StateInterface<Vector3dInterface> state = new SystemState<>(new Vector3D(0, 0, 0));
    /**
     * The Graphics object
     */
    private Graphics2D g;
    /**
     * The background flightImage
     */
    protected BufferedImage flightImage;
    protected BufferedImage landingImage;
    protected boolean IMAGE_FAILED = false;

    @Override
    public void paintComponent(Graphics graphics) {
        //super.paintComponent(graphics);
        g = (Graphics2D) graphics;
        if (flightImage == null || landingImage == null) {
            create();
            setHints(g);
        }
    }

    public void paintImage(Graphics2D g, BufferedImage image) {
        if (!IMAGE_FAILED) {
            g.drawImage(image, 0, 0, screen.width,
                    screen.height, 0, 0, image.getWidth(), image.getHeight(), new Color(0, 0, 0, 111), this);
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
     * Loads the background flightImage from resources
     */
    void create() {
        try {
            File titan = new File(Objects.requireNonNull(Scene.class.getClassLoader().getResource("titan2.png")).getFile());
            //URL resourse = new URL("https://cdn.mos.cms.futurecdn.net/k3FmsfkjnEQao2Ci2AtEUK-1200-80.jpg");
            File flight = new File(Objects.requireNonNull(Scene.class.getClassLoader().getResource("milky-way4k.jpg")).getFile());
            flightImage = ImageIO.read(flight);
            landingImage = ImageIO.read(titan);
        } catch (NullPointerException | IOException e) {
            IMAGE_FAILED = true;
            flightImage = null;
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
     */
    public synchronized void update(final StateInterface<Vector3dInterface> v) {
        this.state = v;
        if (mouse.getScene() == 0) return;
        //xDif = xDif2 = yDif = yDif2 = 0;

        if (mouse.getScene() == 1) {
            x = mouse.getX();
            y = mouse.getY();
            if (mouse.getButton() == MouseInput.ClickType.LeftClick) {
                xDif = x - initialX;
                Vector2DConverter.translate(xDif / mouseSensitivity, 0);
            } else if (mouse.getButton() == MouseInput.ClickType.RightClick) {
                yDif = y - initialY;
                Vector2DConverter.translate(0, yDif / mouseSensitivity);
            } else if (mouse.isScrollingUp()) {
                Vector2DConverter.zoomIn();
            } else if (mouse.isScrollingDown()) {
                Vector2DConverter.zoomOut();
            }
            totalXDif += xDif;
            totalYDif += yDif;
        } else if (mouse.getScene() == 2) {
            x2 = mouse.getX();
            y2 = mouse.getY();
            if (mouse.getButton() == MouseInput.ClickType.LeftClick) {
                deltaX2 = xDif2 = x2 - initialX2;
                totalXDif2 += xDif2;
                this.rotateOnAxisY(false, xDif2 / mouseSensitivity);
            } else if (mouse.getButton() == MouseInput.ClickType.RightClick) {
                deltaY2 = yDif2 = y2 - initialY2;
                totalYDif2 += yDif2;
                this.rotateOnAxisX(false, yDif2 / mouseSensitivity);
            } else if (mouse.isScrollingUp()) {
                Vector3DConverter.zoomIn();
            } else if (mouse.isScrollingDown()) {
                Vector3DConverter.zoomOut();
            }


        }

        //call this from the subclass
        //this.resetMouse();
    }

    /**
     * add the Mouse listener object to the scene
     *
     * @param mouse the mouse
     */
    public abstract void addMouseControl(MouseInput mouse);

    /**
     * Reset the mouse events generated, after the update call,
     * in order to let them awake for the next frame,
     * avoiding such a call will lead to interminable mouse events such as
     * continuous spinning or zooming
     */
    public void resetMouse() {
        mouse.resetScroll();
        //mouse.resetButton();
        initialX = x;
        initialY = y;
        initialX2 = x2;
        initialY2 = y2;
        deltaX2 = 0;
        deltaY2 = 0;
    }

    /**
     * Rotates over the Y axis
     *
     * @param cw true if clockwise , false otherwise
     * @param y  the degree of rotation
     */
    public void rotateOnAxisY(boolean cw, double y) {
        rotateAxisY(top, cw, y);
        rotateAxisY(bottom, cw, y);
        rotateAxisY(left, cw, y);
        rotateAxisY(right, cw, y);
        rotateAxisY(rear, cw, y);
        rotateAxisY(front, cw, y);
    }

    /**
     * Rotates over the X axis
     *
     * @param cw true if clockwise , false otherwise
     * @param x  the degree of rotation
     */
    void rotateOnAxisX(boolean cw, double x) {
        rotateAxisX(top, cw, x);
        rotateAxisX(bottom, cw, x);
        rotateAxisX(left, cw, x);
        rotateAxisX(right, cw, x);
        rotateAxisX(rear, cw, x);
        rotateAxisX(front, cw, x);
    }

    /**
     * Rotates over the Z axis
     *
     * @param cw true if clockwise , false otherwise
     * @param z  the degree of rotation
     */
    void rotateOnAxisZ(boolean cw, double z) {
        rotateAxisZ(top, cw, z);
        rotateAxisZ(bottom, cw, z);
        rotateAxisZ(left, cw, z);
        rotateAxisZ(right, cw, z);
        rotateAxisZ(rear, cw, z);
        rotateAxisZ(front, cw, z);
    }

    /**
     * enum for the scenes implementations
     */
    public enum SceneType {
        /**
         * Landing module
         */
        MODULE_SCENE,
        STARTING_SCENE,
        FLIGHT_SCENE
    }

}
