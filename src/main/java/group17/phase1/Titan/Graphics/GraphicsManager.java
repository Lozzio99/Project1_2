package group17.phase1.Titan.Graphics;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicReference;

public class GraphicsManager
{
    private final String FPS_RATE = "";
    private final AtomicReference <DialogFrame> assist = new AtomicReference<>(new DialogFrame());
    private final MainThread engine;
    private final JFrame frame;
    private MouseInput mouseInput;
    private SystemSimulationUpdater simulationUpdater;
    public static int WIDTH = 1480, HEIGHT = 810;
    static Dimension SCREEN = new Dimension(WIDTH,HEIGHT);
    private WindowEvent listen;


    public GraphicsManager()
    {
        Thread t = new Thread(()-> this.assist.get().init());
        t.start();
        this.frame = new JFrame(FPS_RATE);
        this.frame.setSize(SCREEN);
        setWindowProperties();
        engine = createEngine();
    }

    private MainThread createEngine()
    {
        Container cp =frame.getContentPane();
        MainThread engine = new MainThread(this.simulationUpdater = new SystemSimulationUpdater());
        this.simulationUpdater.addMouseControl(this.mouseInput = new MouseInput());
        this.frame.addMouseListener(mouseInput);
        this.frame.addMouseWheelListener(mouseInput);
        this.frame.addMouseMotionListener(mouseInput);
        cp.add(engine);
        return engine;
    }

    public void startMainThread()
    {
        Thread th = new Thread(this.engine);
        th.start();
    }

    public SystemSimulationUpdater getSimulationUpdater() {
        return this.simulationUpdater;
    }

    private void setWindowProperties() {
        WindowAdapter closed = new WindowAdapter()
        {
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
            updateUI();
        }

        @Override
        public void run()
        {
            long lastTime = System.nanoTime();
            double elapsedTime = 0.0;
            double FPS = 120.0;
            this.visualization.startSimulation();
            while (true) {
                long now = System.nanoTime();
                elapsedTime += ((now - lastTime) / 1_000_000_000d) * FPS;
                lastTime = System.nanoTime();

                if (elapsedTime >= 1) {
                    visualization.update();
                    repaint();
                    elapsedTime--;
                }
                sleep();
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


