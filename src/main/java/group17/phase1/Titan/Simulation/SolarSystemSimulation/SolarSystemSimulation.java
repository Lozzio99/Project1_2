package group17.phase1.Titan.Simulation.SolarSystemSimulation;

import group17.phase1.Titan.Config;
import group17.phase1.Titan.Graphics.DialogFrame;
import group17.phase1.Titan.Graphics.GraphicsManager;
import group17.phase1.Titan.Physics.Bodies.CelestialBody;
import group17.phase1.Titan.Simulation.Simulation;
import group17.phase1.Titan.System.SolarSystem;

public class SolarSystemSimulation extends Simulation {

    @Override
    public void start() {
        this.reset();
        graphics.launch();
        assist.launch();
    }

    @Override
    public void reset() {
        this.system.reset();
    }

    @Override
    public void stop() {
        this.graphics.stop();
        this.assist.stop();
        this.system.stop();
    }

    @Override
    public void initCPU(int cpu) {
        Config.CPU_LEVEL = cpu;
    }

    @Override
    public void initSystem() {
        system = new SolarSystem();
        system.initPlanets();
        system.initProbe();
        system.start();
    }

    @Override
    public void initGraphics(boolean enable_graphics, boolean enable_assist) {
        if (enable_graphics) {
            graphics = new GraphicsManager();
            graphics.init();
        }
        if (enable_assist) {
            assist = new DialogFrame();
            assist.init();
        }
    }

    @Override
    public CelestialBody getBody(String name) {
        for (CelestialBody c : this.system.getCelestialBodies())
            if (c.toString().equals(name))
                return c;
        return null;
    }


}
