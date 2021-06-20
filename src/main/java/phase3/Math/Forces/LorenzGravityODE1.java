package phase3.Math.Forces;

import phase3.Math.ADT.Vector3D;
import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Functions.ODEFunctionInterface;
import phase3.System.State.RateOfChange;

public class LorenzGravityODE1 {
    //private static final double sigma = 10, rho = 28, beta = 8/3.;
    private static final double sigma = 10, rho = 14, beta = 8 / 3.;
    //private static final double sigma = 10, rho = 13, beta = 8/3.;
    //private static final double sigma = 10, rho = 15, beta = 8/3.;

    private final ODEFunctionInterface<Vector3dInterface> velocity = (t, y) -> {
        Vector3dInterface[] vel = new Vector3dInterface[y.get().length];
        for (int i = 0; i < vel.length; i++) {
            Vector3dInterface e = y.get()[i];
            vel[i] = new Vector3D(
                    sigma * (e.getY() - e.getX()),
                    e.getX() * (rho - e.getZ()) - e.getY(),
                    e.getX() * e.getY() - (beta * e.getZ())
            );
        }
        return new RateOfChange<>(vel);
    };

    public final ODEFunctionInterface<Vector3dInterface> getVelocityFunction() {
        return this.velocity;
    }
}
