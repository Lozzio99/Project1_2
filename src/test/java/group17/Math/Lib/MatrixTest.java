package group17.Math.Lib;

import group17.Math.Matrix;
import group17.Math.Vector3D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

    static final List<double[][]> paramA = new ArrayList<>(), paramB = new ArrayList<>(), results = new ArrayList<>();

    static {
        paramA.add(new double[][]{{1, 2, 3}, {2, 0, 1}, {3, 1, 5}});
        paramB.add(new double[][]{{1, 11, 1}, {11, 2, 3}, {1, 2, 1}});
        results.add(new double[][]{{26, 21, 10}, {3, 24, 3}, {19, 45, 11}});
        paramA.add(new double[][]{{1, 2, 3}, {0, 1, 0}, {1, 1, 0}});
        paramB.add(new double[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}});
        results.add(new double[][]{{1, 2, 3}, {0, 1, 0}, {1, 1, 0}});
    }

    @ParameterizedTest(name = "testing matrix {0}")
    @ValueSource(ints = {0, 1})
    void Multiply(int index) {
        Matrix res = Matrix.multiply(new Matrix(paramA.get(index)), new Matrix(paramB.get(index)));
        assertAll(
                () -> assertArrayEquals(results.get(index)[0], res.getMatrix()[0]),
                () -> assertArrayEquals(results.get(index)[1], res.getMatrix()[1]),
                () -> assertArrayEquals(results.get(index)[2], res.getMatrix()[2])
        );
        assertThrows(IllegalArgumentException.class,
                () -> Matrix.multiply(res, new Matrix(new double[][]{{1}, {2}})));
    }

    @Test
    @DisplayName("Invert")
    void Invert() {
        Matrix b = new Matrix(new double[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}});
        Matrix result = new Matrix(Matrix.invert(b.getMatrix()));
        assertAll(
                () -> assertArrayEquals(new double[]{1, 0, 0}, result.getMatrix()[0]),
                () -> assertArrayEquals(new double[]{0, 1, 0}, result.getMatrix()[1]),
                () -> assertArrayEquals(new double[]{0, 0, 1}, result.getMatrix()[2])
        );
    }

    @Test
    @DisplayName("Gaussian")
    void Gaussian() {
        Matrix b = new Matrix(new double[][]{
                {1, 2, 4},
                {3, 6, 12},
                {6, 12, 24}});

        double[][] result = b.getMatrix();
        Matrix.gaussian(result, new int[]{0, 1, 2});
        assertAll(
                () -> assertArrayEquals(new double[]{1, 2, 4}, result[0]),
                () -> assertArrayEquals(new double[]{3, 0, 0}, result[1]),
                () -> assertArrayEquals(new double[]{6, 0, 0}, result[2])
        );

    }

    @Test
    @DisplayName("GetMatrix")
    void GetMatrix() {
        Matrix b = new Matrix(new double[][]{
                {1, 2, 4},
                {3, 6, 12},
                {6, 12, 24}});
        assertArrayEquals(new double[][]{{1, 2, 4}, {3, 6, 12}, {6, 12, 24}}, b.getMatrix());
    }

    @Test
    @DisplayName("MultiplyVector")
    void MultiplyVector() {
        Matrix b = new Matrix(new double[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}});
        assertEquals(new Vector3D(3, 3, 3), b.multiplyVector(new Vector3D(3, 3, 3)));
    }

    @Test
    @DisplayName("TestToString")
    void TestToString() {
        assertEquals("Matrix{ 3x3,\n" +
                "[1.0, 2.0, 3.0]\n" +
                "[0.0, 1.0, 0.0]\n" +
                "[1.0, 1.0, 0.0]}", new Matrix(new double[][]{{1, 2, 3}, {0, 1, 0}, {1, 1, 0}}).toString());
    }
}