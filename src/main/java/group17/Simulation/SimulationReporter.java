package group17.Simulation;

import group17.Interfaces.ReporterInterface;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SimulationReporter implements ReporterInterface {

    private Thread thread;
    private volatile ConcurrentMap<LocalDate, String> report;


    @Override
    public void run() {
        this.report();
    }

    @Override
    public void init() {
        this.report = new ConcurrentHashMap<>();
    }

    @Override
    public void start() {
        this.thread = new Thread(this, "Simulation Reporter");
        this.thread.setDaemon(true);
        this.thread.start();
    }

    @Override
    public void report() {
        for (LocalDate s : this.report.keySet()) {
            System.out.println(this.report.remove(s));
        }
    }

    @Override
    public void report(String string) {
        this.report.put(LocalDate.now(), string);
    }

    @Override
    public void report(List<String> strings) {
        for (String s : strings)
            this.report.put(LocalDate.now(), s);
    }
}
