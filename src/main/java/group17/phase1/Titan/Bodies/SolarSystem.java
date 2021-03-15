package group17.phase1.Titan.Bodies;

import group17.phase1.Titan.Bodies.CelestialBodies.CelestialBodyInterface;
import group17.phase1.Titan.Bodies.CelestialBodies.Star;
import group17.phase1.Titan.Graphics.Renderer.Point3D;
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
    	// Sun
        Star sun = new Star();
        sun.setMASS(1.988500e+30);
        sun.setRADIUS(6.96342e+08);
        sun.setDENSITY(1.41);
        sun.setX_LOCATION(-6.806783239281648e+08);
        sun.setY_LOCATION(1.080005533878725e+09);
        sun.setX_LOCATION(6.564012751690170e+06);
        sun.setX_VELOCITY(1.420511669610689e+01);
        sun.setY_VELOCITY(-4.954714716629277e+00);
        sun.setZ_VELOCITY(3.994237625449041e-01);
        sun.setX_ROTATION(0);
        sun.setY_ROTATION(0);
        sun.setZ_ROTATION(0);
        //.....
        this.allBodies.add(sun);
    }

}
