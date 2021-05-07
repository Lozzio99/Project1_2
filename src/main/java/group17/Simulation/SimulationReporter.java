package group17.Simulation;

import group17.Interfaces.ReporterInterface;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SimulationReporter implements ReporterInterface {

    private Thread thread;
    private volatile ConcurrentMap<LocalDateTime, String> report;


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
        for (LocalDateTime s : this.report.keySet()) {
            System.out.print("{ " + s + "} - " + this.report.remove(s));
        }
    }

    @Override
    public void report(String string) {
        System.out.println(LocalDate.now());
        this.report.put(LocalDateTime.now(), string);
    }

    @Override
    public void report(List<String> strings) {
        for (String s : strings)
            this.report.put(LocalDateTime.now(), s);
    }
}
