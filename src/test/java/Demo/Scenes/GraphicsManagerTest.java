package Demo.Scenes;

import group17.Graphics.GraphicsManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class GraphicsManagerTest extends GraphicsManager {
    public GraphicsManagerTest() {
        this.init();
        this.start();
    }


    @DisplayName("Graphics demo")
    @Test
    public static void main(String[] args) {
        new GraphicsManagerTest();
    }

    @Override
    public void setWindowProperties() {
        this.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mouse = new MouseInput();
        this.changeScene(null);
        JButton j = new JButton("Change Scene");
        j.addActionListener(e -> {
            changeScene(null);
        });
        this.getFrame().getContentPane().add(j, BorderLayout.NORTH);
    }

    @Override
    public void start() {
        ScheduledExecutorService e = Executors.newSingleThreadScheduledExecutor();
        e.scheduleWithFixedDelay(() -> {
            this.currentScene.update();
            this.currentScene.repaint();
        }, 30, 5, TimeUnit.MILLISECONDS);
    }

    @Override
    public void changeScene(Scene.SceneType nullValue) {
        this.currentScene = new SceneTest();
        this.getFrame().remove(this.currentScene);
        this.currentScene.init();
        this.currentScene.addMouseControl(this.mouse);
        this.currentScene.addMouseListener(this.mouse);
        this.currentScene.addMouseWheelListener(this.mouse);
        this.currentScene.addMouseMotionListener(this.mouse);
        this.getFrame().add(this.currentScene);
        this.getFrame().setVisible(true);
    }

}