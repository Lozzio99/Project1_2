package group17.Graphics.Assist;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

public class ErrorWindow extends JPanel {
    public ErrorWindow() {
        this.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        this.init();
    }

    private void init() {
        JTable jTable = new JTable();
        DefaultTableModel csv_model = new DefaultTableModel();
        File csv = new File("trajectory.csv");
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(csv));
            CSVParser cp = CSVFormat.DEFAULT.parse(isr);
            boolean f = true;
            for (CSVRecord c : cp) {
                if (f) {
                    f = false;
                    csv_model.addColumn(c.get(0));
                    csv_model.addColumn(c.get(1));
                    csv_model.addColumn(c.get(2));
                    csv_model.addColumn(c.get(3));
                } else {
                    Vector<String> row = new Vector<>();
                    row.add(c.get(0));
                    row.add(c.get(1));
                    row.add(c.get(2));
                    row.add(c.get(3));
                    csv_model.addRow(row);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        jTable.setModel(csv_model);
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        jTable.setDragEnabled(true);
        jTable.setDropMode(DropMode.ON_OR_INSERT);
        jTable.setSize(new Dimension(1000, 700));
        JScrollPane pane = new JScrollPane();
        pane.getViewport().add(jTable);
        this.add(pane, BorderLayout.CENTER);
    }
}
