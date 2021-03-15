package group17.phase1.Titan.Graphics.Engine;

import group17.phase1.Titan.Bodies.CelestialBodies.CelestialBodyInterface;
import group17.phase1.Titan.Bodies.CelestialBodies.Planet;
import group17.phase1.Titan.Graphics.Renderer.Point3D;
import group17.phase1.Titan.Graphics.Renderer.Point3DConverter;
import group17.phase1.Titan.Main;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SystemSimulationUpdater
{
    private final int UNIT_SIZE = GraphicsManager.HEIGHT/2;
    private final Lock sync = new ReentrantLock();
    Point3D left = new Point3D(-UNIT_SIZE,0,0),
    right = new Point3D(UNIT_SIZE,0,0),
    top = new Point3D(0,UNIT_SIZE,0),
    bottom = new Point3D(0,-UNIT_SIZE,0),
    front = new Point3D(0,0,UNIT_SIZE),
    rear = new Point3D(0,0,-UNIT_SIZE);



    public SystemSimulationUpdater()
    {
        //init variables..
        //..
    }


    void startSimulation(){

    }


    void paint(Graphics graphics)
    {
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.WHITE);
        g.drawString("hello",200,200);
        Line2D.Double yAxis = new Line2D.Double(Point3DConverter.convertPoint(top),Point3DConverter.convertPoint(bottom));
        g.draw(yAxis);
        Line2D.Double xAxis = new Line2D.Double(Point3DConverter.convertPoint(left),Point3DConverter.convertPoint(right));
        g.draw(xAxis);
        Line2D.Double zAxis = new Line2D.Double(Point3DConverter.convertPoint(rear),Point3DConverter.convertPoint(front));
        g.draw(zAxis);
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
