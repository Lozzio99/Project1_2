package group17.phase1.Titan.Simulations.RocketSimulation.Graphics;

import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.Physics.Math.Point3D;

import java.awt.*;

public class Polyhedron3D {

	private final Polygon3D[] polygons;
	private Color color;

	public Polyhedron3D(Color color, boolean decayColor, Polygon3D... polygons) {
		this.color = color;
		this.polygons = polygons;
		if (decayColor) {
			this.setDecayingPolygonColor();
		} else {
			this.setPolygonColor();
		}
		this.sortPolygons();
	}

	public Polyhedron3D(Polygon3D... polygons) {
		this.color = Color.WHITE;
		this.polygons = polygons;
		this.sortPolygons();
	}

	public void render(Graphics g) {
		for (Polygon3D poly : this.polygons) {
			poly.render(g);
		}
	}

	public void translate(double x, double y, double z) {
		for (Polygon3D p : this.polygons) {
			p.translate(x, y, z);
		}
		this.sortPolygons();
	}

	public void translate(Point3D location) {
		for (Polygon3D p : this.polygons) {
			p.translate(location);
		}
		this.sortPolygons();
	}

	public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees, Vector3dInterface lightVector) {
		for (Polygon3D p : this.polygons) {
			p.rotate(CW, xDegrees, yDegrees, zDegrees, lightVector);
		}
		this.sortPolygons();
	}

	public void setLighting(Vector3dInterface lightVector) {
		for (Polygon3D p : this.polygons) {
			p.setLighting(lightVector);
		}
	}

	public Polygon3D[] getPolygons() {
		return this.polygons;
	}

	private void sortPolygons() {
		Polygon3D.sortPolygons(this.polygons);
	}

	private void setPolygonColor() {
		for (Polygon3D poly : this.polygons) {
			poly.setColor(this.color);
		}
	}

	private void setDecayingPolygonColor() {
		double decayFactor = 0.97;
		for (Polygon3D poly : this.polygons) {
			poly.setColor(this.color);
			int r = (int) (this.color.getRed() * decayFactor);
			int g = (int) (this.color.getGreen() * decayFactor);
			int b = (int) (this.color.getBlue() * decayFactor);
			this.color = new Color(r, g, b);
		}
	}

	public void seek(Vector3dInterface dir) {
		for (Polygon3D p : this.polygons) {
			p.seek(dir);
		}

	}
}
