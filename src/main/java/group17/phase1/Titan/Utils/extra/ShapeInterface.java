package group17.phase1.Titan.Utils.extra;

import group17.phase1.Titan.Interfaces.Vector3dInterface;

import java.awt.*;
import java.util.List;


public interface ShapeInterface
{
    void render(Graphics g);

    void translate(double x, double y, double z);

    void rotate(boolean cw, double xDeg, double yDeg, double zDeg, Vector3dInterface lightVector);

    void setLighting(Vector3dInterface lightSource);

    List<Polyhedron3D> getPolyhedrons();

}
