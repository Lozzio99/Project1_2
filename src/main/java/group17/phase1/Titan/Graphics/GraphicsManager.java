package group17.phase1.Titan.Graphics;

import group17.phase1.Titan.Graphics.Scenes.Scene;
import group17.phase1.Titan.Graphics.Scenes.SimulationScene;
import group17.phase1.Titan.Graphics.Scenes.StartingScene;
import group17.phase1.Titan.Graphics.Scenes.Trajectories;
import group17.phase1.Titan.interfaces.GraphicsInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

import static group17.phase1.Titan.Main.simulation;


public class GraphicsManager extends Canvas implements GraphicsInterface
{
    private final JFrame frame;
    public final static Dimension screen = new Dimension(1480,810);
    private WindowEvent listen;
    private MouseInput mouse;
    private Scene currentScene;
    private boolean running;
    private final AtomicReference<Thread> main = new AtomicReference<>();
    private final AtomicReference<Thread> assist = new AtomicReference<>();
    private final AtomicReference<DialogFrame> assistFrame;
    final double FPS = 60;

    private final Semaphore semaphore;

    public GraphicsManager()
    {
        this.frame = new JFrame("Solar System ");
        this.semaphore = new Semaphore(0);
        this.frame.setSize(screen);
        this.assistFrame = new AtomicReference<>(new DialogFrame());
        this.currentScene = new StartingScene();
        this.currentScene.init();
        this.frame.add(this);
        this.frame.add(this.currentScene);
        this.setWindowProperties();
    }


    public AtomicReference<DialogFrame> getAssistFrame(){
        return this.assistFrame;
    }

    @Override
    public synchronized void launch()  {
        this.main.set(new Thread(() -> {
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
                    this.semaphore.release();
                    delta--;
                    frames++;
                }
                if (System.currentTimeMillis() - timer > 1000) {
                    this.frame.setTitle("Solar System " + " | " + frames + " FPS");
                    frames = 0;
                    timer += 1000;
                }
            }
        }, "Main Graphics"));

        this.assist.set(new Thread(() -> {
            long lastTime = System.nanoTime();
            long timer = System.currentTimeMillis();
            final double ns = 1000000000.0 / FPS;
            double delta = 0;

            while (this.running) {
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;
                while (delta >= 1) {
                    if (this.assistFrame.get().isStopped()) {
                        this.assistFrame.get().showAssistParameters();
                        while (this.assistFrame.get().isStopped()) {

                        }
                        this.assistFrame.get().acquireData();
                    } else {
                        this.semaphore.acquireUninterruptibly();
                        System.out.println("step");
                        simulation.step();
                        this.semaphore.drainPermits();
                    }
                    delta--;
                }
                if (System.currentTimeMillis() - timer > 1000) {
                    if (!this.assistFrame.get().isStopped()) {
                        this.assistFrame.get().setOutput(simulation.toString());
                        timer += 1000;
                    }
                }
            }
        }, "Dialog Frame"));

        this.main.get().setDaemon(true);
        this.assist.get().setDaemon(true);
        this.running = true;
        this.main.get().start();
        this.assist.get().start();
    }

    private void setWindowProperties() {
        final var closed = new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                listen = new WindowEvent(frame, 201);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(listen);
                System.out.println("System closed by user");
                System.exit(0);
            }
        };
        this.frame.addWindowListener(closed);
        this.frame.setResizable(true);
        this.frame.setVisible(true);
        this.frame.setLocationRelativeTo(null);// Center window
        this.mouse = new MouseInput();
        this.currentScene.addMouseControl(this.mouse);
        this.currentScene.addMouseListener(this.mouse);
        this.currentScene.addMouseWheelListener(this.mouse);
        this.currentScene.addMouseMotionListener(this.mouse);
    }

    private void update()
    {
        this.currentScene.update();
        this.currentScene.resetMouse();
    }


    public void changeScene(Scene.SceneType scene)
    {
        switch (scene){
            case STARTING_SCENE -> this.currentScene = new StartingScene();
            case SIMULATION_SCENE -> this.currentScene = new SimulationScene();
            case TRAJECTORIES -> this.currentScene = new Trajectories();
        }
        this.currentScene.addMouseControl(this.mouse);
        this.frame.add(this.currentScene);
        this.currentScene.revalidate();
        this.frame.repaint();
        this.currentScene.init();
    }

    @Override
    public Semaphore getSemaphore() {
        return this.semaphore;
    }

}
