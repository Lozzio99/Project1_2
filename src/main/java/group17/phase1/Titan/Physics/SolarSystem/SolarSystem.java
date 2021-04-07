package group17.phase1.Titan.Physics.SolarSystem;

import group17.phase1.Titan.interfaces.RateInterface;
import group17.phase1.Titan.interfaces.SolarSystemInterface;
import group17.phase1.Titan.interfaces.StateInterface;
import group17.phase1.Titan.interfaces.Vector3dInterface;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SolarSystem implements SolarSystemInterface, StateInterface, RateInterface
{

    List<CelestialBody> allBodies;
    List<Vector3dInterface> rateOfChange;
    List<Vector3dInterface> positions;

    public SolarSystem()
    {
         this.allBodies = new ArrayList<>();
         this.initBodies();
    }

    public void initBodies()
    {
        Star sun = new Star();
        sun.setMASS(1.988500e+30);
        sun.setRADIUS(6.96342e08);
        sun.setColour(Color.yellow);
        this.allBodies.add(sun);

        Planet mercury = new Planet(Planet.PlanetsEnum.MERCURY);
        mercury.setMASS(3.302e+23);
        mercury.setRADIUS(2.4397e6);
        mercury.setColour(Color.magenta);
        this.allBodies.add(mercury);

        // Venus
        Planet venus = new Planet(Planet.PlanetsEnum.VENUS);
        venus.setMASS(4.8685e24);
        venus.setRADIUS(6.0518e6);
        venus.setColour(Color.orange);
        this.allBodies.add(venus);

        // Earth
        Planet earth = new Planet(Planet.PlanetsEnum.EARTH);
        earth.setMASS(5.97219e24);
        earth.setRADIUS(6.371e6);
        earth.setColour(Color.blue);
        this.allBodies.add(earth);

        // Luna
        Satellite luna = new Satellite(Satellite.SatellitesEnum.MOON);
        luna.setMASS(7.349e22);
        luna.setRADIUS(1.7371e6);
        luna.setColour(Color.gray);
        this.allBodies.add(luna);

        // Mars
        Planet mars = new Planet(Planet.PlanetsEnum.MARS);
        mars.setMASS(6.4171e23);
        mars.setRADIUS(3.3895e6);
        mars.setColour(Color.red);
        this.allBodies.add(mars);

        // Jupiter
        Planet jupiter = new Planet(Planet.PlanetsEnum.JUPITER);
        jupiter.setMASS(1.89813e27);
        jupiter.setRADIUS(6.9911e7);
        jupiter.setColour(Color.WHITE);
        this.allBodies.add(jupiter);

        // Saturn
        Planet saturn = new Planet(Planet.PlanetsEnum.SATURN);
        saturn.setMASS(5.6834e26);
        saturn.setRADIUS(5.8232e7);
        saturn.setColour(Color.orange);
        this.allBodies.add(saturn);

        // Titan
        Satellite titan = new Satellite(Satellite.SatellitesEnum.TITAN);
        titan.setMASS(1.34553e23);
        titan.setRADIUS(2575.5e3);
        titan.setColour(Color.gray);
        this.allBodies.add(titan);

        // Uranus
        Planet uranus = new Planet(Planet.PlanetsEnum.URANUS);
        uranus.setMASS(8.6813e25);
        uranus.setRADIUS(2.5362e7);
        uranus.setColour(Color.white);
        this.allBodies.add(uranus);

        // Neptune
        Planet neptune = new Planet(Planet.PlanetsEnum.NEPTUNE);
        neptune.setMASS(1.02413e26);
        neptune.setRADIUS(2.4622e7);
        neptune.setColour(Color.blue);
        this.allBodies.add(neptune);
    }

    @Override
    public List<CelestialBody> getCelestialBodies() {
        return this.allBodies;
    }


    @Override
    public void initPlanetPositions()
    {
        this.rateOfChange = new ArrayList<>();
        this.positions = new ArrayList<>();
        for (CelestialBody c : this.allBodies)
        {
            c.initPosition();
            this.positions.add(c.getVectorLocation());
            this.rateOfChange.add(c.getVectorVelocity());
        }
    }



    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        for (CelestialBody p : this.allBodies)
        {
            s.append(p.toString()).append(" : \n");
            s.append("velocity : ").append(p.getVectorVelocity().toString()).append("\n");
            s.append("position : ").append(p.getVectorLocation().toString()).append("\n");
        }
        return s.toString().trim();
    }


    @Override
    public List<Vector3dInterface> getRateOfChange() {
        return this.rateOfChange;
    }

    @Override
    public List<Vector3dInterface> getPositions() {
        return this.positions;
    }

    @Override
    public void setVectorPosition(List<Vector3dInterface> vectorPosition) {
        this.positions = vectorPosition;
    }

    @Override
    public StateInterface addMul(double step, RateInterface rate)
    {
        for (int i= 0; i< this.allBodies.size(); i++)
            this.positions.get(i).addMul(step,rate.getRateOfChange().get(i));
        return this;
    }
}
