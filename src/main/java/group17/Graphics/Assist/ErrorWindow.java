package group17.Graphics.Assist;

import au.com.bytecode.opencsv.CSVWriter;
import group17.System.ErrorData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.*;

public class ErrorWindow extends JPanel {

    private ErrorData[] originalData;
    private final String dir = "trajectoryData/";
    private DefaultTableModel tableModel;

    public ErrorWindow() {
        this.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        this.setLayout(new GridLayout(1, 1));
        this.parseOriginal();
        this.init();
    }

    @Deprecated(forRemoval = true)
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
            while (scan.hasNextLine()) {
                String[][] monthRecord = new String[11][8]; //10 planets, 8 data values
                for (int i = 0; i < 11; i++) {
                    if (i == 0) monthRecord[i][0] = scan.nextLine();
                    else monthRecord[i][0] = "*";
                    monthRecord[i][1] = planets[i];
                    String[] vectorPos = scan.nextLine().split(" ");
                    if (vectorPos.length > 3) vectorPos = removeSpaces(vectorPos);
                    monthRecord[i][2] = vectorPos[0];
                    monthRecord[i][3] = vectorPos[1];
                    monthRecord[i][4] = vectorPos[2];
                }
                String whiteLine = scan.nextLine();
                for (int i = 0; i < 11; i++) {
                    String[] vectorVel = scan.nextLine().split(" ");
                    if (vectorVel.length > 3) vectorVel = removeSpaces(vectorVel);
                    monthRecord[i][5] = vectorVel[0];
                    monthRecord[i][6] = vectorVel[1];
                    monthRecord[i][7] = vectorVel[2];
                }
                if (scan.hasNextLine())
                    whiteLine = scan.nextLine();
                all.addAll(Arrays.asList(monthRecord));
            }
            csvWriter.writeAll(all);
            fw.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("done");
        }
    }

    private void init() {
        JTable jTable = new JTable();
        jTable.setPreferredSize(new Dimension(700, 800));
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        DefaultTableModel csv_model = new DefaultTableModel();
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
            System.err.println(e.getMessage());
        }


        jTable.setModel(csv_model);
        jTable.setDragEnabled(true);
        jTable.setDropMode(DropMode.ON_OR_INSERT);
        JScrollPane pane = new JScrollPane();
        jTable.setPreferredSize(new Dimension(800, 700));
        pane.getViewport().add(jTable);
        this.add(pane, 0);
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
}
