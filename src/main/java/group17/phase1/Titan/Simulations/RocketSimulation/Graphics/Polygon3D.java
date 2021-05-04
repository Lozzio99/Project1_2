package group17.phase1.Titan.Simulations.RocketSimulation.Graphics;

import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.Physics.Math.Point3D;
import group17.phase1.Titan.Physics.Math.Point3DConverter;
import group17.phase1.Titan.Physics.Math.Vector3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Polygon3D {

	private static final double AmbientLight = 0.15;
	private final Point3D[] points;
	private Color baseColor, lightingColor;
	private boolean visible;

	public Polygon3D(Color color, Point3D... points) {
		this.baseColor = this.lightingColor = color;
		this.points = new Point3D[points.length];
		for (int i = 0; i < points.length; i++) {
			Point3D p = points[i];
			this.points[i] = new Point3D(p.x, p.y, p.z);
		}
		this.updateVisibility();
	}

	public Polygon3D(Point3D... points) {
		this.baseColor = this.lightingColor = Color.WHITE;
		this.points = new Point3D[points.length];
		for (int i = 0; i < points.length; i++) {
			Point3D p = points[i];
			this.points[i] = new Point3D(p.x, p.y, p.z);
		}
		this.updateVisibility();
	}

	public static Polygon3D[] sortPolygons(Polygon3D[] polygons) {

		List<Polygon3D> polygonsList = new ArrayList<>(Arrays.asList(polygons));

		polygonsList.sort((p1, p2) -> {
			Point3D p1Average = p1.getAveragePoint();
			Point3D p2Average = p2.getAveragePoint();
			double p1Dist = Point3D.euclidDist(p1Average, Point3D.origin);
			double p2Dist = Point3D.euclidDist(p2Average, Point3D.origin);
			double diff = p1Dist - p2Dist;
			if (diff == 0) {
				return 0;
			}
			return diff < 0 ? 1 : -1;
		});

		for (int i = 0; i < polygons.length; i++) {
			polygons[i] = polygonsList.get(i);
		}

		return polygons;
	}

	public void render(Graphics g) {
		if (!this.visible) return;

		Polygon poly = new Polygon();
		for (int i = 0; i < this.points.length; i++) {
			Point p = Point3DConverter.convertPoint(this.points[i]);
			poly.addPoint(p.x, p.y);
		}

		g.setColor(this.lightingColor);
		g.fillPolygon(poly);
	}

	public void translate(double x, double y, double z) {
		for (Point3D p : points) {
			p.xOffset += x;
			p.yOffset += y;
			p.zOffset += z;
		}
		this.updateVisibility();
	}

	public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees, Vector3dInterface lightVector) {
		for (Point3D p : points) {
			Point3DConverter.rotateAxisX(p, CW, xDegrees);
			Point3DConverter.rotateAxisY(p, CW, yDegrees);
			Point3DConverter.rotateAxisZ(p, CW, zDegrees);
		}
		this.updateVisibility();
		this.setLighting(lightVector);
	}

	public double getAverageX() {
		double sum = 0;
		for (Point3D p : this.points) {
			sum += p.x + p.xOffset;
		}

		return sum / this.points.length;
	}

	public double getAverageY() {
		double sum = 0;
		for (Point3D p : this.points) {
			sum += p.y + p.yOffset;
		}

		return sum / this.points.length;
	}

	public double getAverageZ() {
		double sum = 0;
		for (Point3D p : this.points) {
			sum += p.z + p.zOffset;
		}

		return sum / this.points.length;
	}

	public void setColor(Color color) {
		this.baseColor = color;
	}

	public void translate(Point3D location) {
		for (Point3D p : points) {
			p.xOffset = location.getXCoordinate();
			p.yOffset = location.getYCoordinate();
			p.zOffset = location.getZCoordinate();
		}
		this.updateVisibility();
	}

	public void setLighting(Vector3dInterface lightVector) {
		if (this.points.length < 3) {
			return;
		}
		Vector3dInterface v1 = new Vector3D(this.points[0], this.points[1]);
		Vector3dInterface v2 = new Vector3D(this.points[1], this.points[2]);
		Vector3dInterface normal = Vector3D.normalize(Vector3D.cross(v2, v1));
		double dot = Vector3D.dot(normal, lightVector);
		double sign = dot < 0 ? -1 : 1;
		dot = sign * dot * dot;
		dot = (dot + 1) / 2 * (1 - AmbientLight);

		double lightRatio = Math.min(1, Math.max(0, AmbientLight + dot));
		this.updateLightingColor(lightRatio);
	}

	public boolean isVisible() {
		return this.visible;
	}

	private void updateVisibility() {
		this.visible = true;
		//this.visible = this.getAverageX() < 0;
	}

	private void updateLightingColor(double lightRatio) {
		int red = (int) (this.baseColor.getRed() * lightRatio);
		int green = (int) (this.baseColor.getGreen() * lightRatio);
		int blue = (int) (this.baseColor.getBlue() * lightRatio);
		this.lightingColor = new Color(red, green, blue);
	}

	private Point3D getAveragePoint() {
		double x = 0;
		double y = 0;
		double z = 0;
		for (Point3D p : this.points) {
			x += p.x + p.xOffset;
			y += p.y + p.yOffset;
			z += p.z + p.zOffset;
		}

		x /= this.points.length;
		y /= this.points.length;
		z /= this.points.length;

		return new Point3D(x, y, z);
	}

	public void seek(Vector3dInterface dir) {
		this.points[0] = dir.mul(200).fromVector();
		this.points[1] = (dir.mul(10)).fromVector();
		this.points[2] = (dir.mul(10)).fromVector();
	}
}
