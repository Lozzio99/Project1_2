package group17.Math.Solvers;


import group17.Interfaces.ODEFunctionInterface;
import group17.Interfaces.ODESolverInterface;
import group17.Interfaces.StateInterface;
import group17.Interfaces.Vector3dInterface;
import group17.Main;
import group17.Math.Vector3D;


import static group17.Config.G;
import static java.lang.Double.NaN;


public class EulerSolver implements ODESolverInterface {

    public static double currTime = 0;
    public static double endTime = NaN;


    //p(x,y,z) //displacement
    //v(x,y,z) = p'(x,y,z)   //velocity
    //a(x,y,z) = dv(x,y,z) = dp(x,y,z)   //acceleration
    //           dt          dt
    public ODEFunctionInterface singleCoreF = (t, y) ->
    {
        for (int i = 0; i < y.getPositions().size(); i++) {
            Vector3dInterface totalAcc = new Vector3D(0, 0, 0);
            for (int k = 0; k < y.getPositions().size(); k++) {
                if (i != k) {
                    Vector3dInterface acc = y.getPositions().get(i).clone();
                    double squareDist = Math.pow(y.getPositions().get(i).dist(y.getPositions().get(k)), 2);
                    acc = y.getPositions().get(k).sub(acc); // Get the force vector
                    double den = Math.sqrt(squareDist);
                /*
                    ! Important !
                    if two bodies collapses into the same point
                    that would crash to NaN and consequently
                    the same in all the system
                */
                    acc = acc.mul(1 / (den == 0 ? 0.0000001 : den)); // Normalise to length 1
                    acc = acc.mul((G * Main.simulationInstance.getSystem().getCelestialBodies().get(k).getMASS()) / (squareDist == 0 ? 0.0000001 : squareDist)); // Convert force to acceleration
                    totalAcc = totalAcc.addMul(t, acc);
                    // p = h*acc(derivative of velocity)
                }
            }  // y1 =y0 + h*acc
            // y1 = y0 + p
            y.getRateOfChange().getVelocities()
                    .set(i, y.getRateOfChange().getVelocities().get(i).add(totalAcc));
        }
        return y.getRateOfChange();
    };




    //x1 = x0 + h*v0;
    //x2 = x1 + h*v1;
    //v1 = v0 + h* f(position)
    // f(pos){
    // return ((-G*M)/pos^3)* pos;}


    @Override
    public StateInterface step(ODEFunctionInterface f, double currentTime, StateInterface y, double stepSize) {
        // y1 = y0 +f(x,y0);
        currTime = currentTime;

        return y.addMul(stepSize, f.call(stepSize, y));
        //       y0  +  (y * h      dy(rate of change))

        // add (y * h) to y
    }

    @Override
    public ODEFunctionInterface getFunction() {
        return singleCoreF;
    }


    @Override
    public String toString() {
        return "EulerSolver{" +
                "function used =" + singleCoreF +
                '}';
    }

    @Override
    public void setF(ODEFunctionInterface f) {
        this.singleCoreF = f;
    }


}
