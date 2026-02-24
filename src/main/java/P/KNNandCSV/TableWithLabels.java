package P.KNNandCSV;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableWithLabels extends Table {
    //getRowAt faltaaa
    private final Map<String, Integer> labelsToIndex;

    public TableWithLabels(List<String> header) {
        super(header);
        this.labelsToIndex = new HashMap<>();
    }

    public void addRow(RowWithLabel row) {
        super.addRow(row);

        String label = row.getLabel();

        if (!labelsToIndex.containsKey(label)) {
            labelsToIndex.put(label, labelsToIndex.size());
        }
    }

    public Integer getLabelAsInteger(String label) {
        return labelsToIndex.get(label);
    }

    public String getLabelFromIndex(Integer index) {
        for (Map.Entry<String, Integer> entry : labelsToIndex.entrySet()) {
            if (entry.getValue().equals(index)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
