package group17.System;

import group17.Interfaces.StateInterface;
import group17.Interfaces.SystemInterface;
import group17.Simulation.RocketSimulator;
import group17.System.Bodies.CelestialBody;
import group17.System.Bodies.Planet;
import group17.System.Bodies.Satellite;
import group17.System.Bodies.Star;

import java.util.ArrayList;
import java.util.List;

import static group17.System.Bodies.Planet.PlanetsEnum.*;
import static group17.System.Bodies.Satellite.SatellitesEnum.MOON;
import static group17.System.Bodies.Satellite.SatellitesEnum.TITAN;

public class SolarSystem implements SystemInterface {

    private Clock clock;
    private List<CelestialBody> bodies;
    private volatile StateInterface systemState;
    private RocketSimulator rocket;

    @Override
    public List<CelestialBody> getCelestialBodies() {
        if (this.bodies == null)
            this.bodies = new ArrayList<>();
        return this.bodies;
    }

    @Override
    public StateInterface systemState() {
        return this.systemState;
    }

    @Override
    public RocketSimulator getRocket() {
        return this.rocket;
    }

    @Override
    public void initPlanets() {
        if (this.bodies == null)
            this.bodies = new ArrayList<>();
        this.bodies.add(new Star());
        this.bodies.add(new Planet(MERCURY));
        this.bodies.add(new Planet(VENUS));
        this.bodies.add(new Planet(EARTH));
        this.bodies.add(new Planet(MARS));
        this.bodies.add(new Planet(JUPITER));
        this.bodies.add(new Planet(SATURN));
        this.bodies.add(new Satellite(TITAN));
        this.bodies.add(new Planet(URANUS));
        this.bodies.add(new Planet(NEPTUNE));
        this.bodies.add(new Satellite(MOON));
        for (CelestialBody b : this.bodies)
            b.initProperties();

    }

    @Override
    public void initRocket() {
        if (this.bodies == null)
            this.bodies = new ArrayList<>();
        this.rocket = new RocketSimulator();
        this.rocket.initProperties();
        this.bodies.add(this.rocket);
    }


    @Override
    public Clock getClock() {
        return clock;
    }

    @Override
    public void initClock() {
        this.clock = new Clock().setLaunchDay();
    }

    @Override
    public void start() {
    }

    @Override
    public void reset() {
        this.systemState = new SystemState().state0(this.getCelestialBodies());
        this.clock = this.clock.setLaunchDay();
    }

    @Override
    public void stop() {

    }

    @Override
    public void step() {

    }

    @Override
    public void initialState() {
        this.systemState = new SystemState().state0(this.getCelestialBodies());
    }

    @Override
    public String toString() {
        return "SOLAR SYSTEM\t"
                + clock +
                ",\n\tSTATE\n" + systemState +
                "\n\tROCKET\n" + rocket;
    }
}
