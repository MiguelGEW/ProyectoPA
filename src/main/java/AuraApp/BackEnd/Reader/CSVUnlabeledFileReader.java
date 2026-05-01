package AuraApp.BackEnd.Reader;

import AuraApp.BackEnd.Matrix.Row;
import AuraApp.BackEnd.Matrix.Table;
import java.util.ArrayList;
import java.util.List;

public class CSVUnlabeledFileReader extends FileReader<Table> {

    private static final String SEPARATOR = ",";

    public CSVUnlabeledFileReader(String source) {
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
        this.table = new Table(splitAndClean(headers));
    }

    @Override
    void processData(String data) {
        List<String> tokens = splitAndClean(data);
        List<Double> values = new ArrayList<>();

        for (String token : tokens) values.add(Double.parseDouble(token));
        this.table.addRow(new Row(values));
    }
}
