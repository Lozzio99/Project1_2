package group17.Math.Solvers;

import group17.Interfaces.*;
import group17.Main;
import group17.Math.Utils.Vector3D;
import group17.Simulation.Simulation;

import static group17.Config.*;

public class RocketSimModel {
    public static Vector3dInterface targetPosition;
    static SimulationInterface simulationTest;
    static ODESolverInterface solver;
    static StateInterface initialState;
    public static NewtRaphFunction pF = new NewtRaphFunction() {
        @Override
        public Vector3dInterface stateFX(Vector3dInterface initPos, Vector3dInterface initVelocity, double time) {
            // init parameters
            simulationTest = Main.simulation = new Simulation();
            simulationTest.initSystem();
            //simulationTest.getSystem().getCelestialBodies().get(11).setVectorLocation(initPos);
            simulationTest.getSystem().getCelestialBodies().get(11).setVectorVelocity(initVelocity);
            simulationTest.getSystem().initialState();
            simulationTest.initUpdater();
            initialState = simulationTest.getSystem().systemState();
            solver = simulationTest.getUpdater().getSolver();
            // solve trajectory
            StateInterface[] solution = solver.solve(solver.getFunction(), simulationTest.getSystem().systemState(), time, STEP_SIZE);
            assert (simulationTest.getSystem().getCelestialBodies().size() > 10 &&
                    !simulationTest.getSystem().getCelestialBodies().get(11).isCollided());
            System.out.println(solution.length);
            return solution[solution.length - 1].getPositions().get(11);
        }


    };
    public static Function<Vector3dInterface> modelFx = x -> {
        Vector3D aprxPos = (Vector3D) pF.stateFX(NewtonRaphsonSolver.initPos, x, NewtonRaphsonSolver.endTime);
        return targetPosition.sub(aprxPos);
    };

    static {
        DEFAULT_SOLVER = VERLET_VEL_SOLVER; // put here the best solver
        STEP_SIZE = 10;
        INSERT_ROCKET = true;
    }

    public static void main(String[] args) {

    }

}
