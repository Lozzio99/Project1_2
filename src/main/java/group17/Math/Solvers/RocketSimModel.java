package group17.Math.Solvers;

import group17.Interfaces.*;
import group17.Math.Lib.Vector3D;
import group17.Simulation.Simulation;
import group17.System.SolarSystem;

import static group17.Main.simulation;
import static group17.Utils.Config.*;

public class RocketSimModel {
    public static Vector3dInterface targetPosition;
    static ODESolverInterface solver;
    static StateInterface initialState;

    public static NewtRaphFunction pF = vector -> {
        Vector3D aprxPos = (Vector3D) RocketSimModel.stateFx(NewtonRaphsonSolver.initPos, vector, NewtonRaphsonSolver.endTime);
        System.out.println(aprxPos.toString());
        return targetPosition.sub(aprxPos);
    };

    static {
        DEFAULT_SOLVER = MIDPOINT_SOLVER; // put here the best solver
        STEP_SIZE = 200;
        REPORT = false;
        INSERT_ROCKET = true;
        CHECK_COLLISIONS = false;
    }

    public static SystemInterface createSystem(Vector3dInterface initLocation, Vector3dInterface initVelocity) {
        final SystemInterface[] system = new SystemInterface[1];
        simulation = new Simulation() {
            @Override
            public void initSystem() {
                system[0] = new SolarSystem();
                system[0].initPlanets();
                system[0].initClock();
                system[0].initRocket();
                system[0].initialState();
                system[0].systemState().getPositions().set(11, initLocation);
                system[0].systemState().getRateOfChange().getVelocities().set(11, initVelocity);
            }

            @Override
            public SystemInterface getSystem() {
                return system[0];
            }
        };
        simulation.initSystem();
        simulation.initUpdater();
        return system[0];
    }

    public static Vector3dInterface stateFx(Vector3dInterface initPos, Vector3dInterface initVelocity, double timeFinal) {
        // init parameters
        SystemInterface system = createSystem(initPos, initVelocity);
        initialState = system.systemState().copy();
        solver = simulation.getUpdater().getSolver();

        // solve trajectory
        StateInterface[] solution = solver.solve(solver.getFunction(), initialState, timeFinal, STEP_SIZE);
        assert (simulation.getSystem().getCelestialBodies().size() > 10 &&
                !simulation.getSystem().getCelestialBodies().get(11).isCollided());
        return solution[solution.length - 1].getPositions().get(11).clone();
    }



    public static void main(String[] args) {
        // FIXME: For some reason, simulation produces the same result every time no matter what :/
        Vector3D res1 = (Vector3D) stateFx(new Vector3D(-1.471922101663588E11, -2.860995816266412E10, 8278183.19359608), new Vector3D(10000, 1000000.0, 600000.0), 86700 * 1e2);
        System.out.println(res1.toString());
        Vector3D res2 = (Vector3D) stateFx(new Vector3D(0.0, 0.0, 0.0), new Vector3D(100, 100.0, 100.0), 1);
        System.out.println(res2.toString());

        //(-3.4594511332232066E24,1.8655241966218185E25,-4.1859192756969905E20)
        //(-3.4594511332232066E24,1.8655241966218185E25,-4.1859192756969905E20)
        //(-3.4594511332232066E24,1.8655241966218185E25,-4.1859192756969905E20)

    }

}
