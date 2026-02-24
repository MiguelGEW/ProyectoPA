package P.KNNandCSV;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSV {

    private static final String SEPARATOR = ",";

    public Table readTable(String filePath) throws IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String headerLine = reader.readLine();
            if (headerLine == null) {
                throw new IllegalArgumentException("CSV file is empty");
            }

            List<String> header = parseHeader(headerLine);
            Table table = new Table(header);

            String line;
            while ((line = reader.readLine()) != null) {
                List<Double> values = parseValues(line);
                table.addRow(new Row(values));
            }

            return table;
        }
    }

    public TableWithLabels readTableWithLabels(String filePath) throws IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String headerLine = reader.readLine();
            if (headerLine == null) {
                throw new IllegalArgumentException("CSV file is empty");
            }

            List<String> header = parseHeader(headerLine);
            TableWithLabels table = new TableWithLabels(header);

            String line;
            while ((line = reader.readLine()) != null) {

                String[] tokens = line.split(SEPARATOR);

                List<Double> values = new ArrayList<>();
                for (int i = 0; i < tokens.length - 1; i++) {
                    values.add(Double.parseDouble(tokens[i]));
                }

                String label = tokens[tokens.length - 1];

                table.addRow(new RowWithLabel(values, label));
            }

            return table;
        }
    }

    private List<String> parseHeader(String line) {
        String[] tokens = line.split(SEPARATOR);
        List<String> header = new ArrayList<>();
        for (String token : tokens) {
            header.add(token.strip());
        }
        return header;
    }

    private List<Double> parseValues(String line) {
        String[] tokens = line.split(SEPARATOR);
        List<Double> values = new ArrayList<>();
        for (String token : tokens) {
            values.add(Double.parseDouble(token.strip()));
        }
        return values;
    }
}
