package group17.phase1.Titan.Interfaces;

import java.util.List;

public interface RateInterface
{
    List<Vector3dInterface> getShiftVectors();

    void setShiftVectors(List<Vector3dInterface> nextState);
}
