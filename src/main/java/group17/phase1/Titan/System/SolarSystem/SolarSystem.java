package group17.phase1.Titan.System.SolarSystem;

import group17.phase1.Titan.Config;
import group17.phase1.Titan.Interfaces.*;
import group17.phase1.Titan.Physics.Bodies.*;
import group17.phase1.Titan.Physics.Math.EulerSolver;
import group17.phase1.Titan.Physics.Math.MaxCPUSolver;
import group17.phase1.Titan.System.Clock;
import group17.phase1.Titan.System.RateOfChange;
import group17.phase1.Titan.System.SystemState;

import java.util.ArrayList;
import java.util.List;

import static group17.phase1.Titan.Config.INSERT_PROBE;
import static group17.phase1.Titan.Config.LAUNCH_DATE;

public class SolarSystem implements SystemInterface {

    StateInterface systemState;
    List<CelestialBody> allBodies;
    ODESolverInterface solver;
    ODEFunctionInterface f;


    double t = 0;
    private RateInterface rateOfChange;
    private Clock clock;

    @Override
    public List<CelestialBody> getCelestialBodies() {
        return allBodies;
    }

    @Override
    public void initPlanets() {
        this.allBodies = new ArrayList<>();
        this.allBodies.add(new Star());
        this.allBodies.add(new Planet(Planet.PlanetsEnum.EARTH));
        this.allBodies.add(new Planet(Planet.PlanetsEnum.MARS));
        this.allBodies.add(new Planet(Planet.PlanetsEnum.MERCURY));
        this.allBodies.add(new Planet(Planet.PlanetsEnum.VENUS));
        this.allBodies.add(new Planet(Planet.PlanetsEnum.JUPITER));
        this.allBodies.add(new Planet(Planet.PlanetsEnum.SATURN));
        this.allBodies.add(new Planet(Planet.PlanetsEnum.URANUS));
        this.allBodies.add(new Planet(Planet.PlanetsEnum.NEPTUNE));
        this.allBodies.add(new Satellite(Satellite.SatellitesEnum.MOON));
        this.allBodies.add(new Satellite(Satellite.SatellitesEnum.TITAN));
        for (CelestialBody c : this.allBodies)
            c.initProperties();
    }

    @Override
    public void initProbe() {
        if (INSERT_PROBE) {
            ProbeSimulator p = new ProbeSimulator();
            p.initProperties();
            this.allBodies.add(p);
        }
    }

    @Override
    public void initClock() {
        this.clock = LAUNCH_DATE = new Clock().getLaunchDay();
    }

    @Override
    public void initDetector() {

    }

    @Override
    public void initReport() {

    }

    @Override
    public void reset() {
        this.initClock();
        this.systemState = new SystemState().state0();
        this.rateOfChange = new RateOfChange().state0();

    }

    @Override
    public void startSolver() {
        this.solver = new EulerSolver();
        switch (Config.CPU_LEVEL) {
            case 1 -> {
                this.f = this.solver.getFunction();
            } //4 threads
            case 2 -> {
                this.f = new MaxCPUSolver().setCPULevel(4);
            }  //8 threads
            case 3 -> {
                this.f = new MaxCPUSolver().setCPULevel(6);
            }  //10 threads
            case 4 -> {
                this.f = new MaxCPUSolver().setCPULevel(8);
            }  //12 threads
            case 5 -> {
                this.f = new MaxCPUSolver().setCPULevel(Runtime.getRuntime().availableProcessors());
            } //for me is 16 but can be less
            default -> {
                throw new RuntimeException("Select a valid cpu level [1-5]");
            }
        }
    }

    @Override
    public void stop() {
        this.f.shutDown();
    }

    @Override
    public StateInterface systemState() {
        return systemState;
    }

    @Override
    public RateInterface systemRateOfChange() {
        return this.rateOfChange;
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
        this.systemState = this.solver.step(this.f, t, this.systemState, Config.STEP_SIZE);
        t += Config.STEP_SIZE;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < allBodies.size(); i++) {
            s.append("[");
            s.append(allBodies.get(i).toString()).append("]\n");
            s.append("Position :").append(systemState.getPositions().get(i)).append("\n");
            s.append("Velocity :").append(rateOfChange.getRateOfChange().get(i)).append("\n");
        }
        return s.toString().trim();
    }
}
