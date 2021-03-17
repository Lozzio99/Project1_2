package group17.phase1.Titan.Graphics;

import group17.phase1.Titan.Graphics.Geometry.Point3D;
import group17.phase1.Titan.Graphics.Geometry.Point3DConverter;
import group17.phase1.Titan.Main;
import group17.phase1.Titan.Utils.Configuration;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SystemSimulationUpdater
{
    private final Lock syncMovement = new ReentrantLock();

    private MouseInput mouse;

    private int initialX, initialY;
    private final double mouseSensitivity = 6;

    private final int UNIT_SIZE = GraphicsManager.HEIGHT;


    Point3D [] planetsPositions = new Point3D[0];
    double[] radius;
    Color[] colors;
    double scale = 5e9;
    double radiusMag = 1e2;


    Point3D left = new Point3D(-UNIT_SIZE,0,0),
            right = new Point3D(UNIT_SIZE,0,0),
            top = new Point3D(0,UNIT_SIZE,0),
            bottom = new Point3D(0,-UNIT_SIZE,0),
            front = new Point3D(0,0,UNIT_SIZE),
            rear = new Point3D(0,0,-UNIT_SIZE);


    void startSimulation()
    {

        this.planetsPositions = new Point3D[Main.simulation.getSolarSystemRepository().getCelestialBodies().size()];

        this.updatePlanetPositions();

        // scale /= 1e4;

        radius = new double[Main.simulation.getSolarSystemRepository().getCelestialBodies().size()];
        this.colors = new Color[this.radius.length];

        for (int i =0; i<radius.length; i++)
        {
            radius[i] = (Main.simulation.getSolarSystemRepository().getCelestialBodies().get(i).getRADIUS()/scale)* radiusMag;
            this.colors[i] = Main.simulation.getSolarSystemRepository().getCelestialBodies().get(i).getColour();
        }

        //this.rotateOnAxisZ(true,5);
        //this.rotateOnAxisY(true,5);

        if (Configuration.DEBUG) { // Print initial location and size of the bodies
            System.out.println("Positions:");
            for (int i = 0; i < this.planetsPositions.length; i++) {
                System.out.println(i + "\t : \tX: " + planetsPositions[i].x + "\tY: " + planetsPositions[i].y + "\tZ: " + planetsPositions[i].z);
            }
            System.out.println("Radius:");
            for (int i = 0; i < this.radius.length; i++) {
                System.out.println(i + "\t : \t " + radius[i]);
            }
        }
    }

    private void updatePlanetPositions()
    {
        for (int i = 0; i< this.planetsPositions.length; i++)
        {
            this.planetsPositions[i] = Main.simulation.getSolarSystemRepository().getCelestialBodies().get(i).getVectorLocation().fromVector();
            this.planetsPositions[i].x/= scale;
            this.planetsPositions[i].y/= scale;
            this.planetsPositions[i].z/= scale;
        }
    }

    void paint(Graphics graphics)
    {
        Graphics2D g = (Graphics2D) graphics;

        graphics.setColor(new Color(255,255,255,113));
        graphics.drawString("x Axis",20,20);
        Line2D.Double xAxis = new Line2D.Double(Point3DConverter.convertPoint(left),Point3DConverter.convertPoint(right));
        g.draw(xAxis);

        graphics.setColor(new Color(255, 42, 42,113));
        graphics.drawString("y Axis",20,30);
        Line2D.Double yAxis = new Line2D.Double(Point3DConverter.convertPoint(top),Point3DConverter.convertPoint(bottom));
        g.draw(yAxis);


        graphics.setColor(new Color(120, 255, 108, 116));
        graphics.drawString("z Axis",20,40);
        Line2D.Double zAxis = new Line2D.Double(Point3DConverter.convertPoint(rear),Point3DConverter.convertPoint(front));
        g.draw(zAxis);


        for (int i = 0; i< this.planetsPositions.length; i++)
        {
            g.setColor(this.colors[i]);
            g.fill(planetShape(this.planetsPositions[i], this.radius[i]));
        }
    }

    void update()
    {
        int x = this.mouse.getX();
        int y = this.mouse.getY();



        if(this.mouse.getButton() == MouseInput.ClickType.LeftClick) {
            int xDif = x - initialX;
            this.rotateOnAxisY(false,xDif/mouseSensitivity);
        }

        else if(this.mouse.getButton() == MouseInput.ClickType.RightClick) {
            int yDif = y - initialY;
            this.rotateOnAxisX(false,yDif/mouseSensitivity);
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

        this.updatePlanetPositions();

        //this.rotateOnAxisY(true,0.01);

        // Update size
        for (int i =0; i<radius.length; i++)
        {
            radius[i] = (Main.simulation.getSolarSystemRepository().getCelestialBodies().get(i).getRADIUS()/scale) * Point3DConverter.getScale()* radiusMag;
        }
    }

    Ellipse2D.Double planetShape(Point3D position, double radius)
    {
        Point p = Point3DConverter.convertPoint(position);
        return new Ellipse2D.Double(p.getX() - radius, p.getY() - radius, radius * 2, radius * 2);
    }

    public void addMouseControl(MouseInput mouse){
        this.mouse = mouse;
    }

    public void rotateOnAxisY(boolean cw, double y){
        Point3DConverter.rotateAxisY(top,cw,y);
        Point3DConverter.rotateAxisY(bottom,cw,y);
        Point3DConverter.rotateAxisY(left,cw,y);
        Point3DConverter.rotateAxisY(right,cw,y);
        Point3DConverter.rotateAxisY(rear,cw,y);
        Point3DConverter.rotateAxisY(front,cw,y);
        for (Point3D p : this.planetsPositions)
            Point3DConverter.rotateAxisY(p,cw,y);

    }
    void rotateOnAxisX(boolean cw, double x)
    {
        Point3DConverter.rotateAxisX(top,cw,x);
        Point3DConverter.rotateAxisX(bottom,cw,x);
        Point3DConverter.rotateAxisX(left,cw,x);
        Point3DConverter.rotateAxisX(right,cw,x);
        Point3DConverter.rotateAxisX(rear,cw,x);
        Point3DConverter.rotateAxisX(front,cw,x);
        for (Point3D p : this.planetsPositions)
            Point3DConverter.rotateAxisX(p,cw,x);
    }

    void rotateOnAxisZ(boolean cw, double z){
        Point3DConverter.rotateAxisZ(top,cw,z);
        Point3DConverter.rotateAxisZ(bottom,cw,z);
        Point3DConverter.rotateAxisZ(left,cw,z);
        Point3DConverter.rotateAxisZ(right,cw,z);
        Point3DConverter.rotateAxisZ(rear,cw,z);
        Point3DConverter.rotateAxisZ(front,cw,z);
        for (Point3D p : this.planetsPositions)
            Point3DConverter.rotateAxisZ(p,cw,z);
    }
}
