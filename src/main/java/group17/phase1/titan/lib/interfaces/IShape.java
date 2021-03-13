package group17.phase1.titan.lib.interfaces;

import java.awt.*;

public interface IShape {
    void render(Graphics g);

    void translate(float x, float y, float z);

    void rotate(boolean clockwise, float xDeg, float yDeg, float zDeg, Vector lightingVector);

    void setLighting(Vector lighting);
}
