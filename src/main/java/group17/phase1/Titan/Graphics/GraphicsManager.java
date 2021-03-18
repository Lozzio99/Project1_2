package group17.phase1.Titan.Graphics;


import group17.phase1.Titan.Graphics.user.DialogFrame;
import group17.phase1.Titan.Graphics.user.MouseInput;
import group17.phase1.Titan.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

    }


    public AtomicReference<DialogFrame> getAssist() {
        return this.assist;
    }

    //wait for the assist frame to give the start
    public void waitForStart()
    {
        while (!assist.get().isStarted()){
            /*take coordinates or whatever*/
            if (assist.get().getLaunchVelocityX()!= 0)
                Main.simulation.getBody("PROBE").getVectorVelocity().setX(assist.get().getLaunchVelocityX());

            if (assist.get().getLaunchVelocityY()!= 0)
            Main.simulation.getBody("PROBE").getVectorVelocity().setY(Main.simulation.getGraphicsManager().getAssist().get().getLaunchVelocityY());

            if (assist.get().getLaunchVelocityZ()!= 0)
                Main.simulation.getBody("PROBE").getVectorVelocity().setZ(Main.simulation.getGraphicsManager().getAssist().get().getLaunchVelocityZ());
        }
        Thread th = new Thread(this.engine);
        th.start();
    }


    public void init()
    {
        Thread t = new Thread(()->
        {
            synchronized (syncAssist)
            {
                this.assist.get().init();
                this.assist.get().appendToOutput("Those are the starting coordinates : ");
                this.assist.get().appendToOutput(Main.simulation.getSolarSystemRepository().getCelestialBodies().get(11).getVectorLocation().toString());
                this.assist.get().appendToOutput("This is the starting velocity:");
                this.assist.get().appendToOutput(Main.simulation.getSolarSystemRepository().getCelestialBodies().get(11).getVelocityVector().toString());
                this.assist.get().appendToOutput("If you want to change the starting velocity\n" +
                        " you can increase / decrease the sliders");
                this.assist.get().appendToOutput("If you want to change the starting position\n" +
                        " you can plug in the desired values");
                this.assist.get().appendToOutput("If you trust our shoot then just START SIMULATION :=)");
            }
        });
        t.start();
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


