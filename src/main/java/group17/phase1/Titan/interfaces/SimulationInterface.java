package group17.phase1.Titan.Interfaces;


import group17.phase1.Titan.Graphics.DialogFrame;
import group17.phase1.Titan.Physics.Bodies.CelestialBody;
import group17.phase1.Titan.Simulation.SimulationUpdater;

import java.util.concurrent.atomic.AtomicReference;

public interface SimulationInterface {
    void start();

    void startUpdater();

    void reset();

    void stop();

    SystemInterface system();

    AtomicReference<GraphicsInterface> graphics();

    DialogFrame assist();

    SimulationUpdater updater();

    void initCPU(int cpu);

    void initSystem();

    void initGraphics(boolean graphics, boolean assist);

    CelestialBody getBody(String probe);

}
