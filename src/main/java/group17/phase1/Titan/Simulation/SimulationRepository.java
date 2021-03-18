package group17.phase1.Titan.Simulation;

import group17.phase1.Titan.Graphics.Geometry.Point3D;
import group17.phase1.Titan.Graphics.GraphicsManager;
import group17.phase1.Titan.Interfaces.*;
import group17.phase1.Titan.Main;
import group17.phase1.Titan.Simulation.Probe.ProbeSimulator;
import group17.phase1.Titan.SolarSystem.Bodies.CelestialBody;
import group17.phase1.Titan.SolarSystem.Bodies.Planet;
import group17.phase1.Titan.SolarSystem.Bodies.Star;
import group17.phase1.Titan.SolarSystem.SolarSystem;

import static group17.phase1.Titan.Utils.Configuration.*;

import static group17.phase1.Titan.Interfaces.RateInterface.G;


public class SimulationRepository implements SimulationInterface, ODESolverInterface, ODEFunctionInterface
{
    private SolarSystemInterface solarSystem;
    private GraphicsManager graphicsManager;

    public static double stepSize = 10;
    public static double currTime = 0;
    public static double endTime = Double.MAX_VALUE;
    private int trajectoryLength = 1000;

    private RateInterface rateOfChange;
    protected StateInterface solarSystemState;

    public SimulationRepository()
    {
        this.solarSystem = new SolarSystem();
        this.solarSystemState = (StateInterface) solarSystem;
        this.rateOfChange = (RateInterface) solarSystem;
    }

    public void initProbe()
    {
        ProbeSimulator p = new ProbeSimulator();
        p.init(this,this,this.solarSystemState);
        this.solarSystem.getCelestialBodies().add(p);
    }




    public void initSimulation()
    {
        this.graphicsManager = new GraphicsManager();
        this.graphicsManager.init();

        //TODO: is this really sync?
        this.graphicsManager.waitForStart();
        this.runStepSimulation();

    }



    public void calculateTrajectories()
    {

        StateInterface[] allSteps = generateTimeSequences();

        int allPlanets = this.solarSystem.getCelestialBodies().size();
        for (int planet = 0; planet <allPlanets; planet++)
        {
            Point3D[] path = new Point3D[this.trajectoryLength];
            for (int step = 0; step<trajectoryLength; step++)
            {
                path[step] = allSteps[step].getState().get(planet).getVectorLocation().fromVector();
            }
            this.solarSystem.getCelestialBodies().get(planet).acquirePath(path);
        }

    }

    @Override
    public SolarSystemInterface getSolarSystemRepository() {
        return this.solarSystem;
    }

    @Override
    public void runSimulation()
    {
        for (int i = 0; i < endTime; i++)
        {
            if (DEBUG)System.out.println("Earth Pos: " + solarSystem.getCelestialBodies().get(3).getVectorLocation().toString());
            if (DEBUG)System.out.println("Earth Vel: " + solarSystem.getCelestialBodies().get(3).getVelocityVector().toString());

            this.step(this, currTime, this.solarSystemState, stepSize); // The calculation

            if (DEBUG) System.out.println("Time: " + currTime);
            currTime += stepSize;
        }

        currTime = 0;
        this.runSimulation();

    }

    @Override
    public void runStepSimulation() {

        StateInterface[] allSteps = generateTimeSequences();

        currTime = 0;
        for(;;){
            for (StateInterface step : allSteps)
            {
                this.step(this,currTime,step,stepSize);
                currTime += stepSize;
            }
            currTime = 0;
        }



    }

    private StateInterface[] generateTimeSequences() {
        double [] ts = new double[trajectoryLength];
        double start = 0;
        for (int i = 0; i< trajectoryLength; i++ )
        {
            ts[i] = start;
            start+= stepSize;
        }
        return this.solve(this,this.solarSystemState,ts);
    }




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
        StateInterface[] states = new StateInterface[timeSteps.length];
        endTime = timeSteps[timeSteps.length-1];
        currTime = 0;


        for (int i = 0; i< timeSteps.length; i++){
            states[i] = this.step(this,timeSteps[i],this.solarSystemState,timeSteps[i]-currTime);
            currTime = timeSteps[i];
        }
        return states;
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
    public StateInterface[] solve(ODEFunctionInterface gravity, StateInterface system, double endTime, double timeSize)
    {
        SimulationRepository.endTime = endTime;
        SimulationRepository.stepSize = timeSize;
        StateInterface[] path = new StateInterface[(int)(Math.round(endTime/timeSize))+1];
        SimulationRepository.currTime = 0;
        for (int i = 0; i< path.length-1;i++)
        {
            path[i] = this.step(gravity,currTime,system,timeSize);
            currTime+= timeSize;
        }
        path[path.length-1] = this.step(gravity,endTime,system,endTime-currTime);
        return path;
    }


    /**
     * Update rule for one step.
     * @param   f   the function defining the differential equation dy/dt=f(t,y)
     * @param   t   the time
     * @param   h   the state
     * @param   dt   the step size
     * @return  the new state after taking one step
     */

    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface h, double dt)
    {
        return h.addMul(dt, f.call(t, h));
    }


    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append("Running at ").append(stepSize).append("\n");
        s.append("Current time : ").append(currTime).append("\n");
        s.append("End of Simulation : ").append(endTime).append("\n");
        s.append(this.solarSystem.toString());
        return s.toString().trim();
    }


    @Override
    public RateInterface call(double timeAt, StateInterface stateAtTime)
    {

        for (int i = 0; i< this.getSolarSystemRepository().getCelestialBodies().size(); i++)
        {
            CelestialBody thisBody = this.getSolarSystemRepository().getCelestialBodies().get(i);
            Vector3dInterface totalAcc = new Vector3D(0, 0, 0);
            for (CelestialBody otherBody : this.getSolarSystemRepository().getCelestialBodies())
            {
                if (thisBody != otherBody)
                {
                    Vector3dInterface acc = new Vector3D(thisBody.getVectorLocation().getX(), thisBody.getVectorLocation().getY(), thisBody.getVectorLocation().getZ());
                    double squareDist = Math.pow(thisBody.getVectorLocation().dist(otherBody.getVectorLocation()), 2);
                    acc.sub(otherBody.getVectorLocation()); // Get the force vector
                    double den = Math.sqrt(squareDist);
                    /*
                        ! Important !

                        if two bodies collapses into the same point
                        that would crash to NaN and consequently
                        the same in all the system

                     */
                    acc.mul(1 / (den == 0 ? 0.0000001 : den)); // Normalise to length 1
                    acc.mul((G * otherBody.getMASS()) / (squareDist == 0 ? 0.0000001 : squareDist) ); // Convert force to acceleration
                    totalAcc.addMul(stepSize, acc);
                }
            }
            thisBody.getVectorVelocity().add(totalAcc);
        }
        return this.rateOfChange;
    }

    @Override
    public CelestialBody getBody(String name){
        for (CelestialBody p : this.solarSystem.getCelestialBodies())
        {
             if (p.toString().equals(name))
                return p;
        }
        return null;
    }

}