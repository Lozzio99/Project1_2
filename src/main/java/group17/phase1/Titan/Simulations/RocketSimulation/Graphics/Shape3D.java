package group17.phase1.Titan.Simulations.RocketSimulation.Graphics;


import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.Physics.Math.Point3D;
import group17.phase1.Titan.Physics.Math.Point3DConverter;
import group17.phase1.Titan.Physics.Math.Vector3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static group17.phase1.Titan.Main.simulation;

public class Shape3D implements ShapeInterface {

	private final List<Polyhedron3D> polyhedra;
	private Polygon3D[] polygons;

	public Shape3D(List<Polyhedron3D> polyhedra) {
		this.polyhedra = polyhedra;
		List<Polygon3D> tempList = new ArrayList<Polygon3D>();
		for (Polyhedron3D poly : this.polyhedra) {
			tempList.addAll(Arrays.asList(poly.getPolygons()));
		}
		this.polygons = new Polygon3D[tempList.size()];
		this.polygons = tempList.toArray(this.polygons);
		this.sortPolygons();
	}

	public static ShapeInterface createCube(double size, double centerX, double centerY, double centerZ) {
		Point3D p1 = new Point3D(centerX + size / 2, centerY + -size / 2, centerZ + -size / 2);
		Point3D p2 = new Point3D(centerX + size / 2, centerY + size / 2, centerZ + -size / 2);
		Point3D p3 = new Point3D(centerX + size / 2, centerY + size / 2, centerZ + size / 2);
		Point3D p4 = new Point3D(centerX + size / 2, centerY + -size / 2, centerZ + size / 2);
		Point3D p5 = new Point3D(centerX + -size / 2, centerY + -size / 2, centerZ + -size / 2);
		Point3D p6 = new Point3D(centerX + -size / 2, centerY + size / 2, centerZ + -size / 2);
		Point3D p7 = new Point3D(centerX + -size / 2, centerY + size / 2, centerZ + size / 2);
		Point3D p8 = new Point3D(centerX + -size / 2, centerY + -size / 2, centerZ + size / 2);
		Color c = new Color(229, 15, 15, 220);
		Polyhedron3D tetra = new Polyhedron3D(
				new Polygon3D(c, p5, p6, p7, p8),
				new Polygon3D(c, p1, p2, p6, p5),
				new Polygon3D(c, p1, p5, p8, p4),
				new Polygon3D(c, p2, p6, p7, p3),
				new Polygon3D(c, p4, p3, p7, p8),
				new Polygon3D(c, p1, p2, p3, p4));

		List<Polyhedron3D> tetras = new ArrayList<>();
		tetras.add(tetra);

		return new Shape3D(tetras);
	}

	public static ShapeInterface createRocket(double size) {

		Point3D p1, p2, p3;

		Vector3dInterface p = simulation.system().systemState().getPositions().get(0), dir = simulation.system().systemState().getPositions().get(1).sub(p);
		dir = Vector3D.normalize(dir);

		p1 = p.add(dir.mul(size / 10)).fromVector();
		p2 = p.add(dir.mul(size / 20)).fromVector();
		p3 = p.add(dir.mul(size / 20)).fromVector();

		Point3DConverter.rotateAxisZ(p3, true, 90);
		Point3DConverter.rotateAxisZ(p2, false, 90);

		Polyhedron3D tetra = new Polyhedron3D(
				new Polygon3D(p1, p2, p3));
		List<Polyhedron3D> tetras = new ArrayList<>();
		tetras.add(tetra);
		return new Shape3D(tetras);
	}

	public void addEntity(Shape3D e) {
		this.polyhedra.addAll(e.getPolyhedrons());
		List<Polygon3D> tempList = new ArrayList<Polygon3D>();
		for (Polyhedron3D poly : this.polyhedra) {
			tempList.addAll(Arrays.asList(poly.getPolygons()));
		}
		this.polygons = new Polygon3D[tempList.size()];
		this.polygons = tempList.toArray(this.polygons);
		this.sortPolygons();
	}

	public List<Polyhedron3D> getPolyhedrons() {
		return polyhedra;
	}

	@Override
	public void render(Graphics g) {
		for (Polygon3D poly : this.polygons) {
			poly.render(g);
		}
	}

	@Override
	public void translate(double x, double y, double z) {
		for (Polyhedron3D poly : this.polyhedra) {
			poly.translate(x, y, z);
		}
		this.sortPolygons();
	}

	@Override
	public void translate(Point3D locate) {
		for (Polyhedron3D poly : this.polyhedra) {
			poly.translate(locate);
		}
	}

	@Override
	public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees, Vector3dInterface lightVector) {
		for (Polyhedron3D poly : this.polyhedra) {
			poly.rotate(CW, xDegrees, yDegrees, zDegrees, lightVector);
		}
		this.sortPolygons();
	}

	@Override
	public void setLighting(Vector3dInterface lightVector) {
		for (Polyhedron3D poly : this.polyhedra) {
			poly.setLighting(lightVector);
		}
	}

	//TODO : implement
	@Override
	public void seek(Vector3dInterface dir) {
		for (Polyhedron3D p : this.polyhedra) {
			p.seek(dir);
		}
	}

	private void sortPolygons() {
		Polygon3D.sortPolygons(this.polygons);
	}


}
