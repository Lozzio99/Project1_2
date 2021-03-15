package demo;

import group17.phase1.Titan.Physics.Trajectories.Forces.Vector3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Shape3D implements RenderableShapeInterface {

    private final List<Polyhedron3D> polyhedra;
    private Polygon3D[] polygons;

    public Shape3D(List<Polyhedron3D> polyhedra) {
        this.polyhedra = polyhedra;
        List<Polygon3D> tempList = new ArrayList<>();
        for (Polyhedron3D poly : this.polyhedra) {
            tempList.addAll(Arrays.asList(poly.getPolygons()));
        }
        this.polygons = new Polygon3D[tempList.size()];
        this.polygons = tempList.toArray(this.polygons);
        this.sortPolygons();
    }

    public void addEntity(Shape3D e) {
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
    public void translate(double x, double y, double z) {
        for(Polyhedron3D poly : this.polyhedra) {
            poly.translate(x, y, z);
        }
        this.sortPolygons();
    }


    @Override
    public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees, Vector3D lightVector3D) {
        for(Polyhedron3D poly : this.polyhedra) {
            poly.rotate(CW, xDegrees, yDegrees, zDegrees, lightVector3D);
        }
        this.sortPolygons();
    }

    @Override
    public void setLighting(Vector3D lighting) {
        for (Polyhedron3D poly : this.polyhedra) {
            poly.setLighting(lighting);
        }
    }

    private void sortPolygons() {
        Polygon3D.sortPolygons(this.polygons);
    }
}
