package group17.Interfaces;


import group17.Graphics.Scenes.MouseInput;
import group17.Graphics.Scenes.Scene;

import javax.swing.*;
import java.awt.*;

public interface GraphicsInterface {
    Dimension screen = new Dimension(1480, 810);


    // Graphics Objects
    JFrame getFrame();

    MouseInput getMouse();

    Scene getScene();


    // Simulation actions
    void init();

    void start();

    void update();

    void reset();

    void stop();

    void changeScene(Scene.SceneType scene);

}
