package AuraApp.BackEnd.Reader;

import AuraApp.BackEnd.Matrix.RowWithLabel;
import AuraApp.BackEnd.Matrix.TableWithLabels;
import java.util.ArrayList;
import java.util.List;

public class CSVLabeledFileReader extends FileReader<TableWithLabels> {

    private static final String SEPARATOR = ",";

    public CSVLabeledFileReader(String source) {
        super(source);
    }

    private List<String> splitAndClean(String line) {
        String[] tokens = line.split(SEPARATOR);
        List<String> cleanTokens = new ArrayList<>();

        for (String t : tokens) cleanTokens.add(t.strip());
        return cleanTokens;
    }

    @Override
    void processHeaders(String headers) {
        List<String> tokens = splitAndClean(headers);
        List<String> headerList = new ArrayList<>(tokens.subList(0, tokens.size() - 1));
        this.table = new TableWithLabels(headerList);
    }

    @Override
    void processData(String data) {
        List<String> tokens = splitAndClean(data);
        List<Double> values = new ArrayList<>();
        int lastIndex = tokens.size() - 1;

        for (int i = 0; i < lastIndex; i++) {
            values.add(Double.parseDouble(tokens.get(i)));
        }
        String label = tokens.get(lastIndex);

        this.table.addRow(new RowWithLabel(values, label));
    }
}
