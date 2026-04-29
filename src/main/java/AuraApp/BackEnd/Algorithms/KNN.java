package AuraApp.BackEnd.Algorithms;

import AuraApp.BackEnd.Matrix.Row;
import AuraApp.BackEnd.Matrix.RowWithLabel;
import AuraApp.BackEnd.Matrix.TableWithLabels;
import AuraApp.BackEnd.Metrics.Distance;

import java.util.*;

// 1. Implementamos la interfaz genérica
public class KNN implements Algorithm<TableWithLabels, List<Double>, Integer> {

    private TableWithLabels dataAnalysed;
    Distance distance; // TODO: El atributo no está declarado como privado y esto puede romper la encapsulación

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

        // TODO: El cast a RowWithLabels funciona porque la tabla es etiquetada
        // TODO: Pero deja una dependencia implícita, sería más limpio iterar sobre un tipo etiquetado o encapsular esa lógica en TableWithLabels
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