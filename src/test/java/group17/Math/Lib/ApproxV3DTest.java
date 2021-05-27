package group17.Math.Lib;

import group17.Interfaces.Vector3dInterface;
import group17.Math.ApproxV3D;
import group17.Math.Vector3D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApproxV3DTest {

    @Test
    @DisplayName("TestHashCode")
    void TestHashCode() {
        Vector3dInterface v1, av1;
        av1 = new ApproxV3D(0, 0, 0);
        v1 = new ApproxV3D(9e-10, 9e-10, 9e-10);
        assertEquals(av1.hashCode(), v1.hashCode());
    }

    @Test
    @DisplayName("TestEquals")
    void TestEquals() {
        Vector3dInterface v1, av1;
        av1 = new ApproxV3D(0, 0, 0);
        v1 = new ApproxV3D(9e-10, 9e-10, 9e-10);
        assertEquals(av1, v1);
        assertEquals(v1, new Vector3D());
    }
}