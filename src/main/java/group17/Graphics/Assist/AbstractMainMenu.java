package group17.Graphics.Assist;

import org.jetbrains.annotations.Contract;

import javax.swing.*;
import java.awt.*;

import static group17.Utils.Config.*;

/**
 * The type Main menu.
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractMainMenu {
    /**
     * The constant SIMULATION_TYPES.
     */
// Variables
    static final String[] SIMULATION_TYPES = {"Rocket Simulation", "Pendulum Simulation", "Numerical Simulation", "Particles Simulation", "Solar System Simulation"};
    /**
     * The Cpu levels.
     */
    static final String[] CPU_LEVELS = {"Min CPU", "Max CPU"};
    /**
     * The Solvers.
     */
    static final String[] SOLVERS = {"Euler", "Runge Kutta", "Verlet (VEL)", "Verlet (STD)", "Midpoint", "Lazy Runge Kutta"};
    static private final int FRAME_WIDTH = 1000;
    static private final int FRAME_HEIGHT = 600;
    /**
     * The Current solver.
     */
    static protected int currentSolver = DEFAULT_SOLVER;
    /**
     * The Current simulation type.
     */
    static protected int currentSimulationType = SIMULATION_LEVEL;
    /**
     * The Current cpu level.
     */
    static protected int currentCPULevel = CPU_LEVEL;
    /**
     * The Frame.
     */
    static JFrame frame;
    /**
     * The Title label.
     */
    JLabel titleLabel;


    /**
     * Constructor
     */
    @Contract(pure = true)
    public AbstractMainMenu() {
        //this.setFrame();
    }


    /**
     * Sets frame.
     */
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

    /**
     * Config frame component.
     *
     * @param controlPanel the control panel
     * @return the component
     */
    public Component configFrame(Container controlPanel) {
        //frame.setLocationRelativeTo(null);

        controlPanel.setPreferredSize(new Dimension(700, 300));
        GroupLayout groupLayout = new GroupLayout(controlPanel);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        controlPanel.setLayout(groupLayout);
        controlPanel.setBackground(new Color(150, 150, 200));

        JLabel simulationTypeLabel, cpuLevelLabel, solverLabel;


        simulationTypeLabel = new JLabel("Simulation Type");
        cpuLevelLabel = new JLabel("CPU Level");
        solverLabel = new JLabel("Solver");


        JComboBox simulationTypeDropdown, cpuLevelDropdown, solverDropdown;

        simulationTypeDropdown = new JComboBox<>(SIMULATION_TYPES);
        simulationTypeDropdown.setSelectedIndex(SIMULATION_LEVEL);
        cpuLevelDropdown = new JComboBox<>(CPU_LEVELS);
        cpuLevelDropdown.setSelectedIndex(CPU_LEVEL == MIN_CPU ? 0 : 1);
        solverDropdown = new JComboBox<>(SOLVERS);
        solverDropdown.setSelectedIndex(
                switch (DEFAULT_SOLVER) {
                    case RUNGE_KUTTA_SOLVER -> 1;
                    case VERLET_VEL_SOLVER -> 2;
                    case VERLET_STD_SOLVER -> 3;
                    case MIDPOINT_SOLVER -> 4;
                    default -> 0;
                }
        );


        JSlider trajectoryLengthSlider = new JSlider();
        trajectoryLengthSlider.setMinimum(0);
        trajectoryLengthSlider.setMaximum(10000);
        trajectoryLengthSlider.setToolTipText("Length");


        JButton startButton = new JButton("Start Simulation");

        startButton.addActionListener(e -> {
            // --- Select the correct simulation ---
            currentSimulationType = switch (simulationTypeDropdown.getSelectedItem().toString()) {
                case ("Rocket Simulation") -> ROCKET_SIMULATION;
                case ("Pendulum Simulation") -> PENDULUM_SIMULATION;
                case ("Numerical Simulation") -> NUMERICAL_SIMULATION;
                case ("Particles Simulation") -> PARTICLES_SIMULATION;
                case ("Solar System Simulation") -> SOLAR_SYSTEM_SIMULATION;
                default -> SOLAR_SYSTEM_SIMULATION;
            };
            // --- Select the correct CPU type ---
            currentCPULevel = switch (cpuLevelDropdown.getSelectedItem().toString()) {
                case ("Max CPU") -> MAX_CPU;
                default -> MIN_CPU;
            };

            // --- Select the correct solver ---
            currentSolver = switch (solverDropdown.getSelectedItem().toString()) {
                case ("Runge Kutta") -> RUNGE_KUTTA_SOLVER;
                case ("Verlet (VEL)") -> VERLET_VEL_SOLVER;
                case ("Verlet (STD)") -> VERLET_STD_SOLVER;
                case ("Midpoint") -> MIDPOINT_SOLVER;
                case ("Lazy Runge Kutta") -> LAZY_RUNGE;
                default -> EULER_SOLVER;
            };

            startSimulation();

        });


        controlPanel.setBounds(10, 50, FRAME_WIDTH - 67, FRAME_HEIGHT - 97);


        // --- Horizontal Groups ---
        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(simulationTypeLabel)
                                .addGap(400)
                                .addComponent(cpuLevelLabel)
                                .addGap(400)
                                .addComponent(solverLabel)
                                .addGap(400))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(simulationTypeDropdown)
                                .addGap(200)
                                .addComponent(cpuLevelDropdown)
                                .addGap(200)
                                .addComponent(solverDropdown)
                                .addGap(300)
                                .addComponent(startButton)
                                .addGap(300))
                        .addContainerGap()
        );

        // --- Vertical Groups ---
        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(simulationTypeLabel)
                                .addGap(20)
                                .addComponent(simulationTypeDropdown))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cpuLevelLabel)
                                .addGap(20)
                                .addComponent(cpuLevelDropdown))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(solverLabel)
                                .addGap(20)
                                .addComponent(solverDropdown)
                                .addGap(300))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(startButton))
        );
        return controlPanel;
    }

    /**
     * Start simulation.
     */
    public abstract void startSimulation();
}

