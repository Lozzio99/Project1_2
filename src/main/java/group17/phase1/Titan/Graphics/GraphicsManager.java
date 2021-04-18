package group17.phase1.Titan.Graphics;

import group17.phase1.Titan.Graphics.Scenes.MouseInput;
import group17.phase1.Titan.Graphics.Scenes.Scene;
import group17.phase1.Titan.Graphics.Scenes.SimulationScene;
import group17.phase1.Titan.Graphics.Scenes.StartingScene;
import group17.phase1.Titan.Interfaces.GraphicsInterface;
import group17.phase1.Titan.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicReference;

import static group17.phase1.Titan.Config.FPS;


public class GraphicsManager extends Canvas implements GraphicsInterface, Runnable {

    private JFrame frame;
    private WindowEvent listen;
    private MouseInput mouse;
    private Scene currentScene;
    private boolean running;
    private final AtomicReference<Thread> main = new AtomicReference<>();


    @Override
    public void init() {
        this.frame = new JFrame("Solar System ");
        this.frame.setSize(screen);
        this.frame.add(this);
        this.setWindowProperties();
    }

    private void setWindowProperties() {
        final var closed = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                listen = new WindowEvent(frame, 201);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(listen);
                Main.simulation.stop();
                System.out.println("System closed by user");
                System.exit(0);
            }
        };
        this.frame.addWindowListener(closed);
        this.frame.setResizable(true);
        this.frame.setLocationRelativeTo(null);// Center window
        this.mouse = new MouseInput();
        this.changeScene(Scene.SceneType.STARTING_SCENE);
        this.frame.setVisible(true);
    }

    @Override
    public void launch() {
        this.main.set(new Thread(this, "Main Graphics"));
        this.main.get().setDaemon(true);
        this.running = true;
        this.main.get().start();
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
        /*
        this.currentScene.revalidate();
        this.frame.repaint();
        */
        this.frame.setVisible(true);
    }

    @Override
    public void update() {
        this.currentScene.update();
    }

    @Override
    public void stop() {
        try {
            this.main.get().join(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / FPS;
        double delta = 0;
        int frames = 0;

        while (this.running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                this.update();
                this.currentScene.repaint();
                delta--;
                frames++;
            }
            if (System.currentTimeMillis() - timer > 1000) {
                this.frame.setTitle("Solar System " + " | " + frames + " FPS");
                frames = 0;
                timer += 1000;
            }
        }
    }

    @Override
    public String toString() {
        return "GraphicsManager{" +
                "currentScene=" + currentScene.toString() +
                ", running=" + running +
                ", FPS=" + FPS +
                '}';
    }
}
