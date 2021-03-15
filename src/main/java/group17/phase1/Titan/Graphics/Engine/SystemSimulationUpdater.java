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
            if (xDif<0)
                cw = true;
            this.rotate(cw,  xDif/mouseSensitivity, yDif/mouseSensitivity,0);
        }
        else if(this.mouse.getButton() == MouseInput.ClickType.RightClick) {
            int xDif = x - initialX;
            if (xDif<0)
                cw = true;
            this.rotate(cw, xDif/mouseSensitivity, 0, 0);
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

    public void rotate(boolean cw, double x, double y, double z){
        if (x != 0){
            Point3DConverter.rotateAxisX(top,cw,x);
            Point3DConverter.rotateAxisX(bottom,cw,x);
            Point3DConverter.rotateAxisX(left,cw,x);
            Point3DConverter.rotateAxisX(right,cw,x);
            Point3DConverter.rotateAxisX(rear,cw,x);
            Point3DConverter.rotateAxisX(front,cw,x);
        }
        if (y != 0){
            Point3DConverter.rotateAxisY(top,cw,y);
            Point3DConverter.rotateAxisY(bottom,cw,y);
            Point3DConverter.rotateAxisY(left,cw,y);
            Point3DConverter.rotateAxisY(right,cw,y);
            Point3DConverter.rotateAxisY(rear,cw,y);
            Point3DConverter.rotateAxisY(front,cw,y);
        }
        if (z != 0){
            Point3DConverter.rotateAxisZ(top,cw,z);
            Point3DConverter.rotateAxisZ(bottom,cw,z);
            Point3DConverter.rotateAxisZ(left,cw,z);
            Point3DConverter.rotateAxisZ(right,cw,z);
            Point3DConverter.rotateAxisZ(rear,cw,z);
            Point3DConverter.rotateAxisZ(front,cw,z);
        }
    }
}
