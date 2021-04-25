package group17.phase1.Titan.Simulations.SolarSystemSimulation;

import group17.phase1.Titan.Config;
import group17.phase1.Titan.Interfaces.ODEFunctionInterface;
import group17.phase1.Titan.Interfaces.ODESolverInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Interfaces.SystemInterface;
import group17.phase1.Titan.Physics.Bodies.*;
import group17.phase1.Titan.Physics.Solvers.*;
import group17.phase1.Titan.System.Clock;
import group17.phase1.Titan.System.SystemState;

import java.util.ArrayList;
import java.util.List;

import static group17.phase1.Titan.Config.*;

public class SolarSystem implements SystemInterface {

    StateInterface systemState;
    List<CelestialBody> allBodies;
    ODESolverInterface solver;
    ODEFunctionInterface f;


    double t = 0;
    private Clock clock;

    public SolarSystem() {
        this.allBodies = new ArrayList<>();
    }

    @Override
    public List<CelestialBody> getCelestialBodies() {
        return allBodies;
    }

    @Override
    public void initPlanets() {
        if (SIMULATION_LEVEL == PARTICLES_SIMULATION)
            this.allBodies = new ArrayList<>();
        this.allBodies.add(new Star());
        this.allBodies.add(new Planet(Planet.PlanetsEnum.MERCURY));
        this.allBodies.add(new Planet(Planet.PlanetsEnum.VENUS));
        this.allBodies.add(new Planet(Planet.PlanetsEnum.EARTH));
        this.allBodies.add(new Planet(Planet.PlanetsEnum.MARS));
        this.allBodies.add(new Planet(Planet.PlanetsEnum.JUPITER));
        this.allBodies.add(new Planet(Planet.PlanetsEnum.SATURN));
        this.allBodies.add(new Satellite(Satellite.SatellitesEnum.TITAN));
        this.allBodies.add(new Planet(Planet.PlanetsEnum.URANUS));
        this.allBodies.add(new Planet(Planet.PlanetsEnum.NEPTUNE));
        this.allBodies.add(new Satellite(Satellite.SatellitesEnum.MOON));
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
        this.initClock();
        this.systemState = new SystemState().state0();
        this.systemState.initialVelocity();
    }

    @Override
    public void stop() {
        if (CPU_LEVEL > 1)
            ((MaxCPUSolver) this.f).shutDown();
    }


    //TODO : set f in a proper way (solver may return something else) see : step()
    @Override
    public void startSolver(int solver) {
        switch (solver) {
            case EULER_SOLVER -> this.solver = new EulerSolver();
            case RUNGE_KUTTA_SOLVER -> this.solver = new RungeKutta4thSolver();
            case VERLET_STD_SOLVER -> this.solver = new StandardVerletSolver();
            case VERLET_VEL_SOLVER -> this.solver = new VerletVelocitySolver();
            default -> throw new RuntimeException("Select a valid solver [1-4]");
        }
        switch (CPU_LEVEL) {
            case 1 -> this.f = this.solver.getFunction(); //4 threads
            case 2 -> this.f = new MaxCPUSolver().setCPULevel(4);  //8 threads
            case 3 -> this.f = new MaxCPUSolver().setCPULevel(6);  //10 threads
            case 4 -> this.f = new MaxCPUSolver().setCPULevel(8);  //12 threads
            case 5 -> this.f = new MaxCPUSolver().setCPULevel(Runtime.getRuntime().availableProcessors()); //for me is 16 but can be less
            default -> throw new RuntimeException("Select a valid cpu level [1-5]");
        }
        this.solver.setF(this.f);
        this.solver.setClock(this.clock);
    }

    @Override
    public StateInterface systemState() {
        return systemState;
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
            if (systemState.getPositions().size() == i)
                break;
            s.append("[");
            s.append(allBodies.get(i).toString()).append("]\n");
            s.append("Position :").append(systemState.getPositions().get(i)).append("\n");
            s.append("Velocity :").append(systemState.getRateOfChange().getVelocities().get(i)).append("\n");
        }
        return s.toString().trim();
    }

}
