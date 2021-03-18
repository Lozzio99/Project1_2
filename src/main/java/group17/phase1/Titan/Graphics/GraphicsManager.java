package group17.phase1.Titan.Graphics;


import group17.phase1.Titan.Graphics.Geometry.Point3D;
import group17.phase1.Titan.Graphics.Geometry.Point3DConverter;
import group17.phase1.Titan.Graphics.Geometry.Polygon3D;
import group17.phase1.Titan.Graphics.user.DialogFrame;
import group17.phase1.Titan.Graphics.user.MouseInput;
import group17.phase1.Titan.Main;
import org.lwjgl.system.CallbackI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GraphicsManager
{
    private final String FPS_RATE = "";
    private final Lock syncAssist = new ReentrantLock();
    private final AtomicReference <DialogFrame> assist = new AtomicReference<>(new DialogFrame());
    private MainThread engine;
    private JFrame frame;
    private MouseInput mouseInput;
    private SystemSimulationUpdater simulationUpdater;
    public static int WIDTH = 1480, HEIGHT = 810;
    static Dimension SCREEN = new Dimension(WIDTH,HEIGHT);
    private WindowEvent listen;


    public GraphicsManager()
    {
        Thread t = new Thread(()->
        {
            synchronized (syncAssist)
            {
                this.assist.get().init();
                this.assist.get().appendToOutput("Please insert Coordinates");
            }
        });
        t.start();
    }



    //wait for the assist frame to give the start
    public void waitForStart()
    {
        while (!assist.get().isStarted()){
            /*take coordinates or whatever*/
            assist.get().getLaunchX();

        }
        Thread th = new Thread(this.engine);
        th.start();
    }


    public void init()
    {
        this.frame = new JFrame(FPS_RATE);
        this.frame.setSize(SCREEN);
        setWindowProperties();
        this.engine = createEngine();
    }


    private MainThread createEngine()
    {
        Container cp =this.frame.getContentPane();
        MainThread engine = new MainThread(this.simulationUpdater = new SystemSimulationUpdater());
        this.simulationUpdater.addMouseControl(this.mouseInput = new MouseInput());
        this.frame.addMouseListener(this.mouseInput);
        this.frame.addMouseWheelListener(this.mouseInput);
        this.frame.addMouseMotionListener(this.mouseInput);
        cp.add(engine);
        return engine;

    }


    private void setWindowProperties() {
        WindowAdapter closed = new WindowAdapter()
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
    }

    private class MainThread extends JPanel implements Runnable {

        private final SystemSimulationUpdater visualization;

        public MainThread(SystemSimulationUpdater visualization) {
            this.visualization = visualization;
            this.setSize(SCREEN);
            this.setEnabled(true);
            this.setFocusable(true);
        }

        @Override
        protected void paintComponent(Graphics graphics)
        {
            graphics.setColor(Color.black);
            graphics.fillRect(0,0,GraphicsManager.WIDTH,GraphicsManager.HEIGHT);
            visualization.paint(graphics);
        }

        @Override
        public void run()
        {
            long lastTime = System.nanoTime();
            long timerTitle = System.currentTimeMillis();
            double elapsedTime = 0.0;
            double nanosecond = 1_000_000_000d / 60;
            double frames = 0;
            this.visualization.startSimulation();
            while (true) {
                long now = System.nanoTime();
                elapsedTime += ((now - lastTime) / nanosecond);
                lastTime = now;

                if (elapsedTime >= 1)
                {
                    synchronized (syncAssist)
                    {
                        visualization.update();
                        repaint();
                        if (System.currentTimeMillis() - timerTitle > 1000) {
                            assist.get().setOutput(Main.simulation.toString());
                            timerTitle += 1000;
                        }
                    }
                    elapsedTime--;
                    frames++;
                }
                //sleep();
            }

        }

        private void sleep() {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}


