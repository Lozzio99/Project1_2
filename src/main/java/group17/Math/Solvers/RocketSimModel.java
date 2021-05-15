package group17.Math.Solvers;

import group17.Interfaces.*;
import group17.Main;
import group17.Math.Utils.Vector3D;
import group17.Simulation.Simulation;

import static group17.Config.*;

public class RocketSimModel {
    public static Vector3dInterface targetPosition;
    static SimulationInterface simulation;
    static ODESolverInterface solver;
    static StateInterface initialState;
    public static NewtRaphFunction pF = new NewtRaphFunction() {
        @Override
        public Vector3dInterface stateFX(Vector3dInterface initPos, Vector3dInterface initVelocity, double time) {
            // init parameters
            simulation = Main.simulation = new Simulation();
            simulation.initSystem();
            simulation.getSystem().initRocket();
            //simulation.getSystem().getCelestialBodies().get(11).setVectorLocation(initPos);
            simulation.getSystem().getCelestialBodies().get(11).setVectorVelocity(initVelocity);
            simulation.getSystem().initialState();
            simulation.initUpdater();
            initialState = simulation.getSystem().systemState();
            solver = simulation.getUpdater().getSolver();
            // solve trajectory
            StateInterface[] solution = solver.solve(solver.getFunction(), simulation.getSystem().systemState(), time, STEP_SIZE);
            assert (simulation.getSystem().getCelestialBodies().size() > 10 &&
                    !simulation.getSystem().getCelestialBodies().get(11).isCollided());
            return solution[solution.length - 1].getPositions().get(11);
        }

        @Override
        public Vector3dInterface modelFx(Vector3dInterface vector) {
            Vector3D aprxPos = (Vector3D) pF.stateFX(NewtonRaphsonSolver.initPos, vector, NewtonRaphsonSolver.endTime);
            return targetPosition.sub(aprxPos);
        }
    };

    static {
        SOLVER = VERLET_VEL_SOLVER; // put here the best solver
        STEP_SIZE = 10;
        INSERT_ROCKET = true;
    }

    public static void main(String[] args) {

    }

}
