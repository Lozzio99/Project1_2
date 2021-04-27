package group17.phase1.Titan.Simulations.RocketSimulation;

import group17.phase1.Titan.Graphics.Scenes.Scene;
import group17.phase1.Titan.Physics.Bodies.CelestialBody;
import group17.phase1.Titan.Simulations.RocketSimulation.Graphics.RocketGraphics;
import group17.phase1.Titan.Simulations.Simulation;

import static group17.phase1.Titan.Config.REPORT;
import static group17.phase1.Titan.Config.SOLVER;

public class RocketSimulation extends Simulation {

    @Override
    public void initSystem(int solver) {
        this.system = new RocketSystem();
        this.system.initProbe();
        this.system.initPlanets();
        this.system.reset();
    }

    @Override
    public CelestialBody getBody(String name) {
        return null;
    }

    @Override
    public void initGraphics(boolean enable_graphics, boolean enable_assist) {
        this.graphics.set(new RocketGraphics());
        this.graphics.get().init();
    }

    @Override
    public void initCPU(int cpu) {
        this.updater.set(new RocketUpdater("Updater"));
        this.updater.get().setDaemon(true);
    }

    @Override
    public void startGraphics() {
        this.system.startSolver(SOLVER);
        this.graphics.get().launch();
        this.setWaiting(false);
        this.graphics.get().changeScene(Scene.SceneType.SIMULATION_SCENE);
        this.startUpdater();
        if (REPORT)
            System.out.println("Commence simulation...");
    }

    @Override
    public void startUpdater() {
        super.startUpdater();
    }
}
