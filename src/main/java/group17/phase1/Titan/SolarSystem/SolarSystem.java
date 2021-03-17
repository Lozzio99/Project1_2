package group17.phase1.Titan.SolarSystem;

import group17.phase1.Titan.Interfaces.*;
import group17.phase1.Titan.Simulation.Vector3D;
import group17.phase1.Titan.SolarSystem.Bodies.CelestialBody;
import group17.phase1.Titan.SolarSystem.Bodies.Planet;
import group17.phase1.Titan.SolarSystem.Bodies.Satellite;
import group17.phase1.Titan.SolarSystem.Bodies.Star;
import org.lwjgl.system.CallbackI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SolarSystem implements SolarSystemInterface, StateInterface, RateInterface
{
    List<CelestialBody> allBodies;


    public SolarSystem()
    {
        this.allBodies = new ArrayList<>();
        this.initBodies();
    }

    public void initBodies(){
        Star sun = new Star();
        sun.setMASS(1.988500e+30);
        sun.setRADIUS(6.96342e+08);
        sun.setVectorLocation(new Vector3D(-6.806783239281648e+08, 1.080005533878725e+09, 6.564012751690170e+06));
        sun.setVectorVelocity(new Vector3D(1.420511669610689e+01, -4.954714716629277e+00, 3.994237625449041e-01));
        sun.setColour(Color.yellow);

        this.allBodies.add(sun);

        Planet mercury = new Planet(Planet.PlanetsEnum.MERCURY);
        mercury.setMASS(3.302e+23);
        mercury.setRADIUS(2.4397e6);
        mercury.setVectorLocation(new Vector3D(6.047855986424127e+06, -6.801800047868888e+10, -5.702742359714534e+09));
        mercury.setVectorVelocity(new Vector3D(3.892585189044652e+04, 2.978342247012996e+03, 3.327964151414740e+03));
        mercury.setColour(Color.magenta);
        this.allBodies.add(mercury);

        // Venus
        Planet venus = new Planet(Planet.PlanetsEnum.VENUS);
        venus.setMASS(4.8685e24);
        venus.setRADIUS(6.0518e6);
        venus.setVectorLocation(new Vector3D(-9.435345478592035e+10, 5.350359551033670e+10, 6.131453014410347e+09));
        venus.setVectorVelocity(new Vector3D(-1.726404287724406e+04, -3.073432518238123e+04, 5.741783385280979e-04));
        venus.setColour(Color.orange);
        this.allBodies.add(venus);

        // Earth
        Planet earth = new Planet(Planet.PlanetsEnum.EARTH);
        earth.setMASS(5.97219e24);
        earth.setRADIUS(6.371e6);
        earth.setVectorLocation(new Vector3D(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06));
        earth.setVectorVelocity(new Vector3D(5.427193405797901e+03, -2.931056622265021e+04, 6.575428158157592e-01));
        earth.setColour(Color.blue);
        this.allBodies.add(earth);

        // Luna
        Satellite luna = new Satellite(Satellite.SatellitesEnum.MOON);
        luna.setMASS(7.349e22);
        luna.setRADIUS(1.7371e6);
        luna.setVectorLocation(new Vector3D(-1.472343904597218e+11, -2.822578361503422e+10, 1.052790970065631e+07));
        luna.setVectorVelocity(new Vector3D(4.433121605215677e+03, -2.948453614110320e+04, 8.896598225322805e+01));
        luna.setColour(Color.gray);
        this.allBodies.add(luna);

        // Mars
        Planet mars = new Planet(Planet.PlanetsEnum.MARS);
        mars.setMASS(6.4171e23);
        mars.setRADIUS(3.3895e6);
        mars.setVectorLocation(new Vector3D(-3.615638921529161e+10, -2.167633037046744e+11, -3.687670305939779e+09));
        mars.setVectorVelocity(new Vector3D(2.481551975121696e+04, -1.816368005464070e+03, -6.467321619018108e+02));
        mars.setColour(Color.red);
        this.allBodies.add(mars);

        // Jupiter
        Planet jupiter = new Planet(Planet.PlanetsEnum.JUPITER);
        jupiter.setMASS(1.89813e27);
        jupiter.setRADIUS(6.9911e7);
        jupiter.setVectorLocation(new Vector3D(1.781303138592153e+11, -7.551118436250277e+11, -8.532838524802327e+08));
        jupiter.setVectorVelocity(new Vector3D(1.255852555185220e+04, 3.622680192790968e+03, -2.958620380112444e+02));
        jupiter.setColour(Color.WHITE);
        this.allBodies.add(jupiter);

        // Saturn
        Planet saturn = new Planet(Planet.PlanetsEnum.SATURN);
        saturn.setMASS(5.6834e26);
        saturn.setRADIUS(5.8232e7);
        saturn.setVectorLocation(new Vector3D(6.328646641500651e+11, -1.358172804527507e+12, -1.578520137930810e+09));
        saturn.setVectorVelocity(new Vector3D(8.220842186554890e+03, 4.052137378979608e+03, -3.976224719266916e+02));
        saturn.setColour(Color.orange);
        this.allBodies.add(saturn);

        // Titan
        Satellite titan = new Satellite(Satellite.SatellitesEnum.TITAN);
        titan.setMASS(1.34553e23);
        titan.setRADIUS(2575.5e3);
        titan.setVectorLocation(new Vector3D(6.332873118527889e+11, -1.357175556995868e+12, -2.134637041453660e+09));
        titan.setVectorVelocity(new Vector3D(3.056877965721629e+03, 6.125612956428791e+03, -9.523587380845593e+02));
        titan.setColour(Color.gray);
        this.allBodies.add(titan);

        // Uranus
        Planet uranus = new Planet(Planet.PlanetsEnum.URANUS);
        uranus.setMASS(8.6813e25);
        uranus.setRADIUS(2.5362e7);
        uranus.setVectorLocation(new Vector3D(2.395195786685187e+12, 1.744450959214586e+12, -2.455116324031639e+10));
        uranus.setVectorVelocity(new Vector3D(-4.059468635313243e+03, 5.187467354884825e+03, 7.182516236837899e+01));
        //uranus.setColour(Color.white);
        this.allBodies.add(uranus);

        // Neptune
        Planet neptune = new Planet(Planet.PlanetsEnum.NEPTUNE);
        neptune.setMASS(1.02413e26);
        neptune.setRADIUS(2.4622e7);
        neptune.setVectorLocation(new Vector3D(4.382692942729203e+12, -9.093501655486243e+11, -8.227728929479486e+10));
        neptune.setVectorVelocity(new Vector3D(1.068410720964204e+03, 5.354959501569486e+03, -1.343918199987533e+02));
        neptune.setColour(Color.blue);
        this.allBodies.add(neptune);
    }


    @Override
    public List<CelestialBody> getCelestialBodies() {
        return this.allBodies;
    }


    @Override
    public StateInterface addMul(double step, RateInterface rate)
    {
        for (int i = 0; i< this.allBodies.size(); i++)
        this.allBodies.get(i).getVectorLocation().addMul(step,rate.getRateVector().get(i));
        return this;
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for (CelestialBody p : this.allBodies)
        {
            s.append(p.toString()).append(" : \n");
            s.append("velocity : ").append(p.getVelocityVector().toString()).append("\n");
            s.append("position : ").append(p.getVectorLocation().toString()).append("\n");
        }
        return s.toString().trim();
    }

    @Override
    public void currentState(List<CelestialBody> bodies) {
        this.allBodies = bodies;
    }

    @Override
    public List<CelestialBody> getState() {
        return this.allBodies;
    }


    @Override
    public List<Vector3dInterface> getRateVector()
    {
        List<Vector3dInterface> shiftVectors = new ArrayList<>();
        for (CelestialBody p : this.allBodies){
            shiftVectors.add(p.getVelocityVector());
        }
        return shiftVectors;
    }
}
