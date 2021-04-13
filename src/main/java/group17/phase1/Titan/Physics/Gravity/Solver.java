package group17.phase1.Titan.Physics.Gravity;

import group17.phase1.Titan.Main;
import group17.phase1.Titan.Physics.Vector3D;
import group17.phase1.Titan.interfaces.*;


public class Solver implements ODEFunctionInterface, ODESolverInterface
{
    static final double G =  6.67408e-11;
    public static double currTime = 0;
    public static double endTime = Double.MAX_VALUE;


    @Override
    public RateInterface call(double t, StateInterface y)
    {
        for (int i = 0; i< y.getPositions().size(); i++)
        {
            Vector3dInterface totalAcc = new Vector3D(0, 0, 0);
            for (int k = 0;k< y.getPositions().size(); k++)
            {
                if (i!= k)
                {
                    Vector3dInterface acc = y.getPositions().get(i).clone();
                    double squareDist = Math.pow(y.getPositions().get(i).dist(y.getPositions().get(k)),2);
                    acc.sub(y.getPositions().get(k)); // Get the force vector
                    double den = Math.sqrt(squareDist);
                    /*
                        ! Important !
                        if two bodies collapses into the same point
                        that would crash to NaN and consequently
                        the same in all the system
                     */
                    acc.mul(1 / (den == 0 ? 0.0000001 : den)); // Normalise to length 1
                    acc.mul((G * Main.simulation.solarSystem().getCelestialBodies().get(k).getMASS()) / (squareDist == 0 ? 0.0000001 : squareDist) ); // Convert force to acceleration
                    totalAcc.addMul(Main.simulation.getStepSize(), acc);
                }
            }
            Main.simulation.rateOfChange().getRateOfChange().get(i).add(totalAcc);
        }
        return Main.simulation.rateOfChange();
    }



    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts)
    {
        StateInterface[] states = new StateInterface[ts.length];
        endTime = ts[ts.length-1];
        currTime = ts[0];
        for (int i = 0; i< ts.length-1; i++)
        {
            double h = ts[i+1]-ts[i];
            Main.simulation.setStepSize(h);
            states [i] = y0.addMul(currTime,f.call(h,y0));
            currTime+= h;
        }
        states[states.length-1] = y0.addMul(currTime,f.call(ts[ts.length-1]-currTime,y0));
        return states;
    }


    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h)
    {
        endTime = tf;
        Main.simulation.setStepSize(h);
        StateInterface[] path = new StateInterface[(int)(Math.round(tf/h))+1];
        currTime = 0;
        for (int i = 0; i< path.length-1;i++)
        {
            path[i] = this.step(f,currTime,y0,h);
            currTime+= h;
        }
        path[path.length-1] = this.step(this,tf,y0,tf-currTime);
        return path;
    }

    @Override
    public StateInterface step(ODEFunctionInterface f, double currentTime, StateInterface y, double stepSize) {
        /*
        StateInterface[] ks = new StateInterface[4];
        for (int i = 0; i< y.getPositions().size(); i++){
            ks[0].getPositions().set(i,y.getPositions().get(i).clone().mul(stepSize));
        }
        for (int i = 0; i< y.getPositions().size(); i++){
            ks[1].getPositions().set(i,y.getPositions().get(i).clone().mul(stepSize));
        }
         */

        return y.addMul(stepSize, f.call(currentTime, y));
    }


}
