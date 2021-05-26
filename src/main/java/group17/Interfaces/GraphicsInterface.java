package group17.Interfaces;


import Demo.Scenes.MouseInput;
import Demo.Scenes.Scene;

import javax.swing.*;
import java.awt.*;

/**
 * The interface Graphics interface.
 */
public interface GraphicsInterface {
    /**
     * The constant screen.
     */
    Dimension screen = new Dimension(1480, 810);


    /**
     * Gets frame.
     *
     * @return the frame
     */
    JFrame getFrame();

    /**
     * Gets mouse.
     *
     * @return the mouse
     */
    MouseInput getMouse();

    /**
     * Gets scene.
     *
     * @return the scene
     */
    Scene getScene();


    /**
     * Init.
     */
    void init();

    /**
     * Start.
     */
    void start();

    /**
     * Update.
     */
    void update();

    /**
     * Reset.
     */
    void reset();

    /**
     * Stop.
     */
    void stop();

    /**
     * Change scene.
     *
     * @param scene the scene
     */
    void changeScene(Scene.SceneType scene);

}
