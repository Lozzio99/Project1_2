/**
 * This class represents a simulation of our solar system.
 * @author 	Dan Parii, Lorenzo Pompigna, Nikola Prianikov, Axel Rozental, Konstantin Sandfort, Abhinandan Vasudevan​
 * @version 1.0
 * @since	19/02/2021
 */

package group17.phase1.Titan.Simulation;

import group17.phase1.Titan.Graphics.GraphicsManager;
import group17.phase1.Titan.Interfaces.*;
import group17.phase1.Titan.Main;
import group17.phase1.Titan.Simulation.Probe.ProbeSimulator;
import group17.phase1.Titan.SolarSystem.Bodies.CelestialBody;
import group17.phase1.Titan.SolarSystem.SolarSystem;
import group17.phase1.Titan.Utils.UnitConverter;

import static group17.phase1.Titan.Utils.Configuration.*;

import static group17.phase1.Titan.Interfaces.RateInterface.G;


public class SimulationRepository implements SimulationInterface, ODESolverInterface, ODEFunctionInterface
{
    private SolarSystemInterface solarSystem;
    private GraphicsManager graphicsManager;

    public static double stepSize = 60;
    public static double currTime = 0;
    public static double endTime = Double.MAX_VALUE;
    private int trajectoryLength = 10;

    private static double closestApproachToTitan;


    private RateInterface rateOfChange;
    protected StateInterface solarSystemState;
    public static int sec = 0,min = 0, hour = 0,dd = 1,mm = 4, yy = 2020 ;


    public SimulationRepository()
    {
        this.solarSystem = new SolarSystem();
        this.solarSystemState = (StateInterface) solarSystem;
        this.rateOfChange = (RateInterface) solarSystem;
    }

    /**
     * Initialises a probe.
     */
    public void initProbe()
    {
        ProbeSimulator p = new ProbeSimulator();
        p.init(this,this,this.solarSystemState);
        this.solarSystem.getCelestialBodies().add(p);
        closestApproachToTitan = this.solarSystem.getCelestialBodies().get(11).getVectorLocation().clone().dist(this.solarSystem.getCelestialBodies().get(8).getVectorLocation().clone());

    }



    /**
     * Initialises the simulation.
     */
    public void initSimulation()
    {
        this.graphicsManager = new GraphicsManager();
        this.graphicsManager.init();
        Main.simulation.getGraphicsManager().getAssist().get().setStepField(""+stepSize);
        Main.simulation.getGraphicsManager().getAssist().get().setProbeField(""+Main.simulation.getBody("PROBE").getMASS());
        Main.simulation.getGraphicsManager().getAssist().get().setDdField(""+ dd);
        Main.simulation.getGraphicsManager().getAssist().get().setMmField(""+mm);
        Main.simulation.getGraphicsManager().getAssist().get().setYyField(""+yy);
        Main.simulation.getGraphicsManager().getAssist().get().setHhField(""+hour);
        Main.simulation.getGraphicsManager().getAssist().get().setmField(""+min);
        Main.simulation.getGraphicsManager().getAssist().get().setSsField(""+sec);
        //TODO: sync this
        this.graphicsManager.waitForStart();
        this.runStepSimulation();

    }


    /**
     * Calculates the trajectory of a body.
     */
    public void calculateTrajectories()
    {

        StateInterface[] allSteps = generateTimeSequences();

        currTime = 0;
        for (StateInterface step : allSteps)
        {
            for (int i = 0; i< this.solarSystem.getCelestialBodies().size(); i++)
            {
                this.solarSystem.getCelestialBodies().get(i).addToPath(step.getState().get(i).getVectorLocation().fromVector());
            }
            currTime += stepSize;
        }

    }

    @Override
    public SolarSystemInterface getSolarSystemRepository() {
        return this.solarSystem;
    }

    @Override
    public GraphicsManager getGraphicsManager() {
        return this.graphicsManager;
    }


    /**
     * Starts the simulation.
     */
    @Override
    public void runSimulation()
    {
        for (int i = 0; i< endTime; i++) {
            if (DEBUG)System.out.println("Earth Pos: " + solarSystem.getCelestialBodies().get(3).getVectorLocation().toString());
            if (DEBUG)System.out.println("Earth Vel: " + solarSystem.getCelestialBodies().get(3).getVectorVelocity().toString());

            this.step(this, currTime, this.solarSystemState, stepSize); // The calculation

            if (DEBUG) System.out.println("Time: " + currTime);
            currTime += stepSize;
        }

        currTime = 0;
        this.runSimulation();
    }

