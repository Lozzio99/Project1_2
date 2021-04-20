package group17.phase1.Titan.Interfaces;


import group17.phase1.Titan.Graphics.Scenes.Scene;

import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public interface GraphicsInterface {

    /**
     * Fixed screen size for main Graphics,
     * Accessed in Point3dConverter for dimension scaling
     */
    Dimension screen = new Dimension(1480, 810);

    /**
     * initializes the main frame with subsequent call to changeScene,
     * all of this will take to a suspended state ready to be launched
     */
    void init();

    /**
     * Start the main graphics thread, set it to Daemon -
     * this will lead to the running state of scene
     */
    void launch();

    /**
     * Remove the previous scene from the frame and add the new one
     * may take some time therefore if accessed from another thread can lead to
     * a bad race condition
     *
     * @param scene the new scene to be displayed
     */
    void changeScene(Scene.SceneType scene);

    /**
     * Updates the state of the current scene, included mouse events,
     * all scene modifications happen here
     */
    void update();

    /**
     * Shut down all threads, included the awt event queue and
     * exit from code with status 0
     */
    void stop();


    boolean running();


    void setRunning();


    AtomicBoolean waiting();

}
