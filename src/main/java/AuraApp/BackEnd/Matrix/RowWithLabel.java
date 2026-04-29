package AuraApp.BackEnd.Matrix;

import java.util.List;

public class RowWithLabel extends Row {

    private final String label;

    public RowWithLabel(List<Double> data, String label) {
        super(data);

        if (label == null || label.isBlank()) {
            throw new IllegalArgumentException("Label cannot be null or blank");
        }

        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
