package group17.Graphics.Assist;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static group17.Main.simulation;
import static group17.Main.userDialog;
import static group17.Utils.Config.*;

/**
 * The type Config window.
 */
public class ConfigWindow extends JPanel {
    //launch - stats - plot - rocket - performance - errors - controllers
    private static final String[] s = {"ENABLED", "DISABLED"};
    private static final JButton[] buttons = new JButton[11];
    private static final JLabel[] texts = new JLabel[11];
    private static final List<String> bStrings = new ArrayList<>();

    static {
        bStrings.add("GRAPHICS");
        bStrings.add("LAUNCH ASSIST");
        bStrings.add("REPORT");
        bStrings.add("PLOT");
        bStrings.add("ROCKET INFO");
        bStrings.add("PERFORMANCE");
        bStrings.add("ERRORS");
        bStrings.add("INSERT ROCKET");
        bStrings.add("DRAW TRAJECTORIES");
        bStrings.add("DRAW NAMES");
        bStrings.add("DEBUG");
    }

    private final Color r = new Color(255, 0, 0), g = new Color(0, 255, 35);


    /**
     * Instantiates a new Config window.
     */
    public ConfigWindow() {


        for (int i = 0; i < bStrings.size(); i++) {
            JButton j = new JButton(bStrings.get(i));
            j.setSize(new Dimension(90, 50));
            texts[i] = (new JLabel(bStrings.get(i)));
            configButton(j, i);
            buttons[i] = (j);
        }


        //pane.setSize(new Dimension(600,500));
        this.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        this.setLayout(new GridLayout(6, 4, 40, 80));

        for (int i = 0; i < buttons.length; i++) {
            this.add(buttons[i]);
            this.add(texts[i]);
        }

    }


    /**
     * THIS IS THE ONLY WAY I KNOW TO DO THIS:
     * METHODS (JAVA CREATES COPIES)
     * LISTS( JAVA CREATES COPIES)
     * STATIC BLOCKS( JAVA CREATES COPIES)
     * ATOMIC VARIABLES (JAVA CREATES COPIES)
     * ARRAYS ( JAVA CREATES COPIES)
     * THERE WAS NO WAY TO MAKE THEM UPDATE FROM
     * A COPY WITHOUT EXPLICITLY CALLING THE DESIRED VARIABLE
     * btw ive tried everything of the above feel free to change my mind
     */

