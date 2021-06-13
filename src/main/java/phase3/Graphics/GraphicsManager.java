package phase3.Graphics;


import phase3.Math.ADT.Vector3dInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicReference;

import static phase3.Config.DEBUG;


/**
 * The main graphics threads/scenes handler
 */
public class GraphicsManager extends Canvas implements GraphicsInterface {

    /**
     * The Main graphics thread.
     */
    protected final AtomicReference<Thread> mainGraphicsTh = new AtomicReference<>();
    /**
     * The Mouse.
     */
    protected MouseInput mouse;
    /**
     * The Current scene.
     */
    protected Scene currentScene;
    /**
     * The Frames.
     */
    int frames = 0;
    /**
     * The T.
     */
    double t = System.currentTimeMillis();
    private JFrame frame;
    private WindowEvent listen;
    private static double FPS;
    private Vector3dInterface state;


    @Override
    public void init() {
        this.frame = new JFrame("Solar System ");
        this.frame.setSize(screen);
        this.frame.add(this);
        this.setWindowProperties();
        this.frame.setVisible(true);
        Cursor c = new Cursor(Cursor.MOVE_CURSOR);
        this.frame.setCursor(c);
        //this.frame.setCursor(new Cursor(Cursor.MOVE_CURSOR));
        //this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }

    /**
     * Sets window properties.
     */
    protected void setWindowProperties() {
        final var closed = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                listen = new WindowEvent(frame, 201);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(listen);
                System.out.println("System closed by user");
                System.exit(0);
            }
        };
        this.frame.addWindowListener(closed);
        this.frame.setResizable(true);
        this.frame.setLocationRelativeTo(null);// Center window
        this.mouse = new MouseInput();
        this.changeScene(Scene.SceneType.MODULE_SCENE);
    }

    @Override
    public synchronized void start(final Vector3dInterface state) {
        this.state = state.clone();
        this.mainGraphicsTh.set(new Thread(this, "Main Graphics"));
        this.mainGraphicsTh.get().setDaemon(true);
        this.mainGraphicsTh.get().start();
    }

    @Override
    public JFrame getFrame() {
        return this.frame;
    }

    @Override
    public MouseInput getMouse() {
        return this.mouse;
    }

    @Override
    public Scene getScene() {
        return this.currentScene;
    }

    @Override
    public void changeScene(Scene.SceneType scene) {
        switch (scene) {
            case MODULE_SCENE -> {
                this.currentScene = new ModuleScene();
            }
        }
        if (this.frame.getComponentCount() >= 1)
            this.frame.remove(this.currentScene);
        this.currentScene.init();
        this.currentScene.addMouseControl(this.mouse);
        this.currentScene.addMouseListener(this.mouse);
        this.currentScene.addMouseWheelListener(this.mouse);
        this.currentScene.addMouseMotionListener(this.mouse);
        this.frame.add(this.currentScene);
        this.frame.setVisible(true);
        this.frame.repaint();  //call fast the ui
        //optional
    }

    @Override
    public synchronized void update() {
        this.currentScene.update(this.state);
    }

    @Override
    public void run() {
        if (DEBUG) {
            frames++;
            double v = System.currentTimeMillis();
            if (v - t > 1000) {
                FPS = frames;
                System.out.println("FPS :: " + FPS);
                t = v;
                frames = 0;
            }
        }
        this.update();
        this.currentScene.repaint();
    }

    @Override
    public String toString() {
        return "GraphicsManager{" +
                "currentScene=" + currentScene.toString() +
                '}';
    }
}
