package group17.Math.Lib;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {

    static Point3D p;

    @Test
    @DisplayName("GetXCoordinate")
    void GetXCoordinate() {
        p = new Point3D(-1.1, .1, .3);
        assertAll(
                () -> assertEquals(-1.1, p.getXCoordinate()),
                () -> assertEquals(-1.1, p.x),
                () -> assertEquals(0, p.xOffset));
    }

    @Test
    @DisplayName("GetYCoordinate")
    void GetYCoordinate() {
        p = new Point3D(-1.1, .1, .3);
        assertAll(
                () -> assertEquals(.1, p.getYCoordinate()),
                () -> assertEquals(.1, p.y),
                () -> assertEquals(0, p.yOffset));
    }

    @Test
    @DisplayName("GetZCoordinate")
    void GetZCoordinate() {
        p = new Point3D(-1.1, .1, .3);
        assertAll(
                () -> assertEquals(.3, p.getZCoordinate()),
                () -> assertEquals(.3, p.z),
                () -> assertEquals(0, p.zOffset));
    }

    @Test
    @DisplayName("EuclidDist")
    void EuclidDist() {
        p = new Point3D(3.0, 4.0, 8.0);
        Point3D p2 = new Point3D(.5, .25, .5);
        assertEquals(8.75, p.euclidDist(p2));
        assertEquals(8.75, Point3D.euclidDist(p, p2));

    }

    @Test
    @DisplayName("Translate")
    void Translate() {
        p = new Point3D(3.0, 4.0, 8.0);
        p.translate(2, 2, 2);
        assertNotEquals(new Point3D(5, 6, 10), p);
        assertEquals(5, p.getXCoordinate());
        assertEquals(6, p.getYCoordinate());
        assertEquals(10, p.getZCoordinate());

    }

    @Test
    @DisplayName("Scale")
    void Scale() {
        p = new Point3D(30.0, 40.0, 80.0);
        p.scale(10);
        assertEquals(new Point3D(3, 4, 8), p);
    }

    @Test
    @DisplayName("TestToString")
    void TestToString() {
        assertEquals("( 1.0 , 2.0 , 3.0 )", new Point3D(1, 2, 3).toString());
    }


    @Test
    @DisplayName("TestEquals")
    void TestEquals() {
        Point3D p = new Point3D(1, 1, 1);
        assertNotEquals(new Point3D(0, 0, 0), new Object());
        assertEquals(new Point3D(1, 1, 1), p);
        assertNotEquals(new Point3D(1, 1, 0), p);
        assertNotEquals(new Point3D(1, 0, 1), p);
        assertNotEquals(new Point3D(0, 0, 0), p);
        assertNotEquals(new Point3D(0, 1, 1), p);
        assertNotEquals(new Point3D(1, 0, 0), p);
        assertNotEquals(new Point3D(0, 1, 0), p);
        assertNotEquals(new Point3D(0, 0, 1), p);

        assertNotEquals(new Point3D(0, 0, 0).translate(1, 0, 0), p);
        assertNotEquals(new Point3D(0, 0, 0).translate(0, 1, 0), p);
        assertNotEquals(new Point3D(0, 0, 0).translate(0, 0, 1), p);
        assertNotEquals(new Point3D(0, 0, 0).translate(1, 1, 0), p);
        assertNotEquals(new Point3D(0, 0, 0).translate(1, 0, 1), p);
        assertNotEquals(new Point3D(0, 0, 0).translate(0, 1, 1), p);
        assertNotEquals(new Point3D(0, 0, 0).translate(1, 1, 1), p);


        assertNotEquals(new Point3D(1, 1, 1).translate(1, 0, 0), p);
        assertNotEquals(new Point3D(1, 1, 1).translate(0, 1, 0), p);
        assertNotEquals(new Point3D(1, 1, 1).translate(0, 0, 1), p);
        assertNotEquals(new Point3D(1, 1, 1).translate(1, 1, 0), p);
        assertNotEquals(new Point3D(1, 1, 1).translate(1, 0, 1), p);
        assertNotEquals(new Point3D(1, 1, 1).translate(0, 1, 1), p);
        assertNotEquals(new Point3D(1, 1, 1).translate(1, 1, 1), p);

    }


    @Test
    @DisplayName("TestHeading")
    void Heading() {
        assertNotEquals(new Point3D(0, 0, 0).heading(), new Point3D(-1, 1, 0).heading());
    }
}