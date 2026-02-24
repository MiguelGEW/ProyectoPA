package P.KNNandCSV;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Table {
    //getColumnAt faltaaaa
    private final List<String> header;
    private final List<Row> rows;

    public Table(List<String> header) {
        if (header == null || header.isEmpty()) {
            throw new IllegalArgumentException("Header cannot be null or empty");
        }

        this.header = new ArrayList<>(header);
        this.rows = new ArrayList<>();
    }

    public void addRow(Row row) {
        if (row == null) {
            throw new IllegalArgumentException("Row cannot be null");
        }
        rows.add(row);
    }

    public Row getRowAt(int rowNumber) {
        return rows.get(rowNumber);
    }

    public int getNumberOfRows() {
        return rows.size();
    }

    public int getNumberOfColumns() {
        return header.size();
    }

    public List<String> getHeader() {
        return Collections.unmodifiableList(header);
    }

    public List<Row> getRows() {
        return Collections.unmodifiableList(rows);
    }
}
