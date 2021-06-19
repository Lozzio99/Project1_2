package phase3.Graphics;

import phase3.Graphics.Scenes.Scene;
import phase3.Math.ADT.Vector3dInterface;
import phase3.System.State.StateInterface;

import javax.swing.*;
import java.awt.*;

public interface GraphicsInterface extends Runnable {
    Dimension screen = new Dimension(1480, 810);

    void init();

    void start(StateInterface<Vector3dInterface> state);

    JFrame getFrame();
    Scene getScene();

    void changeScene(Scene.SceneType type);

    void update();
}
