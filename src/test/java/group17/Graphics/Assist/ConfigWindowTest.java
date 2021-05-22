package group17.Graphics.Assist;

import group17.Graphics.UserDialogWindow;
import group17.Simulation.Simulation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;

import static group17.Main.simulation;
import static group17.Main.userDialog;
import static group17.Utils.Config.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfigWindowTest {

    /**
     * assertNotSame(Boolean.valueOf(CONFIG), Boolean.valueOf(copy));
     */

    static ConfigWindow c;

    @BeforeAll
    static void init() {
        c = new ConfigWindow();
        simulation = new Simulation();
        simulation.setAssist(userDialog = new UserDialogWindow());
        simulation.init();
    }

    @AfterAll
    static void tearDown() {
        simulation.getGraphics().getFrame().dispose();
        simulation = null;
        userDialog.getFrame().dispose();
    }

    @Test
    @DisplayName("Testing buttons")
    void testEnableGraphics() {
        ActionEvent e = new ActionEvent(this, 0, null);
        boolean copy = ENABLE_GRAPHICS;
        ConfigWindow.buttons[0].getActionListeners()[0].actionPerformed(e);
        assertTrue(ENABLE_GRAPHICS != copy);
        e = new ActionEvent(this, 1, null);
        copy = ENABLE_GRAPHICS;
        ConfigWindow.buttons[0].getActionListeners()[0].actionPerformed(e);
        assertTrue(ENABLE_GRAPHICS != copy);
    }

    @Test
    @DisplayName("Testing buttons")
    void testEnableAssist() {
        ActionEvent e = new ActionEvent(this, 0, null);
        boolean copy = LAUNCH_ASSIST;
        ConfigWindow.buttons[1].getActionListeners()[0].actionPerformed(e);
        assertTrue(LAUNCH_ASSIST != copy);
        e = new ActionEvent(this, 1, null);
        copy = LAUNCH_ASSIST;
        ConfigWindow.buttons[1].getActionListeners()[0].actionPerformed(e);
        assertTrue(LAUNCH_ASSIST != copy);
    }

    @Test
    @DisplayName("Testing buttons")
    void testEnableReport() {
        ActionEvent e = new ActionEvent(this, 0, null);
        boolean copy = REPORT;
        ConfigWindow.buttons[2].getActionListeners()[0].actionPerformed(e);
        assertTrue(REPORT != copy);
        e = new ActionEvent(this, 1, null);
        copy = REPORT;
        ConfigWindow.buttons[2].getActionListeners()[0].actionPerformed(e);
        assertTrue(REPORT != copy);
    }

    @Test
    @DisplayName("Testing buttons")
    void testEnablePlot() {
        ActionEvent e = new ActionEvent(this, 0, null);
        boolean copy = PLOT;
        ConfigWindow.buttons[3].getActionListeners()[0].actionPerformed(e);
        assertTrue(PLOT != copy);
        e = new ActionEvent(this, 1, null);
        copy = PLOT;
        ConfigWindow.buttons[3].getActionListeners()[0].actionPerformed(e);
        assertTrue(PLOT != copy);
    }

    @Test
    @DisplayName("Testing buttons")
    void testEnableRocketInfo() {
        ActionEvent e = new ActionEvent(this, 0, null);
        boolean copy = ROCKET_INFO;
        ConfigWindow.buttons[4].getActionListeners()[0].actionPerformed(e);
        assertTrue(ROCKET_INFO != copy);
        e = new ActionEvent(this, 1, null);
        copy = ROCKET_INFO;
        ConfigWindow.buttons[4].getActionListeners()[0].actionPerformed(e);
        assertTrue(ROCKET_INFO != copy);
    }

    @Test
    @DisplayName("Testing buttons")
    void testEnablePerformance() {
        ActionEvent e = new ActionEvent(this, 0, null);
        boolean copy = PERFORMANCE;
        ConfigWindow.buttons[5].getActionListeners()[0].actionPerformed(e);
        assertTrue(PERFORMANCE != copy);
        e = new ActionEvent(this, 1, null);
        copy = PERFORMANCE;
        ConfigWindow.buttons[5].getActionListeners()[0].actionPerformed(e);
        assertTrue(PERFORMANCE != copy);
    }

    @Test
    @DisplayName("Testing buttons")
    void testEnableErrorsEval() {
        ActionEvent e = new ActionEvent(this, 0, null);
        boolean copy = ERROR_EVALUATION;
        ConfigWindow.buttons[6].getActionListeners()[0].actionPerformed(e);
        assertTrue(ERROR_EVALUATION != copy);
        e = new ActionEvent(this, 1, null);
        copy = ERROR_EVALUATION;
        ConfigWindow.buttons[6].getActionListeners()[0].actionPerformed(e);
        assertTrue(ERROR_EVALUATION != copy);
    }

    @Test
    @DisplayName("Testing buttons")
    void testEnableInsertRocket() {
        ActionEvent e = new ActionEvent(this, 0, null);
        boolean copy = INSERT_ROCKET;
        ConfigWindow.buttons[7].getActionListeners()[0].actionPerformed(e);
        assertTrue(INSERT_ROCKET != copy);
        e = new ActionEvent(this, 1, null);
        copy = INSERT_ROCKET;
        ConfigWindow.buttons[7].getActionListeners()[0].actionPerformed(e);
        assertTrue(INSERT_ROCKET != copy);
    }

    @Test
    @DisplayName("Testing buttons")
    void testEnableDrawTraj() {
        ActionEvent e = new ActionEvent(this, 0, null);
        boolean copy = DRAW_TRAJECTORIES;
        ConfigWindow.buttons[8].getActionListeners()[0].actionPerformed(e);
        assertTrue(DRAW_TRAJECTORIES != copy);
        e = new ActionEvent(this, 1, null);
        copy = DRAW_TRAJECTORIES;
        ConfigWindow.buttons[8].getActionListeners()[0].actionPerformed(e);
        assertTrue(DRAW_TRAJECTORIES != copy);
    }

    @Test
    @DisplayName("Testing buttons")
    void testEnableDrawNames() {
        ActionEvent e = new ActionEvent(this, 0, null);
        boolean copy = NAMES;
        ConfigWindow.buttons[9].getActionListeners()[0].actionPerformed(e);
        assertTrue(NAMES != copy);
        e = new ActionEvent(this, 1, null);
        copy = NAMES;
        ConfigWindow.buttons[9].getActionListeners()[0].actionPerformed(e);
        assertTrue(NAMES != copy);
    }

    @Test
    @DisplayName("Testing buttons")
    void testEnableDebug() {
        ActionEvent e = new ActionEvent(this, 0, null);
        boolean copy = DEBUG;
        ConfigWindow.buttons[10].getActionListeners()[0].actionPerformed(e);
        assertTrue(DEBUG != copy);
        e = new ActionEvent(this, 1, null);
        copy = DEBUG;
        ConfigWindow.buttons[10].getActionListeners()[0].actionPerformed(e);
        assertTrue(DEBUG != copy);
    }
}