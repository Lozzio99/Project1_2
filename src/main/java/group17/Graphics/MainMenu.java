package group17.Graphics;

import javax.swing.*;
import java.awt.*;

import static group17.Config.*;

@SuppressWarnings("rawtypes")
public abstract class MainMenu {
    // Variables

    static final String[] SIMULATION_TYPES = {"Rocket Simulation", "Pendulum Simulation", "Numerical Simulation", "Particles Simulation", "Solar System Simulation"};
    static final String[] CPU_LEVELS = {"Min CPU", "Max CPU"};
    static final String[] SOLVERS = {"Euler", "Runge Kutta", "Verlet (VEL)", "Verlet (STD)"};
    static private final int FRAME_WIDTH = 1000;
    static private final int FRAME_HEIGHT = 300;
    protected int currentSimulationType = -1;
    protected int currentCPULevel = -1;
    static protected int currentSolver = -1;

    static JFrame frame;
    JPanel controlPanel;
    JLabel titleLabel;
    JLabel simulationTypeLabel;
    JLabel cpuLevelLabel;
    JLabel solverLabel;
    JLabel printLogLabel;
    JLabel consoleDebugLabel;
    JLabel trajectoryLengthLabel;
    JLabel stepSizeLabel;
    JLabel particleLabel;
    JComboBox simulationTypeDropdown;
    JComboBox cpuLevelDropdown;
    JComboBox solverDropdown;
    JCheckBox printLogCheckBox;
    JSlider trajectoryLengthSlider;
    JTextField stepSizeText;
    JTextField particlesText;
    JCheckBox consoleDebugCheckBox;
    JButton startButton;

    /**
     * Constructor
     */
    public MainMenu() {
        //this.setFrame();
    }

