package group17.phase1.Titan.Simulations.PendulumSimulation;

import group17.phase1.Titan.Config;
import group17.phase1.Titan.Interfaces.ODESolverInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Interfaces.SystemInterface;
import group17.phase1.Titan.Physics.Bodies.CelestialBody;
import group17.phase1.Titan.System.Clock;

import java.util.ArrayList;
import java.util.List;

public class PendulumSystem implements SystemInterface {
    StateInterface systemState;
    ODESolverInterface solver;
    private final List<CelestialBody> bodies;
    private Clock clock;
    private double t;

    public PendulumSystem() {
        this.bodies = new ArrayList<>();
        Config.STEP_SIZE = 2;
    }

    @Override
    public List<CelestialBody> getCelestialBodies() {
        return this.bodies;
    }

    @Override
    public void initPlanets() {
        PendulumBody p1 = new PendulumBody(1);
        this.bodies.add(p1);
        PendulumBody p2 = new PendulumBody(2);
        this.bodies.add(p2);
        p1.initProperties();
        p2.initProperties();
        this.systemState = new PendulumState();
    }

    @Override
    public void initProbe() {

    }

    @Override
    public void initClock() {
        this.t = 0;
        this.clock = new Clock().setLaunchDay();
    }

    @Override
    public void initDetector() {

    }

    @Override
    public void initReport() {

    }

    @Override
    public void reset() {
        this.systemState = this.systemState().state0(this.bodies);
        this.systemState().initialVelocity();
        this.initClock();
    }

    @Override
    public void startSolver(int solver) {
        this.solver = new PendulumSolver();
        this.solver.setClock(this.clock);
    }

    @Override
    public void stop() {

    }

    @Override
    public StateInterface systemState() {
        return this.systemState;
    }

    @Override
    public ODESolverInterface solver() {
        return this.solver;
    }

    @Override
    public Clock getClock() {
        return this.clock;
    }

    @Override
    public void step() {
        this.systemState = this.solver().step(this.solver().getFunction(), t, this.systemState(), Config.STEP_SIZE);
        t += Config.STEP_SIZE;
    }

    @Override
    public String toString() {
        return "PendulumSystem{" +
                "clock=" + clock +
                '}';
    }
}
