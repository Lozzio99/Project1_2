package group17.phase1.Titan.Simulations.RocketSimulation.Graphics;

import group17.phase1.Titan.Graphics.GraphicsManager;
import group17.phase1.Titan.Graphics.Scenes.Scene;

import static group17.phase1.Titan.Config.FPS;
import static group17.phase1.Titan.Main.simulation;

public class RocketGraphics extends GraphicsManager {
    @Override
    public void changeScene(Scene.SceneType scene) {
        this.currentScene = new RocketScene();
        this.getFrame().remove(this.currentScene);
        this.currentScene.init();
        this.currentScene.addMouseControl(this.mouse);
        this.currentScene.addMouseListener(this.mouse);
        this.currentScene.addMouseWheelListener(this.mouse);
        this.currentScene.addMouseMotionListener(this.mouse);
        this.getFrame().add(this.currentScene);
        this.getFrame().setVisible(true);
    }

    @Override
    public void update() {
        this.currentScene.update();
        simulation.system().getRocket().update();
    }

    @Override
    public void launch() {
        this.mainGraphicsTh.set(new Thread(this, "Main Graphics"));
        this.mainGraphicsTh.get().setDaemon(true);
        simulation.setRunning();
        this.mainGraphicsTh.get().start();
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
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / FPS;
        double delta = 0;
        int frames = 0;

        while (simulation.running()) {
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
                this.getFrame().setTitle("Rocket System " + " | " + frames + " FPS");
                frames = 0;
                timer += 1000;
            }
        }
    }
}
