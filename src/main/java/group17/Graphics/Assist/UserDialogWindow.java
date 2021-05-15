package group17.Graphics.Assist;

import group17.Simulation.Simulation;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static group17.Config.*;
import static group17.Main.simulation;

public class UserDialogWindow {
    private static final List<String> tabs = new ArrayList<>(), tips = new ArrayList<>();
    private static final List<Color> backGrounds = new ArrayList<>();
    private static final List<ImageIcon> icons = new ArrayList<>();
    static {
        tabs.add("MENU");
        backGrounds.add(new Color(204, 122, 255, 211));
        icons.add(loadIcon("home.png"));
        tips.add("HOME");
        tabs.add("LAUNCH");
        backGrounds.add(new Color(52, 52, 52, 215));
        icons.add(loadIcon("launch.png"));
        tips.add("Launch settings and infos");
        tabs.add("STATS");
        backGrounds.add(new Color(122, 202, 255, 211));
        icons.add(loadIcon("stats.png"));
        tips.add("Stats and evaluations");
        tabs.add("PLOT");
        backGrounds.add(new Color(114, 184, 255, 211));
        icons.add(loadIcon("plot.png"));
        tips.add("Visual plot");
        tabs.add("ROCKET");
        backGrounds.add(new Color(181, 252, 233, 211));
        icons.add(loadIcon("rocket.png"));
        tips.add("Rocket flight and mission infos");
        tabs.add("PERFORMANCE");
        backGrounds.add(new Color(201, 101, 255, 211));
        icons.add(loadIcon("performance.png"));
        tips.add("Cpu level and performance");
        tabs.add("ERRORS");
        backGrounds.add(new Color(195, 255, 220, 211));
        icons.add(loadIcon("errors.png"));
        tips.add("Evaluation errors");
        tabs.add("CONTROLLERS");
        backGrounds.add(new Color(94, 231, 255, 211));
        icons.add(loadIcon("control.png"));
        tips.add("Simulation controllers");
        tabs.add("SETTINGS");
        backGrounds.add(new Color(76, 77, 78, 211));
        icons.add(loadIcon("settings.png"));
        tips.add("Configure settings");

    }
    private final JFrame frame;
    private JTabbedPane mainPane;
    private LaunchAssist assist;
    private SimulationDataWindow dataWindow;
    private MainMenu menu;
    public UserDialogWindow() {
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

           /*
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
             */
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            //
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException ex) {
            //ex.printStackTrace();
        }

        this.frame = new JFrame();
        this.frame.setSize(new Dimension(1280, 620));
        this.frame.setIconImage(loadIcon("death.png").getImage());
        this.frame.getContentPane().add(makeTabs(), BorderLayout.CENTER);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }


    private Component makeTabs() {
        this.mainPane = new JTabbedPane();
        this.mainPane.setBackground(new Color(193, 204, 255, 228));
        for (int i = 0; i < tabs.size(); i++) {
            this.mainPane.addTab(tabs.get(i), configCard(i));
            this.mainPane.setIconAt(i, icons.get(i));
            this.mainPane.setEnabledAt(i, false);
            this.mainPane.getComponentAt(i).setBackground(backGrounds.get(i));
            this.mainPane.setToolTipTextAt(i, tips.get(i));
        }
        this.config();
        return this.mainPane;
    }

    private static ImageIcon loadIcon(String path) {
        ImageIcon i = new ImageIcon(UserDialogWindow.class.getClassLoader().getResource("icons/" + path).getFile());
        Image scaled = i.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        i.setImage(scaled);
        return i;
    }

    private void config() {
        this.enable(0, 7);
        Color c = new Color(11, 255, 3), w = new Color(135, 0, 255), c2 = new Color(0, 255, 255);
        Border b = new CompoundBorder(new StrokeBorder(new BasicStroke(8.f), w), new BevelBorder(BevelBorder.RAISED, c, c2, w.darker(), c2.darker()));
        this.mainPane.setBorder(b);
        this.mainPane.setTabPlacement(JTabbedPane.TOP);
        this.mainPane.setRequestFocusEnabled(true);
        this.mainPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    public Component configCard(int index) {
        switch (index) {
            case 0 -> {
                return initMenu();
            }
            case 1 -> {
                return this.assist = new LaunchAssist();
            }
            case 2 -> {
                return this.dataWindow = new SimulationDataWindow();
            }
            case 3 -> {
                return new PlotWindow().init().test();
            }
            case 4 -> {
                return new RocketWindow();
            }
            case 5 -> {
                return new PerformanceWindow();
            }
            case 6 -> {
                return new ErrorWindow();
            }
            case 7 -> {
                return new ControllersWindow();
            }
            case 8 -> {
                return new ConfigWindow();
            }
            default -> {
                return new JPanel();
            }
        }
    }

    private Component initMenu() {
        UserDialogWindow d = this;
        this.menu = new MainMenu() {
            @Override
            public void startSimulation() {
                SOLVER = currentSolver;
                if (simulation == null) {
                    simulation = new Simulation();
                    simulation.setAssist(d);
                    simulation.init();
                    simulation.start();
                }
                enableTabs();
                mainPane.setSelectedIndex(1);
            }
        };
        return this.menu.configFrame(new JPanel());
    }

    public void enable(int... i) {
        for (int j : i) this.mainPane.setEnabledAt(j, true);
    }
    public void disable(int... i) {
        for (int j : i) this.mainPane.setEnabledAt(j, false);
    }
    public void enableTabs() {
        if (LAUNCH_ASSIST) enable(1);
        if (REPORT) enable(2);
        if (PLOT) enable(3);
        if (ROCKET_INFO) enable(4);
        if (PERFORMANCE) enable(5);
        if (ERROR_EVALUATION) enable(6);
        enable(8);
    }


    public MainMenu getMainMenu() {
        return menu;
    }
    public LaunchAssist getLaunchAssist() {
        return assist;
    }
    public JTabbedPane getMainPane() {
        return mainPane;
    }
    public SimulationDataWindow getOutputWindow() {
        return this.dataWindow;
    }
}
