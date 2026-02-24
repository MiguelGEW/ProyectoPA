package P.KNNandCSV;

import java.util.LinkedList;
import java.util.List;

public class Table {
    private List<String> headers;
    private List<Row> rows;

    public Row getRowAt(int num){
        return new Row();
    }

    public List<Double> getColumnAt(int num){
        return new LinkedList<>();
    }
}