    private void configButton(JButton j, int i) {

        switch (i) {
            case 0 -> {
                texts[i].setText(ENABLE_GRAPHICS ? s[0] : s[1]);
                texts[i].setForeground(ENABLE_GRAPHICS ? g : r);
                j.addActionListener(e -> {
                    //this is necessary
                    if (ENABLE_GRAPHICS) {
                        if (simulation.getGraphics() != null && REPORT) {
                            simulation.getReporter().report(Thread.currentThread(), new UnsupportedOperationException("GRAPHICS WILL BE SET NOT VISIBLE"));
                            simulation.getGraphics().getFrame().setVisible(false);
                        }
                    } else {
                        if (simulation.getGraphics() == null) {
                            simulation.initGraphics();
                            simulation.reset();
                        } else {
                            simulation.getGraphics().getFrame().setVisible(true);
                        }
                    }
                    ENABLE_GRAPHICS = !ENABLE_GRAPHICS;
                    texts[i].setText(ENABLE_GRAPHICS ? s[0] : s[1]);
                    texts[i].setForeground(ENABLE_GRAPHICS ? g : r);
                });
            }
            case 1 -> {
                texts[i].setText(LAUNCH_ASSIST ? s[0] : s[1]);
                texts[i].setForeground(LAUNCH_ASSIST ? g : r);
                j.addActionListener(e -> {
                    LAUNCH_ASSIST = !LAUNCH_ASSIST;
                    texts[i].setText(LAUNCH_ASSIST ? s[0] : s[1]);
                    texts[i].setForeground(LAUNCH_ASSIST ? g : r);
                    simulation.reset();
                });
                j.addChangeListener(e -> {
                    if (LAUNCH_ASSIST)
                        userDialog.enable(1);
                    else
                        userDialog.disable(1);
                });
            }
            case 2 -> {
                texts[i].setText(REPORT ? s[0] : s[1]);
                texts[i].setForeground(REPORT ? g : r);
                j.addActionListener(e -> {
                    REPORT = !REPORT;
                    texts[i].setText(REPORT ? s[0] : s[1]);
                    texts[i].setForeground(REPORT ? g : r);
                });
                j.addChangeListener(e -> {
                    if (REPORT)
                        userDialog.enable(2);
                    else
                        userDialog.disable(2);
                });
            }
            case 3 -> {
                texts[i].setText(PLOT ? s[0] : s[1]);
                texts[i].setForeground(PLOT ? g : r);
                j.addActionListener(e -> {
                    PLOT = !PLOT;
                    texts[i].setText(PLOT ? s[0] : s[1]);
                    texts[i].setForeground(PLOT ? g : r);
                });
                j.addChangeListener(e -> {
                    if (PLOT)
                        userDialog.enable(3);
                    else
                        userDialog.disable(3);
                });
            }
            case 4 -> {
                texts[i].setText(ROCKET_INFO ? s[0] : s[1]);
                texts[i].setForeground(ROCKET_INFO ? g : r);
                j.addActionListener(e -> {
                    ROCKET_INFO = !ROCKET_INFO;
                    texts[i].setText(ROCKET_INFO ? s[0] : s[1]);
                    texts[i].setForeground(ROCKET_INFO ? g : r);
                });
                j.addChangeListener(e -> {
                    if (ROCKET_INFO)
                        userDialog.enable(4);
                    else
                        userDialog.disable(4);
                });
            }
            case 5 -> {
                texts[i].setText(PERFORMANCE ? s[0] : s[1]);
                texts[i].setForeground(PERFORMANCE ? g : r);
                j.addActionListener(e -> {
                    PERFORMANCE = !PERFORMANCE;
                    texts[i].setText(PERFORMANCE ? s[0] : s[1]);
                    texts[i].setForeground(PERFORMANCE ? g : r);
                });
                j.addChangeListener(e -> {
                    if (PERFORMANCE)
                        userDialog.enable(5);
                    else
                        userDialog.disable(5);
                });
            }
            case 6 -> {
                texts[i].setText(ERROR_EVALUATION ? s[0] : s[1]);
                texts[i].setForeground(ERROR_EVALUATION ? g : r);
                j.addActionListener(e -> {
                    ERROR_EVALUATION = !ERROR_EVALUATION;
                    texts[i].setText(ERROR_EVALUATION ? s[0] : s[1]);
                    texts[i].setForeground(ERROR_EVALUATION ? g : r);
                });
                j.addChangeListener(e -> {
                    if (ERROR_EVALUATION)
                        userDialog.enable(6);
                    else
                        userDialog.disable(6);
                });
            }
            case 7 -> {
                texts[i].setText(INSERT_ROCKET ? s[0] : s[1]);
                texts[i].setForeground(INSERT_ROCKET ? g : r);
                j.addActionListener(e -> {
                    INSERT_ROCKET = !INSERT_ROCKET;
                    texts[i].setText(INSERT_ROCKET ? s[0] : s[1]);
                    texts[i].setForeground(INSERT_ROCKET ? g : r);
                    simulation.reset();
                });
                j.addChangeListener(e -> {
                    if (INSERT_ROCKET) {
                        userDialog.enable(4);
                    } else
                        userDialog.disable(4);
                });
            }
            case 8 -> {
                texts[i].setText(DRAW_TRAJECTORIES ? s[0] : s[1]);
                texts[i].setForeground(DRAW_TRAJECTORIES ? g : r);
                j.addActionListener(e -> {
                    DRAW_TRAJECTORIES = !DRAW_TRAJECTORIES;
                    texts[i].setText(DRAW_TRAJECTORIES ? s[0] : s[1]);
                    texts[i].setForeground(DRAW_TRAJECTORIES ? g : r);
                });
            }
            case 9 -> {
                texts[i].setText(NAMES ? s[0] : s[1]);
                texts[i].setForeground(NAMES ? g : r);
                j.addActionListener(e -> {
                    NAMES = !NAMES;
                    texts[i].setText(NAMES ? s[0] : s[1]);
                    texts[i].setForeground(NAMES ? g : r);
                });
            }
            case 10 -> {
                texts[i].setText(DEBUG ? s[0] : s[1]);
                texts[i].setForeground(DEBUG ? g : r);
                j.addActionListener(e -> {
                    DEBUG = !DEBUG;
                    texts[i].setText(DEBUG ? s[0] : s[1]);
                    texts[i].setForeground(DEBUG ? g : r);
                });
            }
        }


    }


}
