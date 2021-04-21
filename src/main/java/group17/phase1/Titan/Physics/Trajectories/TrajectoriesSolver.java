package group17.phase1.Titan.Physics.Trajectories;

import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Simulation.SimulationUpdater;

import static group17.phase1.Titan.Main.simulation;

public class TrajectoriesSolver extends SimulationUpdater {

    private StateInterface[] states;

    public TrajectoriesSolver() {
        super("Trajectories");
        this.isKilled = false;
    }

    @Override
    public void run() {
        //one day =
        //start from april
        int[] monthdays = new int[]{30, 31, 30, 31, 31, 30, 31, 30, 31, 31, 28, 31};
        double minInADay = 60 * 60 * 24; //60sec * 60min * 24h
        double[] ts = new double[monthdays.length];
        for (int i = 0; i < monthdays.length; i++) {
            ts[i] = minInADay * monthdays[i];
        }
        this.states = simulation.system().solver().solve(simulation.system().solver().getFunction(), simulation.system().systemState(), ts);
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
}
