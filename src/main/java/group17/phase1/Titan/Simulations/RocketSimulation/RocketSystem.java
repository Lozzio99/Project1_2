package group17.phase1.Titan.Simulations.RocketSimulation;

import group17.phase1.Titan.Interfaces.ODESolverInterface;
import group17.phase1.Titan.Interfaces.RocketSimulatorInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Interfaces.SystemInterface;
import group17.phase1.Titan.Physics.Bodies.CelestialBody;
import group17.phase1.Titan.Physics.Bodies.Particle;
import group17.phase1.Titan.Physics.Bodies.RocketSimulator;
import group17.phase1.Titan.Physics.Math.Vector3D;
import group17.phase1.Titan.System.Clock;
import group17.phase1.Titan.System.SystemState;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RocketSystem implements SystemInterface {
    List<CelestialBody> allBodies;
    StateInterface state;

    private Clock clock;

    public RocketSystem() {
        this.allBodies = new ArrayList<>();
        this.state = new SystemState();
    }

    @Override
    public List<CelestialBody> getCelestialBodies() {
        return this.allBodies;
    }

    @Override
    public void initPlanets() {
        this.allBodies.add(new Particle(1));
        this.allBodies.get(1).setVectorLocation(new Vector3D(80, 120, 30));
        this.allBodies.get(1).setVectorVelocity(new Vector3D());
        this.allBodies.get(1).setMASS(1000);
        this.allBodies.get(1).setRADIUS(15);
        this.allBodies.get(1).setColour(new Color(255, 0, 0));
    }

    @Override
    public void initProbe() {
        RocketSimulator rocket = new RocketSimulator();
        rocket.initProperties();
        this.allBodies.add(rocket);
    }

    @Override
    public void initClock() {
        this.clock = new Clock().setInitialTime(0, 0, 0);
    }

    @Override
    public void initDetector() {

    }

    @Override
    public void initReport() {

    }

    @Override
    public void reset() {
        this.state.state0(this.allBodies);
        this.state.initialVelocity();
        this.initClock();
    }

    @Override
    public void startSolver(int solver) {

    }

    @Override
    public void stop() {

    }

    @Override
    public StateInterface systemState() {
        return this.state;
    }

    @Override
    public ODESolverInterface solver() {
        return null;
    }

    @Override
    public Clock getClock() {
        return this.clock;
    }

    @Override
    public void step() {

    }

    @Override
    public RocketSimulatorInterface getRocket() {
        return (RocketSimulatorInterface) this.allBodies.get(0);
    }
}
