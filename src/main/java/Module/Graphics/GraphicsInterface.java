package Module.Graphics;

import javax.swing.*;
import java.awt.*;

public interface GraphicsInterface extends Runnable {
    Dimension screen = new Dimension(1480, 810);

    void init();

    void start();

    JFrame getFrame();

    MouseInput getMouse();

    Scene getScene();

    void changeScene(Scene.SceneType type);

    void update();
}
