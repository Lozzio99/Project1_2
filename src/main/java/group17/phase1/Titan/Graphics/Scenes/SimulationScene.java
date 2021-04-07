package group17.phase1.Titan.Graphics.Scenes;

import group17.phase1.Titan.Graphics.Geometry.Point3D;
import group17.phase1.Titan.Graphics.Geometry.Point3DConverter;
import group17.phase1.Titan.Main;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class SimulationScene extends Scene
{
    Point3D [] planetsPositions;
    double[] radius;
    Color[] colors;


    @Override
    public void paintComponent(Graphics graphics)
    {
        Graphics2D g = (Graphics2D) graphics;
        super.paintComponent(g);
        for (int i = 0; i< this.planetsPositions.length; i++)
        {
            Point p = Point3DConverter.convertPoint(this.planetsPositions[i]);
            g.setColor(this.colors[i]);
            g.fill(planetShape(this.planetsPositions[i], this.radius[i]));
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, 10));
            g.drawString(Main.simulation.solarSystem().getCelestialBodies().get(i).toString(),p.x,p.y);
        }
    }

    @Override
    public void init()
    {
        this.planetsPositions = new Point3D[Main.simulation.solarSystem().getCelestialBodies().size()];
        this.radius = new double[Main.simulation.solarSystem().getCelestialBodies().size()];
        this.colors = new Color[this.radius.length];
        for (int i = 0; i< this.planetsPositions.length; i++)
        {
            this.planetsPositions[i] = Main.simulation.systemState().getPositions().get(i).fromVector();
            this.planetsPositions[i].scale(scale);
            radius[i] = (Main.simulation.solarSystem().getCelestialBodies().get(i).getRADIUS()/scale) * Point3DConverter.getScale()* radiusMag;
            this.colors[i] = Main.simulation.solarSystem().getCelestialBodies().get(i).getColour();
        }
    }

    public Point3D[] getPlanetPositions(){
        return this.planetsPositions;
    }

    public void update()
    {
        super.update();
        this.updateBodies();
        //Main.simulation.step();
    }

    public void updateBodies()
    {
        for (int i = 0; i< this.planetsPositions.length; i++)
        {
            this.planetsPositions[i] = Main.simulation.systemState().getPositions().get(i).fromVector();
            this.planetsPositions[i].scale(scale);
            radius[i] = (Main.simulation.solarSystem().getCelestialBodies().get(i).getRADIUS()/scale) * Point3DConverter.getScale()* radiusMag;
            Point3DConverter.rotateAxisX(this.planetsPositions[i], false, totalyDif / mouseSensitivity);
            Point3DConverter.rotateAxisY(this.planetsPositions[i], false, totalxDif / mouseSensitivity);
        }

    }

    Ellipse2D.Double planetShape(Point3D position, double radius)
    {
        Point p = Point3DConverter.convertPoint(position);
        return new Ellipse2D.Double(p.getX() - radius, p.getY() - radius, radius * 2, radius * 2);
    }

}
