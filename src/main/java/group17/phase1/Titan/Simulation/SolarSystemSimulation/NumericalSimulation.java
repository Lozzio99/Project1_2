package group17.phase1.Titan.Simulation.SolarSystemSimulation;

import group17.phase1.Titan.Graphics.DialogFrame;
import group17.phase1.Titan.Interfaces.GraphicsInterface;
import group17.phase1.Titan.Physics.Bodies.CelestialBody;
import group17.phase1.Titan.Physics.Trajectories.TrajectoriesSolver;
import group17.phase1.Titan.Physics.Trajectories.TrajectoryErrorCalc;
import group17.phase1.Titan.Simulation.Simulation;
import group17.phase1.Titan.System.SolarSystem.SolarSystem;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicReference;

public class NumericalSimulation extends Simulation {
    @Override
    public void initSystem() {
        this.system = new SolarSystem();
        this.system.initPlanets();
        this.system.initProbe();
        this.solveAndWait();
    }

    @Override
    public CelestialBody getBody(String name) {
        for (CelestialBody c : this.system.getCelestialBodies())
            if (c.toString().equals(name))
                return c;
        return null;
    }

    @Override
    public void initCPU(int level) {
        this.updater.set(new TrajectoriesSolver());
        this.updater.get().setDaemon(true);
    }


    @Override
    public void initGraphics(boolean enable_graphics, boolean enable_assist) {
        if (enable_graphics)
            System.err.println("Bad Configuration for [ENABLE_GRAPHICS]");
        if (enable_assist)
            System.err.println("Bad Configuration for [ENABLE_ASSIST]");
    }

    @Override
    public AtomicReference<GraphicsInterface> graphics() {
        throw new UnsupportedOperationException("No graphics for numerical simulation");
    }

    @Override
    public DialogFrame assist() {
        throw new UnsupportedOperationException("No graphics for numerical simulation");
    }

    @Override
    public void startGraphics() {

    }

    @Override
    public void stop() {
        this.system.stop();
        this.updater.get().tryStop();
    }

    public void solveAndWait() {
        this.system.reset();
        this.setRunning();
        this.system.startSolver();
        this.updater.get().launch();
        while (!this.updater.get().isKilled()) {
        }
        TrajectoryErrorCalc test = null;
        try {
            test = new TrajectoryErrorCalc();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        test.revert();

        for (int i = 0; i < this.updater.get().getStates().length; i++) {
            System.out.println("Month  " + (i + 1));
            System.out.println("SIMULATION STATE : ");
            System.out.println(this.updater.get().getStates()[i].toString());
            System.out.println("\nCOMPARE TO ORIGINAL : ");
            System.out.println(test.getMonths().get(i).toString());
            System.out.println("\n");
        }
    }

    @Override
    public String toString() {
        return this.system.toString();
    }
}
