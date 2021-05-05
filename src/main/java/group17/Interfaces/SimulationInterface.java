package group17.Interfaces;


import group17.Graphics.DialogFrame;

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
    DialogFrame getAssist();

    void initAssist();


    //System
    SystemInterface getSystem();

    void initSystem();

    void startSystem();


    // simulationInstance actions
    //cpu level
    //configs
    void init();

    void reset();

    void stop();

    boolean running();

    void setRunning();

    boolean waiting();  // waiting for assist to start

    void setWaiting(boolean isWaiting);

}
