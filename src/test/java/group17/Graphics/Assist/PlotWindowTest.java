package group17.Graphics.Assist;

import group17.Interfaces.Function;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;
import static org.junit.jupiter.api.Assertions.*;

class PlotWindowTest {


    static PlotWindow plot = new PlotWindow();
    static JFrame frame = new JFrame();

    @BeforeAll
    @DisplayName("Init")
    static void Init() {
        frame.setSize(500, 500);
        frame.add(plot);
        frame.setVisible(true);
        plot.init();
        assertNotNull(plot.leftGraph);
        assertNotNull(plot.rightGraph);
        assertNotNull(plot.getGraphics());
    }

    @AfterAll
    static void shutDown() {
        frame.dispose();
    }

    @Test
    @DisplayName("Evaluate")
    void Evaluate() {

        Function<Double> f = x -> 80 * sin(x / 17);
        Function<Double> g = x -> 80 * cos(x / 17);

        double[] xs = new double[1000], ySin = new double[1000], yCos = new double[1000];
        int x_ = PlotWindow.GraphPane.graphSize.width / 2;
        double k = -x_;
        for (int i = 0; i < xs.length; i++) {
            xs[i] = k;
            ySin[i] = f.apply(k);
            yCos[i] = g.apply(k);
            k += 0.5;
        }
        plot.evaluate(xs, ySin, 0);
        plot.evaluate(xs, yCos, 1);
    }

    @Test
    @DisplayName("EvaluateSorting")
    void Sort() {

        double[] xs = {
                0, 1, 2, 3, 8, 5, 6, 7, 4, 9
        }, ys = {
                0, 1, 2, 3, 8, 5, 6, 7, 4, 9
        };
        PlotWindow.GraphPane.sortingForDrawing(xs, ys);

        assertArrayEquals(new double[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, xs);
        assertArrayEquals(new double[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, ys);

    }

    @Test
    @DisplayName("Evaluate")
    void MisMatchXsAndYs() {
        double[] xs = new double[1000], ySin = new double[900];
        assertThrows(RuntimeException.class, () -> plot.evaluate(xs, ySin, 0), "drawX and drawY must have the same size");
        assertDoesNotThrow(() -> plot.evaluate(xs, ySin, -10));
    }

    @Test
    @DisplayName("Evaluate")
    void ExceedMaximumSize() {
        double[] xs = new double[1100], ySin = new double[1100];
        assertThrows(RuntimeException.class, () -> plot.evaluate(xs, ySin, 0), "need to implement multiple points arrays");
    }

    @Test
    @DisplayName("Evaluate")
    void TestGraphicsResponse() {
        assertDoesNotThrow(() -> plot.update(plot.getGraphics()));
    }
}