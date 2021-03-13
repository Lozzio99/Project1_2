package group17.phase1.titan.lib.ShapeBuilder;

import group17.phase1.titan.lib.geometry.Polygon3D;
import group17.phase1.titan.lib.geometry.Polyhedron3D;
import group17.phase1.titan.lib.interfaces.IShape;
import group17.phase1.titan.lib.interfaces.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Shape implements IShape {

    private final List<Polyhedron3D> polyhedra;
    private Polygon3D[] polygons;

    public Shape(List<Polyhedron3D> polyhedra) {
        this.polyhedra = polyhedra;
        List<Polygon3D> tempList = new ArrayList<>();
        for (Polyhedron3D poly : this.polyhedra) {
            tempList.addAll(Arrays.asList(poly.getPolygons()));
        }
        this.polygons = new Polygon3D[tempList.size()];
        this.polygons = tempList.toArray(this.polygons);
        this.sortPolygons();
    }

    public void addEntity(Shape e) {
        this.polyhedra.addAll(e.getPolyhedrons());
        List<Polygon3D> tempList = new ArrayList<>();
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
        for(Polygon3D poly : this.polygons) {
            poly.render(g);
        }
    }

    @Override
    public void translate(float x, float y, float z) {
        for(Polyhedron3D poly : this.polyhedra) {
            poly.translate(x, y, z);
        }
        this.sortPolygons();
    }

    @Override
    public void rotate(boolean CW, float xDegrees, float yDegrees, float zDegrees, Vector lightVector) {
        for(Polyhedron3D poly : this.polyhedra) {
            poly.rotate(CW, xDegrees, yDegrees, zDegrees, lightVector);
        }
        this.sortPolygons();
    }

    @Override
    public void setLighting(Vector lighting) {
        for (Polyhedron3D poly : this.polyhedra) {
            poly.setLighting(lighting);
        }
    }

    private void sortPolygons() {
        Polygon3D.sortPolygons(this.polygons);
    }
}
