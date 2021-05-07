package group17.Simulation;

import group17.Interfaces.UpdaterInterface;

public class SimulationUpdater implements UpdaterInterface {

    private RocketSchedule schedule;
    private Thread updaterThread;

    @Override
    public void init() {
        this.schedule = new RocketSchedule();
        //make all tha planning

    }

    public void start() {
        this.updaterThread = new Thread(this, "Simulation Updater");
        this.updaterThread.setDaemon(true);
        this.updaterThread.start();
    }


    @Override
    public void run() {
        // ROCKET DECISION

    }
}
