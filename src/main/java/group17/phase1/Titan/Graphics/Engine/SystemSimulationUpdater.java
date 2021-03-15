package group17.phase1.Titan.Graphics.Engine;

import group17.phase1.Titan.Bodies.CelestialBodies.CelestialBodyInterface;
import group17.phase1.Titan.Bodies.CelestialBodies.Planet;
import group17.phase1.Titan.Graphics.Renderer.Point3D;
import group17.phase1.Titan.Graphics.Renderer.Point3DConverter;
import group17.phase1.Titan.Graphics.UserInput.MouseInput;
import group17.phase1.Titan.Main;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SystemSimulationUpdater
{

    private final Lock sync = new ReentrantLock();

    private MouseInput mouse;

    private int initialX, initialY;
    private final double mouseSensitivity = 6;
    private final double moveSpeed = 100;




    private final int UNIT_SIZE = GraphicsManager.HEIGHT;

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
        this.rotateAxisZ(true,5);
        this.rotateAxisY(true,5);

    }


    void startSimulation(){

    }


    void paint(Graphics graphics)
    {
        Graphics2D g = (Graphics2D) graphics;


        graphics.setColor(Color.WHITE);
        graphics.drawString("x Axis",20,20);
        Line2D.Double xAxis = new Line2D.Double(Point3DConverter.convertPoint(left),Point3DConverter.convertPoint(right));
        g.draw(xAxis);

        graphics.setColor(Color.RED);
        graphics.drawString("y Axis",20,30);
        Line2D.Double yAxis = new Line2D.Double(Point3DConverter.convertPoint(top),Point3DConverter.convertPoint(bottom));
        g.draw(yAxis);


        graphics.setColor(Color.GREEN);
        graphics.drawString("z Axis",20,40);
        Line2D.Double zAxis = new Line2D.Double(Point3DConverter.convertPoint(rear),Point3DConverter.convertPoint(front));
        g.draw(zAxis);
    }

    void update()
    {
        int x = this.mouse.getX();
        int y = this.mouse.getY();

        boolean cw = false;
        if(this.mouse.getButton() == MouseInput.ClickType.LeftClick) {
            int xDif = x - initialX;
            int yDif = y - initialY;
            if (xDif>0)
                cw = true;
            this.rotateAxisX(cw,xDif);
        }
        else if(this.mouse.getButton() == MouseInput.ClickType.RightClick) {
            int xDif = x - initialX;
            if (xDif>0)
                cw = true;
            this.rotateAxisZ(cw,x);

        }

        if(this.mouse.isScrollingUp()) {
            Point3DConverter.zoomIn();
        }
        else if(this.mouse.isScrollingDown()) {
            Point3DConverter.zoomOut();
        }


        this.mouse.resetScroll();

        initialX = x;
        initialY = y;
        this.rotateAxisX(true,0.1);

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

    public void addMouseControl(MouseInput mouse){
        this.mouse = mouse;
    }

    public void rotateAxisX(boolean cw, double x){
        if (cw)
            x*=-1;

        Point3DConverter.rotateAxisY(top,cw,x);
        Point3DConverter.rotateAxisY(bottom,cw,x);
        Point3DConverter.rotateAxisY(left,cw,x);
        Point3DConverter.rotateAxisY(right,cw,x);
        Point3DConverter.rotateAxisY(rear,cw,x);
        Point3DConverter.rotateAxisY(front,cw,x);

    }
    void rotateAxisZ(boolean cw, double z){
        if (cw)
            z*=-1;
            Point3DConverter.rotateAxisX(top,cw,z);
            Point3DConverter.rotateAxisX(bottom,cw,z);
            Point3DConverter.rotateAxisX(left,cw,z);
            Point3DConverter.rotateAxisX(right,cw,z);
            Point3DConverter.rotateAxisX(rear,cw,z);
            Point3DConverter.rotateAxisX(front,cw,z);

    }

    void rotateAxisY(boolean cw, double y){
        if (cw)
            y*=-1;
            Point3DConverter.rotateAxisZ(top,cw,y);
            Point3DConverter.rotateAxisZ(bottom,cw,y);
            Point3DConverter.rotateAxisZ(left,cw,y);
            Point3DConverter.rotateAxisZ(right,cw,y);
            Point3DConverter.rotateAxisZ(rear,cw,y);
            Point3DConverter.rotateAxisZ(front,cw,y);

    }
}
