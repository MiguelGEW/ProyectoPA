package CSV;

import Matrix.Row;
import Matrix.RowWithLabel;
import Matrix.Table;
import Matrix.TableWithLabels;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class CSV {

    private static final String SEPARATOR = ",";

    public Table readTable(String fileName) throws IOException {

        // Obtenemos la ruta real a partir del nombre del fichero
        String filePath = getFilePath(fileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                throw new IllegalArgumentException("CSV file is empty");
            }

            List<String> header = parseHeader(headerLine, false);
            Table table = new Table(header);

            String line;
            while ((line = reader.readLine()) != null) {
                List<Double> values = parseValues(line);
                table.addRow(new Row(values));
            }

            return table;
        }
    }

    public TableWithLabels readTableWithLabels(String fileName) throws IOException {

        // Obtenemos la ruta real a partir del nombre del fichero
        String filePath = getFilePath(fileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                throw new IllegalArgumentException("CSV file is empty");
            }

            List<String> header = parseHeader(headerLine, true);
            TableWithLabels table = new TableWithLabels(header);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(SEPARATOR);
                List<Double> values = new ArrayList<>();

                for (int i = 0; i < tokens.length - 1; i++) {
                    values.add(Double.parseDouble(tokens[i].strip()));
                }

                String label = tokens[tokens.length - 1].strip();
                table.addRow(new RowWithLabel(values, label));
            }

            return table;
        }
    }

    private String getFilePath(String fileName) {
        try {
            URL resource = getClass().getClassLoader().getResource(fileName);
            if (resource == null) {
                throw new IllegalArgumentException("File not found in resources: " + fileName);
            }
            return resource.toURI().getPath();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Error resolving file path for: " + fileName, e);
        }
    }

    private List<String> parseHeader(String line, boolean hasLabel) {
        String[] tokens = line.split(SEPARATOR);
        List<String> header = new ArrayList<>();

        int limit = hasLabel ? tokens.length - 1 : tokens.length;

        for (int i = 0; i < limit; i++) {
            header.add(tokens[i].strip());
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