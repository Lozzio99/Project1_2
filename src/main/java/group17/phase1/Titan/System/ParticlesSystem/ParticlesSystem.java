package group17.phase1.Titan.System.ParticlesSystem;

import group17.phase1.Titan.Config;
import group17.phase1.Titan.Interfaces.ODEFunctionInterface;
import group17.phase1.Titan.Interfaces.ODESolverInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Interfaces.SystemInterface;
import group17.phase1.Titan.Physics.Bodies.BlackHole;
import group17.phase1.Titan.Physics.Bodies.CelestialBody;
import group17.phase1.Titan.Physics.Bodies.Particle;
import group17.phase1.Titan.Physics.Solvers.EulerSolver;
import group17.phase1.Titan.Physics.Solvers.MaxCPUSolver;
import group17.phase1.Titan.System.Clock2;
import group17.phase1.Titan.System.SystemState;

import java.util.ArrayList;
import java.util.List;

import static group17.phase1.Titan.Config.CPU_LEVEL;
import static group17.phase1.Titan.Config.PARTICLES;

public class ParticlesSystem implements SystemInterface {

    StateInterface systemState;
    ODESolverInterface solver;
    ODEFunctionInterface f;


    double t = 0;
    List<CelestialBody> particles;
    private Clock2 clock;

    @Override
    public List<CelestialBody> getCelestialBodies() {
        return this.particles;
    }

    @Override
    public void initPlanets() {
        this.particles = new ArrayList<>();
        this.particles.add(new BlackHole());
        this.particles.get(0).initProperties();

        //TODO : this only at the first launch (lmt)
        PARTICLES = CPU_LEVEL * PARTICLES;

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
        this.clock = new Clock2().setLaunchDay();
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
        this.systemState.initialVelocity();
    }

    @Override
    public void startSolver(int solver) {
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
        //this.f.shutDown();
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
    public Clock2 getClock() {
        return this.clock;
    }

    @Override
    public void step() {
        this.systemState = this.solver.step(this.f, t, this.systemState, Config.STEP_SIZE);
        t += Config.STEP_SIZE;
    }
}
