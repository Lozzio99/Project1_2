package group17.phase1.Titan.Simulation;

import group17.phase1.Titan.Graphics.GraphicsManager;
import group17.phase1.Titan.Physics.Gravity.MetaSolver;
import group17.phase1.Titan.Physics.Gravity.Solver;
import group17.phase1.Titan.Physics.SolarSystem.CelestialBody;
import group17.phase1.Titan.Physics.SolarSystem.ProbeSimulator;
import group17.phase1.Titan.Physics.SolarSystem.SolarSystem;
import group17.phase1.Titan.interfaces.*;

import static group17.phase1.Titan.Config.stepSize;

public class Simulation implements SimulationInterface
{
    GraphicsInterface graphicsManager;
    SolarSystemInterface solarSystem;
    RateInterface rateOfChange;
    StateInterface systemState;
    ODESolverInterface solverInterface;
    ODEFunctionInterface functionInterface;
    private ProbeSimulator probe;
    static double t = 0;

    private static int CPU;


    @Override
    public int CPU() {
        return CPU;
    }

    @Override
    public void init(int cpu) {
        CPU = cpu;
        this.solarSystem = new SolarSystem();
        switch (cpu)
        {
            case 1 -> this.solverInterface = new Solver();
            case 5 -> this.solverInterface = new MetaSolver();
        }
        this.probe = new ProbeSimulator();
        this.solarSystem.getCelestialBodies().add(probe);
        this.reset();
    }

    @Override
    public void initGraphics() {
        this.graphicsManager = new GraphicsManager();
    }

    @Override
    public void reset() {
        this.rateOfChange = (RateInterface) this.solarSystem;
        this.systemState = (StateInterface) this.solarSystem;
        this.functionInterface = (ODEFunctionInterface) this.solverInterface;
        this.solarSystem.initPlanetPositions();
    }

    @Override
    public void step()
    {
        this.solverInterface.step(this.functionInterface,t,this.systemState, stepSize);
        t+= stepSize;
    }

    @Override
    public void start() {
        this.graphicsManager.launch();
    }

    @Override
    public SolarSystemInterface solarSystem() {
        return this.solarSystem;
    }

    @Override
    public GraphicsInterface graphics() {
        return this.graphicsManager;
    }

    @Override
    public StateInterface systemState() {
        return this.systemState;
    }

    @Override
    public RateInterface rateOfChange() {
        return this.rateOfChange;
    }

    @Override
    public CelestialBody getBody(String name)
    {
        for(CelestialBody c : this.solarSystem().getCelestialBodies())
            if (c.toString().equals(name))
                return c;
        return null;
    }

    @Override
    public void setStepSize(double timeStepSize) {
        stepSize = timeStepSize;
    }

    @Override
    public double getStepSize(){
        return stepSize;
    }

    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append("Running at step size : ").append(stepSize).append(" dt\n");
        s.append(this.solarSystem.toString());
        return s.toString().trim();
    }

}
