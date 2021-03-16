package group17.phase1.Titan.SolarSystem;

import group17.phase1.Titan.Simulation.Gravity.ODEFunctionInterface;
import group17.phase1.Titan.Simulation.Gravity.ODESolverInterface;
import group17.phase1.Titan.Simulation.Gravity.Vector3D;
import group17.phase1.Titan.SolarSystem.Bodies.CelestialBody;
import group17.phase1.Titan.SolarSystem.Bodies.Planet;
import group17.phase1.Titan.SolarSystem.Bodies.Satellite;
import group17.phase1.Titan.SolarSystem.Bodies.Star;

import java.util.ArrayList;
import java.util.List;

public class SolarSystem implements SolarSystemInterface
{
    List<CelestialBody> allBodies;
    ODESolverInterface gravityCalculator;
    ODEFunctionInterface gravity;



    public SolarSystem()
    {
        this.allBodies = new ArrayList<>();
        this.initBodies();
    }

    public void initBodies(){
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

        Planet mercury = new Planet(Planet.PlanetsEnum.MERCURY);
        mercury.setMASS(3.302e+23);
        mercury.setRADIUS(2.4397e6);
        mercury.setVectorLocation(new Vector3D(6.047855986424127e+06, -6.801800047868888e+10, -5.702742359714534e+09));
        mercury.setVectorVelocity(new Vector3D(3.892585189044652e+04, 2.978342247012996e+03, 3.327964151414740e+03));
        //mercury.setColour(Color.magenta);
        this.allBodies.add(mercury);

        // Venus
        Planet venus = new Planet(Planet.PlanetsEnum.VENUS);
        venus.setMASS(4.8685e24);
        venus.setRADIUS(6.0518e6);
        venus.setVectorLocation(new Vector3D(-9.435345478592035e+10, 5.350359551033670e+10, 6.131453014410347e+09));
        venus.setVectorVelocity(new Vector3D(-1.726404287724406e+04, -3.073432518238123e+04, 5.741783385280979e-04));
        //venus.setColour(Color.orange);
        this.allBodies.add(venus);

        // Earth
        Planet earth = new Planet(Planet.PlanetsEnum.EARTH);
        earth.setMASS(5.97219e24);
        earth.setRADIUS(6.371e6);
        earth.setVectorLocation(new Vector3D(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06));
        earth.setVectorVelocity(new Vector3D(5.427193405797901e+03, -2.931056622265021e+04, 6.575428158157592e-01));
        //earth.setColour(Color.blue);
        this.allBodies.add(earth);

        // Luna
        Satellite luna = new Satellite(Satellite.SatellitesEnum.MOON);
        luna.setMASS(7.349e22);
        luna.setRADIUS(1.7371e6);
        luna.setVectorLocation(new Vector3D(-1.472343904597218e+11, -2.822578361503422e+10, 1.052790970065631e+07));
        luna.setVectorVelocity(new Vector3D(4.433121605215677e+03, -2.948453614110320e+04, 8.896598225322805e+01));
        //luna.setColour(Color.gray);
        this.allBodies.add(luna);
    }


    @Override
    public List<CelestialBody> getCelestialBodies() {
        return this.allBodies;
    }

}
