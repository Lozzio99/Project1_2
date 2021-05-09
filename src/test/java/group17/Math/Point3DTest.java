package group17.Math;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    }

    @Test
    @DisplayName("Scale")
    void Scale() {

    }

    @Test
    @DisplayName("TestToString")
    void TestToString() {
    }
}