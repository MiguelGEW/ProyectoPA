package CSV;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Row {

    private final List<Double> data;

    public Row(List<Double> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        this.data = new ArrayList<>(data);
    }

    public List<Double> getData() {
        return Collections.unmodifiableList(data);
    }
}
