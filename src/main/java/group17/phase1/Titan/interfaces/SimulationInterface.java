package group17.phase1.Titan.Interfaces;


import group17.phase1.Titan.Graphics.DialogFrame;
import group17.phase1.Titan.Physics.Bodies.CelestialBody;

public interface SimulationInterface {
    void start();

    void reset();

    void stop();

    SystemInterface system();

    GraphicsInterface graphics();

    DialogFrame assist();

    void initCPU(int cpu);

    void initSystem();

    void initGraphics(boolean graphics, boolean assist);

    CelestialBody getBody(String probe);

}
