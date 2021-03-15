package demo;

import group17.phase1.Titan.Physics.Trajectories.Forces.Vector3D;

import java.awt.*;

public interface RenderableShapeInterface
{
    void render(Graphics g);

    //translate with result of the time step function
    void translate(double x, double y, double z);

    void rotate(boolean clockwise, double xDeg, double yDeg, double zDeg, Vector3D lightingVector3D);

    void setLighting(Vector3D lighting);

}
