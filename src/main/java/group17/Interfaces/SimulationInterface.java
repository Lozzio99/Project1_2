package group17.Interfaces;


import group17.Graphics.Assist.LaunchAssist;
import group17.Graphics.Assist.UserDialogWindow;
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
    LaunchAssist getAssist();

    void setAssist(UserDialogWindow assist);

    void initAssist();

    void startAssist();

    //System
    SystemInterface getSystem();

    void initSystem();

    void startSystem();


    SimulationReporter getReporter();

    void initReporter();

    void startReport();


    // simulation actions
    //cpu level
    //configs
    void init();

    void start();

    void reset();

    void loop();

    void stop();

    boolean running();

    void setRunning();

    boolean waiting();  // waiting for userDialog to start

    void setWaiting(boolean isWaiting);

    void setStopped(boolean stopped);

    void updateState();
}
