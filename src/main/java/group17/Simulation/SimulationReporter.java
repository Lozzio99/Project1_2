package group17.Simulation;

import group17.Interfaces.ReporterInterface;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static group17.Config.*;

public class SimulationReporter implements ReporterInterface {

    private Thread thread;
    private volatile Map<LocalDateTime, String> report, exceptions;


    @Override
    public synchronized void run() {
        this.report();
    }

    @Override
    public void init() {
        this.report = new HashMap<>();
        this.exceptions = new HashMap<>();
    }

    @Override
    public void start() {
        this.thread = new Thread(this, "Simulation Reporter");
        this.thread.setDaemon(true);
        this.thread.start();
    }

    @Override
    public void report() {
        for (Entry<LocalDateTime, String> s : this.report.entrySet()) {
            System.out.println("{ " + s + " }");
        }
        for (Entry<LocalDateTime, String> s : this.exceptions.entrySet()) {
            System.err.println("{ " + s + " }");
        }
        this.report.clear();
        this.exceptions.clear();
    }

    @Override
    public void report(String string) {
        this.report.put(LocalDateTime.now(), string);
    }

    @Override
    public void report(List<String> strings) {
        for (String s : strings)
            this.report.put(LocalDateTime.now(), s);
    }

    @Override
    public void report(Throwable e) {
        final String s = this.parseException(e.getMessage());
        this.exceptions.put(LocalDateTime.now(), s);
    }


    @Override
    public String parseException(String message) {
        if (message.startsWith("STOP")) {
            return "\tStopped simulation due to bad initialisation configuration";
        }
        if (message.startsWith("UPDATER"))
            if (message.substring(8)  // there's a slash /
                    .startsWith("SOLVER")) {
                int solver = Integer.parseInt(message.substring(message.length() - 1));
                String s = switch (solver) {
                    case RUNGE_KUTTA_SOLVER -> "RUNGE_KUTTA4TH";
                    case EULER_SOLVER -> "EULER";
                    case VERLET_VEL_SOLVER -> "VERLET_VEL";
                    case VERLET_STD_SOLVER -> "VERLET_STD";
                    default -> "";
                };
                return "\tMissing solver configuration or wrong level selected, select in range [1,4]\n ~ will switch to default :" + s;
            }
        return "EXCEPTION";
    }
}