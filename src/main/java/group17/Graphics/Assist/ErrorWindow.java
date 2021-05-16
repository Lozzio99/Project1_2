package group17.Graphics.Assist;

import au.com.bytecode.opencsv.CSVWriter;
import group17.Interfaces.Vector3dInterface;
import group17.Math.Utils.Vector3D;
import group17.System.Bodies.CelestialBody;
import group17.System.ErrorData;
import group17.System.ErrorReport;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.*;

import static group17.Config.ORIGINAL_DATA;
import static group17.Config.REPORT;
import static group17.Main.simulation;

public class ErrorWindow extends JPanel {

    private final JTable originalDataTable = new JTable();
    private final JScrollPane dataTableScrollPane = new JScrollPane();
    private final String dir = "trajectoryData/";
    private DefaultTableModel csv_model;
    private JComboBox<CelestialBody> planetBox;

    public ErrorWindow() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40), "Horizon Original Data");
        titledBorder.setTitlePosition(TitledBorder.TOP);
        this.setBorder(titledBorder);
        this.setLayout(new GridLayout(2, 1));
        this.parseOriginal();
    }

    @Deprecated(forRemoval = true)
    /* Making csv from txt file  + creating error data array */
    private void parseOriginal() {
        FileReader fr = null;
        FileWriter fw = null;
        try {
            File nasaFile = new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource(dir + "NASA_HORIZON_MONTHS.txt")).getFile()),
                    csvFile = new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource(dir + "ORIGINAL_MONTHS.csv")).getFile());
            fr = new FileReader(nasaFile);
            Scanner scan = new Scanner(fr);
            fw = new FileWriter(csvFile);
            CSVWriter csvWriter = new CSVWriter(fw, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
            List<String[]> all = new ArrayList<>();
            String[] columns = new String[]{"MONTH", "PLANET", "PX", "PY", "PZ", "VX", "VY", "VZ"};
            String[] planets = new String[]{"SUN", "MERCURY", "VENUS", "EARTH", "MOON", "MARS", "JUPITER", "SATURN", "TITAN", "URANUS", "NEPTUNE"};
            all.add(columns);
            int monthIndex = 0;
            while (scan.hasNextLine()) {
                String[][] monthRecord = new String[11][8]; //11 planets, 8 data values
                List<Vector3dInterface> pos = new ArrayList<>(11), vel = new ArrayList<>(11);
                for (int i = 0; i < 11; i++) {
                    if (i == 0) monthRecord[i][0] = scan.nextLine(); //add the month label
                    else monthRecord[i][0] = "*";
                    monthRecord[i][1] = planets[i];
                    String[] vectorPos = scan.nextLine().split(" ");
                    if (vectorPos.length > 3) vectorPos = removeSpaces(vectorPos);
                    pos.add(parseVector(vectorPos));
                    monthRecord[i][2] = vectorPos[0];
                    monthRecord[i][3] = vectorPos[1];
                    monthRecord[i][4] = vectorPos[2];
                }
                String whiteLine = scan.nextLine();
                for (int i = 0; i < 11; i++) {
                    String[] vectorVel = scan.nextLine().split(" ");
                    if (vectorVel.length > 3) vectorVel = removeSpaces(vectorVel);
                    vel.add(parseVector(vectorVel));
                    monthRecord[i][5] = vectorVel[0];
                    monthRecord[i][6] = vectorVel[1];
                    monthRecord[i][7] = vectorVel[2];
                }
                if (scan.hasNextLine()) whiteLine = scan.nextLine();
                ORIGINAL_DATA[monthIndex] = new ErrorData().setData(pos, vel);
                monthIndex++;
                all.addAll(Arrays.asList(monthRecord));
            }
            csvWriter.writeAll(all);
            fw.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Contract("_ -> new")
    private @NotNull Vector3dInterface parseVector(final String @NotNull [] vectorVel) {
        return new Vector3D(Double.parseDouble(vectorVel[0]), Double.parseDouble(vectorVel[1]), Double.parseDouble(vectorVel[2]));
    }

    public void makeTable() {
        originalDataTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        csv_model = new DefaultTableModel();
        File csv = new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource("trajectoryData/ORIGINAL_MONTHS.csv")).getFile());
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(csv));
            CSVParser cp = CSVFormat.DEFAULT.parse(isr);
            boolean f = true;
            for (CSVRecord c : cp) {
                if (f) {
                    f = false; //first line
                    csv_model.addColumn(c.get(0));
                    csv_model.addColumn(c.get(1));
                    csv_model.addColumn(c.get(2));
                    csv_model.addColumn(c.get(3));
                    csv_model.addColumn(c.get(4));
                    csv_model.addColumn(c.get(5));
                    csv_model.addColumn(c.get(6));
                    csv_model.addColumn(c.get(7));
                } else {
                    Vector<String> row = new Vector<>();
                    row.add(c.get(0));
                    row.add(c.get(1));
                    row.add(c.get(2));
                    row.add(c.get(3));
                    row.add(c.get(4));
                    row.add(c.get(5));
                    row.add(c.get(6));
                    row.add(c.get(7));
                    csv_model.addRow(row);
                }
            }
        } catch (Exception e) {
            if (REPORT) simulation.getReporter().report(Thread.currentThread(), e);
        }
        originalDataTable.setModel(csv_model);
        originalDataTable.setDragEnabled(true);
        originalDataTable.setDropMode(DropMode.ON_OR_INSERT);
        dataTableScrollPane.getViewport().add(originalDataTable);
        this.add(dataTableScrollPane, 0);
        this.initButtons();
    }

    @Contract(pure = true)
    private String[] removeSpaces(String @NotNull [] vec) {
        String[] t = new String[3];
        int k = 0;
        for (String s : vec) {
            if (!s.equals("") && !s.equals(" ") && !s.equals("  ")) {
                t[k] = s;
                k++;
            }
        }
        return t;
    }


    private void initButtons() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        this.planetBox = new JComboBox<>();
        for (CelestialBody c : simulation.getSystem().getCelestialBodies()) {
            if (!c.toString().equals("ROCKET")) this.planetBox.addItem(c);
        }
        this.planetBox.addActionListener(e -> {
            CelestialBody selectedItem = (CelestialBody) this.planetBox.getSelectedItem();
            int planetBoxSelectedIndex = this.planetBox.getSelectedIndex();
            int indexInCsv = ErrorReport.monthIndex + 2;
            this.originalDataTable.setAutoscrolls(true);
            this.originalDataTable.setRowSelectionInterval(
                    (indexInCsv * 11) +
                            planetBoxSelectedIndex,
                    (indexInCsv * 11) +
                            planetBoxSelectedIndex);
            originalDataTable.scrollRectToVisible(
                    new Rectangle(originalDataTable.getCellRect(((indexInCsv * 11) +
                            planetBoxSelectedIndex) + 3, 0, true)));
        });
        bottomPanel.add(this.planetBox, BorderLayout.NORTH);
        this.add(bottomPanel);
    }

}
