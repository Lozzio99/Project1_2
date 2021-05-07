package group17.Graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static group17.Config.*;

public abstract class MainMenu {
    // Variables

    final String[] SIMULATION_TYPES = {"Rocket Simulation", "Pendulum Simulation", "Numerical Simulation", "Particles Simulation", "Solar System Simulation"};
    final String[] CPU_LEVELS = {"Min CPU", "Max CPU"};
    final String[] SOLVERS = {"Euler", "Runge Kutta", "Verlet (VEL)", "Verlet (STD)"};
    private final int FRAME_WIDTH = 1000;
    private final int FRAME_HEIGHT = 300;
    JFrame frame;
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
        frame = new JFrame("Project 1.2 - Group 17");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLayout(null);
        //frame.setLocationRelativeTo(null);

        controlPanel = new JPanel();

        GroupLayout groupLayout = new GroupLayout(controlPanel);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        controlPanel.setLayout(groupLayout);
        controlPanel.setBackground(new Color(150, 150, 200));

        titleLabel = new JLabel("Space Simulation");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 24));

        simulationTypeLabel = new JLabel("Simulation Type");
        cpuLevelLabel = new JLabel("CPU Level");
        solverLabel = new JLabel("Solver");
        printLogLabel = new JLabel("Print Log");
        consoleDebugLabel = new JLabel("Console Debug");
        trajectoryLengthLabel = new JLabel("Trajectory Length");
        stepSizeLabel = new JLabel("Step Size");
        particleLabel = new JLabel("Particles");

        simulationTypeDropdown = new JComboBox(SIMULATION_TYPES);
        simulationTypeDropdown.setSelectedIndex(0);
        cpuLevelDropdown = new JComboBox(CPU_LEVELS);
        cpuLevelDropdown.setSelectedIndex(0);
        solverDropdown = new JComboBox(SOLVERS);
        solverDropdown.setSelectedIndex(0);

        printLogCheckBox = new JCheckBox();
        consoleDebugCheckBox = new JCheckBox();

        trajectoryLengthSlider = new JSlider();
        trajectoryLengthSlider.setMinimum(0);
        trajectoryLengthSlider.setMaximum(10000);
        trajectoryLengthSlider.setToolTipText("Length");
        stepSizeText = new JTextField(String.valueOf(STEP_SIZE));
        particlesText = new JTextField();

        startButton = new JButton("Start Simulation");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentSimulationType = -1;
                int currentCPULevel = -1;
                int currentSolver = -1;

                // --- Select the correct simulationInstance ---
                switch (simulationTypeDropdown.getSelectedItem().toString()) {
                    case ("Rocket Simulation"):
                        currentSimulationType = ROCKET_SIMULATION;
                        break;
                    case ("Pendulum Simulation"):
                        currentSimulationType = PENDULUM_SIMULATION;
                        break;
                    case ("Numerical Simulation"):
                        currentSimulationType = NUMERICAL_SIMULATION;
                        break;
                    case ("Particles Simulation"):
                        currentSimulationType = PARTICLES_SIMULATION;
                        break;
                    case ("Solar System Simulation"):
                        currentSimulationType = SOLAR_SYSTEM_SIMULATION;
                        break;
                    default:
                        currentSimulationType = SOLAR_SYSTEM_SIMULATION;
                        break;
                }

                // --- Select the correct CPU type ---
                switch (cpuLevelDropdown.getSelectedItem().toString()) {
                    case ("Min CPU"):
                        currentCPULevel = MIN_CPU;
                        break;
                    case ("Max CPU"):
                        currentCPULevel = MAX_CPU;
                        break;
                    default:
                        currentCPULevel = MIN_CPU;
                        break;
                }

                // --- Select the correct solver ---
                switch (solverDropdown.getSelectedItem().toString()) {
                    case ("Euler"):
                        currentSolver = EULER_SOLVER;
                        break;
                    case ("Runge Kutta"):
                        currentSolver = RUNGE_KUTTA_SOLVER;
                        break;
                    case ("Verlet (VEL)"):
                        currentSolver = VERLET_VEL_SOLVER;
                        break;
                    case ("Verlet (STD)"):
                        currentSolver = VERLET_STD_SOLVER;
                        break;
                    default:
                        currentSolver = EULER_SOLVER;
                        break;
                }

                // --- Select the step size ---
                STEP_SIZE = Double.valueOf(stepSizeText.getText());
                System.out.println("Step size: " + STEP_SIZE);

                if (consoleDebugCheckBox.isSelected()) {
                    DEBUG = true;
                }

                if (printLogCheckBox.isSelected()) {
                    REPORT = true;
                }

                if (!particlesText.getText().equals("")) {
                    PARTICLES = Integer.valueOf(particlesText.getText());
                }

                // TRAJECTORY_LENGTH = trajectoryLengthSlider.getValue();
                startSimulation();
                frame.dispose();
            }
        });

        titleLabel.setBounds(10, 10, 300, 30);
        frame.getContentPane().add(titleLabel);
        controlPanel.setBounds(10, 50, FRAME_WIDTH - 37, FRAME_HEIGHT - 97);

        frame.add(titleLabel);
        frame.add(controlPanel);


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

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    public abstract void startSimulation();
}

