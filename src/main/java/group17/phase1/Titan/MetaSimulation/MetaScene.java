package group17.phase1.Titan.MetaSimulation;

import group17.phase1.Titan.Graphics.Geometry.Point3D;
import group17.phase1.Titan.Graphics.Geometry.Point3DConverter;
import group17.phase1.Titan.Graphics.GraphicsManager;
import group17.phase1.Titan.Graphics.MouseInput;
import group17.phase1.Titan.Main;
import group17.phase1.Titan.Physics.SolarSystem.Satellite;
import group17.phase1.Titan.Physics.Vector3D;
import group17.phase1.Titan.interfaces.Vector3dInterface;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class MetaScene
{

    boolean started = false;
    MouseInput mouse;
    private int x,y,deltaX,deltaY,xDif,initialX,yDif,initialY,totalXDif,totalYDif;
    private final double mouseSensitivity = 20;
    private final double scale = 20;
    private List<Point3D>  positions;
    private List<Double> radius;


    public void setMouse(MouseInput mouse) {
        this.mouse = mouse;
    }


    public void update()
    {
        x = this.mouse.getX();
        y = this.mouse.getY();
        deltaX = deltaY = 0;

        if(this.mouse.getButton() == MouseInput.ClickType.LeftClick) {
            deltaX = xDif = x - initialX;
            this.rotateOnAxisY(false,xDif/mouseSensitivity);
            totalXDif += xDif;
        }

        else if(this.mouse.getButton() == MouseInput.ClickType.RightClick)
        {
            Satellite s = new Satellite(Satellite.SatellitesEnum.ASTEROID);
            s.setRADIUS(50);
            s.setMASS(1e8);
            s.setVectorLocation(getFromScreen(x,y));
            s.setVectorVelocity(new Vector3D(0,0,0));
            Main.simulation.solarSystem().getCelestialBodies().add(s);
            Main.simulation.systemState().getPositions().add(s.getVectorLocation());
            Main.simulation.rateOfChange().getRateOfChange().add(s.getVectorVelocity());
            this.positions.add(s.getVectorLocation().fromVector());
            this.radius.add(50.);
        }


        if(this.mouse.isScrollingUp()) {
            Point3DConverter.zoomIn();
        }
        else if(this.mouse.isScrollingDown()) {
            Point3DConverter.zoomOut();
        }

        this.updateBodies();

        this.initialX = x;
        this.initialY = y;
        this.mouse.resetScroll();
        this.mouse.resetButton();

    }

    private static Vector3dInterface getFromScreen(int x, int y)
    {
        Vector3D v = new Vector3D();
        double scaling =7/(12 * Point3DConverter.getScale());
        int w2x = GraphicsManager.screen.width/2,
                h2y = GraphicsManager.screen.height/2;
        if (x >= w2x){
            v.setX((x-w2x) * scaling);
        }
        else {
            v.setX((w2x-x) * scaling *-1);
        }
        if (y >= h2y){
            v.setY((y-h2y) * scaling*-1);
        }
        else {
            v.setY((h2y-y) * scaling);
        }
        return v;
    }

    public void updateBodies()
    {
        if (positions == null)
            return;
        for (int i = 0; i< this.positions.size(); i++)
        {
            this.positions.set(i,Main.simulation.systemState().getPositions().get(i).fromVector());
            radius.set(i, (Main.simulation.solarSystem().getCelestialBodies().get(i).getRADIUS()/scale) * Point3DConverter.getScale());
            Point3DConverter.rotateAxisX(this.positions.get(i), false, deltaX / mouseSensitivity);
            Point3DConverter.rotateAxisY(this.positions.get(i), false, deltaY / mouseSensitivity);
        }

    }


    private void rotateOnAxisX(boolean b, double v)
    {
        for (Point3D p : this.positions){
            Point3DConverter.rotateAxisX(p,b,v);
        }
    }

    private void rotateOnAxisY(boolean b, double v)
    {
        for (Point3D p : this.positions){
            Point3DConverter.rotateAxisY(p,b,v);
        }
    }

    private Ellipse2D.Double planetShape(int i)
    {
        Point2D p = Point3DConverter.convertPoint(this.positions.get(i));
        return new Ellipse2D.Double(p.getX(),p.getY(),this.radius.get(i),this.radius.get(i));
    }

    public void render(Graphics graphics)
    {
        if (started){
            if (positions == null)
                createPoints();
            Graphics2D g = (Graphics2D) graphics;
            g.setColor(Color.BLUE);
            g.fill(planetShape(0));
            for (int i = 1; i< Main.simulation.systemState().getPositions().size(); i++)
            {
                g.setColor(new Color(100,233,100));
                g.fill(planetShape(i));
            }
        }
    }

    private void createPoints()
    {
        this.positions = new ArrayList<>();
        this.radius = new ArrayList<>();
        for (int i = 0; i< Main.simulation.systemState().getPositions().size(); i++)
        {
            this.radius.add(Main.simulation.solarSystem().getCelestialBodies().get(i).getRADIUS());
            this.positions.add(Main.simulation.systemState().getPositions().get(i).fromVector());
        }
    }

}
