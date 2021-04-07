package group17.phase1.Titan.interfaces;

import group17.phase1.Titan.Graphics.DialogFrame;
import group17.phase1.Titan.Graphics.Scenes.Scene;

import java.util.concurrent.atomic.AtomicReference;

public interface GraphicsInterface
{
    void launch();

    AtomicReference<DialogFrame> getAssistFrame();

    void changeScene(Scene.SceneType scene);
}