    int closeM,closeH,closeDD,closeMM,closeYY;
    Vector3dInterface best,titan;
    String bestCatch = "";

    /**
     * Starts the simulation in several steps.
     */
    @Override
    public void runStepSimulation() {

        boolean got = false;
        StateInterface[] allSteps = generateTimeSequences();
                currTime = 0;
                stepSize = Main.simulation.getGraphicsManager().getAssist().get().getTimeStepSize();
        for(;;){
            currTime = 0;
            for (StateInterface step : allSteps)
            {
                this.step(this,currTime,step,stepSize);
                currTime += stepSize;
                double dist = this.getBody("TITAN").getVectorLocation().clone().dist(this.getBody("PROBE").getVectorLocation().clone());
                sec+= stepSize;
                if (sec == 60)
                {
                    sec = 0;
                    min++;
                }
                if (min == 60)
                {
                    min = 0;
                    hour++;
                }
                if (hour == 24)
                {
                    hour = 0;
                    dd++;
                }
                if (dd == 30)
                {
                    dd = 1;
                    mm++;
                }
                if (mm == 12)
                {
                    mm = 1;
                    yy++;
                }
                Main.simulation.getGraphicsManager().getAssist().get().setDate(sec,min,hour,dd,mm,yy);
                if (dist<closestApproachToTitan)
                {
                    closestApproachToTitan = dist;
                    closeDD = dd;
                    closeMM = mm;
                    closeYY = yy;
                    closeH = hour;
                    closeM = min;
                    best = Main.simulation.getSolarSystemRepository().getCelestialBodies().get(11).getVectorLocation().clone();
                    titan = Main.simulation.getSolarSystemRepository().getCelestialBodies().get(8).getVectorLocation().clone();
                }
                else
                {
                    //if (best== null)continue;
                    if (!got){       //this is very heavy to show here otherwise
                        bestCatch = ("CLOSEST POINT : "+ closestApproachToTitan)+"\n"+
                                (" REACHED ON ("+closeDD + "/"+closeMM+"/"+closeYY+")")+"\n"+
                                ("hh  "+ closeH + " :"+ closeM )+"\n"+
                                (best.toString() + "\n titan : \n "+ titan.toString());
                        got = true;
                    }
                }
            }
        }



    }

    private StateInterface[] generateTimeSequences()
    {
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

        for (int i = 0; i< timeSteps.length; i++)
        {
            states [i] = stateAt0.addMul(timeSteps[i],gravity.call(timeSteps[i],stateAt0));
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
        s.append(bestCatch).append("\n");
        s.append("Running at ").append(stepSize).append("\n");
        s.append("Current time : ").append(currTime).append("\n");
        s.append("End of Simulation : ").append(endTime).append("\n");
        double dist = this.getBody("TITAN").getVectorLocation().clone().dist(this.getBody("PROBE").getVectorLocation().clone());
        s.append("Distance (m) Probe to Titan :").append(dist).append("\n");
        s.append("Distance (vec) Probe to Titan :").append(this.getBody("TITAN").getVectorLocation().clone().sub(this.getBody("PROBE").getVectorLocation().clone())).append("\n");
        s.append("Closest (m) Probe to Titan :").append(closestApproachToTitan).append("\n\n");
        s.append(this.solarSystem.toString());
        return s.toString().trim();
    }


    /**
     * This is an interface for the function f that represents the
     * differential equation dy/dt = f(t,y).
     * You need to implement this function to represent to the laws of physics.
     *
     * For example, consider the differential equation
     *   dy[0]/dt = y[1];  dy[1]/dt=cos(t)-sin(y[0])
     * Then this function would be
     *   f(t,y) = (y[1],cos(t)-sin(y[0])).
     *
     * @param   timeAt   the time at which to evaluate the function
     * @param   stateAtTime   the state at which to evaluate the function
     * @return  The average rate-of-change over the time-step. Has dimensions of [state]/[time].
     */

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

    /**
     * Returns a body which is identified by its name.
     */
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