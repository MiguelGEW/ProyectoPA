package Algorithms;

import Matrix.Row;
import Matrix.RowWithLabel;
import Matrix.TableWithLabels;
import Metrics.Distance;

import java.util.*;

// 1. Implementamos la interfaz genérica
public class KNN implements Algorithm<TableWithLabels, List<Double>, Integer> {

    private TableWithLabels dataAnalysed;
    Distance distance;

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

        Integer chosenClass = -1;

        Optional<Row> row = dataAnalysed.getRows().stream()
                .min(Comparator.comparing(v -> computeDistance(v.getData(),data)));


        if (row.isPresent()) {
            //Aquí hacemos un cast porque sabemos que en TableWithLabels solo trabajamos con RowWithLabel
            RowWithLabel chosenRow = (RowWithLabel) row.get();
            chosenClass = dataAnalysed.getLabelAsInteger(chosenRow.getLabel());
        }

        return chosenClass;
    }

    private Double computeDistance(List<Double> obj1, List<Double> obj2) {

        return distance.calculateDistance(obj1, obj2);
    }
}