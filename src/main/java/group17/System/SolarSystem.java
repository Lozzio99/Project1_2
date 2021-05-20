package group17.System;

import group17.Interfaces.StateInterface;
import group17.Interfaces.SystemInterface;
import group17.Simulation.Rocket.RocketSimulator;
import group17.System.Bodies.CelestialBody;
import group17.System.Bodies.Planet;
import group17.System.Bodies.Satellite;
import group17.System.Bodies.Star;
import group17.System.State.SystemState;

import java.util.ArrayList;
import java.util.List;

import static group17.System.Bodies.Planet.PlanetsEnum.*;
import static group17.System.Bodies.Satellite.SatellitesEnum.MOON;
import static group17.System.Bodies.Satellite.SatellitesEnum.TITAN;
import static group17.Utils.Config.INSERT_ROCKET;
import static group17.Utils.Config.LAUNCH_DATE;

/**
 * The type Solar system.
 */
public class SolarSystem implements SystemInterface {

    /**
     * The Clock.
     */
    protected Clock clock;
    /**
     * The Bodies.
     */
    protected List<CelestialBody> bodies;
    /**
     * The System state.
     */
    protected volatile StateInterface systemState;
    /**
     * The Rocket.
     */
    protected RocketSimulator rocket;

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
        this.bodies = new ArrayList<>();
        this.bodies.add(new Star());
        this.bodies.add(new Planet(MERCURY));
        this.bodies.add(new Planet(VENUS));
        this.bodies.add(new Planet(EARTH));
        this.bodies.add(new Satellite(MOON));
        this.bodies.add(new Planet(MARS));
        this.bodies.add(new Planet(JUPITER));
        this.bodies.add(new Planet(SATURN));
        this.bodies.add(new Satellite(TITAN));
        this.bodies.add(new Planet(URANUS));
        this.bodies.add(new Planet(NEPTUNE));
    }

    @Override
    public void initRocket() {
        if (this.bodies == null)
            this.bodies = new ArrayList<>();
        this.rocket = new RocketSimulator();
        this.bodies.add(this.rocket);
    }


    @Override
    public Clock getClock() {
        return clock;
    }

    @Override
    public void initClock() {
        this.clock = LAUNCH_DATE = new Clock().setLaunchDay();
    }

    @Override
    public void start() {
    }

    @Override
    public void reset() {
        this.initPlanets();
        if (INSERT_ROCKET) this.initRocket();
        this.initClock();
        this.initialState();
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
        if (!INSERT_ROCKET || rocket == null)
            return "SOLAR SYSTEM\t"
                    + clock +
                    ",\n\tSTATE\n" + systemState +
                    "\n\t" + rocket;
        return "SOLAR SYSTEM\t"
                + clock +
                ",\n\tSTATE\n" + systemState +
                "\n\t" + rocket +
                "\n" + rocket.info();
    }
}
