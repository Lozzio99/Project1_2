package group17.Math.Lib;

import group17.Interfaces.Vector3dInterface;

import java.io.Serializable;
import java.util.Random;

/**
 * The type Matrix.
 */
public class Matrix implements Serializable {
    /**
     * The P.
     */
    static int[] p = new int[]{3, 5, 7, 11, 13, 17, 23, 29, 31, 37};
    private final transient long SERIAL_ID = new Random().nextLong() * p[new Random().nextInt(p.length - 1)];
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
     * Constructor with null matrix
     *
     * @param num_rows the number of rows
     * @param num_cols the number of columns Construct a null matrix with given dimensions
     */
    public Matrix(int num_rows, int num_cols) {
        this.n_cols = num_cols;
        this.n_rows = num_rows;
        this.matrix = new double[num_rows][num_cols];
    }

    /**
     * Constructor from array
     *
     * @param input the array resulting in a row Construct a single column matrix
     */
    public Matrix(double[] input) {
        this.matrix = clone(input);
        this.n_rows = input.length;
        this.n_cols = 1;
    }

    /**
     * Constructor from array
     *
     * @param matrix the double dimensions array Construct a matrix with the given values
     */
    public Matrix(double[][] matrix) {
        this.n_rows = matrix.length;
        this.n_cols = matrix[0].length;
        this.matrix = clone(matrix);
    }

    /**
     * method to clone the matrix
     *
     * @param m the Matrix to copy
     * @return a new Matrix with the double dimensions array copied
     */
    public static Matrix clone(Matrix m) {
        double[][] result = new double[m.n_rows][m.n_cols];
        for (int i = 0; i < m.n_rows; i++) {
            for (int k = 0; k < m.n_cols; k++) {
                result[i][k] = m.getMatrix()[i][k];
            }
        }
        return new Matrix(result);
    }

    /**
     * LA matrix transpose
     *
     * @param m the matrix to transpose
     * @return a new matrix equal but transposed
     */
    public static Matrix transpose(Matrix m) {
        double[][] result = new double[m.n_cols][m.n_rows];
        for (int i = 0; i < m.n_cols; i++) {
            for (int k = 0; k < m.n_rows; k++) {
                result[i][k] = m.getMatrix()[k][i];
            }
        }
        return new Matrix(result);
    }

    /**
     * static method for the dot product
     *
     * @param n the left sided matrix
     * @param m the right sided matrix
     * @return a new matrix resulting from the dot product
     */
    public static Matrix product(Matrix n, Matrix m) {
        if (n.n_rows != m.n_rows || n.n_cols != m.n_cols) {
            System.out.println(n.n_rows + " -> " + m.n_rows);
            System.out.println(n.n_cols + " -> " + m.n_cols);
            System.out.println("error");
            return null;
        }
        double[][] res = new double[n.n_rows][m.n_cols];
        for (int i = 0; i < n.n_rows; i++) {
            for (int j = 0; j < n.n_cols; j++) {
                res[i][j] = n.getMatrix()[i][j] * m.getMatrix()[i][j];
            }
        }
        return new Matrix(res);
    }

    /**
     * static method for the matrix multiplication
     *
     * @param n the left side matrix
     * @param m the right side matrix
     * @return the product from the two in a 2-dim array
     */
    public static double[][] multiplyM(Matrix n, Matrix m) {
        if (n.n_cols != m.n_rows) {
            System.out.println("error");
            return null;
        }
        double[][] result = new double[n.n_rows][m.n_cols];
        for (int i = 0; i < n.n_rows; i++) {
            for (int j = 0; j < m.n_cols; j++) {
                double sum = 0;
                for (int k = 0; k < n.n_cols; k++) {
                    sum += n.getMatrix()[i][k] * m.getMatrix()[k][j];
                }
                result[i][j] = sum;
            }
        }
        return result;
    }

