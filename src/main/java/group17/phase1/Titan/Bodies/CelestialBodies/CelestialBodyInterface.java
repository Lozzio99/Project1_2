package group17.phase1.Titan.Bodies.CelestialBodies;

import group17.phase1.Titan.Physics.Trajectories.CoordinateInterface;
import group17.phase1.Titan.Physics.Trajectories.Forces.Vector3D;
import group17.phase1.Titan.Physics.Trajectories.MovementInterface;

import java.util.List;

public interface CelestialBodyInterface extends CoordinateInterface, MovementInterface {

    double getMass();

    double getRadius();

    double getDensity();

    List<CelestialBodyInterface> attractors();

    Vector3D getVectorPosition();

    double getDistanceRadius(CelestialBodyInterface other);
}
