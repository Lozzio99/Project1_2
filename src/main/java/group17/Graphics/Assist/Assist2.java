package group17.Graphics.Assist;

import group17.Graphics.AssistFrame;
import group17.Graphics.MainMenu;
import group17.Simulation.Simulation;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static group17.Config.SOLVER;
import static group17.Main.simulationInstance;

public class Assist2 {
    private static final List<String> tabs = new ArrayList<>();
    private static final List<Color> backGrounds = new ArrayList<>();
    private static final List<ImageIcon> icons = new ArrayList<>();
    static {
        tabs.add("MENU");
        backGrounds.add(new Color(204, 122, 255, 211));
        icons.add(loadIcon("home.png"));
        tabs.add("LAUNCH");
        backGrounds.add(new Color(52, 52, 52, 215));
        icons.add(loadIcon("launch.png"));

        tabs.add("STATS");
        backGrounds.add(new Color(122, 202, 255, 211));
        icons.add(loadIcon("stats.png"));
        tabs.add("PLOT");
        backGrounds.add(new Color(122, 202, 255, 211));
        icons.add(loadIcon("plot.png"));
        tabs.add("ROCKET");
        backGrounds.add(new Color(122, 202, 255, 211));
        icons.add(loadIcon("rocket.png"));
        tabs.add("PERFORMANCE");
        backGrounds.add(new Color(122, 202, 255, 211));
        icons.add(loadIcon("performance.png"));
        tabs.add("ERRORS");
        backGrounds.add(new Color(122, 202, 255, 211));
        icons.add(loadIcon("errors.png"));
        tabs.add("CONTROLLERS");
        backGrounds.add(new Color(122, 202, 255, 211));
        icons.add(loadIcon("control.png"));
        tabs.add("SETTINGS");
        backGrounds.add(new Color(122, 202, 255, 211));
        icons.add(loadIcon("settings.png"));

    }

    private final JFrame frame;
    Color c = new Color(204, 122, 255, 211);
    private JTabbedPane mainPane;
    private AssistFrame assist;

    public Assist2() {
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */

        this.frame = new JFrame();
        this.frame.setSize(new Dimension(1080, 620));
        this.frame.setIconImage(loadIcon("death.png").getImage());
        this.frame.getContentPane().add(makeTabs(), BorderLayout.CENTER);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
        UIManager.put("swing.boldMetal", Boolean.FALSE);
    }

    private static ImageIcon loadIcon(String path) {
        ImageIcon i = new ImageIcon(Assist2.class.getClassLoader().getResource("icons/" + path).getFile());
        Image scaled = i.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        i.setImage(scaled);
        return i;
    }

    private Component makeTabs() {
        this.mainPane = new JTabbedPane();
        this.mainPane.setBackground(new Color(225, 130, 255, 215));
        Color c = new Color(57, 57, 57), w = new Color(252, 0, 71);
        this.mainPane.setBorder(new BasicBorders.MenuBarBorder(c, w));
        this.mainPane.setTabPlacement(JTabbedPane.TOP);
        for (int i = 0; i < tabs.size(); i++) {
            this.mainPane.addTab(tabs.get(i), configCard(i));
            this.mainPane.setIconAt(i, icons.get(i));
        }
        this.mainPane.setToolTipTextAt(0, "HOME");
        this.mainPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        return this.mainPane;
    }

    public JPanel configCard(int index) {
        switch (index) {
            case 0 -> {
                return initMenu(index);
            }
            case 1 -> {
                return initLaunch(index);
            }
            default -> {
                return new Card(index);
            }
        }
    }

    private Card initLaunch(int index) {
        Card card = new Card(index);
        this.assist = new AssistFrame(card);
        card.add(this.assist, BorderLayout.CENTER);
        return card;
    }

    private Card initMenu(int index) {
        Card card = new Card(index);
        MainMenu menu = new MainMenu() {
            @Override
            public void startSimulation() {
                SOLVER = currentSolver;
                simulationInstance = new Simulation();
                simulationInstance.setAssist(assist);
                // Also the testing in that module will be way much easier considering that we only need
                // to test unit cases (single methods, not the overall simulation)
                simulationInstance.init();
                simulationInstance.start();
            }
        };
        menu.configFrame(card);
        return card;
    }

    public static class Card extends JPanel {
        private final String name;
        private Color background;

        public Card(int index) {
            this.name = tabs.get(index);
            this.setBackground(backGrounds.get(index));
        }
    }


}