    /**
     * static method for the matrix multiplication
     *
     * @param n the left side matrix
     * @param m the right side matrix
     * @return the product from the two in a matrix
     */
    public static Matrix multiply(Matrix n, Matrix m) {
        if (n.n_cols != m.n_rows) {
            System.out.println("error");
            return null;
        }
        double[][] res = new double[n.n_rows][m.n_cols];
        for (int i = 0; i < n.n_rows; i++) {
            for (int j = 0; j < m.n_cols; j++) {
                double sum = 0;
                for (int k = 0; k < n.n_cols; k++) {
                    sum += n.getMatrix()[i][k] * m.getMatrix()[k][j];
                }
                res[i][j] = sum;
            }
        }
        return new Matrix(res);
    }

    /**
     * Static method for matrix-vector multiplication
     *
     * @param m left side matrix
     * @param v right side vector
     * @return vector as a product of multiplication
     */
    public static Vector3D multiplyVector(Matrix m, Vector3D v) {
        Matrix n = new Matrix(
                new double[][]{{v.getX()}, {v.getY()}, {v.getZ()}}
        );
        Matrix res = multiply(m, n);
        return new Vector3D(res.matrix[0][0], res.matrix[1][0], res.matrix[2][0]);
    }

    /**
     * mathod for the activation function
     *
     * @param x the value to activate
     * @return the value activated
     */
    public static double activation(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    /**
     * method to calculate the derivative of the matrix
     *
     * @param m the given matrix
     * @return the new matrix with the derivative of the old one
     */
    public static Matrix dfunc(Matrix m) {
        double[][] result = new double[m.n_rows][m.n_cols];
        for (int i = 0; i < m.n_rows; i++) {
            for (int k = 0; k < m.n_cols; k++) {
                result[i][k] = m.getMatrix()[i][k] * (1 - m.getMatrix()[i][k]);
            }
        }
        return new Matrix(result);
    }

    /**
     * static method that performs the activation on a matrix
     *
     * @param m the given matrix
     * @return a new matrix after each value has been activated
     */
    public static Matrix map(Matrix m) {
        double[][] result = new double[m.n_rows][m.n_cols];
        for (int i = 0; i < m.n_rows; i++) {
            for (int k = 0; k < m.n_cols; k++) {
                result[i][k] = activation(m.getMatrix()[i][k]);
            }
        }
        return new Matrix(result);
    }

    /**
     * static method that performs a randomization of a matrix
     *
     * @param m the old matrix
     * @return a new Matrix randomized from -1 to 1
     */
    public static Matrix randomize(Matrix m) {
        double[][] res = new double[m.n_rows][m.n_cols];
        for (int i = 0; i < m.n_rows; i++) {
            for (int k = 0; k < m.n_cols; k++) {
                res[i][k] = new Random().nextDouble();
                if (new Random().nextDouble() < 0.5) {
                    res[i][k] *= -1;
                }
            }
        }
        return new Matrix(res);
    }

    /**
     * Div matrix.
     *
     * @param m the m
     * @param v the v
     * @return the matrix
     */
    public static Matrix div(Matrix m, Matrix v) {
        double[][] res = new double[v.n_rows][v.n_cols];
        for (int i = 0; i < m.getMatrix().length; i++) {
            double s = 0;
            for (int k = 0; k < m.getMatrix()[i].length; k++)
                s += m.getMatrix()[i][k];
            res[i][0] = s / v.getMatrix()[i][0];
        }
        return new Matrix(res);
    }

    /**
     * static method to multiply a matrix by a single value
     *
     * @param m     the given matrix
     * @param value the value to add
     * @return a new Matrix after the evaluation
     */
    public static Matrix scaleMatrix(Matrix m, double value) {
        double[][] res = new double[m.n_rows][m.n_cols];
        for (int i = 0; i < m.n_rows; i++) {
            for (int k = 0; k < m.n_cols; k++) {
                res[i][k] = m.matrix[i][k] * value;
            }
        }
        return new Matrix(res);
    }

    /**
     * static method to perform a matrix subtraction
     *
     * @param n the left side matrix
     * @param m the right side matrix
     * @return a new matrix after the subtraction
     */
    public static Matrix subtract(Matrix n, Matrix m) {
        if (n.n_rows != m.n_rows || n.n_cols != m.n_cols) {
            System.out.println("error");
            return null;
        }
        double[][] result = new double[n.n_rows][n.n_cols];
        for (int i = 0; i < n.n_rows; i++) {
            for (int k = 0; k < n.n_cols; k++) {
                result[i][k] = n.getMatrix()[i][k] - m.getMatrix()[i][k];
            }
        }
        return new Matrix(result);
    }

    /**
     * static method to perform a matrix subtraction
     *
     * @param n the left side matrix
     * @param m the right side matrix
     * @return a 2-dim array after the subtraction
     */
    public static double[][] subtract(double[][] n, double[][] m) {
        if (n.length != m.length || n[0].length != m[0].length) {
            System.out.println("error");
            return null;
        }
        double[][] result = new double[n.length][n[0].length];
        for (int i = 0; i < n.length; i++) {
            for (int k = 0; k < n[0].length; k++) {
                result[i][k] = n[i][k] - m[i][k];
            }
        }
        return result;
    }

    /**
     * static method to perform a matrix addition
     *
     * @param m the right side matrix
     * @param n the left side matrix
     * @return a 2-dim array after the addition
     */
    public static double[][] add(double[][] m, double[][] n) {

        if (m.length != n.length && m[0].length != n[0].length) {
            System.out.println("error");
            return null;
        }
        for (int i = 0; i < m.length; i++) {
            for (int k = 0; k < m[0].length; k++) {
                m[i][k] += n[i][k];
            }
        }
        return m;
    }

    /**
     * static method that combines @method dfunc and map
     *
     * @param m the matrix that comes activated and derivated
     * @return the matrix
     */
    public static Matrix ddfunc(Matrix m) {
        double[][] res = new double[m.n_rows][m.n_cols];
        for (int i = 0; i < m.n_rows; i++) {
            for (int k = 0; k < m.n_cols; k++) {
                res[i][k] = (activation(m.getMatrix()[i][k]) * (1 - activation(m.getMatrix()[i][k])));
            }
        }
        return new Matrix(res);
    }

    /**
     * static method to perform a matrix addition
     *
     * @param n the left side matrix
     * @param m the right side matrix
     * @return a new matrix after the addition
     */
    public static Matrix add(Matrix n, Matrix m) {
        if (n.n_rows != m.n_rows || n.n_cols != m.n_cols) {
            System.out.println("error");
            return null;
        }
        double[][] res = new double[n.n_rows][n.n_cols];
        for (int i = 0; i < n.n_rows; i++) {
            for (int k = 0; k < n.n_cols; k++) {
                res[i][k] = n.getMatrix()[i][k] + m.getMatrix()[i][k];
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
            System.arraycopy(in[i], 0, a[i], 0, in[0].length);
        }
        int n = a.length;
        double[][] x = new double[n][n];
        double[][] b = new double[n][n];
        int[] index = new int[n];
        for (int i = 0; i < n; ++i)
            b[i][i] = 1;

        // Transform the matrix into an upper triangle
        gaussian(a, index);

        // Update the matrix b[i][j] with the ratios stored
        for (int i = 0; i < n - 1; ++i)
            for (int j = i + 1; j < n; ++j)
                for (int k = 0; k < n; ++k)
                    b[index[j]][k]
                            -= a[index[j]][i] * b[index[i]][k];

        // Perform backward substitutions
        for (int i = 0; i < n; ++i) {
            x[n - 1][i] = b[index[n - 1]][i] / a[index[n - 1]][n - 1];
            for (int j = n - 2; j >= 0; --j) {
                x[j][i] = b[index[j]][i];
                for (int k = j + 1; k < n; ++k) {
                    x[j][i] -= a[index[j]][k] * x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }

    /**
     * Gaussian.
     *
     * @param a     the a
     * @param index the index
     */
    public static void gaussian(double[][] a, int[] index) {
        int n = index.length;
        double[] c = new double[n];

        // Initialize the index
        for (int i = 0; i < n; ++i)
            index[i] = i;

        // Find the rescaling factors, one from each row
        for (int i = 0; i < n; ++i) {
            double c1 = 0;
            for (int j = 0; j < n; ++j) {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }

        // Search the pivoting element from each column
        int k = 0;
        for (int j = 0; j < n - 1; ++j) {
            double pi1 = 0;
            for (int i = j; i < n; ++i) {
                double pi0 = Math.abs(a[index[i]][j]);
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
                double pj = a[index[i]][j] / a[index[j]][j];

                // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;

                // Modify other elements accordingly
                for (int l = j + 1; l < n; ++l)
                    a[index[i]][l] -= pj * a[index[j]][l];
            }
        }
    }

    //
    //    00   01   02
    //
    //    10   11   12
    //
    //    20   21   22

    /**
     * method to clone the matrix
     *
     * @param m the double dimensions array to copy
     * @return the double dimensions array copied
     */
    public double[][] clone(double[][] m) {
        double[][] result = new double[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            System.arraycopy(m[i], 0, result[i], 0, m[0].length);
        }
        return result;
    }

    /**
     * method to clone the matrix
     *
     * @param m the single dimension array to copy
     * @return the double dimensions array copied
     */
    public double[][] clone(double[] m) {
        double[][] result = new double[m.length][1];
        for (int i = 0; i < m.length; i++) {
            result[i][0] = m[i];
        }
        return result;
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
     * method to assign a new matrix to the instance field
     *
     * @param m the double dimensions array to set
     */
    public void setMatrix(double[][] m) {
        this.n_rows = m.length;
        this.n_cols = m[0].length;
        this.matrix = clone(m);
    }

    /**
     * method to assign a new matrix to the instance field
     *
     * @param m the matrix to set
     */
    public void setMatrix(Matrix m) {
        this.n_rows = m.n_rows;
        this.n_cols = m.n_cols;
        this.matrix = clone(m.getMatrix());
    }


    //--------------------------------------------------------//

    //for later usage methods

    //-------------------------------------------------------//

    /**
     * instance method for the dot product
     *
     * @param m the right sided matrix
     * @return a new matrix resulting from the dot product
     */
    public Matrix multiply(Matrix m) {
        if (m.n_rows == 1 && m.n_cols == 1) {
            return this.multiply(m.getMatrix()[0][0]);
        }
        if (this.n_rows != m.n_rows || this.n_cols != m.n_cols) {
            System.out.println(this.n_rows + " -> " + m.n_rows);
            System.out.println(this.n_cols + " -> " + m.n_cols);
            System.out.println("error");
            return null;
        }
        double[][] res = new double[this.n_rows][m.n_cols];
        for (int i = 0; i < this.n_rows; i++) {
            for (int j = 0; j < this.n_cols; j++) {
                res[i][j] = this.getMatrix()[i][j] * m.getMatrix()[i][j];
            }
        }
        return new Matrix(res);
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

    /**
     * instance method that performs the activation on a matrix
     */
    public void map() {
        double[][] res = new double[this.n_rows][this.n_cols];
        for (int i = 0; i < this.n_rows; i++) {
            for (int k = 0; k < this.n_cols; k++) {
                res[i][k] = activation(this.getMatrix()[i][k]);
            }
        }
        this.setMatrix(res);
    }

    /**
     * Determinant 3 by 3 double.
     *
     * @return the double
     */
    public double determinant3by3() {
        double a = this.matrix[0][0] * this.matrix[1][1] * this.matrix[2][2];
        double b = this.matrix[0][1] * this.matrix[1][2] * this.matrix[2][0];
        double c = this.matrix[0][2] * this.matrix[1][0] * this.matrix[2][1];
        double d = this.matrix[0][2] * this.matrix[1][1] * this.matrix[2][0];
        double e = this.matrix[0][1] * this.matrix[1][0] * this.matrix[2][2];
        double f = this.matrix[0][0] * this.matrix[1][2] * this.matrix[2][1];
        return a + b + c - d - e - f;
    }

    /**
     * instance method to print the matrix
     */
    public void printMatrix() {
        //System.out.println(this.n_rows+" -> "+ this.n_cols);
        for (int i = 0; i < this.n_rows; i++) {
            for (int k = 0; k < this.n_cols; k++) {
                System.out.print(this.matrix[i][k] + "   ");
            }
            System.out.println();
        }
    }

    /**
     * instance method to multiply a matrix by a single value
     *
     * @param value the value to add
     * @return a new Matrix after the evaluation
     */
    public Matrix multiply(double value) {
        double[][] res = new double[this.n_rows][this.n_cols];
        for (int i = 0; i < this.n_rows; i++) {
            for (int k = 0; k < this.n_cols; k++) {
                res[i][k] = this.getMatrix()[i][k] * value;
            }
        }
        return new Matrix(res);
    }

    /**
     * method to subtract a value from a matrix
     *
     * @param value the value to subtract
     */
    public void subtract(double value) {
        for (int i = 0; i < this.n_rows; i++) {
            for (int k = 0; k < this.n_cols; k++) {
                this.matrix[i][k] -= value;
            }
        }
    }

    /**
     * method to subtract a matrix from a matrix
     *
     * @param m the matrix to subtract
     */
    public void subtract(Matrix m) {

        if (this.n_rows != m.n_rows || this.n_cols != m.n_cols) {
            System.out.println("error");
            return;
        }
        for (int i = 0; i < this.n_rows; i++) {
            for (int k = 0; k < this.n_cols; k++) {
                this.matrix[i][k] -= m.matrix[i][k];
            }
        }
    }

    /**
     * static method to perform a matrix addition with a single value
     *
     * @param value the value to add
     * @return a 2-dim array after the addition
     */
    public double[][] add(double value) {

        double[][] res = new double[this.n_rows][this.n_cols];

        for (int i = 0; i < this.n_rows; i++) {
            for (int k = 0; k < this.n_cols; k++) {

                res[i][k] = this.getMatrix()[i][k] + value;
            }
        }
        return res;
    }

    /**
     * LA matrix transpose
     * a new matrix equal is setted but transposed
     */
    public void transpose() {
        Matrix result = new Matrix(this.n_cols, this.n_rows);
        for (int i = 0; i < this.n_cols; i++) {
            for (int k = 0; k < this.n_rows; k++) {
                result.matrix[i][k] = this.getMatrix()[k][i];
            }
        }
        setMatrix(result.getMatrix());
    }

    // Method to carry out the partial-pivoting Gaussian
    // elimination.  Here index[] stores pivoting order.

    /**
     * instance method to perform a matrix addition
     *
     * @param m the matrix to add
     */
    public void add(Matrix m) {

        if (this.n_rows != m.n_rows || this.n_cols != m.n_cols) {
            System.out.println("error");
            return;
        }

        double[][] res = new double[n_rows][n_cols];
        if (m.n_rows == 1 && m.n_cols == 1) {
            res = this.add(m.getMatrix()[0][0]);
            this.setMatrix(res);
            return;
        } else
            for (int i = 0; i < this.n_rows; i++) {
                for (int k = 0; k < this.n_cols; k++) {
                    res[i][k] = this.getMatrix()[i][k] + m.getMatrix()[i][k];
                }
            }
        this.setMatrix(res);
    }

}
