package group17.phase1.Titan.Simulations.RocketSimulation.Graphics;


import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.Physics.Math.Point3D;

import java.awt.*;

public interface ShapeInterface {

	void render(Graphics g);

	void translate(double x, double y, double z);

	void translate(Point3D locate);

	void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees, Vector3dInterface lightVector);

	void setLighting(Vector3dInterface lightVector);

	void seek(Vector3dInterface vector3dInterface);
}
