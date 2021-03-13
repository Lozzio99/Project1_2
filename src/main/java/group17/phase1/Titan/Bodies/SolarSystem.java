package group17.phase1.Titan.Bodies;

import group17.phase1.Titan.Bodies.CelestialBodies.CelestialBodyInterface;
import group17.phase1.Titan.Bodies.CelestialBodies.Star;
import group17.phase1.Titan.Graphics.Renderer.geometry.Point3D;
import group17.phase1.Titan.Physics.TimeSequence.SolverInterface;
import group17.phase1.Titan.Physics.Trajectories.CoordinateInterface;
import group17.phase1.Titan.Physics.Trajectories.Forces.Vector3D;
import group17.phase1.Titan.Physics.Trajectories.Forces.Vector3DInterface;
import group17.phase1.Titan.Physics.Trajectories.FunctionInterface;

import java.util.ArrayList;
import java.util.List;

public class SolarSystem implements SolarSystemInterface
{
    List<CelestialBodyInterface> allBodies;

    CoordinateInterface SolarSystemBarycenter = new Point3D(0,0,0);
    //which one will be??
    Vector3DInterface SSB = new Vector3D(0,0,0);



    @Override
    public List<CelestialBodyInterface> allCelestialBodies() {
        return this.allBodies;
    }



    @Override
    public void updateLocation(CelestialBodyInterface body, SolverInterface functionSolver) {

        FunctionInterface f =  (e1,e2)->e2 = e2.mul(e1);
        Vector3DInterface[] v = functionSolver.solve( f, //random function
                                SSB,0,0);  //random vector
    }

    public SolarSystem(){
        this.allBodies = new ArrayList<>();
        this.setupAllBodies();
    }

    void setupAllBodies()
    {
        Star sun = new Star();
        sun.setMASS(1.222222);
        sun.setDENSITY(0.2);
        sun.setX_LOCATION(0.102);
        //.....
        this.allBodies.add(sun);
    }

}
