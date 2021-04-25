package group17.phase1.Titan.System.SolarSystem;

import group17.phase1.Titan.Config;
import group17.phase1.Titan.Interfaces.ODEFunctionInterface;
import group17.phase1.Titan.Interfaces.ODESolverInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Interfaces.SystemInterface;
import group17.phase1.Titan.Main;
import group17.phase1.Titan.Physics.Bodies.*;
import group17.phase1.Titan.Physics.Solvers.*;
import group17.phase1.Titan.Physics.Trajectories.TrajectoryErrorCalc;
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
    private TrajectoryErrorCalc trajectoryErrorCalc;
    private int monthCount = 0;
    private int monthIndex = 4;     // Start on April

    @Override
    public List<CelestialBody> getCelestialBodies() {
        return allBodies;
    }

    @Override
    public void initPlanets() {
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

        if (REPORT) {
            trajectoryErrorCalc = new TrajectoryErrorCalc();
            for (int i = 0; i < 10; i++) {
                trajectoryErrorCalc.fillSimulation(i, monthCount, 0, this.getCelestialBodies().get(i).getVectorLocation());
                trajectoryErrorCalc.fillSimulation(i, monthCount, 1, this.getCelestialBodies().get(i).getVectorVelocity());
            }
        }
    }

    @Override
    public void initProbe() {
        if (INSERT_PROBE) {
            ProbeSimulator p = new ProbeSimulator();
            p.initProperties();
            this.allBodies.add(p);
        }
        // if (REPORT)
        //     fillValues();
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
        if (REPORT)
            updateTrajectoryLog();
    }

    /**
     * Determines a start of a new month and if it is the case, the method writes the values into the log
     * of the TrajectoryErrorCalc class.
     */
    public void updateTrajectoryLog() {
        // System.out.println("Month: " + clock.getMonth());
        if (this.monthIndex != clock.getMonths()) {
            if (this.monthCount < 12) {
                monthIndex = clock.getMonths();
                monthCount++;
                // Fill in the values of each body:
                fillValues();
                System.out.println("Month: " + clock.getMonths());
                System.out.println("Month count: " + monthCount);
                System.out.println(13 - monthCount + " months to go to finish the trajectory error calculation...");
                System.out.println();
            } else if (this.monthCount == 12) {
                monthCount++;
                monthIndex = clock.getMonths();
                trajectoryErrorCalc.exportFile();
                System.out.println("Error calculation finished and exported to /out/production/trajectoryData");
            }
        }
    }

    /**
     * Fills the current state of the solar system into the log.
     */
    public void fillValues() {
        for (int i = 0; i < 10; i++) {
            trajectoryErrorCalc.fillSimulation(i, monthCount, 0, Main.simulation.system().systemState().getPositions().get(i));
            trajectoryErrorCalc.fillSimulation(i, monthCount, 1, Main.simulation.system().systemState().getRateOfChange().getVelocities().get(i));
        }
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
