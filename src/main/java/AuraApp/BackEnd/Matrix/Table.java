package AuraApp.BackEnd.Matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Table {

    private final List<String> header;
    private final List<Row> rows;


    public Table(List<String> header) {
        if (header == null || header.isEmpty()) {
            throw new IllegalArgumentException("Header cannot be null or empty");
        }

        this.header = new ArrayList<>(header);
        this.rows = new ArrayList<>();
    }


    public Table(List<String> header, List<Row> rows) {
        if (header == null || header.isEmpty()) {
            throw new IllegalArgumentException("Header cannot be null or empty");
        }
        if (rows == null) {
            throw new IllegalArgumentException("Rows cannot be null");
        }

        this.header = new ArrayList<>(header);
        this.rows = new ArrayList<>(rows);
    }

    public void addRow(Row row) {
        if (row == null) {
            throw new IllegalArgumentException("Row cannot be null");
        }
        rows.add(row);
    }

    public Row getRowAt(int rowNumber) {
        if (rowNumber < 0 || rowNumber >= rows.size()) {
            throw new IndexOutOfBoundsException("Invalid row index: " + rowNumber);
        }
        return rows.get(rowNumber);
    }

    public List<Double> getColumnAt(int columnNumber) {
        if (columnNumber < 0 || columnNumber >= header.size()) {
            throw new IndexOutOfBoundsException("Invalid column index: " + columnNumber);
        }

        List<Double> column = new ArrayList<>();
        for (Row row : rows) {
            column.add(row.getData().get(columnNumber));
        }
        return column;
    }


    public int getRowCount() {
        return rows.size();
    }

    public List<String> getHeaders() {
        // Devuelve una vista inmodificable (Buena práctica SOLID)
        return Collections.unmodifiableList(header);
    }

    public List<Row> getRows() {
        // Devuelve una vista inmodificable
        return Collections.unmodifiableList(rows);
    }
}