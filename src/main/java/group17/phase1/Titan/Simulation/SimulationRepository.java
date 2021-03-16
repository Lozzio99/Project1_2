package group17.phase1.Titan.Simulation;

import group17.phase1.Titan.Graphics.GraphicsManager;
import group17.phase1.Titan.Interfaces.*;
import group17.phase1.Titan.Main;
import group17.phase1.Titan.Simulation.Gravity.Vector3D;
import group17.phase1.Titan.SolarSystem.Bodies.CelestialBody;
import group17.phase1.Titan.SolarSystem.SolarSystem;


public class SimulationRepository implements SimulationInterface, ODESolverInterface , ODEFunctionInterface
{
    SolarSystemInterface repository;
    GraphicsManager graphicsManager;

    public SimulationRepository()
    {
        this.repository = new SolarSystem();
        this.graphicsManager = new GraphicsManager();
        this.graphicsManager.startMainThread();
    }

    @Override
    public SolarSystemInterface getSolarSystemRepository() {
        return this.repository;
    }

    @Override
    public GraphicsManager getGraphicsManager() {
        return this.graphicsManager;
    }

    void loopSimulation(){  }


    /*
     * A class for solving a general differential equation dy/dt = f(t,y)
     *     y(t) describes the state of the system at time t
     *     f(t,y(t)) defines the derivative of y(t) with respect to time t
     */

    /*
     * Solve the differential equation by taking multiple steps.
     *
     * @param   f       the function defining the differential equation dy/dt=f(t,y)
     * @param   y0      the starting state
     * @param   ts      the times at which the states should be output, with ts[0] being the initial time
     * @return  an array of size ts.length with all intermediate states along the path
     */

    @Override
    public StateInterface[] solve(ODEFunctionInterface gravity, StateInterface stateAt0, double[] timeSteps)
    {
        StateInterface[] positions = new StateInterface[timeSteps.length];
        for (int i = 0; i< positions.length; i++){
            positions[i] = this.step(gravity,timeSteps[i],stateAt0,timeSteps.length/timeSteps[i]);
        }
        return positions;
    }


    /*
     * Solve the differential equation by taking multiple steps of equal size, starting at time 0.
     * The final step may have a smaller size, if the step-size does not exactly divide the solution time range
     *
     * @param   f       the function defining the differential equation dy/dt=f(t,y)
     * @param   y0      the starting state
     * @param   tf      the final time
     * @param   h       the size of step to be taken
     * @return  an array of size round(tf/h)+1 including all intermediate states along the path
     */

    @Override
    public StateInterface[] solve(ODEFunctionInterface gravity, StateInterface stateAt0, double endTime, double timeSize)
    {
        StateInterface[] path = new StateInterface[(int)(Math.round(endTime/timeSize))+1];
        double time0 = 0, timeSeq = endTime/timeSize;
        for (int i = 0; i< path.length;i++){
            path[i] = this.step(gravity,time0,stateAt0,timeSize);
            time0+= timeSeq;
        }
        return path;
    }

    /*
     * Update rule for one step.
     *
     * @param   f   the function defining the differential equation dy/dt=f(t,y)
     * @param   t   the time
     * @param   y   the state
     * @param   h   the step size
     * @return  the new state after taking one step
     */

    @Override
    public StateInterface step(ODEFunctionInterface gravity, double timeAt, StateInterface stateAt, double timeSize)
    {
        return stateAt.addMul(timeSize,gravity.call(timeAt,stateAt));
    }


    /*
     * This is an interface for the function f that represents the
     * differential equation dy/dt = f(t,y).
     * You need to implement this function to represent to the laws of physics.
     *
     * For example, consider the differential equation
     *   dy[0]/dt = y[1];  dy[1]/dt=cos(t)-sin(y[0])
     * Then this function would be
     *   f(t,y) = (y[1],cos(t)-sin(y[0])).
     *
     * @param   t   the time at which to evaluate the function
     * @param   y   the state at which to evaluate the function
     * @return  The average rate-of-change over the time-step. Has dimensions of [state]/[time].
     *
     *
     * ++++++++++++++ CALCULATE ATTRACTION ++++++++++++++++++++++*/


    @Override
    public RateInterface call(double timeAt, StateInterface stateAtTime)
    {
        double dx = 0, dy = 0, dz = 0,
                distance,diffX,diffY,diffZ,distance3;
        for (CelestialBody from : Main.simulation.getSolarSystemRepository().getCelestialBodies())
        {
            for (CelestialBody to : Main.simulation.getSolarSystemRepository().getCelestialBodies())
            {
                if (to == from) continue;
                //also, if distance is higher than some value we will keep skipping i guess
                //or better, if mass/distance is higher or who knows

                diffX  = to.getX_LOCATION()-from.getX_LOCATION();
                diffY  = to.getY_LOCATION()-from.getY_LOCATION();
                diffZ  = to.getZ_LOCATION()-from.getZ_LOCATION();
                distance = Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);
                distance3 = distance * distance * distance;
                dx += (to.getMASS() * diffX)/ distance3;
                dy += (to.getMASS() * diffY)/ distance3;
                dz += (to.getMASS() * diffZ)/ distance3;
            }
            dx *= G * timeAt;
            dy *= G * timeAt;
            dz *= G * timeAt;
            from.setShiftVector(new Vector3D(dx,dy,dz));

            //the state of the system now tends to somewhere else that is given in the shift vector
            //this applies to the whole simulation and it's applied to each body with respect to all
            //other bodies
        }
        return Main.simulation.getSolarSystemRepository().getRateOfChange();
    }


}
