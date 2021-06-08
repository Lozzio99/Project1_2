package Module.Math;

/**
 * The type Partial derivative.
 */
public class PartialDerivative {

    private static final double[][] H = new double[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};

    /**
     * Method computes partial derivatives of a 3-d vector function
     *
     * @param fX 3-d vector function
     * @param v  point at which to compute derivatives
     * @param h  step size
     * @param n  number of variable, regarding which partial derivatives are computed (e.g. df1/dx, dfn/dx)
     * @return vector of partial derivatives
     */
    public static Vector3D getPartialDerivatives(NewtRaphFunction fX, Vector3D v, double h, int n) {
        Vector3dInterface diff = fX.modelFx(new Vector3D(v.getX()+H[n][0]*h, v.getY()+H[n][1]*h, v.getZ()+H[n][2]*h))
                .sub(fX.modelFx(new Vector3D(v.getX()-H[n][0]*h, v.getY()-H[n][1]*h, v.getZ()-H[n][2]*h)));
        return (Vector3D) diff.div(2*h);
    }

    /**
     * Computes all first partial derivatives of a target function
     *
     * @param fX the f x
     * @param v  vector value
     * @param h  the h
     * @return Jacobian matrix
     */
    public static double[][] getJacobianMatrix(NewtRaphFunction fX, Vector3D v, double h) {
        double[][] m = new double[3][3];
        for (int i = 0; i < m.length; i++) {
            Vector3dInterface res = getPartialDerivatives(fX, v, h, i);
            m[0][i] = res.getX();
            m[1][i] = res.getY();
            m[2][i] = res.getZ();
        }
        return m;
    }

}
