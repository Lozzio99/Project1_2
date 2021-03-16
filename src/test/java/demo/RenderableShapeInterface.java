package demo;

import group17.phase1.Titan.Simulation.Gravity.Vector3dInterface;

import java.awt.*;

public interface RenderableShapeInterface
{
    void render(Graphics g);

    //translate with result of the time step function
    void translate(double x, double y, double z);

    void rotate(boolean clockwise, double xDeg, double yDeg, double zDeg, Vector3dInterface lightingVector3D);

    void setLighting(Vector3dInterface lighting);

}
