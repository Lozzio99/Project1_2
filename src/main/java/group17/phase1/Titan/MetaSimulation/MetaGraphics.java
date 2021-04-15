package group17.phase1.Titan.MetaSimulation;

import group17.phase1.Titan.Graphics.DialogFrame;
import group17.phase1.Titan.Graphics.GraphicsManager;
import group17.phase1.Titan.Graphics.MouseInput;
import group17.phase1.Titan.Graphics.Scenes.Scene;
import group17.phase1.Titan.Main;
import group17.phase1.Titan.interfaces.GraphicsInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

public class MetaGraphics extends Canvas implements GraphicsInterface, Runnable
{

    private final JFrame frame;
    private JFrame dialogFrame ;
    private final MetaScene scene;
    private final String title = "META SIMULATION";
    private final double fps = 60;
    private boolean running;
    private JButton startButton;
    private final MouseInput mouse;

    public MetaGraphics()
    {
        this.frame = new JFrame();
        this.scene = new MetaScene();
        this.setSize(GraphicsManager.screen);
        this.frame.setSize(this.getSize());
        this.frame.setTitle(title);
        this.mouse = new MouseInput();
        this.addMouseListener(this.mouse);
        this.addMouseMotionListener(this.mouse);
        this.addMouseWheelListener(this.mouse);
        final var closed = new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                WindowEvent listen = new WindowEvent(frame, 201);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(listen);
                System.out.println("System closed by user");
                System.exit(0);
            }
        };
        this.frame.addWindowListener(closed);
        this.frame.setVisible(true);
        this.frame.add(this);
        this.initializeDialog();

    }
    @Override
    public void launch() {
        Thread t = new Thread(this,"Graphics");
        running = true;
        changeScene(Scene.SceneType.SIMULATION_SCENE);
        t.start();
    }

    @Override
    public AtomicReference<DialogFrame> getAssistFrame()
    {
        return null;
    }

    @Override
    public void changeScene(Scene.SceneType scene) {
        switch (scene){
            case SIMULATION_SCENE -> {
                this.scene.setMouse(this.mouse);
                this.scene.started = true;
            }
        }
    }

    @Override
    public Semaphore getSemaphore() {
        return null;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / fps;
        double delta = 0;
        int frames = 0;

        while (this.running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                this.render();
                this.update();
                this.frame.repaint();
                delta--;
                frames++;
            }
            if (System.currentTimeMillis() - timer > 1000) {
                this.frame.setTitle(title + " | " + frames + " fps");
                frames = 0;
                timer += 1000;
            }
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth() , this.getHeight() );
        this.scene.render(g);

        g.dispose();
        bs.show();

    }

    private void update()
    {
        Main.simulation.step();
        this.scene.update();
    }

    private void initializeDialog(){
        this.dialogFrame = new JFrame();
        this.dialogFrame.addWindowListener(frame.getWindowListeners()[0]);
        this.startButton = new JButton("start");
        this.startButton.addActionListener(e -> launch());
        this.dialogFrame.setLocationRelativeTo(this.frame);
        this.dialogFrame.setSize(200,200);
        this.dialogFrame.add(startButton);
        this.dialogFrame.setVisible(true);
    }


}
