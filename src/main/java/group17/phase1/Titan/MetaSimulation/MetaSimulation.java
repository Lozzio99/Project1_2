package group17.phase1.Titan.MetaSimulation;

import group17.phase1.Titan.Physics.SolarSystem.CelestialBody;
import group17.phase1.Titan.interfaces.*;

public class MetaSimulation implements SimulationInterface {


    private GraphicsInterface graphicsInterface;
    private SolarSystemInterface solarSystem;


    @Override
    public void init() {
        this.solarSystem = new MetaSolarSystem();
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

    }

    @Override
    public void start() {

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
        return null;
    }

    @Override
    public RateInterface rateOfChange() {
        return null;
    }

    @Override
    public CelestialBody getBody(String name) {
        return null;
    }

    @Override
    public void setStepSize(double timeStepSize) {

    }

    @Override
    public double getStepSize() {
        return 0;
    }
}
