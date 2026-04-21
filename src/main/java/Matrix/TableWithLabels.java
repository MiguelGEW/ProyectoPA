package Matrix;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableWithLabels extends Table {

    private final Map<String, Integer> labelsToIndex;



    public TableWithLabels(List<String> header) {
        super(header);
        this.labelsToIndex = new HashMap<>();
    }

    public TableWithLabels(List<String> header, List<RowWithLabel> rows) {
        super(header);
        this.labelsToIndex = new HashMap<>();

        if (rows == null) {
            throw new IllegalArgumentException("Rows cannot be null");
        }

        for (RowWithLabel row : rows) {
            this.addRow(row);
        }
    }

    @Override
    public RowWithLabel getRowAt(int rowNumber) {

        return (RowWithLabel) super.getRowAt(rowNumber);
    }

    public void addRow(RowWithLabel row) {
        if (row == null) {
            throw new IllegalArgumentException("Row cannot be null");
        }

        super.addRow(row);

        String label = row.getLabel();
        if (!labelsToIndex.containsKey(label)) {
            labelsToIndex.put(label, labelsToIndex.size());
        }
    }

    public Integer getLabelAsInteger(String label) {
        return labelsToIndex.get(label);
    }

}