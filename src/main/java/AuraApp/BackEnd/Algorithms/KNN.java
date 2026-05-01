package AuraApp.BackEnd.Algorithms;


import AuraApp.BackEnd.Matrix.RowWithLabel;
import AuraApp.BackEnd.Matrix.TableWithLabels;
import AuraApp.BackEnd.Metrics.Distance;

import java.util.*;
import java.util.stream.IntStream;

public class KNN implements Algorithm<TableWithLabels, List<Double>, Integer> {

    private TableWithLabels dataAnalysed;
    private Distance distance;

    public KNN(Distance distance) {
        this.distance = distance;
    }

    @Override
    public void train(TableWithLabels data) {
        this.dataAnalysed = data;
    }

    @Override
    public Integer estimate(List<Double> data) {
        if (dataAnalysed == null) throw new IllegalStateException("Modelo no entrenado");

        Optional<RowWithLabel> closestRow = IntStream.range(0, dataAnalysed.getRowCount())
                .mapToObj(i -> dataAnalysed.getRowAt(i))
                .min(Comparator.comparing(v -> computeDistance(v.getData(), data)));

        if (closestRow.isPresent()) {
            return dataAnalysed.getLabelAsInteger(closestRow.get().getLabel());
        }

        return -1;
    }

    private Double computeDistance(List<Double> obj1, List<Double> obj2) {
        return distance.calculateDistance(obj1, obj2);
    }
}