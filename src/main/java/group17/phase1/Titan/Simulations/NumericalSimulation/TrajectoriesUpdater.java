package group17.phase1.Titan.Simulations.NumericalSimulation;

import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Simulations.SolarSystemSimulation.SimulationUpdater;

import static group17.phase1.Titan.Config.REPORT;
import static group17.phase1.Titan.Main.simulation;

public class TrajectoriesUpdater extends SimulationUpdater {

    private StateInterface[] states;
    private TrajectoryErrorCalc trajectoryErrorCalc;

    private int monthCount = 0;
    private int monthIndex = 4;     // Start on April


    public TrajectoriesUpdater() {

        super("Trajectories");
        this.isKilled = false;
    }

    @Override
    public void run() {
        //one day =
        //start from april
        int[] monthdays = new int[]{30, 31, 30, 31, 31, 30, 31, 30, 31, 31, 28, 31};

        if (REPORT) {
            trajectoryErrorCalc = new TrajectoryErrorCalc();
            //TODO : insert MOON
            for (int i = 0; i < simulation.system().getCelestialBodies().size() - 1; i++) {
                trajectoryErrorCalc.fillSimulation(i, monthCount, 0, simulation.system().getCelestialBodies().get(i).getVectorLocation());
                trajectoryErrorCalc.fillSimulation(i, monthCount, 1, simulation.system().getCelestialBodies().get(i).getVectorVelocity());
            }
        }

        this.states = new StateInterface[13];
        this.states[0] = simulation.system().systemState();
        if (REPORT) {
            updateTrajectoryLog(this.states[0]);
        }
        for (int i = 1; i < 13; i++) {
            StateInterface[] state = new StateInterface[0];
            for (int k = 0; k < monthdays[i - 1]; k++) {
                int daySec = 86400; //60sec * 60min * 24h
                double[] ts = new double[daySec];
                for (int s = 0; s < daySec; s++) {
                    ts[s] = s; //step 1 sec
                }
                state = simulation.system().solver().solve(simulation.system().solver().getFunction(), simulation.system().systemState(), ts);
            }
            this.states[i] = state[state.length - 1];
            if (REPORT) {
                updateTrajectoryLog(this.states[i]);
            }
        }
        if (REPORT) {
            trajectoryErrorCalc.exportFile();
            System.out.println("Error calculation finished and exported to resources/trajectoryData");
            trajectoryErrorCalc.printErrors();
        }
        this.isKilled = true;
    }

    @Override
    public void tryStop() {
        super.tryStop();
    }

    @Override
    public void launch() {
        super.launch();
    }

    @Override
    public StateInterface[] getStates() {
        return states;
    }

    /**
     * Determines a start of a new month and if it is the case, the method writes the values into the log
     * of the TrajectoryErrorCalc class.
     */
    public void updateTrajectoryLog(StateInterface s) {
        // System.out.println("Month: " + clock.getMonth());
        monthIndex = simulation.system().getClock().getMonths();
        fillValues(s);
        monthCount++;
        // Fill in the values of each body:
        System.out.println(simulation.system().getClock().monthString() + " ( " + simulation.system().getClock().getMonths() + " )");
        System.out.println(simulation.system().getClock());
        System.out.println("Month calculated: " + monthCount);
        System.out.println(13 - monthCount + " months to go to finish the trajectory error calculation...");
        System.out.println();

    }

    /**
     * Fills the current state of the solar system into the log.
     */
    public void fillValues(StateInterface s) {
        //TODO : insert MOON
        for (int i = 0; i < simulation.system().getCelestialBodies().size() - 1; i++) {
            trajectoryErrorCalc.fillSimulation(i, monthCount, 0, s.getPositions().get(i));
            trajectoryErrorCalc.fillSimulation(i, monthCount, 1, s.getRateOfChange().getVelocities().get(i));
        }
    }
}
