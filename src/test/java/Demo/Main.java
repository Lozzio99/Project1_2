package Demo;

import group17.phase1.Titan.Graphics.Renderer.RenderableShapeInterface;
import group17.phase1.Titan.Graphics.Renderer.geometry.SphereMesh3D;
import group17.phase1.Titan.Physics.Trajectories.Forces.Vector3D;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main
{
    public static void main(String[] args) {
            Demo display = new Demo();
    }

    public static class Demo extends Canvas implements Runnable {

        public static final int WIDTH = 1180;
        public static final int HEIGHT = 760;
        static final Lock sync = new ReentrantLock();
        private static boolean running = false;
        private final JFrame frame;
        private final DemoShape organizer;
        private Thread display;

        public Demo() {
            this.frame = new JFrame();
            Dimension size = new Dimension(WIDTH, HEIGHT);
            this.setPreferredSize(size);
            this.organizer = new DemoShape();
            this.frame.add(this);
            this.frame.pack();
            this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.frame.setLocationRelativeTo(null);
            this.frame.setResizable(false);
            this.frame.setVisible(true);
            this.start();
        }


        @Override
        public void run() {
            long lastTime = System.nanoTime();
            long timer = System.currentTimeMillis();
            final double ns = 1000000000.0 / 65.;
            double delta = 0;
            int frames = 0;
            this.organizer.init();

            while (running) {
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;
                while (delta >= 1) {
                    synchronized (sync){
                        update();
                        render();
                    }
                    delta--;
                    frames++;
                }

                if (System.currentTimeMillis() - timer > 1000) {
                    timer += 1000;
                    this.frame.setTitle("- " + frames + " fps");
                    frames = 0;
                }
            }

        }

        private void render() {
            BufferStrategy bs = this.getBufferStrategy();
            if (bs == null) {
                this.createBufferStrategy(5);
                return;
            }

            Graphics g = bs.getDrawGraphics();

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, WIDTH * 2, HEIGHT * 2);

            this.organizer.render(g);

            g.dispose();
            bs.show();
        }

        private void update() {
            this.organizer.update();
        }

        private void start() {
            running = true;
            this.display = new Thread(this, "Display");
            this.display.start();
        }

        public static class DemoShape {
            ArrayList<RenderableShapeInterface> shape;
            Vector3D light;

            DemoShape() {
                this.shape = new ArrayList<>();
            }

            public void init() {
                this.shape.add(SphereMesh3D.createSphere(new Color(196, 20, 20), 100, 0, 0, 50));
                this.shape.add(SphereMesh3D.createSphere(Color.BLUE, 50, 0, 300, 50));
                this.light = new Vector3D(0, 0, 1);
                for (RenderableShapeInterface s : shape) {
                    s.setLighting(light);
                    s.translate(0, 0, 300);
                }
            }

            public void render(Graphics g) {
                for (RenderableShapeInterface s : shape)
                    s.render(g);
            }

            public void update() {
                this.shape.get(0).rotate(true, 1, 1, 0, light);
                this.shape.get(1).rotate(true, 0, 1, 1, light);

            }
        }
    }
}
