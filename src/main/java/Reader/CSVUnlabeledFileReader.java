package Reader;

import Matrix.Row;
import Matrix.Table;
import java.util.ArrayList;
import java.util.List;

public class CSVUnlabeledFileReader extends FileReader<Table> {

    private static final String SEPARATOR = ",";

    public CSVUnlabeledFileReader(String source) {
        super(source);
    }

    @Override
    void processHeaders(String headers) {
        String[] tokens = headers.split(SEPARATOR);
        List<String> headerList = new ArrayList<>();

        for (String token : tokens) {
            headerList.add(token.strip());
        }

        this.table = new Table(headerList);
    }

    @Override
    void processData(String data) {
        String[] tokens = data.split(SEPARATOR);
        List<Double> values = new ArrayList<>();

        for (String token : tokens) {
            values.add(Double.parseDouble(token.strip()));
        }

        this.table.addRow(new Row(values));
    }
}
