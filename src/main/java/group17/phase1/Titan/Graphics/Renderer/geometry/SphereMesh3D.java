package group17.phase1.Titan.Graphics.Renderer.geometry;

import group17.phase1.Titan.Graphics.Renderer.RenderableShapeInterface;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SphereMesh3D {
    public static RenderableShapeInterface createSphere(Color color, float radius, double xpos, double ypos, double zpos) {
        java.util.List<Polygon3D> tetras = new ArrayList<>();
        Point3D[] points = new Point3D[3];
        Sphere sphere = new Sphere(radius);
        int t = 0;
        for (Short i : sphere.getIndices().list) {
            float x = sphere.getPositions().list.get(i * 3) + (float) xpos;
            float y = sphere.getPositions().list.get(i * 3 + 1) + (float) ypos;
            float z = sphere.getPositions().list.get(i * 3 + 2) + (float) zpos;

            points[t] = (new Point3D(x,y,z));
            t++;
            if (t == 3)
            {
                tetras.add(new Polygon3D(color,points));
                points = new Point3D[3];
                t =0;
            }

        }

        Polygon3D[] all = new Polygon3D[tetras.size()];
        for (int i = 0; i<all.length; i++ )
            all[i] = tetras.get(i);
        Polyhedron3D tetra = new Polyhedron3D(color,false,all);
        List<Polyhedron3D> esaSphere = new ArrayList<>();
        esaSphere.add(tetra);
        return new Shape(esaSphere);
    }



    static class ListBuilder<T> {
        public final ArrayList<T> list = new ArrayList<>();

        @SafeVarargs
        final public void add(T...items) {
            for (T item : items) {
                list.add(item);
            }
        }
    }


    static class Sphere
    {

        public ListBuilder<Float> positions = new ListBuilder<>();
        public ListBuilder<Float> colors = new ListBuilder<>();
        public ListBuilder<Short> indices = new ListBuilder<>();


        public ListBuilder<Float> getPositions() {
            return positions;
        }

        public ListBuilder<Short> getIndices() {
            return indices;
        }

        public Sphere() {
            generate(
                    5f,
                    16, 12,
                    0, Math.PI * 2,
                    0, Math.PI);
        }

        public Sphere(float radius) {
            generate(
                    radius,
                    40, 30,
                    0, Math.PI * 2,
                    0, Math.PI);
        }

        private void generate(float radius, int widthSegments,
                              int heightSegments, double phiStart,
                              double phiLength, double thetaStart,
                              double thetaLength)
        {

            double thetaEnd = thetaStart + thetaLength;
            int vertexCount = ( ( widthSegments + 1 ) * ( heightSegments + 1 ) );

            int index = 0;
            ArrayList<ArrayList<Integer>> vertices = new ArrayList<>();

            for (int y = 0; y <= heightSegments; y ++ ) {

                ArrayList<Integer> verticesRow = new ArrayList<>();
                float v = y / (float)heightSegments;

                for (int x = 0; x <= widthSegments; x ++ ) {

                    float u = x / (float) widthSegments;
                    final double sin = Math.sin(thetaStart + v * thetaLength);
                    float px = (float) (-radius * Math.cos(phiStart + u * phiLength) * sin);
                    float py = (float) (radius * Math.cos(thetaStart + v * thetaLength));
                    float pz = (float) (radius * Math.sin(phiStart + u * phiLength) * sin);

                    positions.add(px, py, pz);
                    colors.add(0.5f, 0.5f, 0.5f, 1f);

                    verticesRow.add(index);
                    index++;
                }

                vertices.add(verticesRow);
            }

            for ( int y = 0; y < heightSegments; y ++ )
            {
                for ( int x = 0; x < widthSegments; x ++ )
                {

                    int v1 = vertices.get(y).get(x + 1);
                    int v2 = vertices.get(y).get(x);
                    int v3 = vertices.get(y+1).get(x);
                    int v4 = vertices.get(y+1).get(x + 1);

                    if ( y != 0 || thetaStart > 0 ) {
                        indices.add((short) v1, (short) v2, (short) v4);
                    }

                    if ( y != heightSegments - 1 || thetaEnd < Math.PI ) {
                        indices.add((short) v2, (short) v3, (short) v4);
                    }
                }
            }
        }
    }
/*
    public static void main(String[] args)
    {
        Sphere sphere = new Sphere();


        for (Short i : sphere.getIndices().list)
        {
            Float x = sphere.getPositions().list.get(i*3);
            Float y = sphere.getPositions().list.get(i*3+1);
            Float z = sphere.getPositions().list.get(i*3+2);
            System.out.println(" point at : "+ x + " > "+ y + " > "+ z);
        }


        System.out.println("\nRaw vertex and positions: (x,y,z) (r,g,b,a)");
        int vertexCount = sphere.positions.list.size() / 3;
        for (int index = 0; index < vertexCount; index++) {
            String line = String.format(
                    "(%f > %f > %f)\t(%f > %f > %f > %f)\t",
                    sphere.positions.list.get(index*3),
                    sphere.positions.list.get(index*3+1),
                    sphere.positions.list.get(index*3+2),
                    sphere.colors.list.get(index*4),
                    sphere.colors.list.get(index*4+1),
                    sphere.colors.list.get(index*4+2),
                    sphere.colors.list.get(index*4+3));

            System.out.println(line);
        }

        System.out.println("\nMesh zipped up into indices:");
        System.out.println(sphere.indices.list.toString());


    }
 */
}
