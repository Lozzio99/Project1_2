package group17.Interfaces;


import group17.Graphics.Assist.LaunchAssistWindow;
import group17.Graphics.UserDialogWindow;
import group17.Simulation.SimulationReporter;

/**
 * The interface Simulation interface.
 */
public interface SimulationInterface {


    /**
     * Gets updater.
     *
     * @return the updater
     */
// Updater
    UpdaterInterface getUpdater();

    /**
     * Init updater.
     */
    void initUpdater();

    /**
     * Start updater.
     */
    void startUpdater();


    /**
     * Gets graphics.
     *
     * @return the graphics
     */
//Graphics
    GraphicsInterface getGraphics();

    /**
     * Init graphics.
     */
    void initGraphics();

    /**
     * Start graphics.
     */
    void startGraphics();


    /**
     * Gets assist.
     *
     * @return the assist
     */
//Assist
    LaunchAssistWindow getAssist();

    /**
     * Sets assist.
     *
     * @param assist the assist
     */
    void setAssist(UserDialogWindow assist);

    /**
     * Init assist.
     */
    void initAssist();

    /**
     * Start assist.
     */
    void startAssist();

    /**
     * Gets system.
     *
     * @return the system
     */
//System
    SystemInterface getSystem();

    /**
     * Init system.
     */
    void initSystem();

    /**
     * Start system.
     */
    void startSystem();


    /**
     * Gets reporter.
     *
     * @return the reporter
     */
    SimulationReporter getReporter();

    /**
     * Init reporter.
     */
    void initReporter();

    /**
     * Start report.
     */
    void startReport();


    /**
     * Init.
     */
// simulation actions
    //cpu level
    //configs
    void init();

    /**
     * Start.
     */
    void start();

    /**
     * Reset.
     */
    void reset();

    /**
     * Loop.
     */
    void loop();

    /**
     * Stop.
     */
    void stop();

    /**
     * Running boolean.
     *
     * @return the boolean
     */
    boolean running();

    /**
     * Sets running.
     */
    void setRunning();

    /**
     * Waiting boolean.
     *
     * @return the boolean
     */
    boolean waiting();  // waiting for userDialog to start

    /**
     * Sets waiting.
     *
     * @param isWaiting the is waiting
     */
    void setWaiting(boolean isWaiting);

    /**
     * Sets stopped.
     *
     * @param stopped the stopped
     */
    void setStopped(boolean stopped);

    /**
     * Update state.
     */
    void updateState();
}
