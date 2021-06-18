import org.junit.jupiter.api.Test;
import phase3.Math.ADT.Vector3dInterface;
import phase3.System.State.RateInterface;
import phase3.System.State.RateOfChange;

import static org.junit.jupiter.api.Assertions.*;

class RateInterfaceTest {

    @Test
    void get() {
        RateInterface<Double> rate = new RateOfChange<Double>(2.0,3.0);
        assertEquals(rate.get()[0],2.0);
        assertEquals(rate.get()[1],3.0);

    }
}