    public void setFrame() {
        frame = new JFrame("Project 1.2 - Group 17");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLayout(null);
        frame.add(configFrame(new JPanel()));
        titleLabel = new JLabel("Space Simulation");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        titleLabel.setBounds(10, 10, 300, 30);
        frame.getContentPane().add(titleLabel, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public JPanel configFrame(JPanel controlPanel) {
        //frame.setLocationRelativeTo(null);

        controlPanel.setPreferredSize(new Dimension(700, 300));
        GroupLayout groupLayout = new GroupLayout(controlPanel);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        controlPanel.setLayout(groupLayout);
        controlPanel.setBackground(new Color(150, 150, 200));

        JLabel simulationTypeLabel, cpuLevelLabel, solverLabel,
                printLogLabel, consoleDebugLabel, trajectoryLengthLabel, stepSizeLabel,
                particleLabel;


        simulationTypeLabel = new JLabel("Simulation Type");
        cpuLevelLabel = new JLabel("CPU Level");
        solverLabel = new JLabel("Solver");
        printLogLabel = new JLabel("Print Log");
        consoleDebugLabel = new JLabel("Console Debug");
        trajectoryLengthLabel = new JLabel("Trajectory Length");
        stepSizeLabel = new JLabel("Step Size");
        particleLabel = new JLabel("Particles");


        JComboBox simulationTypeDropdown, cpuLevelDropdown, solverDropdown;

        simulationTypeDropdown = new JComboBox<>(SIMULATION_TYPES);
        simulationTypeDropdown.setSelectedIndex(0);
        cpuLevelDropdown = new JComboBox<>(CPU_LEVELS);
        cpuLevelDropdown.setSelectedIndex(0);
        solverDropdown = new JComboBox<>(SOLVERS);
        solverDropdown.setSelectedIndex(0);


        JCheckBox printLogCheckBox, consoleDebugCheckBox;

        printLogCheckBox = new JCheckBox();
        if (REPORT)
            printLogCheckBox.setSelected(true);

        consoleDebugCheckBox = new JCheckBox();
        if (DEBUG)
            consoleDebugCheckBox.setSelected(true);


        JSlider trajectoryLengthSlider = new JSlider();
        trajectoryLengthSlider.setMinimum(0);
        trajectoryLengthSlider.setMaximum(10000);
        trajectoryLengthSlider.setToolTipText("Length");

        JTextField stepSizeText, particlesText;
        stepSizeText = new JTextField(String.valueOf(STEP_SIZE));
        particlesText = new JTextField();

        JButton startButton = new JButton("Start Simulation");
        startButton.addActionListener(e -> {

            // --- Select the correct simulationInstance ---
            int currentSimulationType = switch (simulationTypeDropdown.getSelectedItem().toString()) {
                case ("Rocket Simulation") -> ROCKET_SIMULATION;
                case ("Pendulum Simulation") -> PENDULUM_SIMULATION;
                case ("Numerical Simulation") -> NUMERICAL_SIMULATION;
                case ("Particles Simulation") -> PARTICLES_SIMULATION;
                case ("Solar System Simulation") -> SOLAR_SYSTEM_SIMULATION;
                default -> SOLAR_SYSTEM_SIMULATION;
            };

            // --- Select the correct CPU type ---
            int currentCPULevel = switch (cpuLevelDropdown.getSelectedItem().toString()) {
                case ("Min CPU") -> MIN_CPU;
                case ("Max CPU") -> MAX_CPU;
                default -> MIN_CPU;
            };

            // --- Select the correct solver ---
            int currentSolver = switch (solverDropdown.getSelectedItem().toString()) {
                case ("Euler") -> EULER_SOLVER;
                case ("Runge Kutta") -> RUNGE_KUTTA_SOLVER;
                case ("Verlet (VEL)") -> VERLET_VEL_SOLVER;
                case ("Verlet (STD)") -> VERLET_STD_SOLVER;
                default -> EULER_SOLVER;
            };

            // --- Select the step size ---
            STEP_SIZE = Double.valueOf(stepSizeText.getText());
            if (DEBUG || REPORT)
                System.out.println("Step size: " + STEP_SIZE);


            DEBUG = consoleDebugCheckBox.isSelected();


            REPORT = printLogCheckBox.isSelected();

            if (!particlesText.getText().equals("")) {
                PARTICLES = Integer.valueOf(particlesText.getText());
            }

            // TRAJECTORY_LENGTH = trajectoryLengthSlider.getValue();


            startSimulation();
            //frame.dispose();
        });


        controlPanel.setBounds(10, 50, FRAME_WIDTH - 37, FRAME_HEIGHT - 97);

        //placer.add(titleLabel);
        //placer.add(controlPanel);


        // --- Horizontal Groups ---
        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(simulationTypeLabel)
                                .addComponent(cpuLevelLabel)
                                .addComponent(solverLabel)
                                .addComponent(printLogLabel)
                                .addComponent(consoleDebugLabel)
                                .addComponent(startButton))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(simulationTypeDropdown)
                                .addComponent(cpuLevelDropdown)
                                .addComponent(solverDropdown)
                                .addComponent(printLogCheckBox)
                                .addComponent(consoleDebugCheckBox))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(trajectoryLengthLabel)
                                .addComponent(stepSizeLabel)
                                .addComponent(particleLabel))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(trajectoryLengthSlider)
                                .addComponent(stepSizeText)
                                .addComponent(particlesText))
        );

        // --- Vertical Groups ---
        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(simulationTypeLabel)
                                .addComponent(simulationTypeDropdown)
                                .addComponent(trajectoryLengthLabel)
                                .addComponent(trajectoryLengthSlider))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cpuLevelLabel)
                                .addComponent(cpuLevelDropdown)
                                .addComponent(stepSizeLabel)
                                .addComponent(stepSizeText))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(solverLabel)
                                .addComponent(solverDropdown)
                                .addComponent(particleLabel)
                                .addComponent(particlesText))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(printLogLabel)
                                .addComponent(printLogCheckBox))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(consoleDebugLabel)
                                .addComponent(consoleDebugCheckBox))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(startButton))
        );
        return controlPanel;
    }

    public abstract void startSimulation();
}

