package group17.phase1.Titan.System.ParticlesSystem;

import group17.phase1.Titan.Config;
import group17.phase1.Titan.Interfaces.*;
import group17.phase1.Titan.Physics.Bodies.BlackHole;
import group17.phase1.Titan.Physics.Bodies.CelestialBody;
import group17.phase1.Titan.Physics.Bodies.Particle;
import group17.phase1.Titan.Physics.Math.EulerSolver;
import group17.phase1.Titan.Physics.Math.MaxCPUSolver;
import group17.phase1.Titan.System.Clock;
import group17.phase1.Titan.System.RateOfChange;
import group17.phase1.Titan.System.SystemState;

import java.util.ArrayList;
import java.util.List;

import static group17.phase1.Titan.Config.*;

public class ParticlesSystem implements SystemInterface {

    StateInterface systemState;
    ODESolverInterface solver;
    ODEFunctionInterface f;


    double t = 0;
    List<CelestialBody> particles;
    private RateInterface rateOfChange;
    private Clock clock;

    @Override
    public List<CelestialBody> getCelestialBodies() {
        return this.particles;
    }

    @Override
    public void initPlanets() {
        this.particles = new ArrayList<>();
        this.particles.add(new BlackHole());
        this.particles.get(0).initProperties();
        PARTICLES = CPU_LEVEL * 400;
        for (int i = 1; i < PARTICLES; i++) {
            this.particles.add(new Particle(i));
            this.particles.get(i).initProperties();
        }
    }

    @Override
    public void initProbe() {

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
        this.initPlanets();
        this.systemState = new SystemState().state0();
        this.rateOfChange = new RateOfChange().state0();
    }

    @Override
    public void start() {
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
        return this.systemState;
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
}
