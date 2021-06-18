import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import phase3.System.State.RateInterface;
import phase3.System.State.RateOfChange;
import phase3.System.State.StateInterface;
import phase3.System.State.SystemState;

import static org.junit.jupiter.api.Assertions.*;

class StateInterfaceTest {

   private StateInterface<Double> y;
   private RateInterface<Double> rate;
   private double scalar;

    @BeforeEach
    void setup(){
         y = new SystemState<>(5.0,2.0);
         rate = new RateOfChange<>(6.5,3.0);
         scalar = 1.5;
    }

    @DisplayName("AddMulTest")
    @Test
    void addMul() {
      Double[] results =  y.addMul(scalar,rate).get();
        assertEquals(14.75, results[0]);
        assertEquals(6.5, results[1]);
    }

    @Test
    void get() {
        assertEquals(5.0,y.get()[0]);
        assertEquals(2.0,y.get()[1]);

    }

    @Test
    void copy() {
        StateInterface copy = y.copy();
        assertEquals(5.0,copy.get()[0]);
        assertEquals(2.0,copy.get()[1]);
    }
}