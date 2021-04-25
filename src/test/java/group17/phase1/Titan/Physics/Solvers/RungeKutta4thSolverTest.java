package group17.phase1.Titan.Physics.Solvers;

import group17.phase1.Titan.Interfaces.ODEFunctionInterface;
import group17.phase1.Titan.Interfaces.RateInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.System.RateOfChange;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class RungeKutta4thSolverTest {

    ODEFunctionInterface f = (t, y) ->
    {
        y = StateInterface.clone(y);
        return new RateOfChange();
    };

    @Test
    @DisplayName("Solve")
    void Solve() {
    }

    @Test
    @DisplayName("TestSolve")
    void TestSolve() {
    }

    @Test
    @DisplayName("Step")
    void Step() {

    }

    @Test
    @DisplayName("GetFunction")
    void GetFunction() {

    }

    //y1 = y0 + h*f(t,y);

    class State implements StateInterface {

        double x;


        @Override
        public StateInterface state0() {
            this.x = 0;
            return this;
        }

        @Override
        public List<Vector3dInterface> getPositions() {

            return new ArrayList<>();
        }

        @Override
        public void setPositions(List<Vector3dInterface> v) {

        }

        @Override
        public StateInterface addMul(double step, RateInterface rate) {

            //somthing = step * rate == h*f(t,y)
            //y0 = y0 + that somthing;
            return null;
        }

        @Override
        public RateInterface getRateOfChange() {
            return null;
        }

        @Override
        public void initialVelocity() {

        }
    }
}