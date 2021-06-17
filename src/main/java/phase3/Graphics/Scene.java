package phase3.Graphics;


import phase3.Math.ADT.Vector2DConverter;
import phase3.Math.ADT.Vector3D;
import phase3.Math.ADT.Vector3dInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import static phase3.Main.simulation;

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
 * to the {@link #update(Vector3dInterface v)} and a consequent call to  {@link #resetMouse()}
 */
public abstract class Scene extends JPanel {
    /**
     * The constant mouseSensitivity.
     */
    protected final static double mouseSensitivity = 8;
    /**
     * The constant mouse.
     */
    public static MouseInput mouse;
    public static Line2D.Double X, Y;
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
     * The Total x dif.
     */
    totalXDif,
    /**
     * The Total y dif.
     */
    totalYDif;

    protected final Vector3dInterface state = new Vector3D(0, 0, 0);
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
            //File f = new File(Objects.requireNonNull(Scene.class.getClassLoader().getResource("titan2.png")).getFile());
            URL resourse = new URL("https://cdn.mos.cms.futurecdn.net/k3FmsfkjnEQao2Ci2AtEUK-1200-80.jpg");
            image = ImageIO.read(resourse);
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
     */
    public synchronized void update(final Vector3dInterface v) {
        this.state.set(v);

        x = mouse.getX();
        y = mouse.getY();

        if (mouse.getButton() == MouseInput.ClickType.LeftClick) {
            xDif = x - initialX;
            Vector2DConverter.translate(xDif / mouseSensitivity, 0);
            totalXDif += xDif;
        } else if (mouse.getButton() == MouseInput.ClickType.RightClick) {
            yDif = y - initialY;
            Vector2DConverter.translate(0, yDif / mouseSensitivity);
            totalYDif += yDif;
        }


        if (mouse.isScrollingUp()) {
            Vector2DConverter.zoomIn();
        } else if (mouse.isScrollingDown()) {
            Vector2DConverter.zoomOut();
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
     * enum for the scenes implementations
     */
    public enum SceneType {
        /**
         * Landing module
         */
        MODULE_SCENE
    }

}
