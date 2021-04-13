package group17.phase1.Titan.MetaSimulation;

import group17.phase1.Titan.Physics.Gravity.MetaSolver;
import group17.phase1.Titan.Physics.Gravity.Solver;
import group17.phase1.Titan.Physics.SolarSystem.CelestialBody;
import group17.phase1.Titan.interfaces.*;

public class MetaSimulation implements SimulationInterface {


    private GraphicsInterface graphicsInterface;
    private SolarSystemInterface solarSystem;
    private StateInterface stateInterface;
    private RateInterface rateInterface;
    private static double stepSize,currTime ;
    private ODESolverInterface solver;

    private static int CPU;


    @Override
    public int CPU() {
        return CPU;
    }

    @Override
    public void init(int cpu)
    {
        CPU = cpu;
        this.solarSystem = new MetaSolarSystem();
        this.stateInterface = (StateInterface) this.solarSystem;
        this.rateInterface  =  (RateInterface) this.solarSystem;
        switch (cpu)
        {
            case 1 -> this.solver = new Solver();
            case 5 -> this.solver = new MetaSolver();
        }
        stepSize = .4;
    }

    @Override
    public void initGraphics() {
        this.graphicsInterface = new MetaGraphics();
    }

    @Override
    public void reset() {

    }

    @Override
    public void step() {
        this.solver.step((ODEFunctionInterface) this.solver,currTime,this.stateInterface,stepSize);
        currTime+= stepSize;
    }

    @Override
    public void start() {
        this.solarSystem.initPlanetPositions();
    }

    @Override
    public SolarSystemInterface solarSystem() {
        return this.solarSystem;
    }

    @Override
    public GraphicsInterface graphics() {
        return this.graphicsInterface;
    }

    @Override
    public StateInterface systemState() {
        return this.stateInterface;
    }

    @Override
    public RateInterface rateOfChange() {
        return this.rateInterface;
    }

    @Override
    public CelestialBody getBody(String name)
    {
        int i = Integer.parseInt(name);
        return this.solarSystem.getCelestialBodies().get(i);
    }

    @Override
    public void setStepSize(double timeStepSize) {
        stepSize = timeStepSize;
    }

    @Override
    public double getStepSize() {
        return stepSize;
    }
}
