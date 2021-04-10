package group17.phase1.Titan.MetaSimulation;

import group17.phase1.Titan.Physics.SolarSystem.CelestialBody;
import group17.phase1.Titan.interfaces.RateInterface;
import group17.phase1.Titan.interfaces.SolarSystemInterface;
import group17.phase1.Titan.interfaces.StateInterface;
import group17.phase1.Titan.interfaces.Vector3dInterface;

import java.util.List;

public class MetaSolarSystem implements SolarSystemInterface, StateInterface, RateInterface
{


    @Override
    public List<CelestialBody> getCelestialBodies() {
        return null;
    }

    @Override
    public void initPlanetPositions() {

    }

    @Override
    public List<Vector3dInterface> getRateOfChange() {
        return null;
    }

    @Override
    public List<Vector3dInterface> getPositions() {
        return null;
    }


    @Override
    public StateInterface addMul(double step, RateInterface rate) {
        return null;
    }
}
