package group17.System;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ErrorExportCSV {

    public static void main(String[] args) {
        ErrorExportCSV error = new ErrorExportCSV();
    }

    public ErrorExportCSV() {
        try {
            PrintWriter pw = new PrintWriter(new File("out/errorData/test.csv"));
            StringBuilder sb = new StringBuilder();

            sb.append("Test");
            sb.append("Test2");
            sb.append("\n");

            sb.append("Test");
            sb.append("Test2");
            sb.append("\n");

            sb.append("Test");
            sb.append("Test2");
            sb.append("\n");

            pw.write(sb.toString());
            pw.close();
            System.out.println("Done");
        }
        catch (Exception e) {


        }
    }
}
