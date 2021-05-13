package group17.Graphics;

import group17.Graphics.Scenes.MouseInput;
import group17.Graphics.Scenes.Scene;
import group17.Graphics.Scenes.SimulationScene;
import group17.Graphics.Scenes.StartingScene;
import group17.Interfaces.GraphicsInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicReference;

import static group17.Config.DEBUG;
import static group17.Config.FPS;
import static group17.Main.simulationInstance;


public class GraphicsManager extends Canvas implements GraphicsInterface, Runnable {

    protected final AtomicReference<Thread> mainGraphicsTh = new AtomicReference<>();
    protected MouseInput mouse;
    protected Scene currentScene;
    int frames = 0;
    double t = System.currentTimeMillis();
    private JFrame frame;
    private WindowEvent listen;

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

    private void setWindowProperties() {
        final var closed = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                listen = new WindowEvent(frame, 201);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(listen);
                simulationInstance.stop();
                System.out.println("System closed by user");
                System.exit(0);
            }
        };
        this.frame.addWindowListener(closed);
        this.frame.setResizable(true);
        this.frame.setLocationRelativeTo(null);// Center window
        this.mouse = new MouseInput();
        this.changeScene(Scene.SceneType.STARTING_SCENE);
    }

    @Override
    public void start() {
        this.mainGraphicsTh.set(new Thread(Thread.currentThread().getThreadGroup(), this, "Main Graphics", 10));
        this.mainGraphicsTh.get().setDaemon(true);
        this.mainGraphicsTh.get().setPriority(7);
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
            case STARTING_SCENE -> this.currentScene = new StartingScene();
            case SIMULATION_SCENE -> this.currentScene = new SimulationScene();
        }
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
    public void update() {
        this.currentScene.update();
    }

    @Override
    public void reset() {

    }

    @Override
    public void stop() {
        try {
            this.mainGraphicsTh.get().join(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void run() {

        if (DEBUG) {
            frames++;
            double v = System.currentTimeMillis();
            if (v - t > 1000) {
                System.out.println("FPS :: " + frames);
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
                ", FPS=" + FPS +
                '}';
    }
}
