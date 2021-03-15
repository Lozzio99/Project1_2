package group17.phase1.Titan.Graphics.Engine;

import group17.phase1.Titan.Bodies.CelestialBodies.CelestialBodyInterface;
import group17.phase1.Titan.Bodies.CelestialBodies.Planet;
import group17.phase1.Titan.Main;

import java.awt.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SystemSimulationUpdater
{
    private final Lock sync = new ReentrantLock();

    public SystemSimulationUpdater()
    {
        //init variables..
        //..
    }


    void startSimulation(){

    }


    void paint(Graphics g){
        g.drawString("hello",200,200);
    }

    void update(){

    }

    void updateLocations(float timeStep)
    {
        for (CelestialBodyInterface p : Main.simulation.solarSystemRepository().allCelestialBodies())
        {
            Planet.Slave calculator = new Planet.Slave((Planet) p);
            Planet.Slave.setSyncLock(sync);
            calculator.start();
        }
    }
}
