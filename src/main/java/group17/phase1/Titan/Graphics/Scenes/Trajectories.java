package group17.phase1.Titan.Graphics.Scenes;

import group17.phase1.Titan.Graphics.Geometry.Point3D;
import group17.phase1.Titan.Graphics.Geometry.Point3DConverter;
import group17.phase1.Titan.Main;

import java.awt.*;
import java.awt.geom.Line2D;

public class Trajectories extends SimulationScene
{

    Bag[] trajectories;
    @Override
    public void init(){
        super.init();
        this.trajectories = new Bag[super.getPlanetPositions().length];
        for (int i = 0; i< super.getPlanetPositions().length; i++){
            this.trajectories[i] = new Bag();
            //this.trajectories[i].add(super.getPlanetPositions()[i]);
        }
    }


    @Override
    public void paintComponent(Graphics graphics)
    {
        Graphics2D g = (Graphics2D) graphics;
        super.paintComponent(g);
        for (int i = 0; i< this.trajectories.length; i++){
            g.setColor(Main.simulation.solarSystem().getCelestialBodies().get(i).getColour());
            for (int k = this.trajectories[i].insert; k< this.trajectories[i].getTrajectories().length-1; k++){
                if (this.trajectories[i].getTrajectories()[k+1] == null)
                    break;
                g.draw(new Line2D.Double(
                        Point3DConverter.convertPoint(this.trajectories[i].getTrajectories()[k]),
                        Point3DConverter.convertPoint(this.trajectories[i].getTrajectories()[k+1])));
            }
            for (int k = 0; k< this.trajectories[i].insert-1; k++){
                if (this.trajectories[i].getTrajectories()[k+1] == null)
                    break;
                g.draw(new Line2D.Double(
                        Point3DConverter.convertPoint(this.trajectories[i].getTrajectories()[k]),
                        Point3DConverter.convertPoint(this.trajectories[i].getTrajectories()[k+1])));
            }
        }
    }

    @Override
    public void update() {
        super.update();
        for (int i = 0; i < super.getPlanetPositions().length; i++) {
            this.trajectories[i].add(super.getPlanetPositions()[i]);
            for (int k = 0; k < this.trajectories[i].getTrajectories().length; k++) {
                if (this.trajectories[i].getTrajectories()[k] == null)
                    break;

                //most pleasant "bug" of my life
                Point3DConverter.rotateAxisX(this.trajectories[i].getTrajectories()[k], false, deltaY / mouseSensitivity);
                Point3DConverter.rotateAxisY(this.trajectories[i].getTrajectories()[k], false, deltaX / mouseSensitivity);
            }
        }

    }

    class Bag
    {
       Point3D[] trajectories;
       int insert;
       boolean loop;
       Bag(){
           this.trajectories = new Point3D[1000];
           this.insert = 0;
           this.loop = false;
       }
       void add(Point3D p)
       {
           this.trajectories[this.insert] = p;
           this.insert++;
           if (this.insert == this.trajectories.length)
           {
               this.insert = 0;
           }
       }

       Point3D[] getTrajectories(){
           return this.trajectories;
       }

    }




}
