package group17.Math.Lib;

import group17.Interfaces.Vector3dInterface;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Arrays;

import static java.lang.System.arraycopy;

/**
 * The type Matrix.
 */
public class Matrix implements Serializable {
    /**
     * The N rows.
     */
    public int n_rows;
    /**
     * The N cols.
     */
    public int n_cols;
    /**
     * The Matrix.
     */
    public double[][] matrix;


    /**
     * Constructor from array
     *
     * @param matrix the double dimensions array Construct a matrix with the given values
     */
    public Matrix(double[][] matrix) {
        this.n_rows = matrix.length;
        this.n_cols = matrix[0].length;
        this.matrix = matrix;
    }

    /**
     * static method for the matrix multiplication
     *
     * @param A the left side matrix
     * @param B the right side matrix
     * @return the product from the two in a matrix
     */
    @Contract("_, _ -> new")
    public static @NotNull Matrix multiply(Matrix A, Matrix B) {
        if (A.n_cols != B.n_rows)
            throw new IllegalArgumentException("Columns of A and rows  and B must have same dimensions!");

        double[][] res = new double[A.n_rows][B.n_cols];
        for (int i = 0; i < A.n_rows; i++) {
            for (int j = 0; j < B.n_cols; j++) {
                double sum = 0;
                for (int k = 0; k < A.n_cols; k++) {
                    sum += A.getMatrix()[i][k] * B.getMatrix()[k][j];
                }
                res[i][j] = sum;
            }
        }
        return new Matrix(res);
    }

    /**
     * Invert double [ ] [ ].
     *
     * @param in the in
     * @return the double [ ] [ ]
     */
    public static double[][] invert(double[][] in) {
        double[][] a = new double[in.length][in[0].length];
        for (int i = 0; i < in.length; i++) {
            arraycopy(in[i],
                    0,
                    a[i],
                    0,
                    in[0].length);
        }
        int N = a.length;

        double[][] inverse = new double[N][N], b = new double[N][N];
        int[] indexes = new int[N];

        for (int x = 0; x < N; x++) b[x][x] = 1;
        gaussian(a, indexes); // Transform the matrix into an upper triangle
        // Update the matrix b[v][u] with the ratios stored
        for (int v = 0; v < N - 1; v++)
            for (int u = v + 1; u < N; u++)
                for (int w = 0; w < N; w++) {
                    b[indexes[u]][w] -= a[indexes[u]][v] * b[indexes[v]][w];
                }

        for (int k = 0; k < N; k++) {
            inverse[N - 1][k] = (b[indexes[N - 1]][k]) / (a[indexes[N - 1]][N - 1]);
            for (int j = N - 2; j >= 0; j--) {
                inverse[j][k] = b[indexes[j]][k];        // Perform backward substitutions
                for (int l = j + 1; l < N; l++) {
                    inverse[j][k] -= a[indexes[j]][l] * inverse[l][k];
                }
                inverse[j][k] /= a[indexes[j]][j];
            }
        }
        return inverse;
    }

    /**
     * Gaussian.
     *
     * @param m     the matrix
     * @param index the index
     */
    public static void gaussian(double[][] m, int[] index) {
        int n = index.length;
        double[] c = new double[n];

        // Initialize the index
        for (int i = 0; i < n; ++i)
            index[i] = i;

        // Find the rescaling factors, one from each row
        for (int i = 0; i < n; ++i) {
            double c1 = 0;
            for (int j = 0; j < n; ++j) {
                double c0 = Math.abs(m[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }

        // Search the pivoting element from each column
        int k = 0;
        for (int j = 0; j < n - 1; ++j) {
            double pi1 = 0;
            for (int i = j; i < n; ++i) {
                double pi0 = Math.abs(m[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) {
                    pi1 = pi0;
                    k = i;
                }
            }

            // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i = j + 1; i < n; ++i) {
                double pj = m[index[i]][j] / m[index[j]][j];

                // Record pivoting ratios below the diagonal
                m[index[i]][j] = pj;

                // Modify other elements accordingly
                for (int l = j + 1; l < n; ++l)
                    m[index[i]][l] -= pj * m[index[j]][l];
            }
        }
    }
    /**
     * accessor method
     *
     * @return the matrix of this field
     */
    public double[][] getMatrix() {
        return this.matrix;
    }

    /**
     * Instance method for matrix-vector multiplication
     *
     * @param v right side vector
     * @return vector as a result of product
     */
    public Vector3dInterface multiplyVector(Vector3dInterface v) {
        Matrix n = new Matrix(
                new double[][]{{v.getX()}, {v.getY()}, {v.getZ()}}
        );
        Matrix res = Matrix.multiply(new Matrix(matrix), n);
        return new Vector3D(res.matrix[0][0], res.matrix[1][0], res.matrix[2][0]);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (double[] m : this.matrix) s.append(Arrays.toString(m)).append("\n");

        return "Matrix{ " + n_rows +
                "x" + n_cols +
                ",\n" + s.toString().trim() +
                '}';
    }
}
