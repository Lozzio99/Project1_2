package group17.phase1.Titan.MetaSimulation;

import group17.phase1.Titan.Main;
import group17.phase1.Titan.Physics.SolarSystem.CelestialBody;
import group17.phase1.Titan.Physics.SolarSystem.Satellite;
import group17.phase1.Titan.Physics.SolarSystem.Star;
import group17.phase1.Titan.Physics.Vector3D;
import group17.phase1.Titan.interfaces.RateInterface;
import group17.phase1.Titan.interfaces.SolarSystemInterface;
import group17.phase1.Titan.interfaces.StateInterface;
import group17.phase1.Titan.interfaces.Vector3dInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MetaSolarSystem implements SolarSystemInterface, StateInterface, RateInterface
{



    List<CelestialBody> allBodies;
    List<Vector3dInterface> rateOfChange;
    List<Vector3dInterface> positions;

    public MetaSolarSystem(){
        this.allBodies = new ArrayList<>();
        this.rateOfChange = new ArrayList<>();
        this.positions = new ArrayList<>();
    }
    @Override
    public List<CelestialBody> getCelestialBodies() {
        return this.allBodies;
    }

    @Override
    public void initPlanetPositions() {
        Star s = new Star();
        s.setMASS(10e8);
        s.setRADIUS(300);
        s.setVectorLocation(new Vector3D(0.1,0.1,0.1));
        s.setVectorVelocity(new Vector3D(0,-0,0));
        this.allBodies.add(s);
        this.positions.add(s.getVectorLocation());
        this.rateOfChange.add(s.getVectorVelocity());
        for (int i = 1; i< 2000; i++)
        {
            Satellite particle = new Satellite(Satellite.SatellitesEnum.ASTEROID);
            particle.setMASS(1e8);
            particle.setRADIUS(50);
            double r = new Random().nextInt(200);
            double r1 = new Random().nextInt(200);
            double r2 = new Random().nextInt(200);
            if (new Random().nextInt()<0.5)
                r*=-1;
            if (new Random().nextInt()<0.5)
                r1*=-1;
            if (new Random().nextInt()<0.5)
                r2*=-1;
            particle.setVectorLocation(new Vector3D(r,r1,r2));
            particle.setVectorVelocity(new Vector3D(0,0,0));
            this.positions.add(particle.getVectorLocation());
            this.rateOfChange.add(particle.getVectorVelocity());
            this.allBodies.add(particle);
        }

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
    public StateInterface addMul(double step, RateInterface rate) {
        for (int i = 0; i< this.positions.size(); i++)
            Main.simulation.systemState().getPositions().get(i).addMul(step,rate.getRateOfChange().get(i));
        return this;
    }
}
