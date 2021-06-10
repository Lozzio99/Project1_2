package Module.Math;

import Module.Math.Functions.NewtRaphFunction;

import java.util.concurrent.CompletableFuture;

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
        /*
        for (int i = 0; i < m.length; i++) {
            int finalI = i;
            final double [] theResult =new double[3];
            CompletableFuture<Vector3dInterface> future =CompletableFuture.supplyAsync(() -> getPartialDerivatives(fX, v, h, finalI))
                    .thenApply(vector3D -> {
                        System.arraycopy(theResult ,0, vector3D.getVal(),0,theResult.length);
                        return vector3D;
                    });

            Vector3dInterface res = getPartialDerivatives(fX, v, h, i);
            m[0][i] = res.getX();
            m[1][i] = res.getY();
            m[2][i] = res.getZ();
        }
         */


        final double[][] theResult = new double[3][3];
        CompletableFuture<Void> allFutures =
                CompletableFuture.allOf(
                        CompletableFuture.supplyAsync(
                                () -> getPartialDerivatives(fX, v, h, 0))   // the index of the array is the thing that changes
                                .thenApply(vector3D -> {
                                    System.arraycopy(vector3D.getVal(), 0, theResult[0], 0, theResult.length);
                                    return vector3D;
                                }),
                        CompletableFuture.supplyAsync(() -> getPartialDerivatives(fX, v, h, 1))
                                .thenApply(vector3D -> {
                                    System.arraycopy(vector3D.getVal(), 0, theResult[1], 0, theResult.length);
                                    return vector3D;
                                }),
                        CompletableFuture.supplyAsync(() -> getPartialDerivatives(fX, v, h, 2))
                                .thenApply(vector3D -> {
                                    System.arraycopy(vector3D.getVal(), 0, theResult[2], 0, theResult.length);
                                    return vector3D;
                                }));
        allFutures.thenAccept(e -> {
            for (int i = 0; i < 3; i++) {   //this is probably wrong but i got a bit confused
                m[0][i] = theResult[i][0];
                m[1][i] = theResult[i][1];
                m[2][i] = theResult[i][2];
            }
        });
        return m;
    }



}
