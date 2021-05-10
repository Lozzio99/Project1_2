package group17.Interfaces;


import group17.Graphics.AssistFrame;
import group17.Simulation.SimulationReporter;

public interface SimulationInterface {


    // Updater
    UpdaterInterface getUpdater();
    void initUpdater();
    void startUpdater();


    //Graphics
    GraphicsInterface getGraphics();
    void initGraphics();
    void startGraphics();


    //Assist
    AssistFrame getAssist();

    void initAssist();

    void startAssist();


    //System
    SystemInterface getSystem();

    void setAssist(AssistFrame assist);

    void initSystem();

    void startSystem();


    SimulationReporter getReporter();

    void initReporter();

    void startReport();


    // simulationInstance actions
    //cpu level
    //configs
    void init();

    void start();

    void reset();

    void loop();

    void stop();

    boolean running();

    void setRunning();

    boolean waiting();  // waiting for assist to start

    void setWaiting(boolean isWaiting);

    void setStopped(boolean stopped);

    void updateState();
}
