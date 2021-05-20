package group17.Math.Lib;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.lang.StrictMath.abs;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Graphics Implementation test
 */
class Point3DConverterTest {

    Point3D LEFT, RIGHT;

    @BeforeEach
    void setUp() {
        LEFT = new Point3D(-400, 100, 0);
        RIGHT = new Point3D(400, 100, 0);
    }

    @Test
    @DisplayName("ZoomIn")
    void ZoomIn() {
        double scaleBefore = Point3DConverter.getScale();
        Point3DConverter.zoomIn();
        double scaleAfter = Point3DConverter.getScale();
        assertNotEquals(scaleBefore, scaleAfter);
        assertTrue(scaleAfter > scaleBefore);
    }

    @Test
    @DisplayName("ZoomOut")
    void ZoomOut() {
        double scaleBefore = Point3DConverter.getScale();
        Point3DConverter.zoomOut();
        double scaleAfter = Point3DConverter.getScale();
        assertNotEquals(scaleBefore, scaleAfter);
        assertTrue(scaleAfter < scaleBefore);
    }

    @Test
    @DisplayName("GetScale")
    void GetScale() {
        double scaleBefore = Point3DConverter.getScale();
        assertTrue(scaleBefore != 0);
    }

    @Test
    @DisplayName("ConvertPoint")
    void ConvertPoint() {
        var x1 = Point3DConverter.convertPoint(LEFT);
        var x2 = Point3DConverter.convertPoint(RIGHT);
        assertNotNull(x1);
        assertNotNull(x2);
        assertNotEquals(x1, x2);
        assertNotEquals(x1.getX(), x2.getX());
        assertTrue(abs(x1.getY() - x2.getY()) < 5);
    }

    @Test
    @DisplayName("RotateAxisX")
    void RotateAxisX() {

        double x = LEFT.x, y = LEFT.y, z = LEFT.z;
        Point3DConverter.rotateAxisX(LEFT, false, 90);
        assertEquals(x, LEFT.x);
        assertNotEquals(y, LEFT.y);
        assertNotEquals(z, LEFT.z);
        assertEquals(y, LEFT.z);

    }

    @Test
    @DisplayName("RotateAxisY")
    void RotateAxisY() {
        double x = LEFT.x, y = LEFT.y, z = LEFT.z;
        Point3DConverter.rotateAxisY(LEFT, false, 90);
        assertEquals(y, LEFT.y);
        assertNotEquals(x, LEFT.x);
        assertNotEquals(z, LEFT.z);
        assertEquals(x, LEFT.z);
    }

    @Test
    @DisplayName("RotateAxisZ")
    void RotateAxisZ() {
        double x = LEFT.x, y = LEFT.y, z = LEFT.z;
        Point3DConverter.rotateAxisZ(LEFT, false, 90);
        assertEquals(z, LEFT.z);
        assertNotEquals(x, LEFT.x);
        assertNotEquals(y, LEFT.y);
        assertEquals(x, LEFT.y);
    }
}